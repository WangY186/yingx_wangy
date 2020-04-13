package com.baizhi.wy.aspect;

import com.baizhi.wy.annotation.LogAnnotation;
import com.baizhi.wy.entity.Admin;
import com.baizhi.wy.entity.Log;
import com.baizhi.wy.service.LogService;
import com.baizhi.wy.util.UUIDUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Component
@Aspect
public class LogAspect {

    @Autowired
    HttpSession session;

    @Autowired
    private LogService logService;

    @Around("pt()")
    public Object addLog(ProceedingJoinPoint joinPoint){
        //通过session获得当前管理员
        Admin admin = (Admin) session.getAttribute("admin");
        //通过参数获取操作的方法
        String methodName = joinPoint.getSignature().getName();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodDes = signature.getMethod().getAnnotation(LogAnnotation.class).name();
       String message="fail";
        Object proceed=null;
        try {
            proceed = joinPoint.proceed();
            message="success";
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }finally{
            Log log = new Log();
            String uuid = UUIDUtil.getUUID();
            log.setId(uuid).setTime(new Date()).setAdminOper(admin.getUsername()).setOperation(methodDes+"("+methodName+")").setStatus(message);
            logService.add(log);
        }
        return  proceed;
    }

    @Pointcut("@annotation(com.baizhi.wy.annotation.LogAnnotation)")
    public void pt(){}
}
