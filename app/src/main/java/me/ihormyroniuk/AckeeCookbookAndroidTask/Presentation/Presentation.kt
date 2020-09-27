package me.ihormyroniuk.AckeeCookbookAndroidTask.Presentation

import android.os.Looper
import me.ihormyroniuk.AckeeCookbookAndroidTask.Application.Application
import me.ihormyroniuk.AckeeCookbookAndroidTask.Application.MainLauncherActivity
import me.ihormyroniuk.AckeeCookbookAndroidTask.Business.*
import me.ihormyroniuk.AckeeCookbookAndroidTask.Http.Failure
import me.ihormyroniuk.AckeeCookbookAndroidTask.Http.Result
import me.ihormyroniuk.AckeeCookbookAndroidTask.Http.Success
import me.ihormyroniuk.AckeeCookbookAndroidTask.Presentation.Screens.AddRecipe.AddRecipeScreenActivity
import me.ihormyroniuk.AckeeCookbookAndroidTask.Presentation.Screens.AddRecipe.AddRecipeScreenDelegate
import me.ihormyroniuk.AckeeCookbookAndroidTask.Presentation.Screens.RecipeDetails.RecipeDetailsScreenActivity
import me.ihormyroniuk.AckeeCookbookAndroidTask.Presentation.Screens.RecipeDetails.RecipeDetailsScreenDelegate
import me.ihormyroniuk.AckeeCookbookAndroidTask.Presentation.Screens.RecipesList.RecipesListScreenActivity
import me.ihormyroniuk.AckeeCookbookAndroidTask.Presentation.Screens.RecipesList.RecipesListScreenDelegate
import java.lang.ref.WeakReference

interface PresentationDelegate {
    fun presentationGetRecipes(presentation: Presentation, offset: Int, limit: Int, completionHandler: (Result<List<RecipeInList>, Error>) -> Unit)
    fun presentationGetRecipe(presentation: Presentation, recipeId: String, completionHandler: (Result<RecipeDetails, Error>) -> Unit)
    fun presentationScoreRecipe(presentation: Presentation, recipeId: String, score: Float, completionHandler: (Result<AddedNewRating, Error>) -> Unit)
    fun presentationDeleteRecipe(presentation: Presentation, recipeId: String, completionHandler: (Error?) -> Unit)
    fun presentationUpdateRecipe(presentation: Presentation, updatingRecipe: UpdatingRecipe, completionHandler: (Result<RecipeDetails, Error>) -> Unit)
    fun presentationAddRecipe(presentation: Presentation, creatingRecipe: CreatingRecipe, completionHandler: (Result<RecipeDetails, Error>) -> Unit)
}

class Presentation: RecipesListScreenDelegate, RecipeDetailsScreenDelegate, AddRecipeScreenDelegate {

    fun showRecipesList(mainLauncherActivity: MainLauncherActivity) {
        val intent = RecipesListScreenActivity.intent(mainLauncherActivity, this) { activity ->
            recipesListScreenActivity = WeakReference(activity)
        }
        mainLauncherActivity.startActivity(intent)
        mainLauncherActivity.finish()
    }

    var delegate: WeakReference<PresentationDelegate>? = null

    //region RecipesListScreen
    //

    var recipesListScreenActivity: WeakReference<RecipesListScreenActivity>? = null

    override fun recipesListScreenAddRecipe(recipesListScreen: RecipesListScreenActivity) {
        val intent = AddRecipeScreenActivity.intent(recipesListScreen, this)
        recipesListScreen.startActivity(intent)
    }

    override fun recipesListScreenGetRecipes(recipesListScreen: RecipesListScreenActivity, offset: Int, limit: Int, completionHandler: (Result<List<RecipeInList>, Error>) -> Unit) {
        delegate?.get()?.presentationGetRecipes(this, offset, limit) { result ->
            recipesListScreen.runOnUiThread {
                if (result is Success) {
                    val recipe = Success(result.success)
                    completionHandler(recipe)
                }
                if (result is Failure) {
                    val error = result
                    completionHandler(error)
                }
            }
        }
    }

    override fun recipesListScreenShowRecipeDetails(recipesListScreen: RecipesListScreenActivity, recipeInList: RecipeInList) {
        val intent = RecipeDetailsScreenActivity.intent(recipesListScreen, recipeInList, this)
        recipesListScreen.startActivity(intent)
    }

    //endregion

    //region RecipeDetailsScreen
    //

    override fun recipeDetailsScreenBack(recipeDetailsScreen: RecipeDetailsScreenActivity) {
        recipeDetailsScreen.finish()
    }

    override fun recipeDetailsScreenUpdate(recipeDetailsScreen: RecipeDetailsScreenActivity, recipe: RecipeDetails) {

    }

    override fun recipeDetailsScreenDelete(recipeDetailsScreen: RecipeDetailsScreenActivity, recipeId: String, completionHandler: (Error?) -> Unit) {
        delegate?.get()?.presentationDeleteRecipe(this, recipeId) { error ->
            if (error != null) {

            } else {
                recipesListScreenActivity?.get()?.runOnUiThread {
                    recipesListScreenActivity?.get()?.knowRecipeWasDeleted(recipeDetailsScreen.recipeInList)
                }
                recipeDetailsScreen.finish()
            }
        }
    }

    override fun recipeDetailsScreenGetRecipe(recipeDetailsScreen: RecipeDetailsScreenActivity, recipeId: String, completionHandler: (Result<RecipeDetails, Error>) -> Unit) {
        delegate?.get()?.presentationGetRecipe(this, recipeId) { result ->
            if (result is Success) {
                val recipe = Success(result.success)
                completionHandler(recipe)
            }
            if (result is Failure) {
                val error = result
                completionHandler(error)
            }
        }
    }

    override fun recipeDetailsScreenScore(recipeDetailsScreen: RecipeDetailsScreenActivity, recipeId: String, score: Float, completionHandler: (Result<AddedNewRating, Error>) -> Unit) {
        delegate?.get()?.presentationScoreRecipe(this, recipeId, score) { result ->
            recipeDetailsScreen.runOnUiThread {
                if (result is Success) {
                    val recipe = Success(result.success)
                    completionHandler(recipe)
                    recipesListScreenActivity?.get()?.runOnUiThread {
                        recipesListScreenActivity?.get()?.knowRecipeScoreWasChanged(recipeDetailsScreen.recipeInList, score)
                    }
                }
                if (result is Failure) {
                    val error = result
                    completionHandler(error)
                }
            }
        }
    }

    //endregion

    //region AddRecipeScreenDelegate
    //

    override fun addRecipeScreenBack(addRecipeScreen: AddRecipeScreenActivity) {
        addRecipeScreen.finish()
    }

    override fun addRecipeScreenAddRecipe(addRecipeScreen: AddRecipeScreenActivity, recipe: CreatingRecipe) {
        delegate?.get()?.presentationAddRecipe(this, recipe) { result ->
            if (result is Success) {
                addRecipeScreen.finish()
                recipesListScreenActivity?.get()?.runOnUiThread {
                    recipesListScreenActivity?.get()?.knowRecipeWasAdded(result.success.recipeInList)
                }
            }
            if (result is Failure) {

            }
        }
    }

    //endregion

}