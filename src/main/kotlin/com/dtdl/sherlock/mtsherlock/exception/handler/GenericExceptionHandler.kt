package com.dtdl.sherlock.mtsherlock.exception.handler

import com.dtdl.sherlock.mtsherlock.exception.MtSherlockAuthenticationException
import com.dtdl.sherlock.mtsherlock.exception.MtSherlockBadRequestException
import com.dtdl.sherlock.mtsherlock.exception.MtSherlockNotFoundException
import org.springframework.http.HttpStatus.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GenericExceptionHandler {


    @ExceptionHandler(MtSherlockBadRequestException::class)
    fun badRequest(exception: MtSherlockBadRequestException) =
        ResponseEntity(exception.message, BAD_REQUEST)


    @ExceptionHandler(MtSherlockNotFoundException::class)
    fun notFound(exception: MtSherlockNotFoundException) =
        ResponseEntity(exception.message, NOT_FOUND)


    @ExceptionHandler(MtSherlockAuthenticationException::class)
    fun unauthorized(exception: MtSherlockAuthenticationException) =
        ResponseEntity(exception.message, UNAUTHORIZED)

}