package cn.xiaosm.cloud.core.aspect;

import cn.hutool.extra.servlet.ServletUtil;
import cn.xiaosm.cloud.common.util.ServletUtils;
import cn.xiaosm.cloud.core.config.security.service.TokenService;
import cn.xiaosm.cloud.core.entity.Log;
import cn.xiaosm.cloud.core.service.LogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

@Aspect
@Component
public class ExceptionAspect {

    @Autowired
    TokenService tokenService;
    @Autowired
    LogService logService;
    //定义切点 @Pointcut
    //在注解的位置切入代码
    @Pointcut("execution(public * cn.xiaosm.cloud.core.config.GlobalExceptionHandler.catchException(..))")
    public void logPointCut() { }

    //切面 后置通知
    @AfterReturning("logPointCut()")
    public void after(JoinPoint joinPoint) {
        Log log = new Log();

        log.setError(this.getStackTrace((Exception) joinPoint.getArgs()[0]));
        log.setTitle(this.getFirstLine((Exception) joinPoint.getArgs()[0]));
        log.setType("ERROR");

        log.setIp(ServletUtil.getClientIP(ServletUtils.getRequest()));
        logService.save(log);
    }

    public String getFirstLine(Exception e) {
        return e.getMessage().length() > 30 ?
                e.getMessage().substring(0, 30) : e.getMessage();
    }

    public String getStackTrace(Exception e) {
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
