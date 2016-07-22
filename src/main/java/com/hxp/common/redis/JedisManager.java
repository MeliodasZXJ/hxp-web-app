package com.hxp.common.redis;

import com.hxp.common.redis.config.RedisConfig;
import com.hxp.common.redis.config.RedisConstant;
import com.hxp.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * jedis管理类
 *
 * @author anpushang
 */
public class JedisManager implements RedisConstant {

	private static final Log log = LogFactory.getLog(JedisManager.class);

	private static boolean inited = false;
	private static ShardedJedisPool pool;
	private static JedisPoolConfig poolConfig = new JedisPoolConfig();

	public static void init(Properties prop) {
		if (!inited) {
			log.info("JedisManager init...");
			initShardedPool(prop);
			inited = true;
		}
	}

	static {
		log.info("JedisManager static code block loader...");
		// 最大活动的对象个数
		poolConfig.setMaxTotal(RedisConfig.getInt(REDIS_POOL_MAX_TOTAL));
		// 对象最大空闲时间
		poolConfig.setMaxIdle(RedisConfig.getInt(REDIS_POOL_MAX_IDLE));
		// 获取对象时最大等待时间
		poolConfig.setMaxWaitMillis(RedisConfig.getLong(REDIS_POOL_MAX_WAIT_MILLIS));
		// 当调用borrow Object方法时，是否进行有效性检查
		poolConfig.setTestOnBorrow(RedisConfig.getBoolean(REDIS_POOL_TEST_ON_BORROW));
		String pwd = RedisConfig.getString(REDIS_PWD);
		String[] strArray = RedisConfig.getStringArray(REDIS_POOL);

		List<JedisShardInfo> shardJedis = new ArrayList<JedisShardInfo>();
		JedisShardInfo jsi = null;
		for (int i = 0; i < strArray.length; i++) {
			jsi = new JedisShardInfo(strArray[i]);
			if (!StringUtil.isEmpty(pwd)) {
				jsi.setPassword(pwd);
			}
			shardJedis.add(jsi);
		}
		pool = new ShardedJedisPool(poolConfig, shardJedis);
	}

	/**
	 * 初始化切片池
	 *
	 * @param prop
	 */
	public static void initShardedPool(Properties prop) {
		log.info("JedisManager initConf...");
		destroy();
		// 读取配置文件，构建redispool,格式：192.168.1.200,192.168.1.64 端口号默认
		String[] strArray = prop.getProperty(REDIS_POOL).split(",");
		List<JedisShardInfo> shardJedis = new ArrayList<JedisShardInfo>();
		for (int i = 0; i < strArray.length; i++) {
			shardJedis.add(new JedisShardInfo(strArray[i]));
		}
		pool = new ShardedJedisPool(poolConfig, shardJedis);
	}

	/**
	 * 存储redis的键值不设置时间
	 *
	 * @param jediskey
	 *            redis键
	 * @param str
	 *            redis值
	 */
	public static void setJedisVal(String jediskey, String str) {
		setString(jediskey, str, 0);
	}

	/**
	 * 设置时间存储redis
	 * 
	 * @param jediskey
	 *            key值
	 * @param str
	 *            保存的string
	 * @param liveSeconds
	 *            设置存储时间
	 * @return
	 */
	public static String setString(String jediskey, String str, int liveSeconds) {
		if ((jediskey == null) || (str == null))
			return null;
		ShardedJedis jedis = null;
		String ret = null;
		try {
			jedis = pool.getResource();
			if (liveSeconds <= 0)
				// 永久保存
				ret = jedis.set(encode(jediskey), str);
			else
				// 设置有效期
				ret = jedis.setex(encode(jediskey), liveSeconds, str);
		} catch (Exception e) {
			log.error("key:" + jediskey + "redis存储出错.");
			;
		} finally {
			closeJedis(jedis);
		}
		return ret;
	}

	/***
	 * 根据key值获取redis存储内容
	 * 
	 * @param jediskey
	 * @return
	 */
	public static String getString(String jediskey) {
		ShardedJedis jedis = null;
		String ret = null;
		try {
			jedis = pool.getResource();
			ret = jedis.get(encode(jediskey));
		} catch (Exception e) {
			log.error("key:" + jediskey + "未能取到redis的值.");
			;
		} finally {
			closeJedis(jedis);
		}
		return ret;
	}

	/***
	 * 将值加入队列
	 * 
	 * @param jediskey
	 * @return
	 */
	public static Long lPush(String jediskey, String... value) {
		ShardedJedis jedis = null;
		Long ret = null;
		try {
			jedis = pool.getResource();
			ret = jedis.lpush(encode(jediskey), value);
		} catch (Exception e) {
			log.error("key:" + jediskey + "未能 lpush值..");
			;
		} finally {
			closeJedis(jedis);
		}
		return ret;
	}

	/***
	 * 根据key值获取redis存储内容
	 * 
	 * @param jediskey
	 * @return
	 */
	public static String lPop(String jediskey) {
		ShardedJedis jedis = null;
		String ret = null;
		try {
			jedis = pool.getResource();
			ret = jedis.lpop(encode(jediskey));
		} catch (Exception e) {
			log.error("key:" + jediskey + "未能取到redis的值.");
			;
		} finally {
			closeJedis(jedis);
		}
		return ret;
	}

	public static long delString(String key) {
		return del(key);
	}

	public static long delStringNew(String key) {
		return del(encode(key));
	}

	public static long delObject(String key) {
		return del(key);
	}

	public static Object getObject(String key) {
		if (key == null)
			return null;
		byte[] ret = getBytes(key.getBytes());
		if (ret == null)
			return null;
		return ObjectBytesExchange.toObject(ret);
	}

	public static String setObject(String key, int seconds, Object obj) {
		if ((key == null) || (obj == null))
			return null;
		byte[] byteObj = ObjectBytesExchange.toByteArray(obj);
		if (null == byteObj)
			return null;
		String returnStr = setBytes(key.getBytes(), seconds, byteObj);
		log.debug(key + "  Redis存储结果：" + returnStr);
		return returnStr;
	}

	public static byte[] getBytes(byte[] key) {
		if (key == null)
			return null;
		ShardedJedis jedis = null;
		byte[] ret = null;
		try {
			jedis = (ShardedJedis) pool.getResource();
			ret = jedis.get(key);
		} catch (Exception e) {
			log.error(e);
		} finally {
			if (jedis != null)
				pool.returnResource(jedis);
		}
		return ret;
	}

	public static String setBytes(byte[] key, int seconds, byte[] bytes) {
		if ((key == null) || (bytes == null))
			return null;
		ShardedJedis jedis = null;
		String ret = null;
		try {
			jedis = (ShardedJedis) pool.getResource();
			if (seconds <= 0)
				ret = jedis.set(key, bytes);
			else
				ret = jedis.setex(key, seconds, bytes);
		} catch (Exception e) {
			log.error(e);
		} finally {
			closeJedis(jedis);
		}
		return ret;
	}

	public static String encode(String str) {
		String ret = null;
		if (str != null) {
			try {
				ret = URLEncoder.encode(str, REDIS_ENCODE);
			} catch (UnsupportedEncodingException e) {
				log.error(e);
			}
		}
		return ret;
	}

	/***
	 *
	 * @param key
	 * @return
	 */
	public long del(byte[] key) {
		if (key == null) {
			return 0L;
		}
		ShardedJedis jedis = null;
		long ret = 0L;
		try {
			jedis = pool.getResource();
			ret = jedis.del(key).longValue();
		} catch (Exception e) {
			log.error(e);
			ret = -1L;
		} finally {
			closeJedis(jedis);
		}
		return ret;
	}

	/**
	 * 根据redis key for delete
	 * 
	 * @param key
	 * @return
	 */
	private static long del(String key) {
		if (key == null) {
			return 0L;
		}
		ShardedJedis jedis = null;
		long ret = 0L;
		try {
			jedis = pool.getResource();
			ret = jedis.del(key).longValue();
		} catch (Exception e) {
			log.error(e);
			ret = -1L;
		} finally {
			closeJedis(jedis);
		}
		return ret;
	}

	public static boolean lock(String key, int seconds) {
		if (seconds <= 0) {
			seconds = 21600;
		}
		boolean result = false;
		if (key == null)
			return result;
		ShardedJedis jedis = null;
		long currentTime = System.currentTimeMillis();
		long expireTime = currentTime + seconds * 1000;
		try {
			jedis = pool.getResource();
			if (jedis.setnx(encode(key), String.valueOf(expireTime)).longValue() == 1L) {
				result = true;
				jedis.expire(encode(key), seconds);
			} else {
				Long oldExpireTime = Long.valueOf(getString(encode(key)));
				if ((oldExpireTime != null) && (currentTime > oldExpireTime.longValue() + 300000L)) {
					del(encode(key));
					if (jedis.setnx(encode(key), String.valueOf(expireTime)).longValue() == 1L) {
						result = true;
						jedis.expire(encode(key), seconds);
					}
				}
			}
		} catch (Exception e) {
			log.error(e);
		} finally {
			closeJedis(jedis);
		}
		return result;
	}

	/**
	 * 获取redis pool
	 * 
	 * @return
	 */
	public static ShardedJedisPool getPool() {
		return pool;
	}

	private static void destroy() {
		if (pool != null) {
			try {
				pool.destroy();
			} catch (Exception ex) {
				log.warn("Cannot properly close ShardedJedisPool ", ex);
			}
			pool = null;
		}
	}

	public static long incrByStr(String key) {
		if (key == null) {
			return 0L;
		}
		ShardedJedis jedis = null;
		long ret = 0L;
		try {
			jedis = pool.getResource();
			ret = jedis.incr(encode(key)).longValue();
		} catch (Exception e) {
			log.error(e);
			ret = -1L;
		} finally {
			closeJedis(jedis);
		}
		return ret;
	}

	public static long incrByByte(byte[] key) {
		if ((key == null) || (key.length == 0)) {
			return 0L;
		}
		ShardedJedis jedis = null;
		long ret = 0L;
		try {
			jedis = pool.getResource();
			ret = jedis.incr(key).longValue();
		} catch (Exception e) {
			log.error(e);
			ret = -1L;
		} finally {
			closeJedis(jedis);
		}
		return ret;
	}

	/**
	 * 释放连接对象
	 *
	 * @param jedis
	 * @return void
	 * @throws @Title:closeShardedJedis
	 */
	public static void closeJedis(ShardedJedis jedis) {
		if (null != jedis) {
			pool.returnResource(jedis);
		}
	}

	/**
	 * 释放异常的连接对象
	 *
	 * @param jedis
	 * @return void
	 * @throws @Title:closeShardedJedis
	 */
	public static void closeBrokenJedis(ShardedJedis jedis) {
		if (null != jedis) {
			pool.returnBrokenResource(jedis);
		}

	}

}
