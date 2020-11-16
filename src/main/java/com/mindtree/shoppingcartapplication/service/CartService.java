package com.mindtree.shoppingcartapplication.service;

import java.util.List;
import com.mindtree.shoppingcartapplication.entity.Cart;
import com.mindtree.shoppingcartapplication.exception.CartIsEmptyException;
import com.mindtree.shoppingcartapplication.exception.NoUserFoundException;
import com.mindtree.shoppingcartapplication.exception.ProductDoesNotExistsException;
import com.mindtree.shoppingcartapplication.exception.ProductDoesNotExistsInCartException;

public interface CartService {
	
	public String deleteProduct(int productId) throws ProductDoesNotExistsException,ProductDoesNotExistsInCartException;

	public String deleteALLProducts()throws CartIsEmptyException, NoUserFoundException;

	public String cartTotalAmount() throws CartIsEmptyException, NoUserFoundException;

	public List<Cart> getAllProductsfromCart() throws CartIsEmptyException;

	public String addProducts(List<Integer> productIds) throws ProductDoesNotExistsException, NoUserFoundException;

	public String updateProductQuantity(int productId) throws ProductDoesNotExistsInCartException, ProductDoesNotExistsException, NoUserFoundException;

	public String decreasePrdQuantity(int productId) throws ProductDoesNotExistsInCartException, ProductDoesNotExistsException, NoUserFoundException;
}
