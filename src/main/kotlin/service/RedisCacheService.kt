package service

import network.providers.RedisClientProvider.jedis

object RedisCacheService {

    fun formatKey(namespace: String, key: String) = "$namespace:$key"

    fun set(namespace: String,key:String, value: String, ttlSeconds: Long = 600){
        jedis.set(formatKey(namespace, key), value)
        jedis.expire(formatKey(namespace, key), ttlSeconds)
    }
    fun get(namespace:String, key:String): String? = jedis.get(formatKey(namespace, key))
    fun delete(namespace: String, key:String)  = jedis.del(formatKey(namespace, key))
    fun close() = jedis.close()
}