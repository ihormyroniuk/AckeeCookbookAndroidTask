package me.ihormyroniuk.AckeeCookbookAndroidTask.WebApi

import android.os.AsyncTask
import me.ihormyroniuk.AckeeCookbookAndroidTask.Business.AddedNewRating
import me.ihormyroniuk.AckeeCookbookAndroidTask.Http.HttpURLConnectionHttpResponse
import me.ihormyroniuk.AckeeCookbookAndroidTask.Http.Result
import me.ihormyroniuk.AckeeCookbookAndroidTask.Http.HttpURLConnection
import me.ihormyroniuk.AckeeCookbookAndroidTask.Business.RecipeDetails
import me.ihormyroniuk.AckeeCookbookAndroidTask.Business.RecipeInList
import me.ihormyroniuk.AckeeCookbookAndroidTask.WebApi.Version1.*
import me.ihormyroniuk.AckeeCookbookAndroidTask.WebApi.Version1.GetRecipes.Portion

class WebApiPerformer {

    var apiVersion1 = ApiVersion1.production()

    fun getRecipes(portion: Portion, completionHandler: (Result<List<RecipeInList>, Error>) -> Unit) {
        val endpoint = apiVersion1.getRecipes(portion)
        val request = endpoint.request()
        doAsync {
            val connection = HttpURLConnection(request)
            try {
                val response = HttpURLConnectionHttpResponse(connection)
                val recipes = endpoint.response(response)
                completionHandler(recipes)
            } finally {
                connection.disconnect()
            }
        }.execute()
    }

    fun getRecipe(recipeId: String, completionHandler: (Result<RecipeDetails, Error>) -> Unit) {
        val endpoint = apiVersion1.getRecipe()
        val request = endpoint.request(recipeId)
        doAsync {
            val connection = HttpURLConnection(request)
            try {
                val response = HttpURLConnectionHttpResponse(connection)
                val recipes = endpoint.response(response)
                completionHandler(recipes)
            } finally {
                connection.disconnect()
            }
        }.execute()
    }

    fun addNewRating(recipeId: String, score: Float, completionHandler: (Result<AddedNewRating, Error>) -> Unit) {
        val endpoint = apiVersion1.addNewRatingEndpoint()
        val request = endpoint.request(recipeId, score)
        doAsync {
            val connection = HttpURLConnection(request)
            try {
                val response = HttpURLConnectionHttpResponse(connection)
                val recipes = endpoint.response(response)
                completionHandler(recipes)
            } finally {
                connection.disconnect()
            }
        }.execute()
    }

    fun deleteRecipe(recipeId: String, completionHandler: (Error?) -> Unit) {
        val endpoint = apiVersion1.deleteRecipe()
        val request = endpoint.request(recipeId)
        doAsync {
            val connection = HttpURLConnection(request)
            try {
                val response = HttpURLConnectionHttpResponse(connection)
                val error = endpoint.response(response)
                completionHandler(error)
            } finally {
                connection.disconnect()
            }
        }.execute()
    }

    fun createNewRecipe(name: String?, description: String?, ingredients: List<String>?, duration: Int?, info: String?, completionHandler: (Result<RecipeDetails, Error>) -> Unit) {
        val endpoint = apiVersion1.createNewRecipe()
        val request = endpoint.request(name, description, ingredients, duration, info)
        doAsync {
            val connection = HttpURLConnection(request)
            try {
                val response = HttpURLConnectionHttpResponse(connection)
                val recipe = endpoint.response(response)
                completionHandler(recipe)
            } finally {
                connection.disconnect()
            }
        }.execute()
    }

    fun updateRecipe(id: String, name: String?, description: String?, ingredients: List<String>?, duration: Int?, info: String?, completionHandler: (Result<RecipeDetails, Error>) -> Unit) {
        val endpoint = apiVersion1.updateRecipe()
        val request = endpoint.request(id, name, description, ingredients, duration, info)
        doAsync {
            val connection = HttpURLConnection(request)
            try {
                val response = HttpURLConnectionHttpResponse(connection)
                val recipe = endpoint.response(response)
                completionHandler(recipe)
            } finally {
                connection.disconnect()
            }
        }.execute()
    }

    private class doAsync(val handler: () -> Unit) : AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg params: Void?): Void? {
            handler()
            return null
        }
    }
}

