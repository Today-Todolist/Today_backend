package todolist.today.today.global.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Configuration
@RequiredArgsConstructor
public class EmbeddedRedisConfig {

    private static RedisServer redisServer;

    @Value("${spring.redis.port}")
    private Integer port;

    @PostConstruct
    public void runRedis() {
        if (redisServer == null) {
            redisServer = new RedisServer(port);
            redisServer.start();
        }
    }

    @PreDestroy
    public void stopRedis() {
        if (redisServer != null) {
            redisServer.stop();
        }
    }
}
