package me.ihormyroniuk.AckeeCookbookAndroidTask.WebApi.Version1.GetRecipes

import me.ihormyroniuk.AckeeCookbookAndroidTask.Http.*
import me.ihormyroniuk.AckeeCookbookAndroidTask.Business.RecipeInList
import me.ihormyroniuk.AckeeCookbookAndroidTask.WebApi.Api
import me.ihormyroniuk.AckeeCookbookAndroidTask.WebApi.Version1.ApiVersion1Endpoint
import me.ihormyroniuk.AckeeCookbookAndroidTask.WebApi.Version1.ApiVersion1Error
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL

class ApiVersion1EndpointGetRecipes(protocol: String, host: String, val portion: Portion): ApiVersion1Endpoint(protocol, host) {

    fun request(): HttpRequest {
        var file: String = "${this.basePath}/recipes?limit=${portion.limit}&offset=${portion.offset}"
        val url = URL(super.protocol, super.host, file)
        val method: String = Api.Http.Method.get
        val httpRequest = PlainHttpRequest(method, url, "", null, null)
        return httpRequest
    }

    fun response(httpResponse: HttpResponse): Result<List<RecipeInList>, ApiVersion1Error> {
        val body = httpResponse.body
        val json = String(if (body != null) body else ByteArray(0), Charsets.UTF_8)
        val statusCode = httpResponse.code
        if (statusCode == 200) {
            val jsonArray = JSONArray(json)
            val recipes: MutableList<RecipeInList> = mutableListOf()
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val id = jsonObject.getString("id")
                val name = jsonObject.getString("name")
                val duration = jsonObject.getInt("duration")
                val score = jsonObject.getDouble("score").toFloat()
                val recipe = RecipeInList(id, name, duration, score)
                recipes.add(recipe)
            }
            return Success(recipes)
        } else {
            val jsonObject = JSONObject(json)
            val error = this.error(jsonObject)
            return Failure(error)
        }
    }

}