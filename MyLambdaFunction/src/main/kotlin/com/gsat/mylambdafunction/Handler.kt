package com.gsat.mylambdafunction

import java.io.InputStream
import java.io.OutputStream
import com.fasterxml.jackson.module.kotlin.*


data class HandlerInput(val name: String)
data class HandlerOutput(val greeting: String)


class Handler {

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


