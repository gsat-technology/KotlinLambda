package com.gsat.mylambdafunction

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.gsat.mylambdafunction.domain.ConfigDtc
import java.io.InputStream
import java.io.OutputStream

data class HandlerInput(val name: String)
data class HandlerOutput(val greeting: String)


class Handler(config: ConfigDtc? = null) {


    init {
        println("launch")
        println("initialising environment variables")
        val myvar1 = System.getenv("myvar1") ?: config!!.environmentVariables.myvar1
        val myvar2 = System.getenv("myvar2") ?: config!!.environmentVariables.myvar2
        println("value for myvar1: ${myvar1}")
        println("value for myvar2: ${myvar2}")
    }


    val mapper = jacksonObjectMapper()

    fun convertStreamToString(inStream: InputStream): String {
        val s = java.util.Scanner(inStream).useDelimiter("\\A")
        return if (s.hasNext()) s.next() else ""
    }

    fun handler(input: InputStream, output: OutputStream): Unit {
        println("starting handler")
        val eventString = convertStreamToString(input)
        val event = mapper.readValue<HandlerInput>(eventString)

        println("hi there, ${event.name}")
        mapper.writeValue(output, HandlerOutput("Hello ${event.name}"))
    }
}


