package me.ihormyroniuk.AckeeCookbookAndroidTask.Http

import java.net.URL

interface HttpRequest {

    val method: String
    val uri: URL
    val version: String
    val headers: Map<String, String>?
    val body: ByteArray?

}