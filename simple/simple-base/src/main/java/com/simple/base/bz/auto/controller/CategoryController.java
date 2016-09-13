package com.simple.base.bz.auto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.simple.base.bz.auto.entity.*;
import com.simple.base.bz.auto.service.*;


import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/category")
public class CategoryController {
	@Autowired
	CategoryService service;

  


	@RequestMapping(value= "/", method=RequestMethod.GET)
    public String rootpage(){
    	       return "index";
    }
	@RequestMapping(value = "/queryAll", method = RequestMethod.GET)
	@ResponseBody
	public List<Category> findAll() {
		return service.findAll();
	}
	@ResponseBody
    @RequestMapping(value = "/query/{id}", method = RequestMethod.GET)
    public Category findById(@PathVariable Long id) {
       	System.out.println("input param Id:" + id);
       	Category result = service.findById(id);
    	return result;
    }

    @ResponseBody
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public Category save(@RequestBody Category item) {
		System.out.println("input device params:" + item.toString());
		Category result = service.save(item);
		System.out.println("output device result data:" + result.toString());
		return result;
	}
    @ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Category save2(@RequestBody Category item) {
		 

		System.out.println("input device params:" + item.toString());
		Category result = service.save(item);
		System.out.println("output device result data:" + result.toString());
		return result;
	}


    @ResponseBody
 	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
 	public Category update(@PathVariable Long id, @RequestBody Category item) {
 		System.out.println("input device params:" + item.toString());
 		Category result = service.save(item);
 		System.out.println("output device result data:" + result.toString());
 		return result;
 	}

 	@ResponseBody
     	@RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
     	public Category updateSave(@PathVariable Long id, @RequestBody Category item) {
     	   



     		System.out.println("input device params:" + item.toString());
     		Category result = service.save(item);
     		System.out.println("output device result data:" + result.toString());
     		return result;
     	}



    @ResponseBody
   	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
   	public Long remove(@PathVariable Long id) {
		service.remove(id);
        return id;
    }
    @ResponseBody
    @RequestMapping(value = "/remove/{id}", method = RequestMethod.POST)
    public Long removeById(@PathVariable Long id) {
    	service.remove(id);
    	return id;
    }


    

}