package com.dtdl.sherlock.mtsherlock.dto

import java.util.*

data class UserDTO(
    val id: UUID? = null,
    var name: String? = null,
    val username: String? = null,
    val password: String? = null
) {
}
