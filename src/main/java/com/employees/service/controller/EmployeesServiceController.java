package com.employees.service.controller;

import com.employees.service.config.JwtTokenProvider;
import com.employees.service.exceptions.InvalidInputException;
import com.employees.service.exceptions.ServerException;
import com.employees.service.exceptions.UnauthorizedException;
import com.employees.service.model.Employee;
import com.employees.service.service.EmployeeRoleService;
import com.employees.service.service.EmployeeService;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class EmployeesServiceController {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeesServiceController.class);

    final private EmployeeService employeeService;
    final private AuthenticationManager authenticationManager;
    final private JwtTokenProvider tokenProvider;
    final private EmployeeRoleService employeeRoleService;

    @Value("${application.name}")
    private String applicationName;

    @Value("${build.version}")
    private String buildVersion;

    @Value("${build.timestamp}")
    private String buildTimestamp;

    public EmployeesServiceController(EmployeeService employeeService,
                                      AuthenticationManager authenticationManager,
                                      JwtTokenProvider tokenProvider,
                                      EmployeeRoleService employeeRoleService)
    {
        this.employeeService = employeeService;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.employeeRoleService = employeeRoleService;
    }

    @RequestMapping(value = {"/","/employee"}, produces = MediaType.TEXT_PLAIN_VALUE)
    public String home() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n[Application info]");
        sb.append("\nApplication name : " + applicationName);
        sb.append("\nBuild version    : " + buildVersion);
        sb.append("\nBuild timestamp  : " + buildTimestamp);
        return sb.toString();
    }

    @PostMapping(value = "/employee/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Employee> register(@RequestBody Employee employee) {
        LOG.debug("EmployeesServiceController.register(): tries to create a new employee");

        if (employee.getEmail() == null ||
            employee.getName() == null ||
            employee.getPassword() == null ||
            employee.getMobile() == null ||
            employee.getRole() == null)
        {
            throw new InvalidInputException("Employee fields are incomplete: " + employee.toString());
        }

        if (employeeService.existsEmployeeByEmail(employee.getEmail())) {
            throw new InvalidInputException("Employee with email: " + employee.getEmail() + " already exists");
        }

        if (employeeService.existsEmployeeByMobile(employee.getMobile())) {
            throw new InvalidInputException("Employee with mobile: " + employee.getMobile() + " already exists");
        }

        employee.setPassword(new BCryptPasswordEncoder().encode(employee.getPassword()));
        employee.setRole(employeeRoleService.findByName(employee.getRole().getName()));
        Employee savedUser = employeeService.saveOrUpdateEmployee(employee);

        return ResponseEntity.ok(savedUser);
    }

    @PostMapping(value = "/employee/authenticate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> authenticate(@RequestParam String email, @RequestParam String password) {
        LOG.debug("EmployeesServiceController.authenticate(): tries to authenticate an employee with email: {}", email);

        JSONObject jsonObject = new JSONObject();
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            if (authentication.isAuthenticated()) {
                jsonObject.put("name", authentication.getName());
                jsonObject.put("authorities", authentication.getAuthorities());
                jsonObject.put("token", tokenProvider.createToken(email, employeeService.findEmployeeByEmail(email).getRole()));
            }
        }
        catch (AuthenticationException e) {
            throw new UnauthorizedException("Unauthorized employee with email: " + email);
        }
        catch (JSONException e) {
            throw new ServerException(e.getMessage(), e);
        }

        return ResponseEntity.ok(jsonObject.toString());
    }

    @GetMapping(path="/employee/find-all-employees", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Employee> findAllEmployees()   {
        return employeeService.findAllEmployees();
    }

    @GetMapping(path="/employee/find-employee-by-id/{employeeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Employee findEmployeeById(@PathVariable("employeeId") Long employeeId) {
        return employeeService.findEmployeeById(employeeId);
    }

    @DeleteMapping(path="/employee/delete-employee-by-id/{employeeId}")
    public void deleteEmployeeById(@PathVariable("employeeId") Long id) {
        LOG.debug("EmployeesServiceController.deleteEmployeeById(): tries to delete an employee with id: {}", id);
        try {
            employeeService.deleteEmployeeById(id);
        }
        catch (EmptyResultDataAccessException e) {
            throw new InvalidInputException("Not found employee with id: " + id);
        }
    }

}
