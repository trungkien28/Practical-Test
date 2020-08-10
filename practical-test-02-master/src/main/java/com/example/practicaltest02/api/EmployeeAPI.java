package com.example.practicaltest02.api;

import com.example.practicaltest02.entity.Employee;
import com.example.practicaltest02.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/employees")
@CrossOrigin("*")
public class EmployeeAPI {
    private final EmployeeService employeeService;

    public EmployeeAPI(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<List<Employee>> findAll() {
        List<Employee> employees = employeeService.getEmployees();

        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> findAll(@PathVariable Long id) {
        Optional<Employee> employee = employeeService.findById(id);

        if (!employee.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(employee.get());
    }

    @PostMapping
    public ResponseEntity<Employee> create(@RequestBody Employee employee) {
        return ResponseEntity.ok(employeeService.addEmployee(employee));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Employee> update(@PathVariable Long id,@RequestBody Employee employee) {
        Optional<Employee> oldEmployee = employeeService.findById(id);

        if (!oldEmployee.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        Employee newEmployee = oldEmployee.get();

        newEmployee.setName(employee.getName());
        newEmployee.setEmail(employee.getEmail());
        newEmployee.setPhone(employee.getPhone());

        return ResponseEntity.ok(employeeService.updateEmployee(newEmployee));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        employeeService.deleteById(id);

        return ResponseEntity.ok().build();
    }
}
