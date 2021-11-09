package edu.fontys.horecarobot.adminappbackend.controllers;

import edu.fontys.horecarobot.adminappbackend.dtos.ApiResponse;
import edu.fontys.horecarobot.adminappbackend.dtos.EmployeeModel;
import edu.fontys.horecarobot.adminappbackend.services.EmployeeService;
import edu.fontys.horecarobot.databaselibrary.models.EmployeeUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/employee")
@CrossOrigin(origins = "http://localhost:4000")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<ApiResponse> getEmployeeUsers() {
        try {
            List<EmployeeUser> employees = employeeService.getAllEmployeeUsers();
            return ResponseEntity.ok(ApiResponse.ok().addData("employees", employees));
        }
        catch (Exception e) {
            return ResponseEntity.internalServerError().body(ApiResponse.GENERAL_EXCEPTION_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse> postEmployeeUser(@RequestBody EmployeeModel employeeModel) {
        if(employeeModel.getUsername().isBlank())
            return ResponseEntity.badRequest().body(ApiResponse.REQUIRED_FIELDS_ERROR);

        if(employeeModel.getPin() < 1111 || employeeModel.getPin() > 9999)
            return ResponseEntity.badRequest().body(ApiResponse.REQUIRED_FIELDS_ERROR);

        try {
            employeeService.addEmployeeUser(employeeModel);
            // TODO: Change response entity to contain uri
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch (Exception e) {
            return ResponseEntity.internalServerError().body(ApiResponse.GENERAL_EXCEPTION_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> putEmployeeUser(@PathVariable UUID id, @RequestBody EmployeeModel employeeModel) {
        if(!employeeService.doesEmployeeExist(id))
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Can't locate employee in database."));

        if(employeeModel.getUsername().isBlank())
            return ResponseEntity.badRequest().body(ApiResponse.REQUIRED_FIELDS_ERROR);

        if(employeeModel.getPin() < 1111 || employeeModel.getPin() > 9999)
            return ResponseEntity.badRequest().body(ApiResponse.REQUIRED_FIELDS_ERROR);

        try {
            employeeService.updateEmployeeUser(employeeModel);
            return ResponseEntity.noContent().build();
        }
        catch(Exception e){
            return ResponseEntity.internalServerError().body(ApiResponse.GENERAL_EXCEPTION_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteEmployeeUser(@PathVariable UUID id) {
        if(!employeeService.doesEmployeeExist(id))
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Can't locate employee in database."));

        try {
            employeeService.deleteEmployeeUser(id);
            return ResponseEntity.noContent().build();
        }

        catch (Exception e) {
            return ResponseEntity.internalServerError().body(ApiResponse.GENERAL_EXCEPTION_ERROR);
        }
    }
}
