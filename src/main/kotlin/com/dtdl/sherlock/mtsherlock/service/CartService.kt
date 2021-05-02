package com.dtdl.sherlock.mtsherlock.service

import com.dtdl.sherlock.mtsherlock.model.Cart
import com.dtdl.sherlock.mtsherlock.model.Item
import com.dtdl.sherlock.mtsherlock.repository.CartRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class CartService(val cartRepository: CartRepository) {
    fun itemsCount(id: UUID): Int {
        val cart = cartRepository.findByUserId(id)
        return if (cart.isPresent) cart.get().items.size else 0;
    }

    fun addItem(item: Item) {
        val items = mutableListOf<Item>(item)
        Cart(items = items, id =null,user = null)
    }

}
