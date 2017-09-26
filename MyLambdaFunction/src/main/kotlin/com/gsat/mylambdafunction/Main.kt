package com.gsat.mylambdafunction

import com.gsat.mylambdafunction.core.Helpers
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.nio.charset.StandardCharsets
import java.nio.file.Paths


fun main(args: Array<String>) {
    println("main function")
    var m = Handler(Helpers().loadConfigFromFile(Paths.get("conf/localEnvironment.yml")))
    val inStream = ByteArrayInputStream("{\"name\": \"Bob\"}".toByteArray(StandardCharsets.UTF_8))
    val outStream = ByteArrayOutputStream()

    m.handler(inStream, outStream)
}

