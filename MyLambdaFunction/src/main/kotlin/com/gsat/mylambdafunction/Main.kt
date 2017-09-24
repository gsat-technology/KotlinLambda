package com.gsat.mylambdafunction

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.nio.charset.StandardCharsets
 
fun main(args: Array<String>) {
    println("main function")
    var m = Handler()
    val inStream = ByteArrayInputStream("{\"name\": \"Bob\"}".toByteArray(StandardCharsets.UTF_8))
    val outStream = ByteArrayOutputStream()

    m.handler(inStream, outStream)
}
