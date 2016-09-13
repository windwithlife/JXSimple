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
@RequestMapping("/dictionary")
public class DictionaryController {
	@Autowired
	DictionaryService service;

  
   @Autowired
   private  CategoryService categoryService;
   


	@RequestMapping(value= "/", method=RequestMethod.GET)
    public String rootpage(){
    	       return "index";
    }
	@RequestMapping(value = "/queryAll", method = RequestMethod.GET)
	@ResponseBody
	public List<Dictionary> findAll() {
		return service.findAll();
	}
	@ResponseBody
    @RequestMapping(value = "/query/{id}", method = RequestMethod.GET)
    public Dictionary findById(@PathVariable Long id) {
       	System.out.println("input param Id:" + id);
       	Dictionary result = service.findById(id);
    	return result;
    }

    @ResponseBody
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public Dictionary save(@RequestBody Dictionary item) {
		System.out.println("input device params:" + item.toString());
		Dictionary result = service.save(item);
		System.out.println("output device result data:" + result.toString());
		return result;
	}
    @ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Dictionary save2(@RequestBody Dictionary item) {
		 
                   Long categoryId =item.getCategory().getId();
                   if (categoryId > 0){
                   	  Category categoryObj = categoryService.findById(categoryId);
                      item.setCategory(categoryObj);
                   }

         

		System.out.println("input device params:" + item.toString());
		Dictionary result = service.save(item);
		System.out.println("output device result data:" + result.toString());
		return result;
	}


    @ResponseBody
 	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
 	public Dictionary update(@PathVariable Long id, @RequestBody Dictionary item) {
 		System.out.println("input device params:" + item.toString());
 		Dictionary result = service.save(item);
 		System.out.println("output device result data:" + result.toString());
 		return result;
 	}

 	@ResponseBody
     	@RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
     	public Dictionary updateSave(@PathVariable Long id, @RequestBody Dictionary item) {
     	   
           Long categoryId =item.getCategory().getId();
           if (categoryId > 0){
           	  Category categoryObj = categoryService.findById(categoryId);
              item.setCategory(categoryObj);
           }

           



     		System.out.println("input device params:" + item.toString());
     		Dictionary result = service.save(item);
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


    
    @ResponseBody
    @RequestMapping(value = "/queryByCategory/", method = RequestMethod.GET)
    public List<Dictionary> findByParams(@RequestParam("category") String category) {
           	System.out.println("input param category:" + category);
            Category citem  = categoryService.findOneByName(category);
           	List<Dictionary> result = service.findByCategory(citem);
        	return result;
    }
   

}