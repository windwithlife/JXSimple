package com.simple;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class TestController {

	 @Value("${from}")
	   private String from;
	 
	 @RequestMapping(value = "/config" ,method = RequestMethod.GET)
	 public String config(){
		 return from;
	 }
}
