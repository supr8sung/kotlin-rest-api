package com.dtdl.sherlock.mtsherlock.model

import lombok.Data
import javax.persistence.*

@Entity
@Data
data class User(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,
    var name: String? = null,
    var username: String? = null,
    var password: String? = null,
//    @OneToMany(mappedBy = "user")
    @OneToMany
    var authorities: Set<Authority> = hashSetOf()

)

