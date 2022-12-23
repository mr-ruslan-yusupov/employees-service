package com.employees.service;

import com.employees.service.model.Employee;
import com.employees.service.model.EmployeeRole;
import com.employees.service.model.EmployeeRolesEnum;
import com.employees.service.service.EmployeeRoleService;
import com.employees.service.service.EmployeeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class EmployeesServiceApplication implements CommandLineRunner {
    final private EmployeeRoleService employeeRoleService;
    final private EmployeeService employeeService;

    public EmployeesServiceApplication(EmployeeRoleService employeeRoleService, EmployeeService employeeService) {
        this.employeeRoleService = employeeRoleService;
        this.employeeService = employeeService;
    }

    public static void main(String[] args) {
        SpringApplication.run(EmployeesServiceApplication.class, args);
    }

    @Override
    public void run(String... args) {
        if (employeeRoleService.findAll().isEmpty()) {
            employeeRoleService.saveOrUpdate(new EmployeeRole(EmployeeRolesEnum.ADMIN_ROLE.toString()));
            employeeRoleService.saveOrUpdate(new EmployeeRole(EmployeeRolesEnum.INVENTORY_MANAGER_ROLE.toString()));
            employeeRoleService.saveOrUpdate(new EmployeeRole(EmployeeRolesEnum.ORDER_MANAGER_ROLE.toString()));
        }

        if (employeeService.findAllEmployees().isEmpty()) {
            Employee employee1 = new Employee();
            employee1.setEmail("mr.ruslan.yusupov@gmail.com");
            employee1.setName("Ruska Admin");
            employee1.setMobile("0525093585");
            employee1.setRole(employeeRoleService.findByName(EmployeeRolesEnum.ADMIN_ROLE.toString()));
            employee1.setPassword(new BCryptPasswordEncoder().encode("test-admin"));
            employeeService.saveOrUpdateEmployee(employee1);

            Employee employee2 = new Employee();
            employee2.setEmail("tankist.teddy@gmail.com");
            employee2.setName("Ruska Inventory Manager");
            employee2.setMobile("0526751500");
            employee2.setRole(employeeRoleService.findByName(EmployeeRolesEnum.INVENTORY_MANAGER_ROLE.toString()));
            employee2.setPassword(new BCryptPasswordEncoder().encode("test-stock"));
            employeeService.saveOrUpdateEmployee(employee2);

            Employee employee3 = new Employee();
            employee3.setEmail("rusa.trader@hotmail.com");
            employee3.setName("Ruska Order Manager");
            employee3.setMobile("0528532596");
            employee3.setRole(employeeRoleService.findByName(EmployeeRolesEnum.ORDER_MANAGER_ROLE.toString()));
            employee3.setPassword(new BCryptPasswordEncoder().encode("test-order"));
            employeeService.saveOrUpdateEmployee(employee3);
        }

    }
}
