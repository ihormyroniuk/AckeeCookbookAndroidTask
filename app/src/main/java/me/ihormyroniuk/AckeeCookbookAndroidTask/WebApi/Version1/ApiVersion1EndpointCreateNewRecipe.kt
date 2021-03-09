package me.ihormyroniuk.AckeeCookbookAndroidTask.WebApi.Version1

import me.ihormyroniuk.AckeeCookbookAndroidTask.Business.RecipeDetails
import me.ihormyroniuk.AckeeCookbookAndroidTask.Http.*
import me.ihormyroniuk.AckeeCookbookAndroidTask.WebApi.Api
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL

class ApiVersion1EndpointCreateNewRecipe(protocol: String, host: String): ApiVersion1Endpoint(protocol, host) {

    fun request(name: String?, description: String?, ingredients: List<String>?, duration: Int?, info: String?): HttpRequest {
        var path: String = "${this.basePath}/recipes"
        val url = URL(super.protocol, super.host, path)
        val method: String = Api.Http.Method.post
        val jsonObject = JSONObject()
        jsonObject.put("name", name)
        jsonObject.put("description", description)
        val ingredientsJsonArray = JSONArray(ingredients)
        jsonObject.put("ingredients", ingredientsJsonArray)
        jsonObject.put("duration", duration)
        jsonObject.put("info", info)
        val body = jsonObject.toString().toByteArray(Charsets.UTF_8)
        val headers = mutableMapOf<String, String>()
        headers.put(Api.Http.Header.contentType, Api.Http.Header.contentTypeJson)
        val httpRequest = PlainHttpRequest(method, url, "", headers, body)
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