package com.dtdl.sherlock.mtsherlock.api

import com.dtdl.sherlock.mtsherlock.dto.UserDTO
import com.dtdl.sherlock.mtsherlock.model.User
import com.dtdl.sherlock.mtsherlock.security.service.JwtUserDetailsService
//import com.dtdl.sherlock.mtsherlock.service.MyUserDetailService
import com.dtdl.sherlock.mtsherlock.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/employees")
//@CrossOrigin("*")
class UserResource(
    private val userService: UserService,
    private val authenticationManager: AuthenticationManager,
    private val myUserDetailService: JwtUserDetailsService
) {

    @PostMapping("/signup")
    fun addEmployee(@RequestBody userDTO: UserDTO): ResponseEntity<User> =
        ResponseEntity(userService.add(userDTO), HttpStatus.CREATED)


    @GetMapping
    fun allEmployees(): ResponseEntity<List<UserDTO>> =
        ResponseEntity.ok(userService.getAll());


    @PatchMapping("/signup")
    fun updateEmployee(@RequestBody user: UserDTO): ResponseEntity<UserDTO> =
        ResponseEntity(userService.update(user), HttpStatus.CREATED)


    @DeleteMapping("/delete")
    fun delete(@RequestParam id: Long): ResponseEntity<Unit> =
        userService.delete(id).let { ResponseEntity(HttpStatus.NO_CONTENT) }


    private fun authenticate(username: String?, password: String?) {
        try {
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(username, password))
        } catch (e: DisabledException) {
            throw Exception("USER_DISABLED", e)
        } catch (e: BadCredentialsException) {
            throw Exception("INVALID_CREDENTIALS", e)
        }
    }


}