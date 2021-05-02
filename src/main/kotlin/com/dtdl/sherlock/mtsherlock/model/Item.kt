package com.dtdl.sherlock.mtsherlock.model

import lombok.Builder
import javax.persistence.*

@Entity
@Builder
class Item(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private val id: Long?,
    private val name: String,
    @OneToOne private val seller: Seller?
)
