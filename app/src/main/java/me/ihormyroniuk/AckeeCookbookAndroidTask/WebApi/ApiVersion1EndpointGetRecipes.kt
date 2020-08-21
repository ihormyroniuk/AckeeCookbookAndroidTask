package me.ihormyroniuk.AckeeCookbookAndroidTask.WebApi

import org.json.JSONArray
import java.net.URL

class ApiVersion1EndpointGetRecipes(protocol: String, host: String): ApiVersion1Endpoint(protocol, host) {

    fun request(limit: Int, offset: Int): HttpRequest {
        var file: String = "api/v1/recipes?limit=$limit&offset=$offset"
        val url = URL(super.protocol, super.host, file)
        val method: String = "GET"
        val httpRequest = PlainHttpRequest(method, url, "", null, null)
        return httpRequest
    }

    fun response(httpResponse: HttpResponse): List<RecipeInList> {
        val body = httpResponse.messageBody
        val json = String(if (body != null) body else ByteArray(0), Charsets.UTF_8)
        val jsonArray = JSONArray(json)
        val statusCode = httpResponse.statusCode
        if (statusCode == 200) {
            val recipes: MutableList<RecipeInList> = mutableListOf()
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val id = jsonObject.getString("id")
                val name = jsonObject.getString("name")
                val duration = jsonObject.getInt("duration")
                val score = jsonObject.getDouble("score")
                val recipe = RecipeInList(id, name, duration, score)
                recipes.add(recipe)
            }
            return recipes
        } else {
            val recipes: MutableList<RecipeInList> = mutableListOf()
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val id = jsonObject.getString("id")
                val name = jsonObject.getString("name")
                val duration = jsonObject.getInt("duration")
                val score = jsonObject.getDouble("score")
                val recipe = RecipeInList(id, name, duration, score)
                recipes.add(recipe)
            }
            return recipes
        }
    }

}