package com.baron.spider;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

/**
 * Created by Jason on 2017/5/27.
 */
@Aspect
@Component
public class AutoProxy {
    @Pointcut("within(com.baron..*)")
    public void pointCut() {}

    @Before("pointCut()")
    public boolean before(JoinPoint joinPoint) {
        System.out.println(joinPoint.getSignature());
        return true;
    }
}
