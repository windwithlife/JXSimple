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
public class Dictionary implements Serializable {
	private static final long serialVersionUID = 1L;

    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
          
    //显示名称
    private String name;
      
    @JoinColumn(name = "category_id") // 关联表的字段
    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.EAGER)
    private Category category;
    

      
     public Dictionary() {
	 }
     
     //编号
     public Long getId(){
         return this.id;
     };
     public void setId(Long id){
         this.id = id;
     }
     
     //显示名称
     public String getName(){
         return this.name;
     };
     public void setName(String name){
         this.name = name;
     }
     

    public Category getCategory(){
         return this.category;
    };
    public void setCategory(Category category){
         this.category = category;
    }
                



	@Override
	public String toString() {
		return "CLASS DATA: [id=" + id + ", name=" + name + "]";
	}
}
