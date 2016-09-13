package com.simple.base.bz.auto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.simple.base.bz.auto.dao.*;
import com.simple.base.bz.auto.entity.*;


@Service
public class DictionaryService {
	@Autowired
	DictionaryRepository dao;
	public List<Dictionary> findAll(){
		return  dao.findAll();
		//return items;
	}
	public  List<Dictionary> findByName(String name){
		return dao.findByName(name);
	}
	public  Dictionary findOneByName(String name){
    		return dao.findOneByName(name);
    	}

	public Dictionary findById(Long id){
		return dao.findOne(id);
	}
	public Dictionary save(Dictionary item){
		return this.dao.save(item);
	}
	public void remove(Long id){
		this.dao.delete(id);
	}
	
	public  List<Dictionary> findByCategory(Category category){
		return dao.findByCategory(category);
	}
	
}
