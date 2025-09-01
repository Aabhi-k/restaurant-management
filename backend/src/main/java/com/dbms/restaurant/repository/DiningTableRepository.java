package com.dbms.restaurant.repository;

import com.dbms.restaurant.models.DiningTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface DiningTableRepository extends JpaRepository<DiningTable, Long> {
    Optional<DiningTable> findByTableNumber(Integer tableNumber);
    List<DiningTable> findByLocation(String location);
    List<DiningTable> findBySeatingCapacityGreaterThanEqual(Integer capacity);
}
