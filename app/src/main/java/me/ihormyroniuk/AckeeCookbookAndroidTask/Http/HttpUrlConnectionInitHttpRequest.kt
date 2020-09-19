package me.ihormyroniuk.AckeeCookbookAndroidTask.Http

import java.net.HttpURLConnection

fun httpURLConnectionInit(httpRequest: HttpRequest): HttpURLConnection {
    val httpUrlConnection = httpRequest.requestUri.openConnection() as HttpURLConnection
    httpUrlConnection.requestMethod = httpRequest.method
    httpRequest.headerFields?.let { headers ->
        for ((key, value) in headers) {
            httpUrlConnection.setRequestProperty(key, value)
        }
    }
    httpRequest.messageBody?.let { body ->
        if (body.isNotEmpty()) {
            httpUrlConnection.doInput = true
            httpUrlConnection.doOutput = true
            httpUrlConnection.outputStream.write(body)
        }
    }
    return httpUrlConnection
}