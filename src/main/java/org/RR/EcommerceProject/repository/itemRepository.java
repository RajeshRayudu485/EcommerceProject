package org.RR.EcommerceProject.repository;

import org.RR.EcommerceProject.dto.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface itemRepository extends JpaRepository<Item, Integer>{

}
