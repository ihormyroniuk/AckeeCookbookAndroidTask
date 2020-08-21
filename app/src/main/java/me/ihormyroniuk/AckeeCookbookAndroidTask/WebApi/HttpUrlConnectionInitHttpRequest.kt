package me.ihormyroniuk.AckeeCookbookAndroidTask.WebApi

import java.net.HttpURLConnection

fun httpURLConnectionInit(httpRequest: HttpRequest): HttpURLConnection {
    val httpUrlConnection = httpRequest.requestUri.openConnection() as HttpURLConnection
    httpRequest.headerFields?.let { headers ->
        for ((key, value) in headers) {
            httpUrlConnection.setRequestProperty(key, value)
        }
    }
    httpRequest.messageBody?.let { body ->
        if (body.isNotEmpty()){
            httpUrlConnection.outputStream.write(body)
            httpUrlConnection.doOutput = true
        }
    }
    return httpUrlConnection
}