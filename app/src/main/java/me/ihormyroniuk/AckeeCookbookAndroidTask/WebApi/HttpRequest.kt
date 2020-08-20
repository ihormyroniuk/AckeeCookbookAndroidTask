package me.ihormyroniuk.AckeeCookbookAndroidTask.WebApi

import java.net.HttpURLConnection
import java.net.URL

interface HttpRequest {
    val method: String
    val url: URL
    val version: String
    val headers: Map<String, String>?
    val body: ByteArray?
}

fun HttpRequest.httpUrlConnection(): HttpURLConnection {
    val httpUrlConnection = url.openConnection() as HttpURLConnection
    headers?.let { headers ->
        for ((key, value) in headers) {
            httpUrlConnection.setRequestProperty(key, value)
        }
    }
    body?.let { body ->
        if (!body.isEmpty()){
            httpUrlConnection.outputStream.write(body)
            httpUrlConnection.doOutput = true
        }
    }
    return httpUrlConnection
}