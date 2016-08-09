package com.simple.base.global;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class DefaultWebControllerHandler {
   
    @ExceptionHandler(value = Exception.class)
    public void defaultErrorHandler(HttpServletRequest req, Exception e)  {

       //打印异常信息：
       System.out.println("GlobalDefaultExceptionHandler");
       e.printStackTrace();
  }
    
   
  @ModelAttribute
  public void addAttribute(Model model){
	  model.addAttribute("msg", "test global message");
  }
   
}