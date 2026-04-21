package com.example.UserManagement.repository;

import com.example.UserManagement.model.ELocationType;
import com.example.UserManagement.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    List<Location> findByType(ELocationType type);
    boolean existsByCode(String code);
    Optional<Location> findByCode(String code);
}
