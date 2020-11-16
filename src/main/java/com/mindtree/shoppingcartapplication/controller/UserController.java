package com.mindtree.shoppingcartapplication.controller;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mindtree.shoppingcartapplication.entity.User;
import com.mindtree.shoppingcartapplication.exception.UserAlreadyExistsException;
import com.mindtree.shoppingcartapplication.service.UserService;


@RestController
@RequestMapping("/user")
public class UserController
{
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	UserService userService;
	
	/**
	 * @param user
	 * adding a user into the database
	 * @throws UserAlreadyExistsException
	 */
	@PostMapping("/")
	public ResponseEntity<Map<String, Object>> addUser(@RequestBody User user) throws UserAlreadyExistsException
	{
		logger.info("UserController:addUser:started");
		Map<String, Object> mapObj= new HashMap<String, Object>();
		try
		{
		String message=userService.addUserintoCart(user);
		 mapObj.put("httpStatus", "getSuccess");
		 mapObj.put("body", message);
		 mapObj.put("success", true);
		 mapObj.put("error", false);
		}
		 catch (UserAlreadyExistsException e) 
		{
				mapObj.put("httpStatus", "badRequest");
				mapObj.put("message",  e.getMessage());
				mapObj.put("success", false);
				mapObj.put("error", true);
		}
		 logger.info("UserController:addUser:ended");
		 return ResponseEntity.status(HttpStatus.OK)
					.header("status", String.valueOf(HttpStatus.OK))
					.body(mapObj);
	}
	
}

