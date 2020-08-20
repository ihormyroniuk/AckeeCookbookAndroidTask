package me.ihormyroniuk.AckeeCookbookAndroidTask.WebApi

import java.net.URL

class HttpRequestHttpURLConnection(method: String, url: URL, version: String, headers: Map<String, String>?, body: ByteArray?): HttpRequest {
    override val method: String = method
    override val url: URL = url
    override val version: String = version
    override val headers: Map<String, String>? = headers
    override val body: ByteArray? = body
}