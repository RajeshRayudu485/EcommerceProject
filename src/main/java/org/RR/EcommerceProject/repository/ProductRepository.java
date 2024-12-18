package org.RR.EcommerceProject.repository;

import java.util.LinkedHashMap;
import java.util.List;

import org.RR.EcommerceProject.dto.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer>{

	List<Product> findBySeller_id(int id);

	List<Product> findByApprovedTrue();

	LinkedHashMap<String,Object> findByName(String name);

}
