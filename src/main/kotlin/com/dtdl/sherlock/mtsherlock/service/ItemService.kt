package com.dtdl.sherlock.mtsherlock.service

import com.dtdl.sherlock.mtsherlock.constants.ITEM_NOT_FOUND
import com.dtdl.sherlock.mtsherlock.dto.ItemDto
import com.dtdl.sherlock.mtsherlock.exception.MtSherlockNotFoundException
import com.dtdl.sherlock.mtsherlock.model.Item
import com.dtdl.sherlock.mtsherlock.repository.ItemRepository
import org.springframework.stereotype.Service
import kotlin.streams.toList

@Service
class ItemService(private val itemRepository: ItemRepository) {

    fun findItemById(itemId: Long): Item =
        itemRepository.findById(itemId).orElseThrow { throw MtSherlockNotFoundException(ITEM_NOT_FOUND) };

    fun getItems() =
        itemRepository.findAll().stream().map { ItemDto(it.id, it.name) }.toList()


}
