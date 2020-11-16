package com.mindtree.shoppingcartapplication.service;

import com.mindtree.shoppingcartapplication.entity.User;
import com.mindtree.shoppingcartapplication.exception.UserAlreadyExistsException;


public interface UserService 
{

	String addUserintoCart(User user) throws UserAlreadyExistsException;

}
