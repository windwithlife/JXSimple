package com.simple.base.bz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.simple.base.bz.entity.*;
import com.simple.base.bz.service.*;


import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/product")
public class ProductController {
	@Autowired
	ProductService service;



	@RequestMapping(value= "/", method=RequestMethod.GET)
    public String rootpage(){
    	       return "index";
    }
	@RequestMapping(value = "/queryAll", method = RequestMethod.GET)
	@ResponseBody
	public List<Product> findAll() {
		return service.findAll();
	}
	@ResponseBody
    @RequestMapping(value = "/query/{id}", method = RequestMethod.GET)
    public Product findById(@PathVariable Long id) {
       	System.out.println("input param Id:" + id);
       	Product result = service.findById(id);
    	return result;
    }
    @ResponseBody
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public Product save(@RequestBody Product item) {
		System.out.println("input device params:" + item.toString());
		Product result = service.save(item);
		System.out.println("output device result data:" + result.toString());
		return result;
	}
    @ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Product save2(@RequestBody Product item) {
		System.out.println("input device params:" + item.toString());
		Product result = service.save(item);
		System.out.println("output device result data:" + result.toString());
		return result;
	}


    @ResponseBody
 	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
 	public Product update(@PathVariable Long id, @RequestBody Product item) {
 		System.out.println("input device params:" + item.toString());
 		Product result = service.save(item);
 		System.out.println("output device result data:" + result.toString());
 		return result;
 	}

 	@ResponseBody
     	@RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
     	public Product updateSave(@PathVariable Long id, @RequestBody Product item) {
     		System.out.println("input device params:" + item.toString());
     		Product result = service.save(item);
     		System.out.println("output device result data:" + result.toString());
     		return result;
     	}



   
    @ResponseBody
    @RequestMapping(value = "/remove/{id}", method = RequestMethod.POST)
    public Long removeById(@PathVariable Long id) {
    	service.remove(id);
    	return id;
    }

}