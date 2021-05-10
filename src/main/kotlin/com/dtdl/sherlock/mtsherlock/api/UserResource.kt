package com.dtdl.sherlock.mtsherlock.api

import com.dtdl.sherlock.mtsherlock.config.util.JwtTokenUtil
import com.dtdl.sherlock.mtsherlock.dto.UserDTO
import com.dtdl.sherlock.mtsherlock.model.User
import com.dtdl.sherlock.mtsherlock.service.MyUserDetailService
import com.dtdl.sherlock.mtsherlock.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("/employees")
class UserResource(
    private val userService: UserService,
    private val authenticationManager: AuthenticationManager,
    private val jwtTokenUtil: JwtTokenUtil,
    private val myUserDetailService: MyUserDetailService
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
    fun delete(@RequestParam id: UUID): ResponseEntity<Unit> =
        userService.delete(id).let { ResponseEntity(HttpStatus.NO_CONTENT) }

    @PostMapping("/authenticate")
    fun createAuthentication(@RequestBody userDTO: UserDTO): ResponseEntity<String> {
        authenticate(userDTO.username, userDTO.password);
        val userDetails = myUserDetailService.loadUserByUsername(userDTO.username);
        val token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(token);

    }

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