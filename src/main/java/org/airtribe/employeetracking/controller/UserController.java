package org.airtribe.employeetracking.controller;

import lombok.AllArgsConstructor;
import org.airtribe.employeetracking.dto.EmployeeDTO;
import org.airtribe.employeetracking.entity.Employee;
import org.airtribe.employeetracking.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class UserController {

    @Autowired
    private final EmployeeService userService;

    @PostMapping("/employees")
    public ResponseEntity<Employee> addEmployee(@RequestBody EmployeeDTO employeeDTO)
    {
        Employee createdEmployee = userService.addEmployee(employeeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
    }

    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees()
    {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllEmployees());
    }

    @PutMapping("/employees/{Id}")
    public ResponseEntity<Employee> updateEmployees(Integer Id, @RequestBody Employee employee)
    {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateEmployee(Id, employee));
    }

    // Delete a department
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable int id) {
        userService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/employees/search")
    public ResponseEntity<List<EmployeeDTO>> searchEmployees(@RequestParam(required = false) String name,
                                                          @RequestParam(required = false) String department,
                                                          @RequestParam(required = false) String designation
                                                          ) {
        List<EmployeeDTO> employees = userService.searchEmployees(name, department, designation);
        return ResponseEntity.ok(employees);
    }
}
