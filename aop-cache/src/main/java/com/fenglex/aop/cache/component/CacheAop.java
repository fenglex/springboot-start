package com.fenglex.aop.cache.component;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.time.Duration;

@Aspect
@Component
@Slf4j
public class CacheAop {

    private final StringRedisTemplate redisTemplate;

    private final SpringCacheKeyGenerator keyGenerator;

    public CacheAop(StringRedisTemplate redisTemplate, SpringCacheKeyGenerator keyGenerator) {
        this.redisTemplate = redisTemplate;
        this.keyGenerator = keyGenerator;
    }

    @Pointcut("execution(* com.fenglex.aop.cache..*.*(..))")
    public void pointCut() {
    }


    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 生成缓存
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        Cache annotation = method.getAnnotation(Cache.class);
        if (annotation != null) {
            String key = annotation.key();
            String methodName = method.getName();
            String cls = method.getDeclaringClass().getName();
            String redisKey;
            SimpleKeyGenerator simpleKeyGenerator = new SimpleKeyGenerator();
            if (key.isEmpty()) {
               // Object generate = keyGenerator.generate(joinPoint.getTarget(), method, joinPoint.getArgs());
                Object generate = simpleKeyGenerator.generate(joinPoint.getTarget(), method, joinPoint.getArgs());
                redisKey = generate != null ? generate.toString() : "";
            } else {
                redisKey = cls + "." + methodName + "_" + parseKey(key, method, joinPoint.getArgs());
            }

            assert redisKey != null;
            String value = redisTemplate.opsForValue().get(redisKey);
            if (value != null) {
                Class<?> type = method.getReturnType();
                return JSON.parseObject(value, type);
            } else {
                Object proceed = joinPoint.proceed();
                if (proceed != null) {
                    if (annotation.expire() != 0) {
                        redisTemplate.opsForValue().set(redisKey, JSON.toJSONString(proceed), Duration.ofSeconds(annotation.expire()));
                    } else {
                        redisTemplate.opsForValue().set(redisKey, JSON.toJSONString(proceed));
                    }

                }
                return proceed;
            }
        } else {
            return joinPoint.proceed();
        }
    }

    private String parseKey(String key, Method method, Object[] args) {

        if (StringUtils.isEmpty(key)) return null;

        //获取被拦截方法参数名列表(使用Spring支持类库)
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] paraNameArr = u.getParameterNames(method);

        //使用SPEL进行key的解析
        ExpressionParser parser = new SpelExpressionParser();
        //SPEL上下文
        StandardEvaluationContext context = new StandardEvaluationContext();
        //把方法参数放入SPEL上下文中
        assert paraNameArr != null;
        for (int i = 0; i < paraNameArr.length; i++) {
            context.setVariable(paraNameArr[i], args[i]);
        }
        return parser.parseExpression(key).getValue(context, String.class);

    }
}
