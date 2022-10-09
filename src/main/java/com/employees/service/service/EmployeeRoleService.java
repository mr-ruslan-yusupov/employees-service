package com.employees.service.service;

import com.employees.service.model.EmployeeRole;
import com.employees.service.repository.EmployeeRoleRepository;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class EmployeeRoleService {

    final private EmployeeRoleRepository employeeRoleRepository;

    public EmployeeRoleService(EmployeeRoleRepository employeeRoleRepository) {
        this.employeeRoleRepository = employeeRoleRepository;
    }

    public Collection<EmployeeRole> findAll() {
        return employeeRoleRepository.findAll();
    }

    public Optional<EmployeeRole> findById(Long id) {
        return employeeRoleRepository.findById(id);
    }

    public EmployeeRole findByName(String name) {
        return employeeRoleRepository.findEmployeeRoleByName(name);
    }

    public void saveOrUpdate(EmployeeRole role) {
        employeeRoleRepository.saveAndFlush(role);
    }

    public String deleteById(Long id) {
        JSONObject jsonObject = new JSONObject();
        try {
            employeeRoleRepository.deleteById(id);
            jsonObject.put("message", "Role deleted successfully");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

}
