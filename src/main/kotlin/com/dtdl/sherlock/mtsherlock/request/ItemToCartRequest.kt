package com.dtdl.sherlock.mtsherlock.request

import com.dtdl.sherlock.mtsherlock.model.Item

data class ItemToCartRequest(val itemId: Long, val quantity: Long, val sellerPartnerId: Long) {

}
