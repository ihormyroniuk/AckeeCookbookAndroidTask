package me.ihormyroniuk.AckeeCookbookAndroidTask.Http

import java.net.HttpURLConnection

class HttpURLConnectionHttpResponse(httpURLConnection: HttpURLConnection):
    HttpResponse {

    override val version: String = ""
    override val code: Int = httpURLConnection.responseCode
    override val phrase: String = httpURLConnection.responseMessage
    override val headers: Map<String, String>?
    override val body: ByteArray? = httpURLConnection.inputStream.readBytes()

    init {
        val headerFields = mutableMapOf<String, String>()
        for ((key, value) in httpURLConnection.headerFields) {
            if (key == null) { continue }
            headerFields[key] = value.joinToString(separator = ",")
        }
        this.headers = headerFields
    }

}