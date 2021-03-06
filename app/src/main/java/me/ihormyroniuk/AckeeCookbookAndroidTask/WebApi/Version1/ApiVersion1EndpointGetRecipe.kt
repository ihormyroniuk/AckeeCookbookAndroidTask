package me.ihormyroniuk.AckeeCookbookAndroidTask.WebApi.Version1

import me.ihormyroniuk.AckeeCookbookAndroidTask.Business.RecipeDetails
import me.ihormyroniuk.AckeeCookbookAndroidTask.Http.*
import me.ihormyroniuk.AckeeCookbookAndroidTask.WebApi.Api
import org.json.JSONObject
import java.net.URL

class ApiVersion1EndpointGetRecipe(protocol: String, host: String): ApiVersion1Endpoint(protocol, host) {

    fun request(recipeId: String): HttpRequest {
        var path: String = "${this.basePath}/recipes/$recipeId"
        val url = URL(super.protocol, super.host, path)
        val method: String = Api.Http.Method.get
        val httpRequest = PlainHttpRequest(method, url, "", null, null)
        return httpRequest
    }

    fun response(httpResponse: HttpResponse): Result<RecipeDetails, ApiVersion1Error> {
        val body = httpResponse.body
        val json = String(if (body != null) body else ByteArray(0), Charsets.UTF_8)
        val statusCode = httpResponse.code
        if (statusCode == 200) {
            val jsonObject = JSONObject(json)
            val recipe = recipeDetails(jsonObject)
            return Success(recipe)
        } else {
            val jsonObject = JSONObject(json)
            val error = this.error(jsonObject)
            return Failure(error)
        }
    }

}