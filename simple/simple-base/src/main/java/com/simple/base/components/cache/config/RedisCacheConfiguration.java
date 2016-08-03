package com.simple.base.components.cache.config;

import java.lang.reflect.Method;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableCaching//
public class RedisCacheConfiguration extends CachingConfigurerSupport {
   
    /**
     * 缂撳瓨绠＄悊鍣�.
     * @param redisTemplate
     * @return
     */
    @Bean
    public CacheManager cacheManager(RedisTemplate<?,?> redisTemplate) {
       CacheManager cacheManager = new RedisCacheManager(redisTemplate);
       return cacheManager;
    }
 
    /**
     * 鑷畾涔塳ey.
     * 姝ゆ柟娉曞皢浼氭牴鎹被鍚�+鏂规硶鍚�+鎵�鏈夊弬鏁扮殑鍊肩敓鎴愬敮涓�鐨勪竴涓猭ey,鍗充娇@Cacheable涓殑value灞炴�т竴鏍凤紝key涔熶細涓嶄竴鏍枫��
     */
    @Override
    public KeyGenerator keyGenerator() {
       System.out.println("RedisCacheConfig.keyGenerator()");
       return new KeyGenerator() {
           @Override
           public Object generate(Object o, Method method, Object... objects) {
              // This will generate a unique key of the class name, the method name
              //and all method parameters appended.
              StringBuilder sb = new StringBuilder();
              sb.append(o.getClass().getName());
              sb.append(method.getName());
              for (Object obj : objects) {
                  sb.append(obj.toString());
              }
              System.out.println("keyGenerator=" + sb.toString());
              return sb.toString();
           }
       };
    }
    /**
     * RedisTemplate缂撳瓨鎿嶄綔绫�,绫讳技浜巎dbcTemplate鐨勪竴涓被;
     *
     * 铏界劧CacheManager涔熻兘鑾峰彇鍒癈ache瀵硅薄锛屼絾鏄搷浣滆捣鏉ユ病鏈夐偅涔堢伒娲伙紱
     *
     * 杩欓噷鍦ㄦ墿灞曚笅锛歊edisTemplate杩欎釜绫讳笉瑙佸緱寰堝ソ鎿嶄綔锛屾垜浠彲浠ュ湪杩涜鎵╁睍涓�涓垜浠�
     *
     * 鑷繁鐨勭紦瀛樼被锛屾瘮濡傦細RedisStorage绫�;
     *
     * @param factory : 閫氳繃Spring杩涜娉ㄥ叆锛屽弬鏁板湪application.properties杩涜閰嶇疆锛�
     * @return
     */
    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
       RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();
       redisTemplate.setConnectionFactory(factory);
      
      
       RedisSerializer<String> redisSerializer = new StringRedisSerializer();//Long绫诲瀷涓嶅彲浠ヤ細鍑虹幇寮傚父淇℃伅;
       redisTemplate.setKeySerializer(redisSerializer);
       redisTemplate.setHashKeySerializer(redisSerializer);
      
       return redisTemplate;
    }
   
    @Bean
    public RedisTemplate<Object, Object> redisTemplateObject(RedisConnectionFactory factory) {
       RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<Object, Object>();
       redisTemplate.setConnectionFactory(factory);
      
       ObjectMapper om = new ObjectMapper();
       om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
       om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
       
       Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(Object.class);
       serializer.setObjectMapper(om);
      
       RedisSerializer<String> redisSerializer = new StringRedisSerializer();//Long绫诲瀷涓嶅彲浠ヤ細鍑虹幇寮傚父淇℃伅;
       redisTemplate.setKeySerializer(redisSerializer);
       redisTemplate.setHashKeySerializer(redisSerializer);
       redisTemplate.setValueSerializer(serializer);
     
       return redisTemplate;
    }
   
 
}