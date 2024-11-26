package org.airtribe.employeetracking.service;

import org.airtribe.employeetracking.dto.EmployeeDTO;
import org.airtribe.employeetracking.entity.Employee;
import org.airtribe.employeetracking.service.EmployeeService;
import org.airtribe.employeetracking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    UserService userService;


    public boolean hasAccessToAdminEndpoint(Authentication authentication) throws IllegalArgumentException {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }
        if (!(authentication instanceof JwtAuthenticationToken)){
            return false;
        }
        Employee employee = employeeService.getEmployeeByAuthentication(authentication);
        return "ADMIN".equals(employee.getDesignation());
    }

    public boolean hasAccessToManagerEndpoint(Authentication authentication) throws IllegalArgumentException {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }
        if (!(authentication instanceof JwtAuthenticationToken)){
            return false;
        }
        Employee employee = employeeService.getEmployeeByAuthentication(authentication);
        String role = employee.getDesignation();
        return ("ADMIN".equals(role) || "MANAGER".equals(role));
    }

    public boolean hasAccessToManagerEndpoint(Authentication authentication, EmployeeDTO employeeDTO) throws IllegalArgumentException {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }
        if (!(authentication instanceof JwtAuthenticationToken)){
            return false;
        }
        Employee employee = employeeService.getEmployeeByAuthentication(authentication);
        String role = employee.getDesignation();

        if("MANAGER".equals(role)){
            return (employee.getDepartment().getDeptName()).equals(employeeDTO.getDepartmentName());
        }
        return "ADMIN".equals(role);
    }

    public boolean hasAccessToManagerEndpoint(Authentication authentication, Long id) throws EmployeeNotFoundException {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }
        if (!(authentication instanceof JwtAuthenticationToken)){
            return false;
        }
        Employee employee = employeeService.getEmployeeByAuthentication(authentication);
        String role = employee.getDesignation();

        if("MANAGER".equals(role)){
            return (employee.getDepartment().getDeptName()).equals(employeeService.getEmployeeById(id).getDepartmentName());
        }
        return "ADMIN".equals(role);
    }


    public boolean hasAccessToEmployeeEndpoint(Authentication authentication, Long id) throws EmployeeNotFoundException{
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }
        if (!(authentication instanceof JwtAuthenticationToken)){
            return false;
        }
        Employee employee = employeeService.getEmployeeByAuthentication(authentication);
        String role = employee.getDesignation();

        if("EMPLOYEE".equals(role)){
            return employee.getUserId() == id;
        }
        if("MANAGER".equals(role)){
            return (employee.getDepartment().getDeptName()).equals(employeeService.getEmployeeById(id).getDepartmentName());
        }
        return "ADMIN".equals(role);
    }

}