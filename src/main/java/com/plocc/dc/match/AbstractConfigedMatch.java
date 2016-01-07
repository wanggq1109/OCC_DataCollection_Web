package com.plocc.dc.match;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.PropertiesFactoryBean;


public abstract class AbstractConfigedMatch implements ConfigedMatch, Match {
    private List<Pattern> patterns = new CopyOnWriteArrayList<Pattern>();
    private Set<String> cachedTag = new CopyOnWriteArraySet<String>();
    
    @Autowired
	@Qualifier("matchPropertiesFactory")
	private PropertiesFactoryBean propertiesFactory;
	
	@Override
	public void loadConfig() {
		// 从配置文件中加载Match
		Properties prop = null;

		try {
			prop = propertiesFactory.getObject();
		} catch (IOException e) {
		}
		
		if (prop == null){
			return;
		}
		
		Set<Object> keySet  = prop.keySet();
		Iterator<Object> keyIterator = keySet.iterator();
		while (keyIterator.hasNext()) {
			String key = (String) keyIterator.next();
			String value = (String) prop.getProperty(key);
			if (StringUtils.equalsIgnoreCase(getName(), value)){
				addToPatterns(key);
			}
		}

	}
	
    /**
     * 
     * @param patternStr 
     */
    private void addToPatterns(String patternStr) {
    	String regStr = StringUtils.trimToEmpty(patternStr);
    	if (StringUtils.isEmpty(regStr)){
			return;
		}
		
    	// 处理开头的"*"
    	if (regStr.startsWith("*")){
    		regStr = "." + regStr;
    	}
    	
    	Pattern pattern = Pattern.compile(regStr);
    	patterns.add(pattern);
	}

	@Override
	public boolean isMatch(String businessPattern) {
		if (StringUtils.isBlank(businessPattern)){
			return false;
		}
    	
		if (cachedTag.contains(businessPattern)){
		    // 先从自己的缓存中检查
			return true;
		}else{
			// 从正则表达式中匹配
			Iterator<Pattern> regIterator = patterns.iterator();
			boolean isMatch = false;
			while (regIterator.hasNext() && !isMatch) {
				Pattern pattern = (Pattern) regIterator.next();
				Matcher matcher = pattern.matcher(businessPattern);
				isMatch = matcher.find();
			}
			
			if (isMatch){
				cachedTag.add(businessPattern);
			}
			
			return isMatch;
		}
	}

}
