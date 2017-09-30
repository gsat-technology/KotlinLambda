package com.gsat.mylambdafunction

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.gsat.mylambdafunction.core.Helpers
import java.io.ByteArrayOutputStream
import java.io.File
import java.nio.file.Paths


fun main(args: Array<String>) {
    println("main function")

    val mapper = jacksonObjectMapper()

    var m = Handler(Helpers().loadConfigFromFile(Paths.get("conf/localEnvironment.yml")))

    val request1InputStream = File("requestJSON/request1.json").inputStream()
    val request2InputStream = File("requestJSON/request2.json").inputStream()
    val request3InputStream = File("requestJSON/request3.json").inputStream()

    val outStream = ByteArrayOutputStream()

    m.handler(request1InputStream, outStream)
    m.handler(request2InputStream, outStream)
    m.handler(request3InputStream, outStream)
}

