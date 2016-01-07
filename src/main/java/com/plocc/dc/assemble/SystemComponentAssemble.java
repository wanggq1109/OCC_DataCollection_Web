package com.plocc.dc.assemble;

import com.plocc.dc.consumer.Consumer;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 装配系统组件服务
 *
 * @author wuxin
 */
public class SystemComponentAssemble implements InitializingBean,
        ApplicationContextAware {

    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(SystemComponentAssemble.class);

    private ApplicationContext applicationContext;

    @Autowired
    @Qualifier("sysPropertiesFactory")
    private PropertiesFactoryBean propertiesFactory;

    private boolean isInit = false;
    private final static String JSON_QUEUE_CONSUMER_NUMBER = "json_queue_conumser_num";
    private final static String JSON_QUEUE_CONSUMER_BEANNAME = "json_queue_conumser_beanName";
    private final static String RAWDATA_QUEUE_CONSUMER_NUMBER = "rawdata_queue_conumser_num";
    private final static String RAWDATA_QUEUE_CONSUMER_BEANNAME = "rawdata_queue_conumser_beanName";


    private List<Consumer> threads = new ArrayList<Consumer>();

    /**
     * 初始化
     *
     * @throws AssembleException
     */
    private synchronized void init() throws AssembleException {
        if (isInit) {
            if (logger.isInfoEnabled()) {
                logger.info("系统已初化成功.");
            }
            return;
        }

        Properties prop = null;
        try {
            prop = propertiesFactory.getObject();
        } catch (IOException e) {
            throw new AssembleException("读出系统配置文件出错!");
        }

        // 启动消费线程
        startConsumer(prop);
        isInit = true;

    }

    private void startConsumer(Properties prop) throws AssembleException {
        startRawDataQueueConsumer(prop);
        startJsonQueueConsumer(prop);
    }

    /**
     * 启动一级队列消费者
     *
     * @param prop
     * @throws AssembleException
     */
    private void startRawDataQueueConsumer(Properties prop) throws AssembleException {
        startConsumer(prop, RAWDATA_QUEUE_CONSUMER_NUMBER, RAWDATA_QUEUE_CONSUMER_BEANNAME);
    }

    /**
     * 启动二级队列消费者
     *
     * @param prop
     * @throws AssembleException
     */
    private void startJsonQueueConsumer(Properties prop) throws AssembleException {
        startConsumer(prop, JSON_QUEUE_CONSUMER_NUMBER, JSON_QUEUE_CONSUMER_BEANNAME);

    }

    /**
     * 启动消费者
     *
     * @param prop
     * @param consumerNumPropName
     * @param consumerBeanNamePropName
     * @throws AssembleException
     */
    private void startConsumer(Properties prop, String consumerNumPropName, String consumerBeanNamePropName)
            throws AssembleException {

        int consumerNum = 0;
        try {
            consumerNum = Integer
                    .parseInt(prop.getProperty(consumerNumPropName, String.valueOf(Runtime.getRuntime().availableProcessors())));

            // 启动线程序
            for (int i = 0; i < consumerNum; i++) {
                String beanName = prop.getProperty(consumerBeanNamePropName);
                Consumer consumer = (Consumer) applicationContext.getBean(beanName);
                consumer.setName(beanName + "-" + i);
                consumer.start();
                threads.add(consumer);
            }
        } catch (NumberFormatException e) {
            throw new AssembleException("解析:" + consumerNumPropName + "出错,只能是整数.");
        } catch (Throwable t) {
            logger.error(t);
            throw new AssembleException(t);
        }

    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("开始装配系统组件!");
        }

        init();

        if (logger.isDebugEnabled()) {
            logger.debug("系统组件装配完成!");
        }
    }

    /**
     * 关闭组件
     */
    public synchronized void destory() {

        for (Consumer thread : threads) {
            thread.shutDown();
        }
    }

}