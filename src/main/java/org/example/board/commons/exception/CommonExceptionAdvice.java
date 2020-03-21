package org.example.board.commons.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

// ControllerAdvice 애너테이션을 통해 이 클래스의 객체가 컨트롤러에서 발생하는 Exception 을 전문적으로 처리하는 클래스를 명시해준 것이다.
@ControllerAdvice
public class CommonExceptionAdvice {

    private static final Logger logger = LoggerFactory.getLogger(CommonExceptionAdvice.class);

    // commonException() 메서드의 선언부에는 @ExceptionHandler 라는 애너테이션을 붙여 적절한 타입의 Exception 을 처리하도록 함
    // 일반 컨트롤러 클래스와 다르게 Model 을 파라미터로 사용하는 것을 지원하지 않기 때문에 ModelAndView 타입을 직접 사용하는 형태로 작성해야하 한다.
    // ModelAndView 는 하나의 객체에 Model 데이터와 View 의 처리를 동시에 처리할 수 있는 객체이다.
    // 만약 예외가 발생하게 되면 예외가 발생한 내용이 담긴 데이터를 exception 에 담고, common_error.jsp 에 전달하게 된다.
    @ExceptionHandler(Exception.class)
    public ModelAndView commonException(Exception e) {
        logger.info(e.toString());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exception", e);
        modelAndView.setViewName("/commons/common_error");

        return modelAndView;
    }

}
