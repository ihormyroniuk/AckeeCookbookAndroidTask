package me.ihormyroniuk.AckeeCookbookAndroidTask.Presentation.Screens.AddRecipe

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import me.ihormyroniuk.AckeeCookbookAndroidTask.Business.CreatingRecipe
import me.ihormyroniuk.AckeeCookbookAndroidTask.Business.RecipeDetails
import me.ihormyroniuk.AckeeCookbookAndroidTask.Business.RecipeInList
import me.ihormyroniuk.AckeeCookbookAndroidTask.Presentation.Screens.RecipeDetails.RecipeDetailsScreenActivity
import me.ihormyroniuk.AckeeCookbookAndroidTask.Presentation.Screens.RecipeDetails.RecipeDetailsScreenDelegate
import me.ihormyroniuk.AckeeCookbookAndroidTask.Presentation.Screens.RecipeDetails.RecipeDetailsScreenView
import me.ihormyroniuk.AckeeCookbookAndroidTask.Presentation.Screens.RecipesList.RecipesListScreenActivity
import java.lang.ref.WeakReference
import java.util.*

interface AddRecipeScreenDelegate {
    fun addRecipeScreenBack(addRecipeScreen: AddRecipeScreenActivity)
    fun addRecipeScreenAddRecipe(addRecipeScreen: AddRecipeScreenActivity, recipe: CreatingRecipe)
}

class AddRecipeScreenActivity: Activity() {

    companion object {

        val delegates = mutableMapOf<String, WeakReference<AddRecipeScreenDelegate>>()

        val identiferKey = "identifier"

        fun intent(context: Context, delegate: AddRecipeScreenDelegate): Intent {
            val intent = Intent(context, AddRecipeScreenActivity::class.java)
            val identifier = UUID.randomUUID().toString()
            delegates.put(identifier, WeakReference(delegate))
            intent.putExtra(identiferKey, identifier)
            return intent
        }

    }

    lateinit var view: AddRecipeScreenView
    var delegate: WeakReference<AddRecipeScreenDelegate>? = null

    //region Events
    //

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = AddRecipeScreenView(this)
        setContentView(view)
        delegate = delegates.get(intent.getStringExtra(identiferKey))
        setup()
        setContent()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing) {
            delegates.remove(intent.getStringExtra(identiferKey))
        }
    }

    //endregion

    //region Setup
    //

    private fun setup() {
        setupBackButton()
        setupAddButton()
        setupAddIngredientButton()
    }

    private fun setupBackButton() {
        view.barView.backButton.setOnClickListener {
            back()
        }
    }

    private fun setupAddButton() {
        view.barView.addButton.setOnClickListener {
            add()
        }
    }

    private fun setupAddIngredientButton() {
        view.addIngredientButton.setOnClickListener {
            addIngredient()
        }
    }

    //endregion

    //region Actions
    //

    private fun back() {
        delegate?.get()?.addRecipeScreenBack(this)
    }

    private fun add() {
        val name = view.nameEditText.text.toString()
        val info = view.infoEditText.text.toString()
        val ingredients = view.ingredientsEditTexts.map { it.text.toString() }
        val description = view.descriptionEditText.text.toString()
        val recipe = CreatingRecipe(name, description, info, ingredients, 90)
        delegate?.get()?.addRecipeScreenAddRecipe(this, recipe)
    }

    private fun addIngredient() {
        view.addIngregiendEditText()
    }

    //endregion

    private fun setContent() {
        view.barView.titleTextView.text = "Add Recipe"
        view.nameEditText.hint = "Name"
        view.ingredientsTextView.text = "Ingredients"
    }

}