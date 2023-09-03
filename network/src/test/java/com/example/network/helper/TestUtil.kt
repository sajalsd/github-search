package com.example.network.helper

import okhttp3.mockwebserver.MockResponse
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.Charset
import java.util.concurrent.AbstractExecutorService
import java.util.concurrent.ExecutorService
import java.util.concurrent.TimeUnit

object TestUtil {
    fun immediateExecutorService(): ExecutorService {
        return object : AbstractExecutorService() {
            override fun execute(command: Runnable) {
                command.run()
            }

            override fun shutdown() {}

            override fun shutdownNow(): MutableList<Runnable>? = null

            override fun isShutdown(): Boolean = false

            override fun isTerminated(): Boolean = false

            override fun awaitTermination(timeout: Long, unit: TimeUnit): Boolean {
                return false
            }
        }
    }

    @Throws(IOException::class)
    fun mockResponse(fileName: String): MockResponse {
        return MockResponse().setChunkedBody(readFileToString(TestUtil::class.java, "/$fileName"), 32)
    }

    private fun readFileToString(contextClass: Class<*>, streamIdentifier: String): String {
        val inputStreamReader = InputStreamReader(contextClass.getResourceAsStream(streamIdentifier)!!, Charset.defaultCharset())
        try {
            val stringBuilder = StringBuilder()
            BufferedReader(inputStreamReader).use { reader ->
                var nextLine = reader.readLine()
                while (nextLine != null) {
                    stringBuilder.append(nextLine)
                    nextLine = reader.readLine()
                }
            }
            return stringBuilder.toString()
        } catch (ioException: IOException) {
            throw ioException
        } finally {
            inputStreamReader.close()
        }
    }
}
