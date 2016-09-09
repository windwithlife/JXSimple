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

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
          
     //名称
     private String name;
      
     //性别
     private int sex;
      
     //年龄
     private int age;
      
     public Product() {
	 }
     
     //编号
     public Long getId(){
         return this.id;
     };
     public void setId(Long id){
         this.id = id;
     }
     
     //名称
     public String getName(){
         return this.name;
     };
     public void setName(String name){
         this.name = name;
     }
     
     //性别
     public int getSex(){
         return this.sex;
     };
     public void setSex(int sex){
         this.sex = sex;
     }
     
     //年龄
     public int getAge(){
         return this.age;
     };
     public void setAge(int age){
         this.age = age;
     }
     



	@Override
	public String toString() {
		return "CLASS DATA: [id=" + id + ", name=" + name + "]";
	}
}
