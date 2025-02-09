package com.wayup.Fola_Logistics.repository;

import com.wayup.Fola_Logistics.entity.PackageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PackageRequestRepository extends JpaRepository<PackageRequest, Long> {
    List<PackageRequest> findByStatus (PackageRequest.Status status );
}
