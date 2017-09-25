package com.gsat.mylambdafunction.core

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.gsat.mylambdafunction.domain.ConfigDtc
import java.nio.file.Files
import java.nio.file.Path


class Helpers {

    open fun loadConfigFromFile(path: Path): ConfigDtc {
        val mapper = ObjectMapper(YAMLFactory()) // Enable YAML parsing
        mapper.registerModule(KotlinModule()) // Enable Kotlin support

        return Files.newBufferedReader(path).use {
            mapper.readValue(it, ConfigDtc::class.java)
        }
    }
}
