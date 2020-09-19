package me.ihormyroniuk.AckeeCookbookAndroidTask.Http

import java.net.HttpURLConnection

class HttpURLConnectionHttpResponse(httpURLConnection: HttpURLConnection):
    HttpResponse {

    override val httpVersion: String = ""
    override val statusCode: Int = httpURLConnection.responseCode
    override val reasonPhrase: String = httpURLConnection.responseMessage
    override val headerFields: Map<String, String>?
    override val messageBody: ByteArray? = httpURLConnection.inputStream.readBytes()

    init {
        val headerFields = mutableMapOf<String, String>()
        for ((key, value) in httpURLConnection.headerFields) {
            if (key == null) { continue }
            headerFields[key] = value.joinToString(separator = ",")
        }
        this.headerFields = headerFields
    }

}