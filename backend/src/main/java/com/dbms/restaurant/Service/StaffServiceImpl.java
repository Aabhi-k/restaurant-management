package com.dbms.restaurant.Service;

import com.dbms.restaurant.models.Staff;
import com.dbms.restaurant.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StaffServiceImpl implements StaffService {
    
    @Autowired
    private StaffRepository staffRepository;
    
    @Override
    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }
    
    @Override
    public Optional<Staff> getStaffById(Long id) {
        return staffRepository.findById(id);
    }
    
    @Override
    public Staff saveStaff(Staff staff) {
        return staffRepository.save(staff);
    }
    
    @Override
    public void deleteStaff(Long id) {
        staffRepository.deleteById(id);
    }
    
    @Override
    public List<Staff> getStaffByRole(String roleName) {
        return staffRepository.findByStaffRoleRoleName(roleName);
    }
    
    @Override
    public Optional<Staff> getStaffByEmail(String email) {
        return staffRepository.findByEmail(email);
    }
    
    @Override
    public List<Staff> searchStaffByName(String name) {
        return staffRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(name, name);
    }
}
