package com.java8.java8hanghaetest.repository;

import com.java8.java8hanghaetest.model.Category;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {


    List<Category> findByCategoryName(String categoryName);
}
