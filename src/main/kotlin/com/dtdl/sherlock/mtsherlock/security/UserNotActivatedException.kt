package com.dtdl.sherlock.mtsherlock.security

import org.springframework.security.core.AuthenticationException

class UserNotActivatedException(var msg: String) : AuthenticationException(msg) {
    private val serialVersionUID = -1126699074574529145L

}