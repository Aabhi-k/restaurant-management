package com.dbms.restaurant.Service;

import com.dbms.restaurant.models.Staff;
import java.util.List;
import java.util.Optional;

public interface StaffService {
    
    List<Staff> getAllStaff();
    
    Optional<Staff> getStaffById(Long id);
    
    Staff saveStaff(Staff staff);
    
    void deleteStaff(Long id);
    
    List<Staff> getStaffByRole(String roleName);
    
    Optional<Staff> getStaffByEmail(String email);
    
    List<Staff> searchStaffByName(String name);
}
