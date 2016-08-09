package com.simple.base.bz.admin.controller;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class AdminController {
	   @RequestMapping("/iot")
	    //@RequiresPermissions("userInfo:del")//权限管理;
	    public String rootpage(){
	       return "index";
	    }
	   @RequestMapping("/iot/deviceType")
	    //@RequiresPermissions("userInfo:del")//权限管理;
	    public String channelpage(){
	       return "index";
	    }
	  public ModelAndView handleAuthorizationException(Exception e){
			 System.out.println("A Authorization Failure, MSG: " + e.getMessage());
			 
			 ModelAndView model = new ModelAndView("403");
			 model.addObject("Msg",e.getMessage());
			 return model;
		 }
}


