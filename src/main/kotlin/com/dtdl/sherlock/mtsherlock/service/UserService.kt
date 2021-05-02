package com.dtdl.sherlock.mtsherlock.service

import com.dtdl.sherlock.mtsherlock.exception.MtSherlockBadRequestException
import com.dtdl.sherlock.mtsherlock.exception.MtSherlockNotFoundException
import com.dtdl.sherlock.mtsherlock.model.User
import com.dtdl.sherlock.mtsherlock.repository.UserRepository
import com.dtdl.sherlock.mtsherlock.dto.UserDTO
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(val userRepository: UserRepository) {

    val employees = mutableMapOf<UUID, User>()
    fun add(userDTO: UserDTO): User {
        val employee = User()
        employee.name = userDTO.name
        return userRepository.save(employee)
    }

    fun getAll() = userRepository.findAll().map { UserDTO(it.id, it.name) }


    fun update(userDTO: UserDTO): UserDTO {
        val id = userDTO.id ?: throw MtSherlockBadRequestException("id should be present");


        val employee = userRepository.findById(id)
            .orElseThrow({ throw MtSherlockNotFoundException("no employee found with given id") })
        employee.name = userDTO.name

        return userRepository.save(employee).let { UserDTO(it.id, it.name) }
    }

    fun delete(id: UUID) = userRepository.deleteById(id)


}
