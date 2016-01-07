package com.plocc.dc.match;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


/**
 * Match的管理类
 * @author wuxin
 *
 */
public  class MatchManager implements ApplicationContextAware,InitializingBean {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MatchManager.class);

	private Set<Match> matchSet = new CopyOnWriteArraySet<Match>();
	private ApplicationContext applicationContext;
	private Match defaultMatch;
	public void setDefaultMatch(Match defaultMatch) {
		this.defaultMatch = defaultMatch;
	}

	/**
	 * 注册Match
	 * @param match
	 * @return
	 */
	public  boolean registerMatch(Match match) {
		if (match == null) {
			return false;
		}
		
		if (match instanceof ConfigedMatch){
			// 如果是已配置的MATCH，则从match配置文件读取它能够处理的列表
			((ConfigedMatch) match).loadConfig();
		}
		
		return matchSet.add(match);
	}
	
	
	/**
	 * 从Spring容量中发现非prototype类型的Match
	 */
	private  void registerMatch(){
		Map<String, Match> matchMap = applicationContext.getBeansOfType(Match.class);
		if (matchMap != null && !matchMap.isEmpty()){
			Iterator<String> keySet = matchMap.keySet().iterator();
			while (keySet.hasNext()) {
				String key = (String) keySet.next();
				registerMatch(matchMap.get(key));
			}
		}
	}

	/**
	 * 返回第一个匹配的Match
	 * @param json
	 * @return
	 */
	public List<Match> findMatch(String businessPattern){
		
		// 从已注册的match中找到一个匹配的Match，如果找不到返回默认值
		List<Match> result = new ArrayList<Match>();
		Iterator<Match> iterator = matchSet.iterator();
		while (iterator.hasNext()) {
			Match match = (Match) iterator.next();
			if (match.isMatch(businessPattern)){
				result.add(match);
				if(logger.isDebugEnabled()){
					logger.debug("找到一个匹配的MATCH:" + match.getName());
				}
			}
		}
		
		if (result.size() == 0){
			result.add(defaultMatch);
		}
		
		return result;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		registerMatch();
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

}
