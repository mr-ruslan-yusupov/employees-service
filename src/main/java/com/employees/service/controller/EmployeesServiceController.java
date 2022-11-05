package com.employees.service.controller;

import com.employees.service.config.JwtTokenProvider;
import com.employees.service.model.Employee;
import com.employees.service.service.EmployeeRoleService;
import com.employees.service.service.EmployeeService;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
public class EmployeesServiceController {
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
    public ResponseEntity<String> register(@RequestBody Employee employee) {
        JSONObject jsonObject = new JSONObject();
        try {
            employee.setPassword(new BCryptPasswordEncoder().encode(employee.getPassword()));
            employee.setRole(employeeRoleService.findByName(employee.getRole().getName()));
            Employee savedUser = employeeService.saveOrUpdate(employee);
            jsonObject.put("message", savedUser.getName() + " saved successfully");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        } catch (JSONException e) {
            try {
                jsonObject.put("exception", e.getMessage());
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping(value = "/employee/authenticate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> authenticate(@RequestBody Employee employee) {
        JSONObject jsonObject = new JSONObject();
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(employee.getEmail(), employee.getPassword()));
            if (authentication.isAuthenticated()) {
                String email = employee.getEmail();
                jsonObject.put("name", authentication.getName());
                jsonObject.put("authorities", authentication.getAuthorities());
                jsonObject.put("token", tokenProvider.createToken(email, employeeService.findByEmail(email).getRole()));
                return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
            }
        } catch (AuthenticationException | JSONException e) {
            try {
                jsonObject.put("exception", e.getMessage());
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.UNAUTHORIZED);
        }
        return null;
    }

    @RequestMapping(path="/employee/find-all-employees", method = RequestMethod.GET)
    public Collection<Employee> findAllEmployees()   {
        return employeeService.findAll();
    }

    @RequestMapping(path="/employee/find-employee-by-id/{employeeId}", method = RequestMethod.GET)
    public Optional<Employee> findEmployeeById(@PathVariable("employeeId") Long employeeId) {
        return employeeService.findById(employeeId);
    }

    @RequestMapping(path="/employee/delete-employee-by-id/{employeeId}", method = RequestMethod.GET)
    public ResponseEntity<String> deleteEmployeeById(@PathVariable("employeeId") Long employeeId) {
        String jsonResponse = employeeService.deleteById(employeeId);
        return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
    }

}
