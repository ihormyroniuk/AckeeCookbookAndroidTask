package me.ihormyroniuk.AckeeCookbookAndroidTask.WebApi.Version1

import me.ihormyroniuk.AckeeCookbookAndroidTask.Business.AddedNewRating
import me.ihormyroniuk.AckeeCookbookAndroidTask.Http.*
import me.ihormyroniuk.AckeeCookbookAndroidTask.WebApi.Api
import org.json.JSONObject
import java.net.URL

class ApiVersion1EndpointAddNewRating(protocol: String, host: String): ApiVersion1Endpoint(protocol, host) {

    fun request(recipeId: String, score: Float): HttpRequest {
        var path: String = "${this.basePath}/recipes/$recipeId/ratings"
        val url = URL(super.protocol, super.host, path)
        val method: String = Api.Http.Method.post
        val jsonObject = JSONObject()
        jsonObject.put("score", score)
        val body = jsonObject.toString().toByteArray(Charsets.UTF_8)
        val headers = mutableMapOf<String, String>()
        headers.put(Api.Http.Header.contentType, Api.Http.Header.contentTypeJson)
        val httpRequest = PlainHttpRequest(method, url, "", headers, body)
        return httpRequest
    }

    fun response(httpResponse: HttpResponse): Result<AddedNewRating, ApiVersion1Error> {
        val body = httpResponse.body
        val json = String(if (body != null) body else ByteArray(0), Charsets.UTF_8)
        val statusCode = httpResponse.code
        if (statusCode == 200) {
            val jsonObject = JSONObject(json)
            val id = jsonObject.getString("id")
            val recipeId = jsonObject.getString("recipe")
            val score = jsonObject.getDouble("score").toFloat()
            val addedNewRating = AddedNewRating(id, recipeId, score)
            return Success(addedNewRating)
        } else {
            val jsonObject = JSONObject(json)
            val error = this.error(jsonObject)
            return Failure(error)
        }
    }

}