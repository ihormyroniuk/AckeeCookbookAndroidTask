package me.ihormyroniuk.AckeeCookbookAndroidTask.WebApi.Version1

import me.ihormyroniuk.AckeeCookbookAndroidTask.Http.*
import me.ihormyroniuk.AckeeCookbookAndroidTask.WebApi.Api
import org.json.JSONObject
import java.net.URL

class ApiVersion1EndpointDeleteRecipe(protocol: String, host: String): ApiVersion1Endpoint(protocol, host) {

    fun request(recipeId: String): HttpRequest {
        var path: String = "${this.basePath}/recipes/$recipeId"
        val url = URL(super.protocol, super.host, path)
        val method: String = Api.Http.Method.delete
        val httpRequest = PlainHttpRequest(method, url, "", null, null)
        return httpRequest
    }

    fun response(httpResponse: HttpResponse): ApiVersion1Error? {
        val statusCode = httpResponse.code
        if (statusCode == 204) {
            return null
        } else {
            val body = httpResponse.body
            val json = String(if (body != null) body else ByteArray(0), Charsets.UTF_8)
            val jsonObject = JSONObject(json)
            val error = this.error(jsonObject)
            return error
        }
    }

}