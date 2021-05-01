package com.dtdl.sherlock.mtsherlock.service

import com.dtdl.sherlock.mtsherlock.exception.MtSherlockBadRequestException
import com.dtdl.sherlock.mtsherlock.exception.MtSherlockNotFoundException
import com.dtdl.sherlock.mtsherlock.model.Employee
import com.dtdl.sherlock.mtsherlock.repository.EmployeeRepository
import com.dtdl.sherlock.mtsherlock.request.EmployeeDTO
import org.springframework.stereotype.Service
import java.util.*

@Service
class EmployeeService(val employeeRepository: EmployeeRepository) {

    val employees = mutableMapOf<UUID, Employee>()
    fun add(employeeDTO: EmployeeDTO): Employee {
        val employee = Employee()
        employee.name = employeeDTO.name
        return employeeRepository.save(employee)
    }

    fun getAll() = employeeRepository.findAll().map { EmployeeDTO(it.id, it.name) }


    fun update(employeeDTO: EmployeeDTO): EmployeeDTO {
        val id = employeeDTO.id ?: throw MtSherlockBadRequestException("id should be present");


        val employee = employeeRepository.findById(id)
            .orElseThrow({ throw MtSherlockNotFoundException("no employee found with given id") })
        employee.name = employeeDTO.name

        return employeeRepository.save(employee).let { EmployeeDTO(it.id, it.name) }
    }

    fun delete(id: UUID) = employeeRepository.deleteById(id)


}
