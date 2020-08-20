package me.ihormyroniuk.AckeeCookbookAndroidTask.Presentation

import android.app.Activity
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import me.ihormyroniuk.AckeeCookbookAndroidTask.WebApi.RecipeInList
import me.ihormyroniuk.AckeeCookbookAndroidTask.WebApi.WebApiPerformer
import java.net.HttpURLConnection
import java.net.URL

class RecipesListScreenActivity: Activity() {

    lateinit var view: RecipesListScreenView

    val webApiPerformer = WebApiPerformer()

    var recipes = listOf<RecipeInList>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = RecipesListScreenView(this)
        view.recyclerView.layoutManager = LinearLayoutManager(this)
        val recyclerViewAdapter = RecyclerViewAdapter()
        view.recyclerView.adapter = recyclerViewAdapter
        setContentView(view)
        webApiPerformer.getRecipes(0,10) { result ->
            runOnUiThread {
                recipes = result
                recyclerViewAdapter.notifyItemRangeInserted(0, recipes.size)
            }
        }
        view.swipeRefreshLayout.setOnRefreshListener {
            view.swipeRefreshLayout.isRefreshing = false
        }
    }

    private inner class RecyclerViewAdapter: RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

        private inner class MyViewHolder(val recipeListItemView: RecipeListItemWithDivider) : RecyclerView.ViewHolder(recipeListItemView)

        override fun onCreateViewHolder(parent: ViewGroup,
                                        viewType: Int): RecyclerViewAdapter.MyViewHolder {
            return MyViewHolder(RecipeListItemWithDivider(parent.context))
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.recipeListItemView.recipeView.nameTextView.text = recipes[position].name
        }

        override fun getItemCount(): Int {
            return recipes.size
        }
    }

}