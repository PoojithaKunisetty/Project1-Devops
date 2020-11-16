package com.mindtree.shoppingcartapplication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mindtree.shoppingcartapplication.entity.Product;


@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> 
{

       @Query(value="select * from product where product_name=:productName",nativeQuery = true)
	   public Optional<Product> findByproductName(String productName);
  
	  
}
