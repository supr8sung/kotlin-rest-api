package com.dtdl.sherlock.mtsherlock.model

import org.hibernate.annotations.Fetch
import org.hibernate.hql.internal.antlr.SqlTokenTypes.SELECT
import javax.persistence.*

@Entity
data class Authority(
    @Id
    @Column(length = 50)
    val name: String,

//    @ManyToOne(fetch = FetchType.EAGER, cascade = arrayOf(CascadeType.ALL))
//    @Fetch(value = SELECT)

//    val user: User
)
