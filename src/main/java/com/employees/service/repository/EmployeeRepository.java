package com.employees.service.repository;

import com.employees.service.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    Employee findEmployeeByEmail(String employeeEmail);
    Boolean existsEmployeeByEmail(String email);
    Boolean existsEmployeeByMobile(String mobile);
}
