package com.mindtree.shoppingcartapplication.service.Impl;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.mindtree.shoppingcartapplication.entity.Apparel;
import com.mindtree.shoppingcartapplication.entity.Book;
import com.mindtree.shoppingcartapplication.entity.Product;
import com.mindtree.shoppingcartapplication.exception.SearchByProductNameException;
import com.mindtree.shoppingcartapplication.repository.CartRepository;
import com.mindtree.shoppingcartapplication.repository.ProductRepository;
import com.mindtree.shoppingcartapplication.service.ProductService;


@Service
@Transactional(propagation=Propagation.REQUIRED)

public class ProductServiceImpl implements ProductService {
	
	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
	
	@Autowired
	ProductRepository productRepo;

	@Autowired
	CartRepository cartRepo;

	
	/**
	 * search by particular product based on productName
	 */
	@Override
	 public Optional<Product> getByProductNameDetails(String productName) throws SearchByProductNameException {
		logger.info("ProductServiceImpl:getByProductNameDetails:started");
		Optional<Product> searchProductByName = productRepo.findByproductName(productName);
		System.out.println(searchProductByName);
        if(!searchProductByName.isPresent())
        {
        	throw new SearchByProductNameException("product doesn't exists in the products table");
        }
		logger.info("ProductServiceImpl:getByProductNameDetails:ended");
		List<Product>prd=productRepo.findAll();
		System.out.println(prd);
		return searchProductByName;
	}
	
	/**
	 * insert data into the database
	 */
	@Override
	public String addDataIntoDataBase() {
       
	   logger.info("ProductServiceImpl:addDataIntoDataBase:started");
		Book b = new Book();
		b.setProductId(1);
		b.setProductName("Book1");
		b.setPublications("Sun");
		b.setAuthor("Srinivasa");
		b.setGenre("Thriller");
		b.setPrice(500);
		productRepo.save(b);

		Book b1 = new Book();
		b1.setProductId(2);
		b1.setProductName("Book2");
		b1.setPublications("Surya");
		b1.setAuthor("Ganesh");
		b1.setGenre("Fiction");
		b1.setPrice(8000);
		productRepo.save(b1);

		Book b2 = new Book();
		b2.setProductId(3);
		b2.setProductName("Book3");
		b2.setPublications("Chandra");
		b2.setAuthor("Chaitanya");
		b2.setGenre("Action");
		b2.setPrice(1500);
		productRepo.save(b2);

		Apparel a = new Apparel();
		a.setProductId(4);
		a.setProductName("Kurthi");
		a.setType("fron slit");
		a.setBrand("Zara");
		a.setDesign("straight");
		a.setPrice(600);
		productRepo.save(a);

		Apparel a1 = new Apparel();
		a1.setProductId(5);
		a1.setProductName("tops");
		a1.setType("xyz");
		a1.setBrand("Biba");
		a1.setDesign("Half-sleeves");
		a1.setPrice(1600);
		productRepo.save(a1);
		logger.info("ProductServiceImpl:addDataIntoDataBase:ended");
		return "Successfully Added Data into the Database";
	}

}
