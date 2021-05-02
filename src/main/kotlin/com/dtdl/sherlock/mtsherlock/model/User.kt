package com.dtdl.sherlock.mtsherlock.model

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID? = null,
    var name: String? = null,
    var username: String,
    var password: String
)

//https://www.myntra.com/gateway/v2/search/men-sneakers?f=Brand%3APuma%3A%3AColor%3ABlack_36454f&plaEnabled=false&rows=50&o=0
