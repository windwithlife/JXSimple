package com.simple.base.bz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.simple.base.bz.dao.*;
import com.simple.base.bz.entity.*;


@Service
public class ProductService {
	@Autowired
	ProductRepository dao;
	public List<Product> findAll(){
		return  dao.findAll();
		//return items;
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
