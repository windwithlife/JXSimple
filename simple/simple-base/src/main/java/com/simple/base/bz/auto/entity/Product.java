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
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
          
    //名称
    private String name;
      
    //年龄
    private int age;
      
    @JoinColumn(name = "level_id") // 关联表的字段
    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.EAGER)
    private Level level;
    

      
    @JoinColumn(name = "dictionary_id") // 关联表的字段
    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.EAGER)
    private Dictionary sex;
    

      
    //图片
    private String pic;
      
    //产品详情图片
    private String productImg;
      
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
     
     //年龄
     public int getAge(){
         return this.age;
     };
     public void setAge(int age){
         this.age = age;
     }
     

    public Level getLevel(){
         return this.level;
    };
    public void setLevel(Level level){
         this.level = level;
    }
                

    public Dictionary getSex(){
         return this.sex;
    };
    public void setSex(Dictionary sex){
         this.sex = sex;
    }
                
     //图片
     public String getPic(){
         return this.pic;
     };
     public void setPic(String pic){
         this.pic = pic;
     }
     
     //产品详情图片
     public String getProductImg(){
         return this.productImg;
     };
     public void setProductImg(String productImg){
         this.productImg = productImg;
     }
     



	@Override
	public String toString() {
		return "CLASS DATA: [id=" + id + ", name=" + name + "]";
	}
}
