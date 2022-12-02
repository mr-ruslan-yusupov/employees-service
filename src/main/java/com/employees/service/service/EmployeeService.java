package com.employees.service.service;

import com.employees.service.model.Employee;
import com.employees.service.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class EmployeeService {
    final private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Collection<Employee> findAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> findEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public Employee findEmployeeByEmail(String email) {
        return employeeRepository.findEmployeeByEmail(email);
    }

    public Boolean existsEmployeeByEmail(String email) {
        return employeeRepository.existsEmployeeByEmail(email);
    }

    public Boolean existsEmployeeByMobile(String mobile) {
        return employeeRepository.existsEmployeeByMobile(mobile);
    }

    public Employee saveOrUpdateEmployee(Employee employee) {
        return employeeRepository.saveAndFlush(employee);
    }

    public void deleteEmployee(Long employeeId) {
        employeeRepository.deleteById(employeeId);
    }

}
