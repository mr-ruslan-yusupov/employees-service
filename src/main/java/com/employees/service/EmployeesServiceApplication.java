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
            employeeRoleService.saveOrUpdate(new EmployeeRole(EmployeeRolesEnum.STOCK_MANAGER_ROLE.toString()));
            employeeRoleService.saveOrUpdate(new EmployeeRole(EmployeeRolesEnum.ORDER_MANAGER_ROLE.toString()));
        }

        if (employeeService.findAll().isEmpty()) {
            Employee employee1 = new Employee();
            employee1.setEmail("test@admin.com");
            employee1.setName("Test Admin");
            employee1.setMobile("123456789");
            employee1.setRole(employeeRoleService.findByName(EmployeeRolesEnum.ADMIN_ROLE.toString()));
            employee1.setPassword(new BCryptPasswordEncoder().encode("test-admin"));
            employeeService.saveOrUpdate(employee1);

            Employee employee2 = new Employee();
            employee2.setEmail("test@stock.com");
            employee2.setName("Test Stock Manager");
            employee2.setMobile("987654321");
            employee2.setRole(employeeRoleService.findByName(EmployeeRolesEnum.STOCK_MANAGER_ROLE.toString()));
            employee2.setPassword(new BCryptPasswordEncoder().encode("test-stock"));
            employeeService.saveOrUpdate(employee2);

            Employee employee3 = new Employee();
            employee3.setEmail("test@order.com");
            employee3.setName("Test Order Manager");
            employee3.setMobile("159753258");
            employee3.setRole(employeeRoleService.findByName(EmployeeRolesEnum.ORDER_MANAGER_ROLE.toString()));
            employee3.setPassword(new BCryptPasswordEncoder().encode("test-order"));
            employeeService.saveOrUpdate(employee3);
        }

    }
}
