package me.ihormyroniuk.AckeeCookbookAndroidTask.WebApi

import android.os.AsyncTask
import android.util.Log
import me.ihormyroniuk.AckeeCookbookAndroidTask.Presentation.RecipesListScreenActivity
import java.net.HttpURLConnection
import java.net.URL

data class RecipeInList(val id: String, val name: String, val duration: Int, val score: Double) {

}

class WebApiPerformer {

    fun getRecipes(offset: Int, limit: Int, completionHandler: (List<RecipeInList>) -> Unit) {
        val gg = ApiVersion1EndpointGetRecipes("https", "cookbook.ack.ee")
        val request = gg.request(limit, offset)
        doAsync {
            val connection = request.httpUrlConnection()
            try {
                val response = connection.httpResponse()
                val recipes = gg.response(response)
                completionHandler(recipes)
            } finally {
                connection.disconnect()
            }
        }.execute()
    }

    class doAsync(val handler: () -> Unit) : AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg params: Void?): Void? {
            handler()
            return null
        }
    }
}

