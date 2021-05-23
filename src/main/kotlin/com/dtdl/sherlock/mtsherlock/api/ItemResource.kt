package com.dtdl.sherlock.mtsherlock.api

import com.dtdl.sherlock.mtsherlock.dto.ItemDto
import com.dtdl.sherlock.mtsherlock.model.Item
import com.dtdl.sherlock.mtsherlock.service.ItemService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/items")
class ItemResource(val itemService: ItemService) {

    var items = mutableListOf<Item>();

    @GetMapping
    fun allItems(): ResponseEntity<List<ItemDto>> {
        return ResponseEntity.ok(itemService.getItems());
    }
}