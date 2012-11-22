package com.oilchem.trade.util;


import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @className:EHCacheUtil.java
 * @classDescription:ehcache工具类
 * @author:wei.luo
 * @createTime:2012-2-1
 */

public class EHCacheUtil {

    /**
     * 元素最大数量
     */
    public static int MAXELEMENTSINMEMORY = 300000;
    /**
     * 是否把溢出数据持久化到硬盘
     */
    public static boolean OVERFLOWTODISK = false;
    /**
     * 是否会死亡
     */
    public static boolean ETERNAL = false;
    /**
     * 缓存的间歇时间
     */
    public static int TIMETOIDLESECONDS = 1800;

    /**
     * 存活时间
     */
    public static int TIMETOlIVESECONDS = 7200;
    /**
     * 需要持久化到硬盘否
     */

    public static boolean DISKPERSISTENT = false;
    /**
     * 内存存取策略
     */
    public static String MEMORYSTOREEVICTIONPOLICY = "LFU";

    /**
     * 磁盘缓存池大小
     */
    public static int DISKSPOOLBUFFERSIZEMB = 50;

    protected static Logger logger = (Logger) LoggerFactory.getLogger(EHCacheUtil.class);

    /**
     * 初始化缓存
     *
     * @param cacheName           缓存名称
     * @param maxElementsInMemory 元素最大数量
     * @param overflowToDisk      是否持久化到硬盘
     * @param eternal             是否会死亡
     * @param timeToLiveSeconds   缓存存活时间
     * @param timeToIdleSeconds   缓存的间隔时间
     * @return 缓存
     * @throws Exception 异常
     * @author wei.luo
     * @createTime 2012-4-23
     */
    public static Cache initCache(String cacheName, int maxElementsInMemory,
                                  boolean overflowToDisk, boolean eternal, long timeToLiveSeconds,
                                  long timeToIdleSeconds) {
        try {
            CacheManager singletonManager = CacheManager.create();
            Cache myCache = singletonManager.getCache(cacheName);
            if (myCache != null) {
                CacheConfiguration config = myCache.getCacheConfiguration();
                config.setTimeToLiveSeconds(timeToLiveSeconds);
                config.setMaxElementsInMemory(maxElementsInMemory);
                config.setOverflowToDisk(overflowToDisk);
                config.setEternal(eternal);
                config.setTimeToIdleSeconds(timeToIdleSeconds);
            }
            if (myCache == null) {
                Cache memoryOnlyCache = new Cache(cacheName, maxElementsInMemory, overflowToDisk,
                        eternal, timeToLiveSeconds, timeToIdleSeconds);
                singletonManager.addCache(memoryOnlyCache);
                myCache = singletonManager.getCache(cacheName);
            }
            return myCache;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 初始化cache
     *
     * @param cacheName         cache的名字
     * @param timeToLiveSeconds 有效时间
     * @return cache 缓存
     * @throws Exception 异常
     * @author wei.luo
     * @createTime 2012-4-23
     */
    public static Cache initCache(String cacheName, long timeToLiveSeconds) {
        try {
            CacheManager myManager = CacheManager.create();
            Cache myCache = myManager.getCache(cacheName);
            if (myCache != null) {
                CacheConfiguration config = myCache.getCacheConfiguration();
                config.setTimeToLiveSeconds(timeToLiveSeconds);
            }
            if (myCache == null) {
                myCache = new Cache(
                        new CacheConfiguration(cacheName, MAXELEMENTSINMEMORY)
                                .memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.LFU)
                                .overflowToDisk(OVERFLOWTODISK)
                                .eternal(ETERNAL)
                                .timeToLiveSeconds(timeToLiveSeconds)
                                .timeToIdleSeconds(TIMETOIDLESECONDS)
                                .diskPersistent(DISKPERSISTENT)
                                .diskExpiryThreadIntervalSeconds(0));
                myManager.addCache(myCache);
            }
            return myCache;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 初始化Cache
     *
     * @param cacheName cache容器名
     * @return cache容器
     * @throws Exception 失败抛出异常
     * @author wei.luo
     * @createTime 2012-4-26
     */
    public static Cache initMyCache(String cacheName) {
        try {
            CacheManager myManager = CacheManager.create();
            Cache myCache = myManager.getCache(cacheName);
            if (myCache == null) {
                myCache = new Cache(
                        new CacheConfiguration(cacheName, MAXELEMENTSINMEMORY)
                                .memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.LFU)
                                .overflowToDisk(OVERFLOWTODISK)
                                .eternal(ETERNAL)
                                .timeToLiveSeconds(TIMETOlIVESECONDS)
                                .timeToIdleSeconds(TIMETOIDLESECONDS)
                                .diskPersistent(DISKPERSISTENT)
                                .diskExpiryThreadIntervalSeconds(0));
                myManager.addCache(myCache);
            }
            return myCache;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }


    /**
     * 初始化Cache
     *
     * @param cacheName cache容器名
     * @return cache容器
     * @throws Exception 失败抛出异常
     * @author wei.luo
     * @createTime 2012-4-26
     */
    public static Cache initMyCache(String cacheName, int maxElementsInMemory,
                                    Boolean overFlowToDisk, Boolean diskPersistent, int diskSpoolBufferSizeMB) {
        try {
            CacheManager myManager = CacheManager.create();
            Cache myCache = myManager.getCache(cacheName);
            if (myCache == null) {
                myCache = new Cache(
                        new CacheConfiguration(cacheName, maxElementsInMemory)
                                .memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.LFU)
                                .overflowToDisk(overFlowToDisk)
                                .diskSpoolBufferSizeMB(diskSpoolBufferSizeMB)
                                .eternal(ETERNAL)
                                .timeToLiveSeconds(TIMETOlIVESECONDS)
                                .timeToIdleSeconds(TIMETOIDLESECONDS)
                                .diskPersistent(diskPersistent)
                                .diskExpiryThreadIntervalSeconds(0));
                myManager.addCache(myCache);
            }
            return myCache;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 修改缓存容器配置
     *
     * @param cacheName           缓存名
     * @param timeToLiveSeconds   有效时间
     * @param maxElementsInMemory 最大数量
     * @return 真
     * @throws Exception 异常
     * @author wei.luo
     * @createTime 2012-4-23
     */
    public static boolean modifyCache(String cacheName,
                                      long timeToLiveSeconds, int maxElementsInMemory) {
        Boolean flag = false;
        try {
            if (StringUtils.isNotBlank(cacheName) && timeToLiveSeconds != 0L
                    && maxElementsInMemory != 0) {
                CacheManager myManager = CacheManager.create();
                Cache myCache = myManager.getCache(cacheName);
                CacheConfiguration config = myCache.getCacheConfiguration();
                config.setTimeToLiveSeconds(timeToLiveSeconds);
                config.setMaxElementsInMemory(maxElementsInMemory);
                flag = true;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return flag;
    }

    /**
     * 向指定容器中设置值
     *
     * @param cacheName 容器名
     * @param key       键
     * @param value     值
     * @return 返回真
     * @throws Exception 异常
     * @author wei.luo
     * @createTime 2012-4-23
     */
    public static boolean setValue(String cacheName, String key, Object value) {
        Boolean flag = false;
        try {
            CacheManager myManager = CacheManager.create();
            Cache myCache = myManager.getCache(cacheName);
            if (myCache == null) {
                myCache = initMyCache(cacheName);
                //myManager.addCache(cacheName);
                //myCache=myManager.getCache(cacheName);
            }
            Element element = new Element(key, value);
//			element.updateAccessStatistics();
            myCache.put(element);
            flag = true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return flag;
    }

    /**
     * 向指定容器中设置值
     *
     * @param cacheName         容器名
     * @param key               键
     * @param value             值
     * @param timeToLiveSeconds 存活时间
     * @return 真
     * @throws Exception 抛出异常
     * @author wei.luo
     * @createTime 2012-4-26
     */
    public static boolean setValue(String cacheName, String key, Object value,
                                   Integer timeToLiveSeconds) {
        Boolean flag = false;
        try {
            CacheManager myManager = CacheManager.create();
            Cache myCache = myManager.getCache(cacheName);
            if (myCache == null) {
                initCache(cacheName, timeToLiveSeconds);
                myCache = myManager.getCache(cacheName);
            }
            myCache.put(new Element(key, value, ETERNAL,
                    TIMETOIDLESECONDS, timeToLiveSeconds));
            flag = true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return flag;
    }

    /**
     * 从ehcache的指定容器中取值
     *
     * @param key 键
     * @return 返回Object类型的值
     * @throws Exception 异常
     * @author wei.luo
     * @createTime 2012-4-23
     */
    public static Object getValue(String cacheName, String key) {
        try {
            CacheManager myManager = CacheManager.create();
            Cache myCache = myManager.getCache(cacheName);
            if (myCache == null) {
                myCache = initMyCache(cacheName);
            }
            return myCache.get(key).getValue();
        } catch (Exception e) {
//            logger.warn(e.getMessage(),e);
            return null;
        }
    }

    /**
     * 删除指定的ehcache容器
     *
     * @param cacheName
     * @return 真
     * @throws Exception 失败抛出异常
     * @author wei.luo
     * @createTime 2012-4-23
     */
    public static boolean removeEhcache(String cacheName) {
        Boolean flag = false;
        try {
            CacheManager myManager = CacheManager.create();
            myManager.removeCache(cacheName);
            flag = true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return flag;
    }

    /**
     * 删除所有的EHCache容器
     *
     * @param cacheName 容器名
     * @return 返回真
     * @throws Exception 失败抛出异常
     * @author wei.luo
     * @createTime 2012-4-26
     */
    public static boolean removeAllEhcache(String cacheName) {
        Boolean flag = false;
        try {
            CacheManager myManager = CacheManager.create();
            myManager.removalAll();
            flag = true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return flag;
    }

    /**
     * 删除EHCache容器中的元素
     *
     * @param cacheName 容器名
     * @param key       键
     * @return 真
     * @throws Exception 失败抛出异常
     * @author wei.luo
     * @createTime 2012-4-26
     */
    public static boolean removeElment(String cacheName, String key) {
        Boolean flag = false;
        try {
            CacheManager myManager = CacheManager.create();
            Cache myCache = myManager.getCache(cacheName);
            if (myCache != null) {
                myCache.remove(key);
            }
            flag = true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return flag;
    }

    /**
     * 删除指定容器中的所有元素
     *
     * @param cacheName 容器名
     * @return 真
     * @throws Exception 失败抛出异常
     * @author wei.luo
     * @createTime 2012-4-26
     */
    public static boolean removeAllElment(String cacheName) {
        Boolean flag = false;
        try {
            CacheManager myManager = CacheManager.create();
            Cache myCache = myManager.getCache(cacheName);
            if (myCache != null) {
                myCache.removeAll();
            }
            flag = true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return flag;
    }

}