package com.dtdl.sherlock.mtsherlock.repository

import com.dtdl.sherlock.mtsherlock.model.Cart
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CartRepository : JpaRepository<Cart, Long> {
    fun findByUserId(id: UUID): Optional<Cart>

}
