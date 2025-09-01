package com.dbms.restaurant.repository;

import com.dbms.restaurant.models.Staff;
import com.dbms.restaurant.models.StaffRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {
    Optional<Staff> findByEmail(String email);
    List<Staff> findByStaffRole(StaffRole staffRole);
    List<Staff> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);
    List<Staff> findByStaffRoleRoleName(String roleName);
}
