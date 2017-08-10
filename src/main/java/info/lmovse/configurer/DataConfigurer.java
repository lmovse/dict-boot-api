package info.lmovse.configurer;

import info.lmovse.util.GenericFastJsonRedisSerializer;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.persistence.EntityManagerFactory;

/**
 * Created by lmovse on 2017/8/7.
 * Tomorrow is a nice day.
 */
@Configuration
public class DataConfigurer {

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        //设置默认的Serialize，包含 keySerializer & valueSerializer
        redisTemplate.setDefaultSerializer(new GenericFastJsonRedisSerializer());
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        return redisTemplate;
    }

//    @Bean
//    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
//        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
//        // corePoolSize 是基础的线程数量，只有当 QueueCapacity 设置得值超出了后才会启用新线程，新线程的极限为 maxPoolSize
//        threadPoolTaskExecutor.setCorePoolSize(2);
//        threadPoolTaskExecutor.setMaxPoolSize(10);
//        threadPoolTaskExecutor.setKeepAliveSeconds(3000);
//        threadPoolTaskExecutor.setQueueCapacity(400);
//        return threadPoolTaskExecutor;
//    }

    @Bean
    public SessionFactory sessionFactory(EntityManagerFactory factory) {
        return factory.unwrap(SessionFactory.class);
    }

}
