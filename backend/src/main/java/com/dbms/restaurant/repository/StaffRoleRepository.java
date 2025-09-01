package com.dbms.restaurant.repository;

import com.dbms.restaurant.models.StaffRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StaffRoleRepository extends JpaRepository<StaffRole, Long> {
    Optional<StaffRole> findByRoleName(String roleName);
}
