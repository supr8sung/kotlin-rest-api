package com.dtdl.sherlock.mtsherlock.config

import lombok.Getter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class Configurations {

    companion object {
        @Value(value = "\${authorization.jwt.bffCacheInitialLoad.headerKey}")
        val bffCacheInitialLoadHeaderKey: String? = null
    }

}