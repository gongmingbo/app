package app.aspect;


import app.packet.error.ErrorEnum;
import app.packet.request.CommonPacket;
import app.packet.response.CommonMessage;
import app.service.UserService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ApiAspect {
    @Autowired
    private UserService userService;
    @Pointcut(value = "@annotation(app.aspect.annotation.ApiLoginRequired)")
    public void apiLoginRequired(){

    }

    @Around(value = "apiLoginRequired()")
    public Object doAround(ProceedingJoinPoint point )throws Throwable {
        Object[] args = point.getArgs();
        for (Object arg : args){
            if (arg instanceof CommonPacket){
                CommonPacket packet = (CommonPacket)arg;
                String token = userService.validateAndRefreshToken(packet);
                if (token != null){
                    Object o = point.proceed(args);
                    if (o instanceof CommonMessage){
                        ((CommonMessage)o).setToken(token);
                    }
                    return o;
                }else {
                    return CommonMessage.failure(ErrorEnum.TOKEN_INVALID);
                }
            }
        }
        return point.proceed(args);
    }

}
