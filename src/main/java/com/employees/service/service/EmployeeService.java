package com.employees.service.service;

import com.employees.service.model.Employee;
import com.employees.service.repository.EmployeeRepository;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
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

    public Collection<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> findById(Long id) {
        return employeeRepository.findById(id);
    }

    public Employee findByEmail(String email) {
        return employeeRepository.findEmployeeByEmail(email);
    }

    public Employee saveOrUpdate(Employee employee) {
        return employeeRepository.saveAndFlush(employee);
    }

    public void deleteById(Long id) {
        employeeRepository.deleteById(id);
    }

}
