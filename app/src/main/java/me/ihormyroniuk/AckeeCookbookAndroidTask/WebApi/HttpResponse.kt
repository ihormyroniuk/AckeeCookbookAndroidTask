package me.ihormyroniuk.AckeeCookbookAndroidTask.WebApi

import java.net.HttpURLConnection

interface HttpResponse {
    val version: String
    val statusCode: Int
    val statusMessage: String
    val headers: Map<String, String>
    val body: ByteArray
}

fun HttpURLConnection.httpResponse(): HttpResponse {
    val version = ""
    val statusCode = responseCode
    val statusMessage = responseMessage
    var headers = mutableMapOf<String, String>()
    for ((key, value) in headerFields) {
        if (key == null) {
            continue
        }
        headers.put(key, value.joinToString(separator = "; "))
    }
    val body = inputStream.readBytes()
    val httpResponse = HttpResponseHttpURLConnection(version, statusCode, statusMessage, headers, body)
    return httpResponse
}