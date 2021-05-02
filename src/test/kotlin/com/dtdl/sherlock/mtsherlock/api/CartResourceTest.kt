package com.dtdl.sherlock.mtsherlock.api

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
class CartResourceTest(val mockMvc: MockMvc) {


    @Test
    fun `should be able to add an item to cart`() {
        this.mockMvc.perform(post("")).andExpect { status().isCreated() }
    }

    @Test
    internal fun `should be able to send total cart count`() {

    }
}

