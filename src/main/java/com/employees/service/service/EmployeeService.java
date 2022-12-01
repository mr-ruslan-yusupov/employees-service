package com.employees.service.service;

import com.employees.service.exceptions.InvalidInputException;
import com.employees.service.exceptions.NotFoundException;
import com.employees.service.model.Employee;
import com.employees.service.repository.EmployeeRepository;
import org.springframework.dao.EmptyResultDataAccessException;
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

    public Employee findEmployeeById(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new NotFoundException("No employee found for employeeId: " + id));
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

    public void deleteEmployeeById(Long id) {
        employeeRepository.deleteById(id);
    }

}
