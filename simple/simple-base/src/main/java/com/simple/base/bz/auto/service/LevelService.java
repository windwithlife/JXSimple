package com.simple.base.bz.auto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.simple.base.bz.auto.dao.*;
import com.simple.base.bz.auto.entity.*;


@Service
public class LevelService {
	@Autowired
	LevelRepository dao;
	public List<Level> findAll(){
		return  dao.findAll();
		//return items;
	}
	public  List<Level> findByName(String name){
		return dao.findByName(name);
	}
	public  Level findOneByName(String name){
    		return dao.findOneByName(name);
    	}

	public Level findById(Long id){
		return dao.findOne(id);
	}
	public Level save(Level item){
		return this.dao.save(item);
	}
	public void remove(Long id){
		this.dao.delete(id);
	}
	
}
