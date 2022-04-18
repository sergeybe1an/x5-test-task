package com.sbelan.x5testproject.repository;

import com.sbelan.x5testproject.model.business.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
