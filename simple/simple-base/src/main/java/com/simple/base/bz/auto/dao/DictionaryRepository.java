package com.simple.base.bz.auto.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.simple.base.bz.auto.entity.*;

public interface DictionaryRepository extends JpaRepository<Dictionary, Long> {
    public  List<Dictionary> findByName(String name);
    public  Dictionary findOneByName(String name);

    
	public  List<Dictionary> findByCategory(Category category);
    
}
