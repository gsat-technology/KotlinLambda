package com.gsat.mylambdafunction

import java.io.InputStream

class StreamCache(val maxItems: Int) {

    val queue = mutableListOf<String>()
    val inputStreams = hashMapOf<String, InputStream>()

    fun addStreamWithKey(stream: InputStream, key: String) {

        if (queue.size == maxItems) {
            removeStreamForKey(key)
         }

        inputStreams.put(key, stream)
        queue.add(key)
    }

    fun removeStreamForKey(key: String) {
        val first = queue.first()
        queue.remove(first)
        inputStreams.remove(first)
    }

    fun getStreamForKey(key: String): InputStream? {
        return inputStreams[key]
    }

    fun listStreams(): String {
        return queue.toString()
    }
}
