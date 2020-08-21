package me.ihormyroniuk.AckeeCookbookAndroidTask.WebApi

interface HttpResponse {
    val httpVersion: String
    val statusCode: Int
    val reasonPhrase: String
    val headerFields: Map<String, String>?
    val messageBody: ByteArray?
}