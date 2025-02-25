package com.wayup.Fola_Logistics.repository;

import com.wayup.Fola_Logistics.entity.PackageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PackageRequestRepository extends JpaRepository<PackageRequest, Long> {
    List<PackageRequest> findByStatus (PackageRequest.Status status );

    @Query("SELECT c FROM PackageRequest c WHERE c.status ='PICKED_UP'")
    List<PackageRequest> findAcceptedRequest();
}
