package me.ihormyroniuk.AckeeCookbookAndroidTask.Presentation.Screens.RecipesList

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import me.ihormyroniuk.AckeeCookbookAndroidTask.Http.Success
import me.ihormyroniuk.AckeeCookbookAndroidTask.Presentation.Screens.RecipeDetails.RecipeDetailsScreenActivity
import me.ihormyroniuk.AckeeCookbookAndroidTask.Business.RecipeInList
import me.ihormyroniuk.AckeeCookbookAndroidTask.Http.Result
import me.ihormyroniuk.AckeeCookbookAndroidTask.WebApi.Version1.ApiVersion1Error
import me.ihormyroniuk.AckeeCookbookAndroidTask.WebApi.WebApiPerformer
import java.lang.ref.WeakReference
import java.util.*

interface RecipesListScreenDelegate {
    fun recipesListScreenAddRecipe(recipesListScreen: RecipesListScreenActivity)
    fun recipesListScreenGetRecipes(recipesListScreen: RecipesListScreenActivity, offset: Int, limit: Int, completionHandler: (Result<List<RecipeInList>, Error>) -> Unit)
    fun recipesListScreenShowRecipeDetails(recipesListScreen: RecipesListScreenActivity, recipeInList: RecipeInList)
}

class RecipesListScreenActivity: Activity() {

    companion object {

        val delegates = mutableMapOf<String, WeakReference<RecipesListScreenDelegate>>()

        val identiferKey = "identifier"

        fun intent(context: Context, delegate: RecipesListScreenDelegate): Intent {
            val intent = Intent(context, RecipesListScreenActivity::class.java)
            val identifier = UUID.randomUUID().toString()
            val weakReference = WeakReference(delegate)
            delegates.put(identifier, weakReference)
            intent.putExtra(identiferKey, identifier)
            return intent
        }

    }

    private lateinit var view: RecipesListScreenView
    private var delegate: WeakReference<RecipesListScreenDelegate>? = null
    private var recipes = listOf<RecipeInList>()

    //region Events
    //

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        delegate = delegates.get(intent.getStringExtra(identiferKey))
        view = RecipesListScreenView(this)
        setContentView(view)
        setup()
        refreshList()
        setContent()
    }

    override fun onDestroy() {
        super.onDestroy()
        delegates.remove(intent.getStringExtra(identiferKey))
    }

    //endregion

    //region Actions
    //

    private fun setup() {
        setupAddRecipe()
        setupSwipeRefreshLayout()
        setupRecyclerView()
    }

    private fun setupAddRecipe() {
        view.barView.addRecipeButton.setOnClickListener {
            addRecipe()
        }
    }

    private fun setupRecyclerView() {
        view.recyclerView.layoutManager = LinearLayoutManager(this)
        view.recyclerView.adapter = RecyclerViewAdapter()
    }

    private fun setupSwipeRefreshLayout() {
        view.swipeRefreshLayout.setOnRefreshListener {
            refreshList()
        }
    }

    //endregion

    //region Actions
    //

    private fun addRecipe() {
        delegate?.get()?.recipesListScreenAddRecipe(this)
    }

    private fun refreshList() {
        view.swipeRefreshLayout.isRefreshing = true
        delegate?.get()?.recipesListScreenGetRecipes(this, 0, 10) { result ->
            runOnUiThread {
                if (result is Success) {
                    view.recyclerView.adapter?.notifyItemRangeRemoved(0, recipes.size)
                    recipes = result.success
                    view.recyclerView.adapter?.notifyItemRangeInserted(0, recipes.size)
                    view.swipeRefreshLayout.isRefreshing = false
                } else {

                }
            }
        }
    }

    //endregion

    //region RecyclerViewAdapter
    //

    private inner class RecyclerViewAdapter: RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

        private inner class MyViewHolder(val recipesListScreenRecipeView: RecipesListScreenRecipeView) : RecyclerView.ViewHolder(recipesListScreenRecipeView)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            return MyViewHolder(RecipesListScreenRecipeView(parent.context))
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val recipe = recipes[position]
            holder.recipesListScreenRecipeView.nameTextView.text = recipe.name
            holder.recipesListScreenRecipeView.scoreStarsView.setScore(recipe.score)
            holder.recipesListScreenRecipeView.durationTextView.text = "${recipe.duration} min."
            holder.recipesListScreenRecipeView.setOnClickListener {
                delegate?.get()?.recipesListScreenShowRecipeDetails(this@RecipesListScreenActivity, recipe)
            }
        }

        override fun getItemCount(): Int {
            return recipes.size
        }

    }

    //endregion

    //region Content
    //

    private fun setContent() {
        view.barView.titleTextView.text = "Recipes"
    }

    //endregion

}