package com.mindtree.shoppingcartapplication.service;

import java.util.Optional;
import com.mindtree.shoppingcartapplication.entity.Product;
import com.mindtree.shoppingcartapplication.exception.SearchByProductNameException;



public interface ProductService 
{

	public String addDataIntoDataBase();
	
    public Optional<Product> getByProductNameDetails(String productName) throws SearchByProductNameException;
	

}
