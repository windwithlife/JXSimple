package com.simple.base.bz.auto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.simple.base.bz.auto.dao.*;
import com.simple.base.bz.auto.entity.*;


@Service
public class ProductService {
	@Autowired
	ProductRepository dao;
	public List<Product> findAll(){
		return  dao.findAll();
		//return items;
	}
	public  List<Product> findByName(String name){
		return dao.findByName(name);
	}
	public  List<Product> findByNameLike(String name){
    		return dao.findByNameLike(name);
    }

	public  Product findOneByName(String name){
    		return dao.findOneByName(name);
    	}

	public Product findById(Long id){
		return dao.findOne(id);
	}
	public Product save(Product item){
		return this.dao.save(item);
	}
	public void remove(Long id){
		this.dao.delete(id);
	}
	
}
