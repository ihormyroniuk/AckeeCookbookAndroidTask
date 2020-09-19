package me.ihormyroniuk.AckeeCookbookAndroidTask.Presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
import me.ihormyroniuk.AckeeCookbookAndroidTask.WebApi.Version1.ApiVersion1Error
import me.ihormyroniuk.AckeeCookbookAndroidTask.WebApi.WebApiPerformer
import java.lang.ref.WeakReference

interface PresentationDelegate {
    fun presentationGetRecipes(presentation: PresentationActivity, offset: Int, limit: Int, completionHandler: (Result<List<RecipeInList>, Error>) -> Unit)
    fun presentationGetRecipe(presentation: PresentationActivity, recipeId: String, completionHandler: (Result<RecipeDetails, Error>) -> Unit)
    fun presentationScoreRecipe(presentation: PresentationActivity, recipeId: String, score: Float, completionHandler: (Result<AddedNewRating, Error>) -> Unit)
    fun presentationDeleteRecipe(presentation: PresentationActivity, recipeId: String, completionHandler: (Error?) -> Unit)
    fun presentationUpdateRecipe(presentation: PresentationActivity, updatingRecipe: UpdatingRecipe, completionHandler: (Result<RecipeDetails, Error>) -> Unit)
    fun presentationAddRecipe(presentation: PresentationActivity, creatingRecipe: CreatingRecipe, completionHandler: (Result<RecipeDetails, Error>) -> Unit)
}

class PresentationActivity: AppCompatActivity(), RecipesListScreenDelegate, RecipeDetailsScreenDelegate, AddRecipeScreenDelegate {

    var delegate: WeakReference<PresentationDelegate>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val weakApplication = WeakReference(application as PresentationDelegate)
        delegate = weakApplication
        val intent = RecipesListScreenActivity.intent(this, this)
        startActivity(intent)
        //finish()
    }

    //region RecipesListScreen
    //

    override fun recipesListScreenAddRecipe(recipesListScreen: RecipesListScreenActivity) {
        val intent = AddRecipeScreenActivity.intent(recipesListScreen, this)
        recipesListScreen.startActivity(intent)
    }

    override fun recipesListScreenGetRecipes(recipesListScreen: RecipesListScreenActivity, offset: Int, limit: Int, completionHandler: (Result<List<RecipeInList>, Error>) -> Unit) {
        delegate?.get()?.presentationGetRecipes(this, offset, limit) { result ->
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

    //region AddRecipeScreenDelegate
    //

    override fun addRecipeScreenBack(addRecipeScreen: AddRecipeScreenActivity) {
        addRecipeScreen.finish()
    }

    override fun addRecipeScreenAddRecipe(addRecipeScreen: AddRecipeScreenActivity, recipe: CreatingRecipe) {
        delegate?.get()?.presentationAddRecipe(this, recipe) { result ->
            if (result is Success) {
                addRecipeScreen.finish()
            }
            if (result is Failure) {

            }
        }
    }

    //endregion

}
