package me.ihormyroniuk.AckeeCookbookAndroidTask.WebApi.Version1

data class ApiVersion1Error(val code: Int, val status: Int, val name: String, override val message: String): Error(message) {

}