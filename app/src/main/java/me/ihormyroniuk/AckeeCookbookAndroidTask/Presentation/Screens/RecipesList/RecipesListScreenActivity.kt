package me.ihormyroniuk.AckeeCookbookAndroidTask.Presentation.Screens.RecipesList

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import me.ihormyroniuk.AckeeCookbookAndroidTask.Business.RecipeInList
import me.ihormyroniuk.AckeeCookbookAndroidTask.Http.Result
import me.ihormyroniuk.AckeeCookbookAndroidTask.Http.Success
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
        val onCreates = mutableMapOf<String, (RecipesListScreenActivity) -> Unit>()

        const val identiferKey = "identifier"

        fun intent(context: Context, delegate: RecipesListScreenDelegate, onCreate: (RecipesListScreenActivity) -> Unit): Intent {
            val intent = Intent(context, RecipesListScreenActivity::class.java)
            val identifier = UUID.randomUUID().toString()
            val weakReference = WeakReference(delegate)
            delegates.put(identifier, weakReference)
            onCreates.put(identifier, onCreate)
            intent.putExtra(identiferKey, identifier)
            return intent
        }

    }

    fun knowRecipeWasDeleted(recipe: RecipeInList) {
        refreshList()
    }

    fun knowRecipeWasAdded(recipe: RecipeInList) {
        refreshList()
    }

    fun knowRecipeScoreWasChanged(recipe: RecipeInList, score: Float) {
        val recipeId = recipe.id
        val item = recipes.first { it.id == recipeId }
        item?.score = score
        val index = recipes.indexOf(item)
        if (index != null) {
            view.recyclerView.adapter?.notifyItemChanged(index)
        }
    }

    private lateinit var view: RecipesListScreenView
    private val linearLayoutManager = LinearLayoutManager(this)
    private var delegate: WeakReference<RecipesListScreenDelegate>? = null
    private var recipesInListLoadOffset = 0
    private val recipesInListLoadlimit = 10
    private var lastDisplayedRecipeInListIndex: Int? = null
    private var recipes = listOf<RecipeInList>()

    //region Events
    //

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        delegate = delegates.get(intent.getStringExtra(identiferKey))
        (onCreates.get(intent.getStringExtra(identiferKey)))?.let { it(this) }
        view = RecipesListScreenView(this)
        setContentView(view)
        setup()
        refreshList()
        setContent()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing) {
            delegates.remove(intent.getStringExtra(identiferKey))
            onCreates.remove(intent.getStringExtra(identiferKey))
        }
    }

    //endregion

    //region Setup
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
        view.recyclerView.layoutManager = linearLayoutManager
        view.recyclerView.adapter = RecyclerViewAdapter()
        view.recyclerView.addOnScrollListener(RecyclerViewOnScrollListener())
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
        lastDisplayedRecipeInListIndex = null
        recipesInListLoadOffset = 0
        view.swipeRefreshLayout.isRefreshing = true
        val offset = recipesInListLoadOffset
        val limit = 2 * recipesInListLoadlimit
        delegate?.get()?.recipesListScreenGetRecipes(this, offset, limit) { result ->
            if (result is Success) {
                recipesInListLoadOffset = limit
                view.recyclerView.adapter?.notifyItemRangeRemoved(0, recipes.size)
                recipes = result.success
                view.recyclerView.adapter?.notifyItemRangeInserted(0, recipes.size)
                view.swipeRefreshLayout.isRefreshing = false
            } else {

            }
        }
    }

    private fun loadList() {
        val offset = recipesInListLoadOffset
        val limit = recipesInListLoadlimit
        delegate?.get()?.recipesListScreenGetRecipes(this, offset, limit) { result ->
            if (result is Success) {
                recipesInListLoadOffset += limit
                recipes += result.success
                view.recyclerView.adapter?.notifyItemRangeInserted(offset, recipes.size)
            } else {

            }
        }
    }

    private fun willDisplayRecipeInListAtIndex(index: Int) {
        if (recipesInListLoadOffset % recipesInListLoadlimit == 0) {
            if (recipesInListLoadOffset - recipesInListLoadlimit / 2 == index) {
                if (lastDisplayedRecipeInListIndex != null) {
                    if (index > lastDisplayedRecipeInListIndex!!) {
                        lastDisplayedRecipeInListIndex = index
                        loadList()
                    }
                } else {
                    lastDisplayedRecipeInListIndex = index
                    loadList()
                }
            }
        }
    }

    //endregion

    //region RecyclerView
    //

    private inner class RecyclerViewAdapter: RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

        private inner class MyViewHolder(val recipesListScreenRecipeView: RecipesListScreenRecipeView) : RecyclerView.ViewHolder(
            recipesListScreenRecipeView
        )

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            return MyViewHolder(RecipesListScreenRecipeView(parent.context))
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val recipe = recipes[position]
            holder.recipesListScreenRecipeView.nameTextView.text = recipe.name
            holder.recipesListScreenRecipeView.scoreStarsView.setScore(recipe.score)
            holder.recipesListScreenRecipeView.durationTextView.text = "${recipe.duration} min."
            holder.recipesListScreenRecipeView.setOnClickListener {
                delegate?.get()?.recipesListScreenShowRecipeDetails(
                    this@RecipesListScreenActivity,
                    recipe
                )
            }
        }

        override fun getItemCount(): Int {
            return recipes.size
        }

    }

    private inner class RecyclerViewOnScrollListener: RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition()
            willDisplayRecipeInListAtIndex(lastVisibleItemPosition)
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