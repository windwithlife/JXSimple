package com.simple.base.bz.auto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.simple.base.bz.auto.entity.*;
import com.simple.base.bz.auto.service.*;


import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/level")
public class LevelController {
	@Autowired
	LevelService service;

  


	@RequestMapping(value= "/", method=RequestMethod.GET)
    public String rootpage(){
    	       return "index";
    }
	@RequestMapping(value = "/queryAll", method = RequestMethod.GET)
	@ResponseBody
	public List<Level> findAll() {
		return service.findAll();
	}
	@ResponseBody
    @RequestMapping(value = "/query/{id}", method = RequestMethod.GET)
    public Level findById(@PathVariable Long id) {
       	System.out.println("input param Id:" + id);
       	Level result = service.findById(id);
    	return result;
    }
    @ResponseBody
    @RequestMapping(value = "/queryByNameLike/", method = RequestMethod.GET)
    public List<Level> findByNameLike(@RequestParam("name") String name ) {
           	System.out.println("input param Name:" + name);
            return service.findByNameLike(name);

    }

    @ResponseBody
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public Level save(@RequestBody Level item) {
		System.out.println("input device params:" + item.toString());
		Level result = service.save(item);
		System.out.println("output device result data:" + result.toString());
		return result;
	}
    @ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Level save2(@RequestBody Level item) {
		 

		System.out.println("input device params:" + item.toString());
		Level result = service.save(item);
		System.out.println("output device result data:" + result.toString());
		return result;
	}


    @ResponseBody
 	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
 	public Level update(@PathVariable Long id, @RequestBody Level item) {
 		System.out.println("input device params:" + item.toString());
 		Level result = service.save(item);
 		System.out.println("output device result data:" + result.toString());
 		return result;
 	}

 	@ResponseBody
     	@RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
     	public Level updateSave(@PathVariable Long id, @RequestBody Level item) {
     	   



     		System.out.println("input device params:" + item.toString());
     		Level result = service.save(item);
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