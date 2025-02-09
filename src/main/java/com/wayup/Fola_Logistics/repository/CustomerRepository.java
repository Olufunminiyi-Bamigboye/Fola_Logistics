package com.wayup.Fola_Logistics.repository;

import com.wayup.Fola_Logistics.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByEmail(String email);
}
