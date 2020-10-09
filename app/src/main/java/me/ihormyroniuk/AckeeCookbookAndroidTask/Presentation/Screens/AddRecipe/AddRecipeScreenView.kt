package me.ihormyroniuk.AckeeCookbookAndroidTask.Presentation.Screens.AddRecipe

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.ConstraintSet.WRAP_CONTENT
import me.ihormyroniuk.AckeeCookbookAndroidTask.Http.dp
import me.ihormyroniuk.AckeeCookbookAndroidTask.Presentation.Colors.Colors
import me.ihormyroniuk.AckeeCookbookAndroidTask.R

class AddRecipeScreenBarView(context: Context): ConstraintLayout(context) {

    val backButton = ImageButton(context)
    val titleTextView = TextView(context)
    var addButton = ImageButton(context)

    init {
        elevation = dp(8).toFloat()
        val backgroundDrawable = GradientDrawable()
        backgroundDrawable.setColor(Color.WHITE)
        backgroundDrawable.shape = GradientDrawable.RECTANGLE
        background = backgroundDrawable
        setup()
        layout()
    }

    fun setup() {
        setupBackButton()
        setupTitleTextView()
        setupAddButton()
    }

    fun setupBackButton() {
        backButton.id = View.generateViewId()
        backButton.setBackgroundColor(Color.WHITE)
        backButton.setImageResource(R.drawable.ic_back_blue)
    }

    fun setupTitleTextView() {
        titleTextView.id = View.generateViewId()
        titleTextView.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD)
        titleTextView.textSize = 18.0f
        titleTextView.setTextColor(Color.BLACK)
    }

    fun setupAddButton() {
        addButton.id = View.generateViewId()
        addButton.setBackgroundColor(Color.WHITE)
        addButton.setImageResource(R.drawable.ic_add_blue)
    }

    fun layout() {
        layoutBackButton()
        layoutTitleTextView()
        layoutAddButton()
    }

    fun layoutBackButton() {
        addView(backButton)
        val constraintSet = ConstraintSet()
        constraintSet.connect(backButton.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
        constraintSet.connect(backButton.id, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, dp(24))
        constraintSet.connect(backButton.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
        constraintSet.constrainWidth(backButton.id, WRAP_CONTENT)
        constraintSet.constrainHeight(backButton.id, WRAP_CONTENT)
        constraintSet.applyTo(this)
    }

    fun layoutTitleTextView() {
        addView(titleTextView)
        val constraintSet = ConstraintSet()
        constraintSet.connect(titleTextView.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
        constraintSet.connect(titleTextView.id, ConstraintSet.LEFT, backButton.id, ConstraintSet.RIGHT, dp(24))
        constraintSet.connect(titleTextView.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
        constraintSet.constrainWidth(titleTextView.id, WRAP_CONTENT)
        constraintSet.constrainHeight(titleTextView.id, WRAP_CONTENT)
        constraintSet.applyTo(this)
    }

    fun layoutAddButton() {
        addView(addButton)
        val constraintSet = ConstraintSet()
        constraintSet.connect(addButton.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
        constraintSet.connect(addButton.id, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, dp(24))
        constraintSet.connect(addButton.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
        constraintSet.constrainWidth(addButton.id, WRAP_CONTENT)
        constraintSet.constrainHeight(addButton.id, WRAP_CONTENT)
        constraintSet.applyTo(this)
    }

}

class AddRecipeScreenView(context: Context): ConstraintLayout(context) {

    val barView = AddRecipeScreenBarView(context)
    val scrollView = ScrollView(context)
    val scrollViewConstraitLayout = ConstraintLayout(context)
    val nameEditText = EditText(context)
    val infoEditText = EditText(context)
    val ingredientsConstraintLayout = ConstraintLayout(context)
    val ingredientsTextView = TextView(context)
    val ingredientsEditTexts = arrayListOf<EditText>()
    val addIngredientButton = Button(context)
    val descriptionEditText = EditText(context)
    val durationEditText = EditText(context)

    init {
        setup()
        layout()
    }

    //region Setup
    //

    private fun setup() {
        setupIngredientsTextView()
    }

    private fun setupIngredientsTextView() {
        ingredientsTextView.setTextColor(Color.BLUE)
        ingredientsTextView.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD)
        ingredientsTextView.textSize = 18.0f
    }

    //endregion

    //region Layout
    //

    private fun layout() {
        barView.id = View.generateViewId()
        scrollView.id = View.generateViewId()
        scrollViewConstraitLayout.id = View.generateViewId()
        nameEditText.id = View.generateViewId()
        infoEditText.id = View.generateViewId()
        ingredientsConstraintLayout.id = View.generateViewId()
        ingredientsTextView.id = View.generateViewId()
        addIngredientButton.id = View.generateViewId()
        descriptionEditText.id = View.generateViewId()
        durationEditText.id = View.generateViewId()
        layoutBarView()
        layoutScrollView()
        layoutScrollViewConstraitLayout()
        layoutNameEditText()
        layoutInfoEditText()
        layoutIngredientsConstraintLayout()
        layoutAddIngredientsButton()
        layoutIngredientsTextView()
        layoutDescriptionEditText()
        layoutDurationEditText()
    }

    private fun layoutBarView() {

        addView(barView)
        val constraintSet = ConstraintSet()
        constraintSet.connect(barView.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
        constraintSet.connect(barView.id, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT)
        constraintSet.connect(barView.id, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT)
        constraintSet.constrainHeight(barView.id, dp(56))
        constraintSet.applyTo(this)
    }

    private fun layoutScrollView() {
        addView(scrollView)
        val constraintSet = ConstraintSet()
        constraintSet.connect(scrollView.id, ConstraintSet.TOP, barView.id, ConstraintSet.BOTTOM)
        constraintSet.connect(scrollView.id, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT)
        constraintSet.connect(scrollView.id, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT)
        constraintSet.connect(scrollView.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
        constraintSet.applyTo(this)
    }

    private fun layoutScrollViewConstraitLayout() {
        scrollView.addView(scrollViewConstraitLayout, MATCH_PARENT, WRAP_CONTENT)
    }

    private fun layoutNameEditText() {
        scrollViewConstraitLayout.addView(nameEditText)
        val constraintSet = ConstraintSet()
        constraintSet.connect(nameEditText.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, dp(30))
        constraintSet.connect(nameEditText.id, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, dp(24))
        constraintSet.connect(nameEditText.id, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, dp(24))
        constraintSet.constrainHeight(nameEditText.id, WRAP_CONTENT)
        constraintSet.applyTo(scrollViewConstraitLayout)
    }

    private fun layoutInfoEditText() {
        scrollViewConstraitLayout.addView(infoEditText)
        val constraintSet = ConstraintSet()
        constraintSet.connect(infoEditText.id, ConstraintSet.TOP, nameEditText.id, ConstraintSet.BOTTOM, dp(30))
        constraintSet.connect(infoEditText.id, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, dp(24))
        constraintSet.connect(infoEditText.id, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, dp(24))
        constraintSet.constrainHeight(infoEditText.id, WRAP_CONTENT)
        constraintSet.applyTo(scrollViewConstraitLayout)
    }

    private fun layoutIngredientsConstraintLayout() {
        scrollViewConstraitLayout.addView(ingredientsConstraintLayout)
        val constraintSet = ConstraintSet()
        constraintSet.connect(ingredientsConstraintLayout.id, ConstraintSet.TOP, infoEditText.id, ConstraintSet.BOTTOM, dp(36))
        constraintSet.connect(ingredientsConstraintLayout.id, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, dp(24))
        constraintSet.connect(ingredientsConstraintLayout.id, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, dp(24))
        constraintSet.constrainHeight(ingredientsConstraintLayout.id, WRAP_CONTENT)
        constraintSet.applyTo(scrollViewConstraitLayout)
    }

    private fun layoutIngredientsTextView() {
        ingredientsConstraintLayout.addView(ingredientsTextView)
        val constraintSet = ConstraintSet()
        constraintSet.connect(ingredientsTextView.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
        constraintSet.connect(ingredientsTextView.id, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT)
        constraintSet.connect(ingredientsTextView.id, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT)
        constraintSet.constrainHeight(ingredientsTextView.id, WRAP_CONTENT)
        constraintSet.applyTo(ingredientsConstraintLayout)
    }

    private fun layoutAddIngredientsButton() {
        ingredientsConstraintLayout.addView(addIngredientButton)
        val constraintSet = ConstraintSet()
        constraintSet.connect(addIngredientButton.id, ConstraintSet.TOP, ingredientsTextView.id, ConstraintSet.BOTTOM, dp(16))
        constraintSet.connect(addIngredientButton.id, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT)
        constraintSet.connect(addIngredientButton.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
        constraintSet.constrainHeight(addIngredientButton.id, WRAP_CONTENT)
        constraintSet.applyTo(ingredientsConstraintLayout)
    }

    private fun layoutDescriptionEditText() {
        scrollViewConstraitLayout.addView(descriptionEditText)
        val constraintSet = ConstraintSet()
        constraintSet.connect(descriptionEditText.id, ConstraintSet.TOP, ingredientsConstraintLayout.id, ConstraintSet.BOTTOM, dp(30))
        constraintSet.connect(descriptionEditText.id, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, dp(24))
        constraintSet.connect(descriptionEditText.id, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, dp(24))
        constraintSet.constrainHeight(descriptionEditText.id, WRAP_CONTENT)
        constraintSet.applyTo(scrollViewConstraitLayout)
    }

    private fun layoutDurationEditText() {
        scrollViewConstraitLayout.addView(durationEditText)
        val constraintSet = ConstraintSet()
        constraintSet.connect(durationEditText.id, ConstraintSet.TOP, descriptionEditText.id, ConstraintSet.BOTTOM, dp(30))
        constraintSet.connect(durationEditText.id, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, dp(24))
        constraintSet.connect(durationEditText.id, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, dp(24))
        constraintSet.connect(durationEditText.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, dp(24))
        constraintSet.constrainHeight(durationEditText.id, WRAP_CONTENT)
        constraintSet.applyTo(scrollViewConstraitLayout)
    }

    //endregion

    //region
    //

    fun addIngregiendEditText(): EditText {

        val lastView = if (ingredientsEditTexts.isEmpty()) ingredientsTextView else ingredientsEditTexts.last()
        val editText = EditText(context)
        editText.id = View.generateViewId()
        ingredientsEditTexts.add(editText)
        ingredientsConstraintLayout.addView(editText)

        val constraintSet = ConstraintSet()
        constraintSet.connect(editText.id, ConstraintSet.TOP, lastView.id, ConstraintSet.BOTTOM)
        constraintSet.connect(editText.id, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT)
        constraintSet.connect(editText.id, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT)
        constraintSet.constrainHeight(editText.id, WRAP_CONTENT)

        constraintSet.clear(addIngredientButton.id)
        constraintSet.connect(addIngredientButton.id, ConstraintSet.TOP, editText.id, ConstraintSet.BOTTOM, dp(16))
        constraintSet.connect(addIngredientButton.id, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT)
        constraintSet.connect(addIngredientButton.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
        constraintSet.constrainHeight(addIngredientButton.id, WRAP_CONTENT)

        constraintSet.applyTo(ingredientsConstraintLayout)
        return editText
    }

    //endregion

}