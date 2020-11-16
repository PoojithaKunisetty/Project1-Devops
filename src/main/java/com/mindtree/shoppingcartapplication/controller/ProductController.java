package com.mindtree.shoppingcartapplication.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mindtree.shoppingcartapplication.entity.Product;
import com.mindtree.shoppingcartapplication.exception.SearchByProductNameException;
import com.mindtree.shoppingcartapplication.repository.ProductRepository;
import com.mindtree.shoppingcartapplication.service.ProductService;


@RestController @CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/products")
public class ProductController
{
	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	ProductService productService;
	
	@Autowired
	ProductRepository productRepo;
	
	/**
	* @param productName
    * @return a product
	* @throws SearchByProductNameException
	*/
	@GetMapping("/{productName}")
	public ResponseEntity<Map<String, Object>> searchByProductName(@PathVariable String productName) throws SearchByProductNameException 
	{
		logger.info("ProductController:searchByProductName:started");
		Map<String, Object> mapObj = new HashMap<String, Object>();
		try {
			Optional<Product> searchProductName = this.productService.getByProductNameDetails(productName);
			mapObj.put("httpStatus", "getSuccess");
			mapObj.put("body", searchProductName);
			mapObj.put("success", true);
			mapObj.put("error", false);
		} catch (SearchByProductNameException e) {

			mapObj.put("httpStatus", "badRequest");
			mapObj.put("message",  e.getMessage());
			mapObj.put("success", false);
			mapObj.put("error", true);
		}
		logger.info("ProductController:searchByProductName:ended");
		return ResponseEntity.status(HttpStatus.OK).header("status", String.valueOf(HttpStatus.OK)).body(mapObj);
	}
	
	/**
	 * add the data into the database
	 */
	@GetMapping("/addData")
	public Map<String, Object> addDataBaseDetails() 
	{
		logger.info("ProductController:addDataBaseDetails:started");
		Map<String, Object> mapObj= new HashMap<String, Object>();
		String message=productService.addDataIntoDataBase();
		mapObj.put("httpStatus", "getSuccess");
		mapObj.put("message", message);
		mapObj.put("success", true);
		mapObj.put("error", false);
		logger.info("ProductController:addDataBaseDetails:ended");
		return mapObj;
	}
	
	
 }