package com.mindtree.shoppingcartapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mindtree.shoppingcartapplication.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

}
