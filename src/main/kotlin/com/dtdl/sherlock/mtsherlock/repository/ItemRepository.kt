package com.dtdl.sherlock.mtsherlock.repository

import com.dtdl.sherlock.mtsherlock.model.Item
import org.springframework.data.jpa.repository.JpaRepository

interface ItemRepository : JpaRepository<Item, Long> {

}
