package com.gsat.mylambdafunction

import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model.S3ObjectInputStream
import com.amazonaws.services.s3.model.AmazonS3Exception
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.gsat.mylambdafunction.core.Helpers
import com.gsat.mylambdafunction.domain.ConfigDtc
import com.gsat.mylambdafunction.domain.HandlerInput
import com.gsat.mylambdafunction.domain.HandlerOutput
import java.io.InputStream
import java.io.OutputStream



class Handler(val config: ConfigDtc? = null) {

    val dataBucket = System.getenv("dataBucket") ?: config!!.environmentVariables.dataBucket
    var s3: AmazonS3
    val streamCache = StreamCache(1000)
    val mapper = jacksonObjectMapper()


    init {
        println("environment variables:")
        println("\tdataBucket: ${dataBucket}")

        if (config == null) {
            println("config is null")
        }
        else {
            println("config is not null")
        }

        s3 = if (config != null) AmazonS3ClientBuilder.standard()
                .withCredentials(ProfileCredentialsProvider(config!!.aws.namedProfile))
                .withRegion(config.aws.region)
                .build() else AmazonS3ClientBuilder.standard().build()
    }



    fun getStreamFromCacheOrS3(key: String, useCache: Boolean): InputStream? {

        if (useCache) {
            val stream: InputStream? = streamCache.getStreamForKey(key)
            if (stream != null) {
                println("$key: found in cache")
                return stream
            } else {
                println("$key: not in cache")
            }
        } else {
            println("$key not looking in cache")
        }

        println("$key: attempting to find in S3")

        val s3ObjectInputStream: S3ObjectInputStream
        try {
            s3ObjectInputStream = s3.getObject(dataBucket, key).objectContent
            println("$key: found in s3")
        } catch (e: AmazonS3Exception) {
            println("$key: could not retrieve object from s3")
            return null
        }

        streamCache.addStreamWithKey(s3ObjectInputStream, key)
        return s3ObjectInputStream
    }


    fun handler(input: InputStream, output: OutputStream): Unit {
        println("starting handler")
        val eventString = Helpers().convertStreamToString(input)
        val event = mapper.readValue<HandlerInput>(eventString)
        println("useCache, ${event.useCache}")

        println("input streams currently in cache ${streamCache.listStreams()}")

        val streams = mutableListOf<InputStream>()

        for (key in event.keys) {
            val s = getStreamFromCacheOrS3(key, event.useCache)
            if (s != null) {
                streams.add(s)
            }
        }

        println("input streams currently in cache ${streamCache.listStreams()}")
        val handlerOut = HandlerOutput(message = "some result")
        mapper.writeValue(output, handlerOut)
    }
}


