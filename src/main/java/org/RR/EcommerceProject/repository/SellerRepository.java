package org.RR.EcommerceProject.repository;

import org.RR.EcommerceProject.dto.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, Integer> {

	boolean existsByEmail(String email);

	boolean existsByMobile(long mobile);

	Seller findByEmail(String email);

}
