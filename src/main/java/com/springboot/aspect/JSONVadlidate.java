package com.springboot.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author: chuan.bai
 * @Description
 * @Date: Created on 10:00 01/12/2017
 * @Modified By:
 */
@Aspect
@Component
public class JSONVadlidate {

    private Logger logger = LoggerFactory.getLogger("JSONLog");

    private String data;


    public JSONVadlidate() {
        logger.info("[Init JSONLog]");
    }
    /***
     * 定义切面点 SLLog注解
     */
    @Pointcut("@annotation(com.springboot.annotation.JSONCheck)")
    private void anyMethod() {

    }//定义一个切入点

    @Before("anyMethod() && args(data)")
    public void doJSONVerificatonCheck(String data) {
        this.data = data;
    }


    @Around("anyMethod()")
    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
        Object object = pjp.proceed();
        System.out.println("data1212+"+data);
        if (object instanceof Map) {
            Map result = (Map) object;
            System.out.println("asasa:"+result);
            if(data==null){
                result.put("isSuccessful",0);
                result.put("error","参数错误");
                return result;
            }
            try {
                JSONObject jo = new JSONObject(data);
            } catch (JSONException e) {
                e.printStackTrace();
                result.put("isSuccessful", 0);
                result.put("error", "提交的非JSON数据");
                return result;
            }
        }
        return object;
    }

}
