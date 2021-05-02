package com.dtdl.sherlock.mtsherlock.api

import com.dtdl.sherlock.mtsherlock.model.User
import com.dtdl.sherlock.mtsherlock.dto.UserDTO
import com.dtdl.sherlock.mtsherlock.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/employees")
class UserResource(
    private val userService: UserService
) {

    @PostMapping("/add")
    fun addEmployee(@RequestBody userDTO: UserDTO): ResponseEntity<User> =
        ResponseEntity(userService.add(userDTO), HttpStatus.CREATED)


    @GetMapping
    fun allEmployees(): ResponseEntity<List<UserDTO>> =
        ResponseEntity.ok(userService.getAll());


    @PatchMapping("/add")
    fun updateEmployee(@RequestBody user: UserDTO): ResponseEntity<UserDTO> =
        ResponseEntity(userService.update(user), HttpStatus.CREATED)


    @DeleteMapping("/delete")
    fun delete(@RequestParam id: UUID): ResponseEntity<Unit> =
        userService.delete(id).let { ResponseEntity(HttpStatus.NO_CONTENT) }

}