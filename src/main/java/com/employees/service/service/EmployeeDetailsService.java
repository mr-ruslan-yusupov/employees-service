package com.employees.service.service;

import com.employees.service.model.Employee;
import com.employees.service.model.EmployeeRolesEnum;
import com.employees.service.repository.EmployeeRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class EmployeeDetailsService implements UserDetailsService {

    final private EmployeeRepository employeeRepository;

    public EmployeeDetailsService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findEmployeeByEmail(email);
        if (employee == null) {
            throw new UsernameNotFoundException("Email " + email + " not found");
        }
        return new org.springframework.security.core.userdetails.User(employee.getEmail(), employee.getPassword(), getGrantedAuthority(employee));
    }

    private Collection<GrantedAuthority> getGrantedAuthority(Employee employee) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(employee.getRole().getName()));
        if (employee.getRole().getName().equalsIgnoreCase(EmployeeRolesEnum.ADMIN_ROLE.toString())) {
            authorities.add(new SimpleGrantedAuthority(EmployeeRolesEnum.STOCK_MANAGER_ROLE.toString()));
            authorities.add(new SimpleGrantedAuthority(EmployeeRolesEnum.ORDER_MANAGER_ROLE.toString()));
        }
        return authorities;
    }

}
