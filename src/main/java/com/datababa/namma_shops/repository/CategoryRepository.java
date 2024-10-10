package com.datababa.namma_shops.repository;

import com.datababa.namma_shops.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByName(String name);
}
