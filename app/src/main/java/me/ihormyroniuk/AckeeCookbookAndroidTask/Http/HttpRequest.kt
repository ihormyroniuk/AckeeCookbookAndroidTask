package me.ihormyroniuk.AckeeCookbookAndroidTask.Http

import java.net.URL

interface HttpRequest {
    val method: String
    val requestUri: URL
    val httpVersion: String
    val headerFields: Map<String, String>?
    val messageBody: ByteArray?
}