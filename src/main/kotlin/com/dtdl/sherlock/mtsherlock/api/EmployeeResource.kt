package com.dtdl.sherlock.mtsherlock.api

import com.dtdl.sherlock.mtsherlock.model.Employee
import com.dtdl.sherlock.mtsherlock.request.EmployeeDTO
import com.dtdl.sherlock.mtsherlock.service.EmployeeService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/employees")
class EmployeeResource(
    private val employeeService: EmployeeService
) {

    @PostMapping("/add")
    fun addEmployee(@RequestBody employee: EmployeeDTO): ResponseEntity<Employee> =
        ResponseEntity(employeeService.add(employee), HttpStatus.CREATED)


    @GetMapping
    fun allEmployees(): ResponseEntity<List<EmployeeDTO>> =
        ResponseEntity.ok(employeeService.getAll());


    @PatchMapping("/add")
    fun updateEmployee(@RequestBody employee: EmployeeDTO): ResponseEntity<EmployeeDTO> =
        ResponseEntity(employeeService.update(employee), HttpStatus.CREATED)


    @DeleteMapping("/delete")
    fun delete(@RequestParam id: UUID): ResponseEntity<Unit> =
        employeeService.delete(id).let { ResponseEntity(HttpStatus.NO_CONTENT) }

}