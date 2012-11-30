package com.oilchem.trade.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * config util 配置工具类
 * User: luowei
 * Date: 12-11-8
 * Time: 下午5:54
 */
public class ConfigUtil {
    /**
     * 记录器
     */
    protected static Logger logger = (Logger) LoggerFactory.getLogger(ConfigUtil.class);

    public static String CONFIG_COMMON_JSON = "config_common.json";

    /**
     * ConfigUtil实例
     */
    private static ConfigUtil configUtil = null;

    /**
     * config json string,converted from Map<String,ConfigBean<T>>
     */
    private String configJson = null;

    /**
     * 配置工具类的map
     */
    private Map<String, ConfigBean> configBeanMap = new HashMap<String, ConfigBean>();

    /**
     * 构造方法
     *
     * @param configJson json字符串
     */
    private ConfigUtil(String configJson) {
        this.configJson = configJson;
    }

    /**
     * 拿配置文件路径
     *
     * @return 返回路径
     */
    public static String getConfigFilePath() {
        String path = ConfigUtil.class.getResource("ConfigUtil.class").getPath();
        String path1 = ConfigUtil.class.getResource(CONFIG_COMMON_JSON).getPath();
        String configFilePath = ConfigUtil.class.getResource(CONFIG_COMMON_JSON).getPath().substring(1);
        // 判断系统 linux，windows
        if ("\\".equals(File.separator)) {
            configFilePath = configFilePath.replace("%20", " ");
        } else if ("/".equals(File.separator)) {
            configFilePath = "/" + configFilePath.replace("%20", " ");
        }
        return configFilePath;
    }

    /**
     * get configJson string , sington
     *
     * @return 配置的json字符串
     */
    public static String getConfigJson() {
        return configUtil.configJson;
    }

    /**
     * set configJson string to config file
     *
     * @param configJson 配置的json字符串
     * @return 成功或失败
     */
    public static Boolean setConfigJson(String configJson) {
        String filePath = getConfigFilePath();
        if (configUtil == null) {
            configUtil = initConfigUtil();
        }
        configUtil.configJson = configJson;
        Boolean flag = CommonUtil.writeString2File(filePath, configJson);
        return flag;
    }

    /**
     * get config bean Map<String,ConfigBean<T>> ,such as: <configBean.getKey(),configBean>
     *
     * @return 配置信息map
     */
    public static Map<String, ConfigBean> getConfigMap() {
        if (null == configUtil) {
            initConfigUtil();
        }
        if (configUtil.configBeanMap != null && !configUtil.configBeanMap.isEmpty())
            return configUtil.configBeanMap;

        String configStr = ConfigUtil.getConfigJson();
        if (StringUtils.isNotBlank(configStr)) {
            Gson gson = new Gson();
            Type configBeanType = new TypeToken<Map<String, ConfigBean>>() {
            }.getType();
            configUtil.configBeanMap = gson.fromJson(configStr, configBeanType);
        }

        return configUtil.configBeanMap;
    }

    /**
     * set configBeanMap to config file
     *
     * @param configBeanMap ConfigBean的map
     * @return 成功或失败
     */
    public static Boolean setConfigMap(Map<String, ConfigBean> configBeanMap) {
        if (null == configUtil) {
            initConfigUtil();
        }
        Gson gson = new Gson();
        String configStr = gson.toJson(configBeanMap);
        configUtil.configBeanMap = configBeanMap;
        Boolean flag = ConfigUtil.setConfigJson(configStr);
        return flag;
    }

    /**
     * 得到默认配置
     *
     * @param config
     * @return
     */
    public static String getDefault(Enum config) {
        Map<String, ConfigBean> map = getConfigMap();
        return getConfigMap().get(config.name()).getValue();
    }

    /**
     * init configUtil instance
     *
     * @return 配置实例
     */
    private static synchronized ConfigUtil initConfigUtil() {
        if (configUtil == null) {
            String configFilePath = getConfigFilePath();
            String jsonStr = CommonUtil.readStringFromFile(configFilePath);
            configUtil = new ConfigUtil(jsonStr);
        }
        return configUtil;
    }

    /**
     * destroy configUtil instance
     */
    public static synchronized void destroy() {
        if (configUtil != null) {
            configUtil = null;
        }
    }


    /**
     * config bean ,to store config info
     * User: luowei
     * Date: 12-7-8
     * Time: 下午1:13
     */
    public static class ConfigBean implements Serializable {

        //配置名称
        protected String name;
        //key值
        protected String key;
        //value值
        protected String value;
        //描述信息
        protected String describe;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }
    }


}
