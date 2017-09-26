package com.gsat.mylambdafunction

import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model.S3ObjectInputStream
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.gsat.mylambdafunction.core.Helpers
import com.gsat.mylambdafunction.domain.ConfigDtc
import java.io.InputStream
import java.io.OutputStream

data class HandlerInput(val name: String)
data class HandlerOutput(val greeting: String)


class Handler(config: ConfigDtc? = null) {

    val dataBucket = System.getenv("dataBucket") ?: config!!.environmentVariables.dataBucket
    var s3: AmazonS3

    init {
        println("environment variables:")
        println("\tdataBucket: ${dataBucket}")

        s3 = if (config != null) AmazonS3ClientBuilder.standard()
                .withCredentials(ProfileCredentialsProvider(config!!.aws.namedProfile))
                .withRegion(config.aws.region)
                .build() else AmazonS3ClientBuilder.defaultClient()
    }


    val mapper = jacksonObjectMapper()


    fun handler(input: InputStream, output: OutputStream): Unit {
        println("starting handler")
        val eventString = Helpers().convertStreamToString(input)
        val event = mapper.readValue<HandlerInput>(eventString)

        println("hi there, ${event.name}")

        val s3ObjectInputStream: S3ObjectInputStream = s3.getObject(dataBucket, "file1.txt").objectContent
        val inputAsString = s3ObjectInputStream.bufferedReader().use { it.readText() }

        println(inputAsString)

        mapper.writeValue(output, HandlerOutput("Hello ${event.name}"))
    }
}


