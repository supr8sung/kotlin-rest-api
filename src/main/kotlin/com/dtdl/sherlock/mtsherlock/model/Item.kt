package com.dtdl.sherlock.mtsherlock.model

import lombok.Builder
import javax.persistence.*

@Entity
@Builder
data class Item(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long?,
    val name: String,
    @OneToOne private val seller: Seller?
)
