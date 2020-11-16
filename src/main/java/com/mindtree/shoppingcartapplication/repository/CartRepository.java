package com.mindtree.shoppingcartapplication.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.mindtree.shoppingcartapplication.entity.Cart;
import com.mindtree.shoppingcartapplication.entity.Product;


@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

	@Modifying
	@Query(value="delete from cart_products where products_product_id=:productId",nativeQuery = true)
	public int deleteByProductId(int productId);
	
	@Modifying
	@Query(value="truncate cart_products",nativeQuery = true)
	public int deleteAllProducts(List<Product> productdList);
	
	
}
