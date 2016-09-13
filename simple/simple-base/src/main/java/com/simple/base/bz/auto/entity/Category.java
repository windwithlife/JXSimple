package com.simple.base.bz.auto.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.*;

import java.util.List;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
public class Category implements Serializable {
	private static final long serialVersionUID = 1L;

    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
          
    //字典类别名称
    private String name;
      
    //类别用途描述
    private String description;
      
     public Category() {
	 }
     
     //编号
     public Long getId(){
         return this.id;
     };
     public void setId(Long id){
         this.id = id;
     }
     
     //字典类别名称
     public String getName(){
         return this.name;
     };
     public void setName(String name){
         this.name = name;
     }
     
     //类别用途描述
     public String getDescription(){
         return this.description;
     };
     public void setDescription(String description){
         this.description = description;
     }
     



	@Override
	public String toString() {
		return "CLASS DATA: [id=" + id + ", name=" + name + "]";
	}
}
