package me.ihormyroniuk.AckeeCookbookAndroidTask.Http

import java.net.URL

class PlainHttpRequest(method: String, url: URL, version: String, headers: Map<String, String>?, body: ByteArray?):
    HttpRequest {
    override val method: String = method
    override val requestUri: URL = url
    override val httpVersion: String = version
    override val headerFields: Map<String, String>? = headers
    override val messageBody: ByteArray? = body
}