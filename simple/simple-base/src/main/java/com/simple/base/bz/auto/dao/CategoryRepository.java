package com.simple.base.bz.auto.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.simple.base.bz.auto.entity.*;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    public  List<Category> findByName(String name);
    public  List<Category> findByNameLike(String name);

    public  Category findOneByName(String name);

    
}
