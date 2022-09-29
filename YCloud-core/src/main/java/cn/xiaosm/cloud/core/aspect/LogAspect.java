package cn.xiaosm.cloud.core.aspect;

import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.json.JSONUtil;
import cn.xiaosm.cloud.common.exception.CanShowException;
import cn.xiaosm.cloud.common.util.ServletUtils;
import cn.xiaosm.cloud.core.annotation.LogRecord;
import cn.xiaosm.cloud.core.config.security.service.TokenService;
import cn.xiaosm.cloud.core.entity.Log;
import cn.xiaosm.cloud.core.service.LogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

@Aspect
@Component
public class LogAspect {

    @Autowired
    TokenService tokenService;
    @Autowired
    LogService logService;

    //定义切点 @Pointcut
    //在注解的位置切入代码
    @Pointcut("@annotation(cn.xiaosm.cloud.core.annotation.LogRecord)")
    public void logPointCut() { }

    //切面 环绕通知
    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        // System.out.println("环绕。。。。。");
        //保存日志
        Log log = new Log();
        // 设置请求IP
        log.setIp(ServletUtil.getClientIP(ServletUtils.getRequest()));
        // 设置请求者
        log.setUserId(tokenService.getLoginUser().getId());
        // 设置请求参数
        log.setContent(JSONUtil.toJsonStr(joinPoint.getArgs()));
        // 获取操作名称
        log.setTitle(((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(LogRecord.class).value());
        // 设置被调用的方法名称
        String className = joinPoint.getSignature().getDeclaringTypeName();
        log.setMethod(className + "." + joinPoint.getSignature().getName());
        // 执行方法 得到的对象是返回值
        Object returnObj;
        try {
            returnObj= joinPoint.proceed();
            log.setType("INFO");
        } catch (CanShowException e) {
            log.setType("ERROR");
            log.setError(this.getStackTrace(e));
            throw e;
        } catch (Throwable throwable) {
            log.setType("ERROR");
            log.setError(this.getStackTrace(throwable));
            throwable.printStackTrace();
            throw throwable;
        } finally {
            // 设置请求耗时
            log.setTime((int) (System.currentTimeMillis() - start));
            logService.save(log);
        }
        return returnObj;
    }

    //切面 后置通知
    @AfterReturning("logPointCut()")
    public void after(JoinPoint joinPoint) {
        // System.out.println("后置。。。。。");
        //保存日志

    }

    public String getStackTrace(Throwable e) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        e.printStackTrace(ps);
        try {
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        ps.close();
        return out.toString();
    }

}
