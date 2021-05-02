package com.dtdl.sherlock.mtsherlock.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Seller(
    @Id @GeneratedValue(strategy = GenerationType.AUTO) private val id: Long,
    private val address: String

)
