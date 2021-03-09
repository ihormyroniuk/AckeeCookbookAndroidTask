package me.ihormyroniuk.AckeeCookbookAndroidTask.Http

interface HttpResponse {

    val version: String
    val code: Int
    val phrase: String
    val headers: Map<String, String>?
    val body: ByteArray?

}