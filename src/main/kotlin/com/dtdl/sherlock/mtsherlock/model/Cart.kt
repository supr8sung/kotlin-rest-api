package com.dtdl.sherlock.mtsherlock.model

import javax.persistence.*

@Entity
data class Cart(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long?,

    @OneToOne val user: User,

    @OneToMany val items: MutableList<Item> = mutableListOf()
)
