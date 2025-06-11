package service

import integration.providers.RedisClientProvider.jedis
import org.slf4j.LoggerFactory

object RedisCacheService {

    private val LOG = LoggerFactory.getLogger(RedisCacheService::class.java)

    fun formatKey(namespace: String, key: String) = "$namespace:$key"

    fun set(namespace: String,key:String, value: String, ttlSeconds: Long = 600){
        jedis.set(formatKey(namespace, key), value)
        jedis.expire(formatKey(namespace, key), ttlSeconds)
        LOG.info("Cache set for $key:$value in $namespace with TTL $ttlSeconds seconds")
    }
    fun get(namespace:String, key:String): String? {
       val value = jedis.get(formatKey(namespace, key))
        LOG.info("Cache get for $key:$value in $namespace")
        return value
    }

    fun delete(namespace: String, key:String)  = jedis.del(formatKey(namespace, key))
    fun close() = jedis.close()
}