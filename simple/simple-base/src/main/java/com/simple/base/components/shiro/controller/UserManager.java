package com.simple.base.components.shiro.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserManager {

	/* @ExceptionHandler(value = Exception.class)
	 public ModelAndView handleException(Exception e){
		 System.out.println("usermanager mydefine messge" + e.getMessage());
		 ModelAndView model = new ModelAndView("403");
		 model.addObject("errorMsg",e.getMessage());
		 return model;
	 }*/
	 
	 @ExceptionHandler(value = AuthorizationException.class)
	 public ModelAndView handleAuthorizationException(Exception e){
		 System.out.println("A Authorization Failure, MSG: " + e.getMessage());
		 
		 ModelAndView model = new ModelAndView("403");
		 model.addObject("Msg",e.getMessage());
		 return model;
	 }
	 
	 @ExceptionHandler(value = AuthenticationException.class)
	 public String handleAuthenticationException(Exception e){
		 System.out.println(" Authentication Failure, MSG : " + e.getMessage());
		 return "login";
	 }
    /**
     * 用户查询.
     * @return
     */
    @RequestMapping("/userList")
    public String userInfo(){
       return "userInfo";
    }
   
    /**
     * 用户添加;
     * @return
     */
    @RequestMapping("/userAdd")
    @RequiresPermissions("userInfo:add")//权限管理;
    public String userInfoAdd(){
       return "userInfoAdd";
    }
    
    /**
     * 用户删除;
     * @return
     */
    @RequestMapping("/userDel")
    @RequiresPermissions("userInfo:del")//权限管理;
    public String userDel(){
       return "userInfoDel";
    }
    
  
}
