package me.ihormyroniuk.AckeeCookbookAndroidTask.Presentation.Screens.RecipeDetails

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import me.ihormyroniuk.AckeeCookbookAndroidTask.Business.AddedNewRating
import me.ihormyroniuk.AckeeCookbookAndroidTask.Business.RecipeDetails
import me.ihormyroniuk.AckeeCookbookAndroidTask.Business.RecipeInList
import me.ihormyroniuk.AckeeCookbookAndroidTask.Http.Failure
import me.ihormyroniuk.AckeeCookbookAndroidTask.Http.Result
import me.ihormyroniuk.AckeeCookbookAndroidTask.Http.Success
import me.ihormyroniuk.AckeeCookbookAndroidTask.Presentation.Screens.AddRecipe.AddRecipeScreenActivity
import me.ihormyroniuk.AckeeCookbookAndroidTask.Presentation.Screens.RecipesList.RecipesListScreenActivity
import java.lang.ref.WeakReference
import java.util.*

interface RecipeDetailsScreenDelegate {
    fun recipeDetailsScreenBack(recipeDetailsScreen: RecipeDetailsScreenActivity)
    fun recipeDetailsScreenUpdate(recipeDetailsScreen: RecipeDetailsScreenActivity, recipe: RecipeDetails)
    fun recipeDetailsScreenDelete(recipeDetailsScreen: RecipeDetailsScreenActivity, recipeId: String, completionHandler: (Error?) -> Unit)
    fun recipeDetailsScreenGetRecipe(recipeDetailsScreen: RecipeDetailsScreenActivity, recipeId: String, completionHandler: (Result<RecipeDetails, Error>) -> Unit)
    fun recipeDetailsScreenScore(recipeDetailsScreen: RecipeDetailsScreenActivity, recipeId: String, score: Float, completionHandler: (Result<AddedNewRating, Error>) -> Unit)
}

class RecipeDetailsScreenActivity: Activity() {

    companion object {

        val delegates = mutableMapOf<String, WeakReference<RecipeDetailsScreenDelegate>>()
        val recipesInLists = mutableMapOf<String, RecipeInList>()

        val identiferKey = "identifier"

        fun intent(context: Context, recipeInList: RecipeInList, delegate: RecipeDetailsScreenDelegate): Intent {
            val intent = Intent(context, RecipeDetailsScreenActivity::class.java)
            val identifier = UUID.randomUUID().toString()
            delegates.put(identifier, WeakReference(delegate))
            recipesInLists.put(identifier, recipeInList)
            intent.putExtra(identiferKey, identifier)
            return intent
        }

    }

    lateinit var view: RecipeDetailsScreenView
    var delegate: WeakReference<RecipeDetailsScreenDelegate>? = null
    lateinit var recipeInList: RecipeInList
    var recipeDetails: RecipeDetails? = null

    //region Events
    //

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = RecipeDetailsScreenView(this)
        setContentView(view)
        delegate = delegates.get(intent.getStringExtra(RecipeDetailsScreenActivity.identiferKey))
        recipeInList = recipesInLists.get(intent.getStringExtra(RecipeDetailsScreenActivity.identiferKey))!!
        setup()
        refresh()
        setContent()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing) {
            delegates.remove(intent.getStringExtra(identiferKey))
            recipesInLists.remove(intent.getStringExtra(identiferKey))
        }
    }

    //endregion

    //region Setup
    //

    private fun setup() {
        setupBackButton()
        setupDeleteButton()
        setupUpdateButton()
        setupSwipeRefreshLayout()
        setupStarButtons()
    }

    private fun setupBackButton() {
        view.barView.backButton.setOnClickListener {
            delegate?.get()?.recipeDetailsScreenBack(this)
        }
    }

    private fun setupUpdateButton() {
        view.barView.updateButton.setOnClickListener {
            update()
        }
    }

    private fun setupDeleteButton() {
        view.barView.deleteButton.setOnClickListener {
            delete()
        }
    }

    private fun setupSwipeRefreshLayout() {
        view.swipeRefreshLayout.setOnRefreshListener {
            refresh()
        }
    }

    private fun setupStarButtons() {
        for (button in view.scoreView.scoreStarsView.starButtons) {
            button.setOnClickListener {
                score(button)
            }
        }
    }

    //endregion

    //region Actions
    //

    private fun update() {
        val recipeDetailsCopy = recipeDetails?.copy()
        if (recipeDetailsCopy != null) {
            delegate?.get()?.recipeDetailsScreenUpdate(this, recipeDetailsCopy)
        }
    }

    private fun delete() {
        val recipeId = recipeInList.id
        delegate?.get()?.recipeDetailsScreenDelete(this, recipeId) {

        }
    }

    private fun refresh() {
        val recipeId = recipeInList.id
        view.swipeRefreshLayout.isRefreshing = true
        delegate?.get()?.recipeDetailsScreenGetRecipe(this, recipeId) { result ->
            runOnUiThread {
                if (result is Success) {
                    view.swipeRefreshLayout.isRefreshing = false
                    recipeDetails = result.success
                    setContent()
                }
                if (result is Failure) {

                }
            }
        }
    }

    private fun score(button: Button) {
        val starButtonIndex = view.scoreView.scoreStarsView.starButtons.indexOf(button)
        if (starButtonIndex != -1) {
            val score = (starButtonIndex + 1).toFloat()
            view.scoreView.progressBar.visibility = View.VISIBLE
            val recipeId = recipeInList.id
            delegate?.get()?.recipeDetailsScreenScore(this, recipeId, score) { result ->
                if (result is Success) {
                    recipeInList.score = result.success.score
                    view.scoreView.progressBar.visibility = View.INVISIBLE
                    setContent()
                }
                if (result is Failure) {

                }
            }
        }
    }

    //endregion

    //region Content
    //

    private fun setContent() {
        view.barView.deleteButton.text = "Delete"
        view.barView.updateButton.text = "Update"
        view.nameTextView.text = recipeInList.name
        view.scoreView.scoreStarsView.setSelectedScore(recipeInList.score)
        view.scoreStarsView.setScore(recipeInList.score)
        view.durationTextView.text = "${recipeInList.duration} min."
        recipeDetails?.let { recipeDetails ->
            view.infoTextView.text = recipeDetails.info
            view.ingredientsTextView.text = "Ingredients"
            view.descriptionTitleLabel.text = "Description"
            view.descriptionLabel.text = recipeDetails.description
            view.scoreView.textView.text = "Score this recipe"
            view.setIngredients(recipeDetails.ingredients)
        }
    }

    //endregion

}