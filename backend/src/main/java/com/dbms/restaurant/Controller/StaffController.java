package com.dbms.restaurant.Controller;

import com.dbms.restaurant.models.Staff;
import com.dbms.restaurant.Service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/staff")
public class StaffController {
    
    @Autowired
    private StaffService staffService;
    
    @GetMapping
    public ResponseEntity<List<Staff>> getAllStaff() {
        List<Staff> staffList = staffService.getAllStaff();
        return ResponseEntity.ok(staffList);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Staff> getStaffById(@PathVariable Long id) {
        Optional<Staff> staff = staffService.getStaffById(id);
        return staff.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/role/{roleName}")
    public ResponseEntity<List<Staff>> getStaffByRole(@PathVariable String roleName) {
        List<Staff> staffList = staffService.getStaffByRole(roleName);
        return ResponseEntity.ok(staffList);
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<Staff>> searchStaffByName(@RequestParam String name) {
        List<Staff> staffList = staffService.searchStaffByName(name);
        return ResponseEntity.ok(staffList);
    }
    
    @PostMapping
    public ResponseEntity<Staff> createStaff(@RequestBody Staff staff) {
        Staff savedStaff = staffService.saveStaff(staff);
        return ResponseEntity.ok(savedStaff);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Staff> updateStaff(@PathVariable Long id, @RequestBody Staff staff) {
        Optional<Staff> existingStaff = staffService.getStaffById(id);
        if (existingStaff.isPresent()) {
            staff.setStaffId(id);
            Staff updatedStaff = staffService.saveStaff(staff);
            return ResponseEntity.ok(updatedStaff);
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStaff(@PathVariable Long id) {
        Optional<Staff> existingStaff = staffService.getStaffById(id);
        if (existingStaff.isPresent()) {
            staffService.deleteStaff(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
