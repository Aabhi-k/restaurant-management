package com.dbms.restaurant.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "staff_role")
public class StaffRole {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;
    
    @Column(name = "role_name", nullable = false, length = 50)
    private String roleName;
    
    @Column(name = "role_description", length = 255)
    private String roleDescription;
    
    @OneToMany(mappedBy = "staffRole", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Staff> staff;
    
    public StaffRole() {}
    
    public StaffRole(String roleName, String roleDescription) {
        this.roleName = roleName;
        this.roleDescription = roleDescription;
    }
    
    public Long getRoleId() {
        return roleId;
    }
    
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
    
    public String getRoleName() {
        return roleName;
    }
    
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    
    public String getRoleDescription() {
        return roleDescription;
    }
    
    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }
    
    public List<Staff> getStaff() {
        return staff;
    }
    
    public void setStaff(List<Staff> staff) {
        this.staff = staff;
    }
}
