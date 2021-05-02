package com.dtdl.sherlock.mtsherlock.api

import com.dtdl.sherlock.mtsherlock.request.ItemToCartRequest
import com.dtdl.sherlock.mtsherlock.service.CartService
import com.dtdl.sherlock.mtsherlock.service.ItemService
import org.springframework.http.HttpStatus.OK
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/cart")
class CartResource(val cartService: CartService, val itemService: ItemService) {


    @GetMapping("/summary")
    fun summary(@PathVariable id: UUID): ResponseEntity<Int> {
        return ResponseEntity(cartService.itemsCount(id), OK)
    }

    @PostMapping("/add")
    fun add(@RequestBody itemToCartRequest: ItemToCartRequest) {

        val item = itemService.findItemById(itemToCartRequest.itemId)
        cartService.addItem(item);
    }

}