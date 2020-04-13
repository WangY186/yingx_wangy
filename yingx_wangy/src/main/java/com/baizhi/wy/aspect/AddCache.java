package com.baizhi.wy.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AddCache {

    @Autowired
    private RedisTemplate redisTemplate;

    @Around("@annotation(com.baizhi.wy.annotation.AddCaches)")
    public Object addCache(ProceedingJoinPoint proceedingJoinPoint){
        //获取类的全限定名作为redis中的hash中的大KEY
        String className = proceedingJoinPoint.getTarget().getClass().getName();
        StringBuilder sb = new StringBuilder();

        //获取方法名
        String methodName = proceedingJoinPoint.getSignature().getName();
        sb.append(methodName);
        //获取方法参数
        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg : args) {
            if(arg!=null){
                sb.append(arg.toString());
            }
        }
        //sb作为hash的小key
        String key = sb.toString();
        StringRedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        redisTemplate.setHashKeySerializer(redisSerializer);
        HashOperations hash = redisTemplate.opsForHash();

        Boolean aBoolean = hash.hasKey(className, key);

        Object result=null;
        //判断小key是否存在
        if(aBoolean){
            //存在，就在redis中查询返回
            result = hash.get(className, key);
        }else{
            //不存在，就放行，通过方法查询
            try {
                result=  proceedingJoinPoint.proceed();
                //并将查询结果放入缓存
                hash.put(className,key,result);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
        //将结果返回
        return result;
    }
    @After("@annotation(com.baizhi.wy.annotation.DelCache)")
    public void delCache(JoinPoint joinPoint){
        //获取大KEY
        String className = joinPoint.getTarget().getClass().getName();

        //删除
        redisTemplate.delete(className);
    }
}
