package me.ihormyroniuk.AckeeCookbookAndroidTask.Presentation.Screens.AddRecipe

import android.app.Activity
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.View.NOT_FOCUSABLE
import android.widget.EditText
import android.widget.TimePicker
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

class AddRecipeScreenActivity: Activity(), TimePickerDialog.OnTimeSetListener {

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

    override fun onTimeSet(p0: TimePicker, p1: Int, p2: Int) {
        val minutes = p1 * 60 + p2
        view.durationEditText.text = SpannableStringBuilder("$minutes")
    }

    //endregion

    //region Setup
    //

    private fun setup() {
        setupBackButton()
        setupAddButton()
        setupAddIngredientButton()
        setupDurationEditText()
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

    private fun setupDurationEditText() {
        view.durationEditText.setOnClickListener {
            val timePickerDialog = TimePickerDialog(this, this, 0, 30, true)
            timePickerDialog.show()
        }
        view.durationEditText.focusable = NOT_FOCUSABLE
        view.durationEditText.isFocusableInTouchMode = false
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
        val duration = view.durationEditText.text.toString().toInt()
        val recipe = CreatingRecipe(name, description, info, ingredients, duration)
        delegate?.get()?.addRecipeScreenAddRecipe(this, recipe)
    }

    private fun addIngredient() {
        var editText = view.addIngregiendEditText()
        setIngredientEditText(editText)
    }

    //endregion

    private fun setContent() {
        view.barView.titleTextView.text = "Add Recipe"
        view.addIngredientButton.text = "Add"
        view.nameEditText.hint = "Name"
        view.infoEditText.hint = "Info"
        view.descriptionEditText.hint = "Description"
        view.ingredientsTextView.text = "Ingredients"
        view.durationEditText.hint = "Duration"
    }

    private fun setIngredientEditText(editText: EditText) {
        editText.hint = "Ingredient"
    }

}