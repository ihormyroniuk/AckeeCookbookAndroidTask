package me.ihormyroniuk.AckeeCookbookAndroidTask.WebApi.Version1

import me.ihormyroniuk.AckeeCookbookAndroidTask.Business.RecipeDetails
import org.json.JSONObject

open class ApiVersion1Endpoint(protocol: String, host: String) {

    open val protocol: String = protocol
    open val host: String = host
    open val basePath: String = "api/v1"

    open fun error(jsonObject: JSONObject): ApiVersion1Error {
        val message = jsonObject.getString("message")
        val errorJsonObject = jsonObject.getJSONObject("err")
        val code = errorJsonObject.getInt("errorCode")
        val status = errorJsonObject.getInt("status")
        val name = errorJsonObject.getString("name")
        val error = ApiVersion1Error(code, status, name, message )
        return error
    }

    open fun recipeDetails(jsonObject: JSONObject): RecipeDetails {
        val id = jsonObject.getString("id")
        val name = jsonObject.getString("name")
        val description = jsonObject.getString("description")
        val info = jsonObject.getString("info")
        val duration = jsonObject.getInt("duration")
        val score = jsonObject.getDouble("score").toFloat()
        val ingredientsJsonArray = jsonObject.getJSONArray("ingredients")
        val ingredients = ArrayList<String>()
        for (i in 0 until ingredientsJsonArray.length()) {
            val ingredient = ingredientsJsonArray.getString(i)
            ingredients.add(ingredient)
        }
        val recipe = RecipeDetails(id, name, description, info, ingredients, duration, score)
        return recipe
    }


}