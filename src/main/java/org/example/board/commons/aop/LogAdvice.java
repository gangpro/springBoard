package org.example.board.commons.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component  // 스프링 빈으로 인식되기 위한 애너테이션
@Aspect     // AOP 기능을 하는 클래스에 반드시 추가해야할 애너테이션
public class LogAdvice {

    private static final Logger logger = LoggerFactory.getLogger(LogAdvice.class);

    // 메서드 실행 전체를 앞, 뒤로 감싸서 특정한 기능을 실행할 수 있는 강력한 타입의 Advice
    @Around("execution(* org.example.board..*Controller.*(..))"
            + " || execution(* org.example.board..service..*Impl.*(..))"
            + " || execution(* org.example.board..persistence..*Impl.*(..))")
    public Object logPrint(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {  // 리턴타입 Object : 다른 Advice 와 달리 @Around 는 반드시 리턴타입을 Object 로 선언해줘야 한다.

        long start = System.currentTimeMillis();

        Object result = proceedingJoinPoint.proceed();  // proceedingJoinPoint : Around 타입의 Advice 메서드의 파라미터로 사용되는 인터페이스, JoinPoint 의 하위 인터페이스
                                                        // proceed() : 다음 Advice 를 실행허거나, 실제 target 객체의 메서드를 실행하는 기능을 가진 메서드
        String type = proceedingJoinPoint.getSignature().getDeclaringTypeName();    // getSignature() : 실행하는 대상 객체의 메서드에 대한 정보를 알아낼 때 사용한다.
        String name = "";

        if (type.contains("Controller")) {
            name = "Controller : ";
        } else if (type.contains("Service")) {
            name = "Service : ";
        } else if (type.contains("DAO")) {
            name = "Persistence : ";
        }

        long end = System.currentTimeMillis();

        logger.info(name + type + "."+proceedingJoinPoint.getSignature().getName() + "()");     // getSignature() : 실행하는 대상 객체의 메서드에 대한 정보를 알아낼 때 사용한다.
        logger.info("Argument/Parameter : " + Arrays.toString(proceedingJoinPoint.getArgs()));  // getArgs() : 전달되는 모든 파라미터들을 Object 의 배열로 가졍온다.

        if (result != null) {
            logger.info("Return Value : " + result.toString());
        } else {
            logger.info("Return Type : void");
        }

        logger.info("Running Time : " + (end-start));
        logger.info("----------------------------------------------------------------");

        return result;
    }

}