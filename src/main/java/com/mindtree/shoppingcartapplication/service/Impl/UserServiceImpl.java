package com.mindtree.shoppingcartapplication.service.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mindtree.shoppingcartapplication.entity.Cart;
import com.mindtree.shoppingcartapplication.entity.User;
import com.mindtree.shoppingcartapplication.exception.UserAlreadyExistsException;
import com.mindtree.shoppingcartapplication.repository.CartRepository;
import com.mindtree.shoppingcartapplication.repository.UserRepository;
import com.mindtree.shoppingcartapplication.service.UserService;

@Service
@Transactional(propagation=Propagation.REQUIRED)

public class UserServiceImpl implements UserService 
{
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	CartRepository cartRepo;
	
	/**
	 * add a user into the database
	 */
	@Override
	public String addUserintoCart(User user) throws UserAlreadyExistsException 
	{
		logger.info("UserServiceImpl:addUserintoCart:started");
		long userCount=userRepository.count();
		if(userCount==1)
		{
		  throw new UserAlreadyExistsException("only one user allowed");
		}else
		{
		Cart cartObject=new Cart();
		cartObject.setCartId(1);
		user.setCart(cartObject);
		userRepository.save(user);
	   }
		logger.info("UserServiceImpl:addUserintoCart:started");
		return "Successfully Added User";
	}

}
