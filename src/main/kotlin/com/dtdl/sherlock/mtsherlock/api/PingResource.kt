package com.dtdl.sherlock.mtsherlock.api

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/ping")
class PingResource {

    @GetMapping
    fun pong(): ResponseEntity<String> {

        return ResponseEntity.ok("pong");
    }
}