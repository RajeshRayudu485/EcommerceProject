package org.RR.EcommerceProject.repository;

import java.util.List;

import org.RR.EcommerceProject.dto.Customer;
import org.RR.EcommerceProject.dto.customerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface customerOrderRepository extends JpaRepository<customerOrder, Integer>  {

	List<customerOrder> findByCustomerAndPaymentIdIsNotNull(Customer customer);

}
