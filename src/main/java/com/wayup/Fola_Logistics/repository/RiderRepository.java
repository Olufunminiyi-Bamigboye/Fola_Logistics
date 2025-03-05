package com.wayup.Fola_Logistics.repository;

import com.wayup.Fola_Logistics.entity.Rider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RiderRepository extends JpaRepository<Rider, Long> {
    boolean existsByEmail(String email);
    List<Boolean> findByIsAvailableTrue();
}
