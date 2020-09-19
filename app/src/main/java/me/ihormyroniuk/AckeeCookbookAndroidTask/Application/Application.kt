package me.ihormyroniuk.AckeeCookbookAndroidTask.Application

import android.app.Application
import me.ihormyroniuk.AckeeCookbookAndroidTask.Business.*
import me.ihormyroniuk.AckeeCookbookAndroidTask.Http.Failure
import me.ihormyroniuk.AckeeCookbookAndroidTask.Http.Result
import me.ihormyroniuk.AckeeCookbookAndroidTask.Http.Success
import me.ihormyroniuk.AckeeCookbookAndroidTask.Presentation.PresentationActivity
import me.ihormyroniuk.AckeeCookbookAndroidTask.Presentation.PresentationDelegate
import me.ihormyroniuk.AckeeCookbookAndroidTask.WebApi.WebApiPerformer

class Application: Application(), PresentationDelegate {

    val webApiPerformer = WebApiPerformer()

    override fun presentationGetRecipes(presentation: PresentationActivity, offset: Int, limit: Int, completionHandler: (Result<List<RecipeInList>, Error>) -> Unit) {
        webApiPerformer.getRecipes(0, 10) { result ->
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

    override fun presentationGetRecipe(presentation: PresentationActivity, recipeId: String, completionHandler: (Result<RecipeDetails, Error>) -> Unit) {
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

    override fun presentationScoreRecipe(presentation: PresentationActivity, recipeId: String, score: Float, completionHandler: (Result<AddedNewRating, Error>) -> Unit) {
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

    override fun presentationDeleteRecipe(presentation: PresentationActivity, recipeId: String, completionHandler: (Error?) -> Unit) {
        webApiPerformer.deleteRecipe(recipeId) { error ->
            completionHandler(error)
        }
    }

    override fun presentationUpdateRecipe(presentation: PresentationActivity, updatingRecipe: UpdatingRecipe, completionHandler: (Result<RecipeDetails, Error>) -> Unit) {
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

    override fun presentationAddRecipe(presentation: PresentationActivity, creatingRecipe: CreatingRecipe, completionHandler: (Result<RecipeDetails, Error>) -> Unit) {
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

}