package com.gsat.mylambdafunction.core

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.gsat.mylambdafunction.domain.ConfigDtc
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Path


class Helpers {

    fun loadConfigFromFile(path: Path): ConfigDtc {
        val mapper = ObjectMapper(YAMLFactory()) // Enable YAML parsing
        mapper.registerModule(KotlinModule()) // Enable Kotlin support

        return Files.newBufferedReader(path).use {
            mapper.readValue(it, ConfigDtc::class.java)
        }
    }

    fun convertStreamToString(inStream: InputStream): String {
        val s = java.util.Scanner(inStream).useDelimiter("\\A")
        return if (s.hasNext()) s.next() else ""
    }
}
