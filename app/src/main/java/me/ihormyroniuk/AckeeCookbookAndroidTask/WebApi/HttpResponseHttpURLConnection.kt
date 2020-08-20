package me.ihormyroniuk.AckeeCookbookAndroidTask.WebApi

class HttpResponseHttpURLConnection(version: String, statusCode: Int, statusMessage: String, headers: Map<String, String>, body: ByteArray): HttpResponse {
    override val version: String = version
    override val statusCode: Int = statusCode
    override val statusMessage: String = statusMessage
    override val headers: Map<String, String> = headers
    override val body: ByteArray = body
}