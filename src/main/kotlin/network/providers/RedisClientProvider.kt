package network.providers

import redis.clients.jedis.Jedis

object RedisClientProvider {
    val jedis: Jedis by lazy {
        Jedis("localhost", 6379).apply{
            ping()
        }
    }
}
