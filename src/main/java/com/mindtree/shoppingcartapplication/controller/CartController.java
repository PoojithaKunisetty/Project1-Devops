package com.mindtree.shoppingcartapplication.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mindtree.shoppingcartapplication.entity.Cart;
import com.mindtree.shoppingcartapplication.exception.CartIsEmptyException;
import com.mindtree.shoppingcartapplication.exception.NoUserFoundException;
import com.mindtree.shoppingcartapplication.exception.ProductDoesNotExistsException;
import com.mindtree.shoppingcartapplication.exception.ProductDoesNotExistsInCartException;
import com.mindtree.shoppingcartapplication.service.CartService;


@RestController @CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	CartService cartService;
	
	private static final Logger logger = LoggerFactory.getLogger(CartController.class);
	
	/**
	 * add the products into the cart
	 * @param productIds
	 * @return mapObj
	 * @throws ProductDoesNotExistsException
	 * @throws NoUserFoundException
	 */
	@PostMapping("/products")
	public ResponseEntity<Map<String, Object>> addProductsIntoCart(@RequestBody List<Integer> productIds) throws ProductDoesNotExistsException, NoUserFoundException
	{
		logger.info("CartController:addProdutsIntoCart:started");
		Map<String, Object> mapObj= new HashMap<String, Object>();
		try{
		String message=cartService.addProducts(productIds);
		mapObj.put("httpStatus", "getSuccess");
		mapObj.put("body", message);
		mapObj.put("success", true);
		mapObj.put("error", false);
		}
		catch (ProductDoesNotExistsException|NoUserFoundException e) {
			mapObj.put("httpStatus", "badRequest");
			mapObj.put("message", e.getMessage());
			mapObj.put("success", false);
			mapObj.put("error", true);
		}
		logger.info("CartController:addProdutsIntoCart:ended");
		return ResponseEntity.status(HttpStatus.OK)
				.header("status", String.valueOf(HttpStatus.OK))
				.body(mapObj);
    }
	 /**
	 * delete a product in the cart
	 * @param productId
	 * @return mapObj
	 * @throws ProductDoesNotExistsException
	 * @throws ProductDoesNotExistsInCartException
	 */
	  @DeleteMapping("/products/{productId}")
	  public ResponseEntity<Map<String, Object>> deleteProductInCart(@PathVariable int productId)throws ProductDoesNotExistsException,ProductDoesNotExistsInCartException
	  {
		  logger.info("CartController:deleteProductInCart:started");
		  Map<String, Object> mapObj= new HashMap<String, Object>();
			try{
	        String message= cartService.deleteProduct(productId);
			mapObj.put("httpStatus", "getSuccess");
			mapObj.put("body", message);
			mapObj.put("success", true);
			mapObj.put("error", false);
			}
			catch (ProductDoesNotExistsException|ProductDoesNotExistsInCartException e) {
				mapObj.put("httpStatus", "badRequest");
				mapObj.put("message",  e.getMessage());
				mapObj.put("success", false);
				mapObj.put("error", true);
			}
			logger.info("CartController:deleteProductInCart:ended");
			return ResponseEntity.status(HttpStatus.OK)
					.header("status", String.valueOf(HttpStatus.OK))
					.body(mapObj);
		}
	 /**
	 * delete all products in the cart
	 * @return mapObj
	 * @throws NoUserFoundException 
	 * @throws CartIsEmptyException
	 */
	  @DeleteMapping("/products")
	  public ResponseEntity<Map<String, Object>> deleteAllProductsInCart() throws CartIsEmptyException, NoUserFoundException
	  {
		  logger.info("CartController:deleteAllProductsInCart:started");
		  Map<String, Object> mapObj= new HashMap<String, Object>();
			try{
		    String message=cartService.deleteALLProducts();
			mapObj.put("httpStatus", "getSuccess");
			mapObj.put("body", message);
			mapObj.put("success", true);
			mapObj.put("error", false);
			}
			catch (CartIsEmptyException|NoUserFoundException e) {
					mapObj.put("httpStatus", "badRequest");
					mapObj.put("message", e.getMessage());
					mapObj.put("success", false);
					mapObj.put("error", true);
				}
			logger.info("CartController:deleteAllProductsInCart:ended");
			return ResponseEntity.status(HttpStatus.OK)
					.header("status", String.valueOf(HttpStatus.OK))
					.body(mapObj);
	  } 
	 /**
	 * total price of products in the cart
	 * @return mapObj
	 * @throws CartIsEmptyException
	 * @throws NoUserFoundException 
	 */
	 @GetMapping("/totalPrice")
	  public ResponseEntity<Map<String, Object>> displayTotalAmountOfProducts() throws CartIsEmptyException, NoUserFoundException
	  {
		 logger.info("CartController:displayTotalAmountOfProducts:started");
		 Map<String, Object> mapObj= new HashMap<String, Object>();
			try{
		    String message= cartService.cartTotalAmount();
			mapObj.put("httpStatus", "getSuccess");
			mapObj.put("body", message);
			mapObj.put("success", true);
			mapObj.put("error", false);
			}
			catch (CartIsEmptyException|NoUserFoundException e) {
		
				mapObj.put("httpStatus", "badRequest");
				mapObj.put("message",  e.getMessage());
				mapObj.put("success", false);
				mapObj.put("error", true);
			}
		    logger.info("CartController:displayTotalAmountOfProducts:ended");
			return ResponseEntity.status(HttpStatus.OK)
					.header("status", String.valueOf(HttpStatus.OK))
					.body(mapObj);
	  }	   
	 /**
	 * display all the products in the cart 
	 * @return mapObj
	 * @throws CartIsEmptyException
	 */
	  @GetMapping("/products")
	  public ResponseEntity<Map<String, Object>> getAllProductsfromCart() throws CartIsEmptyException
	  {
		  logger.info("CartController:getAllProductsfromCart:started");
		  Map<String, Object> mapObj= new HashMap<String, Object>();
			try{
		  List<Cart> allProducts = new ArrayList<>();
		  allProducts=cartService.getAllProductsfromCart();
		  mapObj.put("httpStatus", "getSuccess");
		  mapObj.put("body", allProducts);
		  mapObj.put("success", true);
		  mapObj.put("error", false);
		 }
			catch (CartIsEmptyException e) {
				mapObj.put("httpStatus", "badRequest");
				mapObj.put("message",  e.getMessage());
				mapObj.put("success", false);
				mapObj.put("error", true);
			}
		   logger.info("CartController:getAllProductsfromCart:ended");
			return ResponseEntity.status(HttpStatus.OK)
					.header("status", String.valueOf(HttpStatus.OK))
					.body(mapObj);
	  }
	 /**
	 * increase the quantity of the product in the cart
	 * @param productId
	 * @return mapObj
	 * @throws ProductDoesNotExistsException
	 * @throws ProductDoesNotExistsInCartException
	 * @throws NoUserFoundException 
	 */
	  @PutMapping("/products/increaseQuantity/{productId}")
	  public ResponseEntity<Map<String, Object>> increaseProductQuantity(@PathVariable int productId) throws ProductDoesNotExistsInCartException, ProductDoesNotExistsException, NoUserFoundException
	  {
		  logger.info("CartController:increaseProductQuantity:started");
		  Map<String, Object> mapObj= new HashMap<String, Object>();
			try{
		    String message= cartService.updateProductQuantity(productId);
			mapObj.put("httpStatus", "getSuccess");
			mapObj.put("body", message);
			mapObj.put("success", true);
			mapObj.put("error", false);
			}
			catch (ProductDoesNotExistsException|ProductDoesNotExistsInCartException|NoUserFoundException e) {
				mapObj.put("httpStatus", "badRequest");
				mapObj.put("message", e.getMessage());
				mapObj.put("success", false);
				mapObj.put("error", true);
			}
		  logger.info("CartController:increaseProductQuantity:ended");
		  return ResponseEntity.status(HttpStatus.OK)
					.header("status", String.valueOf(HttpStatus.OK))
					.body(mapObj);
	  } 
	 /**
	 * decrease the quantity of the product in the cart
	 * @param productId
	 * @return mapObj
	 * @throws ProductDoesNotExistsInCartException
	 * @throws ProductDoesNotExistsException
	 * @throws NoUserFoundException 
	 */
	  @PutMapping("/products/decreaseQuantity/{productId}")
	  public ResponseEntity<Map<String, Object>> decreaseProductQuantity(@PathVariable int productId) throws ProductDoesNotExistsInCartException, ProductDoesNotExistsException, NoUserFoundException
	  {
		  logger.info("CartController:decreaseProductQuantity:started");
		  Map<String, Object> mapObj= new HashMap<String, Object>();
			try{
		    String getInfo=cartService.decreasePrdQuantity(productId);
			mapObj.put("httpStatus", "getSuccess");
			mapObj.put("body", getInfo);
			mapObj.put("success", true);
			mapObj.put("error", false);
			}
			catch (ProductDoesNotExistsException|ProductDoesNotExistsInCartException|NoUserFoundException e) {
				mapObj.put("httpStatus", "badRequest");
				mapObj.put("message",  e.getMessage());
				mapObj.put("success", false);
				mapObj.put("error", true);
			}
		  logger.info("CartController:decreaseProductQuantity:ended");
		  return ResponseEntity.status(HttpStatus.OK)
					.header("status", String.valueOf(HttpStatus.OK))
					.body(mapObj);
	  }
}
