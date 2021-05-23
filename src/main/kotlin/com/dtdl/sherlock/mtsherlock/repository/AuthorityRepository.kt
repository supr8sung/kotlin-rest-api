package com.dtdl.sherlock.mtsherlock.repository

import com.dtdl.sherlock.mtsherlock.model.Authority
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthorityRepository : JpaRepository<Authority, String> {
}