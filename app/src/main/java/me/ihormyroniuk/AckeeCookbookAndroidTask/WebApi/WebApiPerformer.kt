package me.ihormyroniuk.AckeeCookbookAndroidTask.WebApi

import android.os.AsyncTask

data class RecipeInList(val id: String, val name: String, val duration: Int, val score: Double) {

}

class WebApiPerformer {

    fun getRecipes(offset: Int, limit: Int, completionHandler: (List<RecipeInList>) -> Unit) {
        val gg = ApiVersion1EndpointGetRecipes("https", "cookbook.ack.ee")
        val request = gg.request(limit, offset)
        doAsync {
            val connection = httpURLConnectionInit(request)
            try {
                val response = HttpURLConnectionHttpResponse(connection)
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

