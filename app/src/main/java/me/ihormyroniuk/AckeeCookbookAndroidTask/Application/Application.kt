package me.ihormyroniuk.AckeeCookbookAndroidTask.Application

import android.app.Application
import me.ihormyroniuk.AckeeCookbookAndroidTask.Business.*
import me.ihormyroniuk.AckeeCookbookAndroidTask.Http.Failure
import me.ihormyroniuk.AckeeCookbookAndroidTask.Http.Result
import me.ihormyroniuk.AckeeCookbookAndroidTask.Http.Success
import me.ihormyroniuk.AckeeCookbookAndroidTask.Presentation.Presentation
import me.ihormyroniuk.AckeeCookbookAndroidTask.Presentation.PresentationDelegate
import me.ihormyroniuk.AckeeCookbookAndroidTask.WebApi.Version1.GetRecipes.Portion
import me.ihormyroniuk.AckeeCookbookAndroidTask.WebApi.WebApiPerformer
import java.lang.ref.WeakReference

class Application: Application(), PresentationDelegate {

    fun onCreateMainLauncherActivity(mainLauncherActivity: MainLauncherActivity) {
        presentation.showRecipesList(mainLauncherActivity)
    }

    //region Presentation
    //

    private val presentation: Presentation by lazy {
        val presentation = Presentation()
        presentation.delegate = WeakReference(this as PresentationDelegate)
        presentation
    }

    override fun presentationGetRecipes(presentation: Presentation, offset: Int, limit: Int, completionHandler: (Result<List<RecipeInList>, Error>) -> Unit) {
        val portion = Portion(limit, offset)
        webApiPerformer.getRecipes(portion) { result ->
            if (result is Success) {
                val recipes = Success(result.success)
                completionHandler(recipes)
            }
            if (result is Failure) {
                val error = result
                completionHandler(error)
            }
        }
    }

    override fun presentationGetRecipe(presentation: Presentation, recipeId: String, completionHandler: (Result<RecipeDetails, Error>) -> Unit) {
        webApiPerformer.getRecipe(recipeId) { result ->
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

    override fun presentationScoreRecipe(presentation: Presentation, recipeId: String, score: Float, completionHandler: (Result<AddedNewRating, Error>) -> Unit) {
        webApiPerformer.addNewRating(recipeId, score) { result ->
            if (result is Success) {
                val addedNewRating = Success(result.success)
                completionHandler(addedNewRating)
            }
            if (result is Failure) {
                val error = result
                completionHandler(error)
            }
        }
    }

    override fun presentationDeleteRecipe(presentation: Presentation, recipeId: String, completionHandler: (Error?) -> Unit) {
        webApiPerformer.deleteRecipe(recipeId) { error ->
            completionHandler(error)
        }
    }

    override fun presentationUpdateRecipe(presentation: Presentation, updatingRecipe: UpdatingRecipe, completionHandler: (Result<RecipeDetails, Error>) -> Unit) {
        val id = updatingRecipe.id
        val name = updatingRecipe.name
        val description = updatingRecipe.description
        val ingredients = updatingRecipe.ingredients
        val duration = updatingRecipe.duration
        val info = updatingRecipe.info
        webApiPerformer.updateRecipe(id, name, description, ingredients, duration, info) { result ->
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

    override fun presentationAddRecipe(presentation: Presentation, creatingRecipe: CreatingRecipe, completionHandler: (Result<RecipeDetails, Error>) -> Unit) {
        val name = creatingRecipe.name
        val description = creatingRecipe.description
        val ingredients = creatingRecipe.ingredients
        val duration = creatingRecipe.duration
        val info = creatingRecipe.info
        webApiPerformer.createNewRecipe(name, description, ingredients, duration, info) { result ->
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

    //endregion

    //region Presentation
    //

    private val webApiPerformer: WebApiPerformer by lazy {
        val webApiPerformer = WebApiPerformer()
        webApiPerformer
    }

    //endregion

}