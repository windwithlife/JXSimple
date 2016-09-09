package com.simple.base;


import java.util.ArrayList;
import java.util.List;
//import java.util.logging.Logger;

import javax.persistence.Entity;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

import com.simple.base.bz.auto.entity.*;



@Configuration
class DataRestConfiguration{
	//Logger logger = LoggerFactory.getLogger(HelloWorld.class);

	static List<Class> clsList = new ArrayList();
	
	
    @Bean
    public RepositoryRestConfigurer repositoryRestConfigurer() {

        return new RepositoryRestConfigurerAdapter() {
            @Override
            public void configureRepositoryRestConfiguration(
                    RepositoryRestConfiguration config) {
            	config.exposeIdsFor(Product.class);
            	/*System.out.println("Registry Entity begin Entity Total Count: -------------" + clsList.size());
            	for(Class cls: clsList){
            		System.out.println("Registry Entity Name: -------------" + cls.getName());
            		config.exposeIdsFor(cls);
            	}*/
            }
        };
    }

    //此方法执行时机过晚，不起作用。
	
	public void onApplicationEvent(ContextRefreshedEvent event) {
		// TODO Auto-generated method stub
		try {
			// 获取上下文
			ApplicationContext context = event.getApplicationContext();
			// 获取所有beanNames
			String[] beanNames = context.getBeanNamesForType(Object.class);
			for (String beanName : beanNames) {
			        
				Entity helloClass = context.findAnnotationOnBean(beanName,
						Entity.class);
				//判断该类是否含有HelloClass注解		
				if (helloClass != null) {
					Class item = context.getBean(beanName).getClass();
					this.clsList.add(item);
					
					System.out.println("Get Entity Name: -------------" + item.getName());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}