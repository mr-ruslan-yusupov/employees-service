package com.employees.service.repository;

import com.employees.service.model.EmployeeRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRoleRepository extends JpaRepository<EmployeeRole,Long> {
    EmployeeRole findEmployeeRoleByName(String employeeRoleName);
}
