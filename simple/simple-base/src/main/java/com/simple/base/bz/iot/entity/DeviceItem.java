package com.simple.base.bz.iot.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

   
@Entity
public class DeviceItem implements Serializable{
	 private static final long serialVersionUID = 1L;
	    @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private Long   id; // 编号
	    private String name; // 角色标识程序中判断使用,如"admin",这个是唯一的:
	    private int    status; // 角色描述,UI界面显示使用
	   // private Long   typeid;
	    private int    temperature;
	    private int ext1; 
	    private int ext2; 
	  
	    @JoinColumn(name="type_id")//关联user表的字段
	    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},fetch=FetchType.EAGER,optional=false)
	    private DeviceType type;
	    
	    public DeviceItem(){
	    	this.name = "testfile";
	    	
	    }
	   
	    public Long getId() {
	        return id;
	     }
	    
	   
	     public void setId(Long id) {
	        this.id = id;
	     }
	     
	     public DeviceType getType() {
		        return this.type;
		 }
		    
		   
		  public void setType(DeviceType t) {
		        this.type = t;
		  }
	     
	  /*   public Long getTypeid() {
		        return this.typeid;
		 }
		    
		   
		     public void setTypeid(Long id) {
		        this.typeid = id;
		     }*/
	    
		public String getName(){
			return this.name;
		}
		public void setName(String name){
			 this.name = name;
		}
		public int getStatus(){
			return this.status;
		}
		public void setStatus(int s){
			 this.status = s;
		}
		public int getTemperature(){
			return this.temperature;
		}
		public void setTemperature(int t){
			 this.temperature = t;
		}
		
	     
	    @Override
	    public String toString() {
	       return "DeviceType DATA: [id=" + id + ", name=" + name + ", status=" + status + ", temperature=" + temperature + "]";
	    }
}
