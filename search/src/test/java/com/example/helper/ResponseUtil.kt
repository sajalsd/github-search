package com.example.helper

import com.example.network.data.dto.SearchResponse
import com.example.network.data.dto.SearchResponseJsonAdapter
import com.example.network.data.model.ApiResponse
import com.squareup.moshi.Moshi
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.Charset

object ResponseUtil {
    private fun readFileToString(contextClass: Class<*>, streamIdentifier: String): String {
        val inputStreamReader = InputStreamReader(
            contextClass.getResourceAsStream(streamIdentifier)!!,
            Charset.defaultCharset()
        )
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

    fun getErrorSearchResponse(exception: Exception = IOException("No internet connection")): ApiResponse<SearchResponse> {
        return ApiResponse.Error(exception)
    }

    fun getSuccessResponse(fileName: String): ApiResponse.Success<SearchResponse> {
        return ApiResponse.Success(getMockSearchResponse(fileName))
    }

    fun getMockSearchResponse(fileName: String): SearchResponse {
        val jsonString = readFileToString(ResponseUtil::class.java, "/$fileName")
        val moshi = Moshi.Builder()
            .build()
        return SearchResponseJsonAdapter(moshi).fromJson(jsonString)!!
    }
}
