package com.springboot.aspect;

import com.springboot.annotation.SLLog;
import org.apache.ibatis.javassist.*;
import org.apache.ibatis.javassist.bytecode.CodeAttribute;
import org.apache.ibatis.javassist.bytecode.LocalVariableAttribute;
import org.apache.ibatis.javassist.bytecode.MethodInfo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @Author: chuan.bai
 * @Description
 * @Date: Created on 10:00 01/12/2017
 * @Modified By:
 */
@Aspect
@Component
public class AspectLog {

    private Logger logger = LoggerFactory.getLogger("SLLog");

    public AspectLog() {
        logger.info("[Init AspectLog]");
    }

    /***
     * 定义切面点 SLLog注解
     */
    @Pointcut("@annotation(com.springboot.annotation.SLLog)")
    private void anyMethod() {

    }//定义一个切入点


    /***
     * 获取方法参数信息
     * @param joinPoint
     * @throws Throwable
     */
    private void getInfo(ProceedingJoinPoint joinPoint) throws Throwable {
        String classType = joinPoint.getTarget().getClass().getName();
        Class<?> clazz = Class.forName(classType);
        String clazzName = clazz.getName();
        //获取方法名称
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        //获取参数名称和值
        if (args.length <= 0) {
            logger.info(methodName + " 方法没有参数");
        } else {
            String[] paramNames = getFieldsName(this.getClass(), clazzName, methodName);
            logger.info("[======== 3.遍历方法参数 start ========]");
            //如果参数是一个对象，则该参数值为显示其 toString 方法内容
            for (int i = 0; i < args.length; i++) {
                logger.info("参数名：" + paramNames[i] + "，该参数值 = { " + args[i] + " }");
            }
            logger.info("[======== 遍历方法参数 end ========]");
        }
    }

    private SLLog getAnnotationInfo(ProceedingJoinPoint joinPoint) throws NoSuchMethodException {
        Method method = joinPoint.getTarget().getClass().getMethod(joinPoint.getSignature().getName(),
                ((MethodSignature) joinPoint.getSignature()).getMethod().getParameterTypes());
        SLLog slLog = method.getAnnotation(SLLog.class);
        return slLog;
    }

    /***
     * 环绕切面
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("anyMethod()")
    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable{
        long startTime=System.currentTimeMillis();   //获取开始时间
        logger.info("[================== SLLog START ==================]");
        logger.info("[======== 1.开始调用: "+pjp.getTarget().getClass().getName()+" ========]");
        SLLog slLog = this.getAnnotationInfo(pjp);
        if(slLog.content()!=""&&slLog.content().length()>0){
            logger.info("[======== SLLOG:"+slLog.content()+" ========]");
        }
        String methodName = pjp.getSignature().getName();   //获取方法名称
        logger.info("[======== 2.调用:"+methodName+" 方法 ========]");
        getInfo(pjp);
        Object object = pjp.proceed();//执行该方法
        logger.info("[======== 5." + methodName + " 方法返回值: " + object + " ========]");
        long endTime=System.currentTimeMillis(); //获取结束时间
        logger.info("[======== 6."+methodName+" 方法结束，耗时"+(endTime-startTime)+"ms ========]");

        Long takeTime = endTime - startTime;

        Long seconds = takeTime / 1000;

        if (seconds > 10) {
            logger.info("严重注意：该方法可能存在严重性能问题");
        } else if (seconds > 5) {
            logger.info("注意：该方法可能存在一般性能问题");
        }

        if(methodName==oldMethodName){
            excCount++;
        }else{
            excCount = 1;
        }
        oldMethodName = methodName;
        if(excCount>=2){
            logger.info("!!!!!!!!!警告：【"+methodName+"】该方法被连续调用:"+excCount+"次!!!!!!!!");
        }
        logger.info("[================== SLLog END ==================]");
        logger.info("\n\n");
        return object;
    }




    /**
     * 反射得到方法参数的名称
     * @param cls
     * @param clazzName
     * @param methodName
     * @return
     * @throws NotFoundException
     */
    private static String[] getFieldsName(Class cls, String clazzName, String methodName) throws NotFoundException {
        ClassPool pool = ClassPool.getDefault();
        ClassClassPath classPath = new ClassClassPath(cls);
        pool.insertClassPath(classPath);
        CtClass cc = pool.get(clazzName);
        CtMethod cm = cc.getDeclaredMethod(methodName);
        MethodInfo methodInfo = cm.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
        if (attr == null) {
        }
        String[] paramNames = new String[cm.getParameterTypes().length];

        int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
        for (int i = 0; i < paramNames.length; i++){
            paramNames[i] = attr.variableName(i + pos); //paramNames即参数名
        }
        return paramNames;
    }

    //调用次数
    private int excCount = 0;
    //上一次调用的方法名称
    private String oldMethodName = "";
}
