package com.mindtree.shoppingcartapplication.service.Impl;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.mindtree.shoppingcartapplication.entity.Cart;
import com.mindtree.shoppingcartapplication.entity.Product;
import com.mindtree.shoppingcartapplication.exception.CartIsEmptyException;
import com.mindtree.shoppingcartapplication.exception.NoUserFoundException;
import com.mindtree.shoppingcartapplication.exception.ProductDoesNotExistsException;
import com.mindtree.shoppingcartapplication.exception.ProductDoesNotExistsInCartException;
import com.mindtree.shoppingcartapplication.repository.CartRepository;
import com.mindtree.shoppingcartapplication.repository.ProductRepository;
import com.mindtree.shoppingcartapplication.service.CartService;

@Service
@Transactional(propagation=Propagation.REQUIRED)

public class CartServiceImpl implements CartService {
    
	private static final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);
	
	@Autowired
	CartRepository cartRepo;
	
	@Autowired
	ProductRepository productRepo;
	
	/**
	 * delete a product in the cart
	 */
	@Override
	public String deleteProduct( int productId) throws ProductDoesNotExistsException, ProductDoesNotExistsInCartException 
	{
		logger.info("CartServiceImpl:deleteProduct:started");
		Optional<Product> findProduct = productRepo.findById(productId);
		if (!findProduct.isPresent())
			throw new ProductDoesNotExistsException("product doesn't exists in the product table");
		findProduct.get().setQuantity(0);
		int checkProductdObj = 0;
		checkProductdObj = cartRepo.deleteByProductId(productId);
		System.out.println(checkProductdObj);
		if (checkProductdObj == 0) 
			throw new ProductDoesNotExistsInCartException("product is not present in the  cart");
		logger.info("CartServiceImpl:deleteProduct:ended");
		return "Successfully removed product from the cart";
	}
	
	/**
	 * remove all the products from the cart
	 */
	@Override
	public String deleteALLProducts() throws CartIsEmptyException, NoUserFoundException 
	{
		logger.info("CartServiceImpl:deleteALLProducts:started");
		String message = "";
		List<Product> productdsList = new ArrayList<>();
		List<Product> productdList = getProductsFromCart();
		if (productdList.isEmpty())
			throw new CartIsEmptyException("cart is empty");
		for (Product product : productdList) {
			product.setQuantity(0);
			productdsList.add(product);
		}
		cartRepo.deleteAllProducts(productdsList);
		message = "Successfully removed all the products in the cart";
		logger.info("CartServiceImpl:deleteALLProducts:ended");
		return message;
	}
	
	/**
	 * display the total amount of  products in the cart
	 */
	@Override
	public String cartTotalAmount() throws CartIsEmptyException, NoUserFoundException
	{
		logger.info("CartServiceImpl:cartTotalAmount:started");
		List<Float> floatValues = new ArrayList<>();
		String message = null;
		float totalPrice = 0.0f;float sum = 0.0f;
		List<Product> getProducts = getProductsFromCart();
		if (getProducts.isEmpty()) 
			throw new CartIsEmptyException("cart is empty");
		for (Product productObj : getProducts) {
			totalPrice = productObj.getPrice() * productObj.getQuantity();
			floatValues.add(totalPrice);
		}
		for (Float floatObj : floatValues) {
			sum = sum + floatObj;
		}
		message = "total price of products in the cart : " + sum;
		logger.info("CartServiceImpl:cartTotalAmount:ended");
		return message;
	}

	/**
	 * user can view the products in the cart
	 */
	@Override
	public List<Cart> getAllProductsfromCart() throws CartIsEmptyException 
	{
		logger.info("CartServiceImpl:getAllProductsfromCart:started");
		List<Cart> cartList = cartRepo.findAll();
		List<Product> getProductsFromCart = new ArrayList<>();
		getProductsFromCart = cartList.get(0).getProducts();
		if (getProductsFromCart.isEmpty()) 
		throw new CartIsEmptyException("cart is empty");
		logger.info("CartServiceImpl:getAllProductsfromCart:ended");
		return cartList;
	}
	
	/**
	 * adding the products into the cart
	 * if product is already exists it increases the quantity 
	 */
	@Override
	public String addProducts(List<Integer> productIds) throws ProductDoesNotExistsException, NoUserFoundException
	{
		logger.info("CartServiceImpl:addProducts:started");
		List<Cart> cartList = cartRepo.findAll();
		List<Product> getProducts = getProductsFromCart();
		List<Integer> addProductsIds = new ArrayList<>();
		Cart cartObj = new Cart();  int flag = 0;
		if (cartList.size() == 0)
		 throw new NoUserFoundException("please add the user");
		else
		 cartObj.setCartId(cartList.get(0).getCartId());
		for (Integer productIdObj : productIds) 
		{
			for (Product cartProduct : getProducts) 
			{
				if (productIdObj == cartProduct.getProductId()) {
					int prdQuantity = cartProduct.getQuantity();
					cartProduct.setQuantity(++prdQuantity);
					flag = 1; }
			}
			if (flag == 0)
			addProductsIds.add(productIdObj);
		}
		List<Product> productsList = new ArrayList<>();
		List<Integer> checkProducts = new ArrayList<>();
		for (Integer productId : addProductsIds) 
		{
			Product getProduct = productRepo.getOne(productId);
			checkProducts.add(getProduct.getProductId());
		}
		productsList = productRepo.findAllById(checkProducts);
		for (int i = 0; i < addProductsIds.size(); i++) {
			if (productsList.isEmpty())
				throw new ProductDoesNotExistsException("product doesn't exists in the products table");
			if (productsList.get(i).getQuantity() == 0)
				productsList.get(i).setQuantity(1);
			else {
				int q = productsList.get(i).getQuantity();
				productsList.get(i).setQuantity(++q);
			}
		}
		getProducts.addAll(productsList);
		cartObj.setProducts(getProducts);
		cartRepo.save(cartObj);
		logger.info("CartServiceImpl:addProducts:started");
		return "successfully added  products into the cart";
	}
	
	/**
	 * increase the quantity of the product
	 */
	@Override
	public String updateProductQuantity(int productId) throws ProductDoesNotExistsInCartException, ProductDoesNotExistsException, NoUserFoundException
	{
		logger.info("CartServiceImpl:updateProductQuantity:started");
		List<Product> productsList = getProductsFromCart();
		Optional<Product> findProductId = productRepo.findById(productId);
		if (!findProductId.isPresent())
			throw new ProductDoesNotExistsException("product doesn't exists in the products table");
		Product product = findProductId.get();
		if (productsList.contains(product)) {
			int productQua = findProductId.get().getQuantity();
			product = findProductId.get();
			product.setQuantity(++productQua);
			productRepo.save(product);
		} else
			throw new ProductDoesNotExistsInCartException("product is not present in the  cart");
		logger.info("CartServiceImpl:updateProductQuantity:ended");
		return "successfully increase the quantity of the product";
	}
	
	 /**
	 * decrease the quantity of the product
	 */
	 @Override
	   public String decreasePrdQuantity(int productId) throws ProductDoesNotExistsInCartException, ProductDoesNotExistsException, NoUserFoundException 
	  {
		logger.info("CartServiceImpl:decreasePrdQuantity:started");
		String getMessage = "";
		List<Product> getProducts = getProductsFromCart();
		Optional<Product> findProduct = productRepo.findById(productId);
		if (!findProduct.isPresent()) 
			throw new ProductDoesNotExistsException("product doesn't exists in the products table");
		Product product = findProduct.get();
		if (getProducts.contains(product)) {
			int productQuantity = findProduct.get().getQuantity();
			if (productQuantity == 1) {
				product.setQuantity(0);
				cartRepo.deleteByProductId(productId);
				getMessage = "successfully delete the product because quantity having zero";
			} else {
				findProduct.get().setQuantity(--productQuantity);
				productRepo.save(product);
				getMessage = "successfully decrease the quantity of the product";
			}
		} else
			throw new ProductDoesNotExistsInCartException("product is not present in the  cart");
		logger.info("CartServiceImpl:decreasePrdQuantity:ended");
		return getMessage;
	}
	    /**
		 * get the products from the cart
		 */
	    public List<Product> getProductsFromCart() throws NoUserFoundException
	    {
	    	logger.info("CartServiceImpl:getProductsFromCart:started");
	    	List<Cart> cartObj=cartRepo.findAll();
	    	List<Product> productObj=new ArrayList<>();
	    	if(cartObj.size()==0)
	    	throw new NoUserFoundException("Please add user");
	    	productObj =cartObj.get(0).getProducts();
	    	logger.info("CartServiceImpl:getProductsFromCart:ended");
			return productObj;
	    }
 }