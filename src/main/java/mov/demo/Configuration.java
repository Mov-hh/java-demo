package mov.demo;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 属性读取类
 * @author mov
 * @since 0.0.1
 */
public class Configuration {
	private static Logger log = LoggerFactory.getLogger(Configuration.class);
	
    private static Properties props = new Properties();;
    
    /** 默认属性文件 */
    private static final String CONFIG_FILE_DEFAULT = "config.default.properties";
    
    /** 各工程自定义配置 */
    private static final String CONFIG_FILE_USER_DEFINED = "config.properties";
    
    /** 不可覆盖的属性文件 */
    private static final String CONFIG_FILE_NO_OVERRIDE = "config.nooverride.properties";

    static {
    	int successLoadedCount = 0;
    	/* 先加载默认配置 */
    	if (loadConfig(CONFIG_FILE_DEFAULT)) {
			++successLoadedCount;
		}
    	
    	/* 再加载用户自定义配置，以覆盖默认 */
    	if (loadConfig(CONFIG_FILE_USER_DEFINED)) {
    		++successLoadedCount;
    	}

    	/* 最后加载不可覆盖的配置，以覆盖所有 */
    	if (loadConfig(CONFIG_FILE_NO_OVERRIDE)) {
    		++successLoadedCount;
    	}
    	
    	if (successLoadedCount == 0) {
    		log.error("all config file load error");
    	}
    }

    /**
     * 获取属性值
     * @param key
     * @return 属性存在时返回对应的值，否则返回""
     */ 
    public static String getCfgValue(String key) {
        return getCfgValue(key, "");
    }
    
    /**
     * 获取属性值
     * @param key
     * @param defaultValue
     * @return 属性存在时返回对应的值，否则返回defaultValue
     */ 
    public static String getCfgValue(String key, String defaultValue) {
        return props.getProperty(key, defaultValue);
    }
    
    /**
     * 获取属性值
     * @param key
     * @return 属性存在且可以转为int时返回对应的值，否则返回0
     */
    public static int getIntCfgValue(String key) {
        return getIntCfgValue(key, 0);
    }

    /**
     * 获取属性值
     * @param key
     * @param defaultValue
     * @return 属性存在且可以转为int时时返回对应的值，否则返回defaultValue
     */
    public static int getIntCfgValue(String key, int defaultValue) {
        String val = getCfgValue(key);
        if (!isEmpty(val)) {
        	try {
        		return Integer.parseInt(val);
        	} catch (NumberFormatException e) {
        		log.warn("error get config. value '{}' is not a valid int for key '{}'"
        				, val, key);
        	}
        }
        
        return defaultValue;
    }
    
    /**
     * 获取属性值
     * @param key
     * @return 属性存在时返回对应的值，否则返回false
     */
    public static boolean getBooleanCfgValue(String key) {
        return getBooleanCfgValue(key, false);
    }
    
    /**
     * 获取属性值
     * @param key
     * @param defaultValue
     * @return 属性存在时返回对应的值，否则返回defaultValue
     */
    public static boolean getBooleanCfgValue(String key, boolean defaultValue) {
        String val = getCfgValue(key);
        if (!isEmpty(val)) {
            return Boolean.parseBoolean(val);
        } else {
            return defaultValue;
        }
    }

    private static boolean loadConfig(String configFile) {
    	boolean success = false;
    	InputStream inputStream = null;
    	try {
    		inputStream = Configuration.class.getClassLoader().getResourceAsStream(configFile);
    		
    		if (inputStream != null) {
    			props.load(inputStream);
    			success = true;
    		} else {
    			log.warn("project config file 'classpath:{}' not found", configFile);
    		}
    	} catch (Throwable e) {
    		log.error("error load config file 'classpath:{}'", configFile, e);
    	} finally {
    		if (inputStream != null) {
	    		try {
	    			inputStream.close();
	            } catch (IOException e) {
	                // do nothing
	            }
    		}
    	}
    	
    	return success;
    }
    
    private static boolean isEmpty(String val) {
		return val == null || val.length() == 0;
	}
}