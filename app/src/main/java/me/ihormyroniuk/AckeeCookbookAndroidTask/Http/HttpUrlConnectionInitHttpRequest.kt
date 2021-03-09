package me.ihormyroniuk.AckeeCookbookAndroidTask.Http

import java.net.HttpURLConnection

fun HttpURLConnection(httpRequest: HttpRequest): HttpURLConnection {
    val httpUrlConnection = httpRequest.uri.openConnection() as HttpURLConnection
    httpUrlConnection.requestMethod = httpRequest.method
    httpRequest.headers?.let { headers ->
        for ((key, value) in headers) {
            httpUrlConnection.setRequestProperty(key, value)
        }
    }
    httpRequest.body?.let { body ->
        if (body.isNotEmpty()) {
            httpUrlConnection.doInput = true
            httpUrlConnection.doOutput = true
            httpUrlConnection.outputStream.write(body)
        }
    }
    return httpUrlConnection
}