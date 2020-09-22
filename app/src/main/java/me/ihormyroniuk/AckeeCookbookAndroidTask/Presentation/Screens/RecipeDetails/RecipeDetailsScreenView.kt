package me.ihormyroniuk.AckeeCookbookAndroidTask.Presentation.Screens.RecipeDetails

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.Gravity.CENTER
import android.view.View
import android.view.WindowInsets
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.ConstraintSet.WRAP_CONTENT
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import me.ihormyroniuk.AckeeCookbookAndroidTask.Http.dp
import me.ihormyroniuk.AckeeCookbookAndroidTask.Presentation.Components.ScoreStarsView
import me.ihormyroniuk.AckeeCookbookAndroidTask.R
import kotlin.math.roundToInt

class RecipeDetailsScreenBarView(context: Context): ConstraintLayout(context) {

    val backButton: ImageButton = ImageButton(context)
    var deleteButton = TextView(context)
    var updateButton = TextView(context)

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
        setBackgroundColor(Color.TRANSPARENT)
        setupBackButton()
        setupDeleteButton()
        setupUpdateButton()
    }

    fun setupBackButton() {
        backButton.id = View.generateViewId()
        backButton.setBackgroundColor(Color.WHITE)
        backButton.setImageResource(R.drawable.ic_back_blue)
    }

    fun setupDeleteButton() {
        deleteButton.id = View.generateViewId()
        //deleteButton.setBackgroundColor(Color.TRANSPARENT)

        val states = arrayOf(
            intArrayOf(android.R.attr.state_enabled),
            intArrayOf(-android.R.attr.state_enabled),
            intArrayOf(-android.R.attr.state_checked),
            intArrayOf(android.R.attr.state_pressed)
        )

        val colors = intArrayOf(
            Color.BLACK,
            Color.RED,
            Color.GREEN,
            Color.BLUE
        )

        val myList = ColorStateList(states, colors)

        deleteButton.setTextColor(myList)
    }

    fun setupUpdateButton() {
        updateButton.id = View.generateViewId()

        val states = arrayOf(
            intArrayOf(android.R.attr.state_enabled),
            intArrayOf(android.R.attr.state_checkable),
            intArrayOf(-android.R.attr.state_checked),
            intArrayOf(android.R.attr.state_pressed)
        )

        val colors = intArrayOf(
            Color.BLACK,
            Color.RED,
            Color.GREEN,
            Color.BLUE
        )

        val myList = ColorStateList(states, colors)

        updateButton.setTextColor(myList)
    }

    fun layout() {
        layoutBackButton()
        layoutDeleteButton()
        layoutUpdateButton()
    }

    fun layoutBackButton() {
        addView(backButton)
        val constraintSet = ConstraintSet()
        constraintSet.connect(
            backButton.id,
            ConstraintSet.TOP,
            ConstraintSet.PARENT_ID,
            ConstraintSet.TOP
        )
        constraintSet.connect(
            backButton.id, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, dp(
                24
            )
        )
        constraintSet.connect(
            backButton.id,
            ConstraintSet.BOTTOM,
            ConstraintSet.PARENT_ID,
            ConstraintSet.BOTTOM
        )
        constraintSet.constrainWidth(backButton.id, WRAP_CONTENT)
        constraintSet.constrainHeight(backButton.id, WRAP_CONTENT)
        constraintSet.applyTo(this)
    }

    fun layoutDeleteButton() {
        addView(deleteButton)
        val constraintSet = ConstraintSet()
        constraintSet.connect(
            deleteButton.id,
            ConstraintSet.TOP,
            ConstraintSet.PARENT_ID,
            ConstraintSet.TOP
        )
        constraintSet.connect(
            deleteButton.id, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, dp(
                24
            )
        )
        constraintSet.connect(
            deleteButton.id,
            ConstraintSet.BOTTOM,
            ConstraintSet.PARENT_ID,
            ConstraintSet.BOTTOM
        )
        constraintSet.constrainWidth(deleteButton.id, WRAP_CONTENT)
        constraintSet.constrainHeight(deleteButton.id, WRAP_CONTENT)
        constraintSet.applyTo(this)
        deleteButton.minWidth = 0
    }

    fun layoutUpdateButton() {
        addView(updateButton)
        val constraintSet = ConstraintSet()
        constraintSet.connect(
            updateButton.id,
            ConstraintSet.TOP,
            ConstraintSet.PARENT_ID,
            ConstraintSet.TOP
        )
        constraintSet.connect(
            updateButton.id, ConstraintSet.RIGHT, deleteButton.id, ConstraintSet.LEFT, dp(
                24
            )
        )
        constraintSet.connect(
            updateButton.id,
            ConstraintSet.BOTTOM,
            ConstraintSet.PARENT_ID,
            ConstraintSet.BOTTOM
        )
        constraintSet.constrainWidth(updateButton.id, WRAP_CONTENT)
        constraintSet.constrainHeight(updateButton.id, WRAP_CONTENT)
        constraintSet.applyTo(this)
        updateButton.minWidth = 0
    }

}

class RecipeDetailsScreenView(context: Context): ConstraintLayout(context) {

    val barView: RecipeDetailsScreenBarView = RecipeDetailsScreenBarView(context)
    val swipeRefreshLayout = SwipeRefreshLayout(context)
    val scrollView = ScrollView(context)
    val scrollViewConstraintLayout = ConstraintLayout(context)
    private val pictureImageView = PictureImageView(context)
    val nameTextView = TextView(context)
    val scoreDurationConstraintLayout = ConstraintLayout(context)
    val scoreStarsView = ScoreStarsView(context)
    val durationTextView = TextView(context)
    val durationImageView = ImageView(context)
    val infoTextView = TextView(context)
    val ingredientsConstraintLayout = ConstraintLayout(context)
    val ingredientsTextView = TextView(context)
    val ingredientsTextViews = mutableListOf<TextView>()
    val descriptionTitleLabel = TextView(context)
    val descriptionLabel = TextView(context)
    val scoreView = ScoreView(context)

    init {
        setup()
        layout()
    }

    //region Setup
    //

    private fun setup() {
        setupPictureImageView()
        setupNameTextView()
        setupDurationTextView()
        setupScoreDurationView()
        setupDurationImageView()
        setupInfoTextView()
        setupIngredientsTextView()
        setupDescriptionTitleLabel()
        setupDescriptionLabel()
    }

    private fun setupPictureImageView() {
        pictureImageView.scaleType = ImageView.ScaleType.FIT_XY
        pictureImageView.setImageResource(R.drawable.ic_recipe_large)
    }

    private fun setupNameTextView() {
        nameTextView.setTextColor(Color.WHITE)
        nameTextView.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD)
        nameTextView.textSize = 24.0f
    }

    private fun setupDurationTextView() {
        durationTextView.setTextColor(Color.WHITE)
        durationTextView.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD)
        durationTextView.textSize = 14.0f
    }

    private fun setupDurationImageView() {
        durationImageView.setImageResource(R.drawable.ic_time)
    }

    private fun setupScoreDurationView() {
        scoreDurationConstraintLayout.setBackgroundColor(Color.RED)
    }

    private fun setupInfoTextView() {
        infoTextView.setTextColor(Color.BLACK)
        infoTextView.textSize = 17.0f
    }

    private fun setupIngredientsTextView() {
        ingredientsTextView.setTextColor(Color.BLUE)
        ingredientsTextView.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD)
        ingredientsTextView.textSize = 18.0f
    }

    private fun setupDescriptionTitleLabel() {
        descriptionTitleLabel.setTextColor(Color.BLUE)
        descriptionTitleLabel.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD)
        descriptionTitleLabel.textSize = 18.0f
    }

    private fun setupDescriptionLabel() {
        descriptionLabel.setTextColor(Color.BLACK)
        descriptionLabel.textSize = 17.0f
    }

    //endregion

    //region Layout
    //

    private var systemWindowInsetTop: Int? = null

    private fun layout() {
        barView.id = View.generateViewId()
        scrollView.id = View.generateViewId()
        swipeRefreshLayout.id = View.generateViewId()
        scrollViewConstraintLayout.id = View.generateViewId()
        pictureImageView.id = View.generateViewId()
        scoreDurationConstraintLayout.id = View.generateViewId()
        scoreStarsView.id = View.generateViewId()
        nameTextView.id = View.generateViewId()
        ingredientsConstraintLayout.id = View.generateViewId()
        infoTextView.id = View.generateViewId()
        ingredientsTextView.id = View.generateViewId()
        descriptionTitleLabel.id = View.generateViewId()
        descriptionLabel.id = View.generateViewId()
        scoreView.id = View.generateViewId()
        durationTextView.id = View.generateViewId()
        durationImageView.id = View.generateViewId()
        layoutBarView()
        layoutSwipeRefreshLayout()
        layoutScrollView()
        layoutScrollViewConstraintLayout()
        layoutPictureImageView()
        layoutScoreDurationConstraintLayout()
        layoutScoreStarsView()
        layoutDurationTextView()
        layoutDurationImageView()
        layoutNameTextView()
        layoutInfoTextView()
        layoutIngredientsConstraintLayout()
        layoutIngredientsTextView()
        layoutDescriptionTitleLabel()
        layoutDescriptionLabel()
        layoutScoreView()

        setOnApplyWindowInsetsListener { v, insets ->
            systemWindowInsetTop = insets.systemWindowInsetTop // status bar height
            layoutBarView(insets.systemWindowInsetTop)
            insets
        }

        fitsSystemWindows = false
        systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        requestApplyInsetsWhenAttached()
    }

    private fun layoutBarView() {
        addView(barView)
        val constraintSet = ConstraintSet()
        constraintSet.connect(
            barView.id,
            ConstraintSet.TOP,
            ConstraintSet.PARENT_ID,
            ConstraintSet.TOP
        )
        constraintSet.connect(
            barView.id,
            ConstraintSet.LEFT,
            ConstraintSet.PARENT_ID,
            ConstraintSet.LEFT
        )
        constraintSet.connect(
            barView.id,
            ConstraintSet.RIGHT,
            ConstraintSet.PARENT_ID,
            ConstraintSet.RIGHT
        )
        constraintSet.constrainHeight(barView.id, WRAP_CONTENT)
        constraintSet.applyTo(this)
    }

    private fun layoutBarView(systemWindowInsetTop: Int) {
        val constraintSet = ConstraintSet()
        constraintSet.clear(barView.id)
        constraintSet.connect(
            barView.id,
            ConstraintSet.TOP,
            ConstraintSet.PARENT_ID,
            ConstraintSet.TOP,
            systemWindowInsetTop
        )
        constraintSet.connect(
            barView.id,
            ConstraintSet.LEFT,
            ConstraintSet.PARENT_ID,
            ConstraintSet.LEFT
        )
        constraintSet.connect(
            barView.id,
            ConstraintSet.RIGHT,
            ConstraintSet.PARENT_ID,
            ConstraintSet.RIGHT
        )
        constraintSet.constrainHeight(barView.id, WRAP_CONTENT)
        constraintSet.applyTo(this)
    }

    private fun layoutSwipeRefreshLayout() {
        addView(swipeRefreshLayout)
        //swipeRefreshLayout.setProgressViewOffset(false, 300, 600)
        val constraintSet = ConstraintSet()
        constraintSet.connect(
            swipeRefreshLayout.id,
            ConstraintSet.TOP,
            ConstraintSet.PARENT_ID,
            ConstraintSet.TOP
        )
        constraintSet.connect(
            swipeRefreshLayout.id,
            ConstraintSet.LEFT,
            ConstraintSet.PARENT_ID,
            ConstraintSet.LEFT
        )
        constraintSet.connect(
            swipeRefreshLayout.id,
            ConstraintSet.RIGHT,
            ConstraintSet.PARENT_ID,
            ConstraintSet.RIGHT
        )
        constraintSet.connect(
            swipeRefreshLayout.id,
            ConstraintSet.BOTTOM,
            ConstraintSet.PARENT_ID,
            ConstraintSet.BOTTOM
        )
        constraintSet.applyTo(this)
    }

    private fun layoutScrollView() {
        swipeRefreshLayout.addView(scrollView)
    }

    private fun layoutScrollViewConstraintLayout() {
        scrollView.addView(
            scrollViewConstraintLayout, LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
            )
        )
    }

    private fun layoutPictureImageView() {
        scrollViewConstraintLayout.addView(pictureImageView)
        val constraintSet = ConstraintSet()
        constraintSet.connect(
            pictureImageView.id,
            ConstraintSet.TOP,
            ConstraintSet.PARENT_ID,
            ConstraintSet.TOP
        )
        constraintSet.connect(
            pictureImageView.id,
            ConstraintSet.LEFT,
            ConstraintSet.PARENT_ID,
            ConstraintSet.LEFT
        )
        constraintSet.connect(
            pictureImageView.id,
            ConstraintSet.RIGHT,
            ConstraintSet.PARENT_ID,
            ConstraintSet.RIGHT
        )
        constraintSet.constrainHeight(pictureImageView.id, WRAP_CONTENT)
        constraintSet.applyTo(scrollViewConstraintLayout)
    }

    private fun layoutScoreDurationConstraintLayout() {
        scrollViewConstraintLayout.addView(scoreDurationConstraintLayout)
        val constraintSet = ConstraintSet()
        constraintSet.connect(
            scoreDurationConstraintLayout.id,
            ConstraintSet.BOTTOM,
            pictureImageView.id,
            ConstraintSet.BOTTOM
        )
        constraintSet.connect(
            scoreDurationConstraintLayout.id,
            ConstraintSet.LEFT,
            ConstraintSet.PARENT_ID,
            ConstraintSet.LEFT
        )
        constraintSet.connect(
            scoreDurationConstraintLayout.id,
            ConstraintSet.RIGHT,
            ConstraintSet.PARENT_ID,
            ConstraintSet.RIGHT
        )
        constraintSet.constrainHeight(scoreDurationConstraintLayout.id, dp(48))
        constraintSet.applyTo(scrollViewConstraintLayout)
    }

    private fun layoutNameTextView() {
        scrollViewConstraintLayout.addView(nameTextView)
        val constraintSet = ConstraintSet()
        constraintSet.connect(
            nameTextView.id,
            ConstraintSet.BOTTOM,
            scoreDurationConstraintLayout.id,
            ConstraintSet.TOP,
            dp(
                32
            )
        )
        constraintSet.connect(
            nameTextView.id, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, dp(
                24
            )
        )
        constraintSet.connect(
            nameTextView.id, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, dp(
                24
            )
        )
        constraintSet.constrainHeight(nameTextView.id, WRAP_CONTENT)
        constraintSet.applyTo(scrollViewConstraintLayout)
    }

    private fun layoutScoreStarsView() {
        scoreStarsView.starSizeDp = dp(22)
        scoreDurationConstraintLayout.addView(scoreStarsView)
        val constraintSet = ConstraintSet()
        constraintSet.connect(
            scoreStarsView.id,
            ConstraintSet.BOTTOM,
            ConstraintSet.PARENT_ID,
            ConstraintSet.BOTTOM
        )
        constraintSet.connect(
            scoreStarsView.id, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, dp(
                24
            )
        )
        constraintSet.connect(
            scoreStarsView.id,
            ConstraintSet.TOP,
            ConstraintSet.PARENT_ID,
            ConstraintSet.TOP
        )
        constraintSet.constrainHeight(scoreStarsView.id, WRAP_CONTENT)
        constraintSet.applyTo(scoreDurationConstraintLayout)
    }

    private fun layoutDurationTextView() {
        scoreDurationConstraintLayout.addView(durationTextView)
        val constraintSet = ConstraintSet()
        constraintSet.connect(
            durationTextView.id,
            ConstraintSet.BOTTOM,
            ConstraintSet.PARENT_ID,
            ConstraintSet.BOTTOM
        )
        constraintSet.connect(
            durationTextView.id,
            ConstraintSet.TOP,
            ConstraintSet.PARENT_ID,
            ConstraintSet.TOP
        )
        constraintSet.connect(
            durationTextView.id,
            ConstraintSet.RIGHT,
            ConstraintSet.PARENT_ID,
            ConstraintSet.RIGHT,
            dp(
                24
            )
        )
        constraintSet.constrainHeight(durationTextView.id, WRAP_CONTENT)
        constraintSet.applyTo(scoreDurationConstraintLayout)
    }

    private fun layoutDurationImageView() {
        scoreDurationConstraintLayout.addView(durationImageView)
        val constraintSet = ConstraintSet()
        constraintSet.connect(
            durationImageView.id,
            ConstraintSet.BOTTOM,
            ConstraintSet.PARENT_ID,
            ConstraintSet.BOTTOM
        )
        constraintSet.connect(
            durationImageView.id,
            ConstraintSet.TOP,
            ConstraintSet.PARENT_ID,
            ConstraintSet.TOP
        )
        constraintSet.connect(
            durationImageView.id, ConstraintSet.RIGHT, durationTextView.id, ConstraintSet.LEFT, dp(
                24
            )
        )
        constraintSet.constrainHeight(durationImageView.id, WRAP_CONTENT)
        constraintSet.applyTo(scoreDurationConstraintLayout)
    }

    private fun layoutInfoTextView() {
        scrollViewConstraintLayout.addView(infoTextView)
        val constraintSet = ConstraintSet()
        constraintSet.connect(
            infoTextView.id, ConstraintSet.TOP, pictureImageView.id, ConstraintSet.BOTTOM, dp(
                32
            )
        )
        constraintSet.connect(
            infoTextView.id, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, dp(
                24
            )
        )
        constraintSet.connect(
            infoTextView.id, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, dp(
                24
            )
        )
        constraintSet.constrainHeight(infoTextView.id, WRAP_CONTENT)
        constraintSet.applyTo(scrollViewConstraintLayout)
    }

    private fun layoutIngredientsConstraintLayout() {
        scrollViewConstraintLayout.addView(ingredientsConstraintLayout)
        val constraintSet = ConstraintSet()
        constraintSet.connect(ingredientsConstraintLayout.id, ConstraintSet.TOP, infoTextView.id, ConstraintSet.BOTTOM, dp(32))
        constraintSet.connect(ingredientsConstraintLayout.id, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, dp(24))
        constraintSet.connect(ingredientsConstraintLayout.id, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, dp(24))
        constraintSet.constrainHeight(ingredientsConstraintLayout.id, WRAP_CONTENT)
        constraintSet.applyTo(scrollViewConstraintLayout)
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

    private fun layoutDescriptionTitleLabel() {
        scrollViewConstraintLayout.addView(descriptionTitleLabel)
        val constraintSet = ConstraintSet()
        constraintSet.connect(
            descriptionTitleLabel.id,
            ConstraintSet.TOP,
            ingredientsConstraintLayout.id,
            ConstraintSet.BOTTOM,
            dp(
                32
            )
        )
        constraintSet.connect(
            descriptionTitleLabel.id,
            ConstraintSet.LEFT,
            ConstraintSet.PARENT_ID,
            ConstraintSet.LEFT,
            dp(
                24
            )
        )
        constraintSet.connect(
            descriptionTitleLabel.id,
            ConstraintSet.RIGHT,
            ConstraintSet.PARENT_ID,
            ConstraintSet.RIGHT,
            dp(
                24
            )
        )
        constraintSet.constrainHeight(descriptionTitleLabel.id, WRAP_CONTENT)
        constraintSet.applyTo(scrollViewConstraintLayout)
    }

    private fun layoutDescriptionLabel() {
        scrollViewConstraintLayout.addView(descriptionLabel)
        val constraintSet = ConstraintSet()
        constraintSet.connect(
            descriptionLabel.id,
            ConstraintSet.TOP,
            descriptionTitleLabel.id,
            ConstraintSet.BOTTOM,
            dp(
                32
            )
        )
        constraintSet.connect(
            descriptionLabel.id,
            ConstraintSet.LEFT,
            ConstraintSet.PARENT_ID,
            ConstraintSet.LEFT,
            dp(
                24
            )
        )
        constraintSet.connect(
            descriptionLabel.id,
            ConstraintSet.RIGHT,
            ConstraintSet.PARENT_ID,
            ConstraintSet.RIGHT,
            dp(
                24
            )
        )
        constraintSet.constrainHeight(descriptionLabel.id, WRAP_CONTENT)
        constraintSet.applyTo(scrollViewConstraintLayout)
    }

    private fun layoutScoreView() {
        scrollViewConstraintLayout.addView(scoreView)
        val constraintSet = ConstraintSet()
        constraintSet.connect(
            scoreView.id, ConstraintSet.TOP, descriptionLabel.id, ConstraintSet.BOTTOM, dp(
                32
            )
        )
        constraintSet.connect(
            scoreView.id,
            ConstraintSet.LEFT,
            ConstraintSet.PARENT_ID,
            ConstraintSet.LEFT
        )
        constraintSet.connect(
            scoreView.id,
            ConstraintSet.RIGHT,
            ConstraintSet.PARENT_ID,
            ConstraintSet.RIGHT
        )
        constraintSet.connect(
            scoreView.id,
            ConstraintSet.BOTTOM,
            ConstraintSet.PARENT_ID,
            ConstraintSet.BOTTOM
        )
        constraintSet.constrainHeight(scoreView.id, WRAP_CONTENT)
        constraintSet.applyTo(scrollViewConstraintLayout)
    }

    //endregion

    fun setIngredients(ingredients: List<String>) {
        ingredientsTextViews.forEach({ ingredientsConstraintLayout.removeView(it) })
        ingredientsTextViews.clear()
        val constraintSet2 = ConstraintSet()
        constraintSet2.clear(ingredientsTextView.id, ConstraintSet.BOTTOM)
        constraintSet2.applyTo(ingredientsConstraintLayout)
        var lastView = ingredientsTextView
        for (ingredient in ingredients) {
            val text = "  •     $ingredient"
            val textView = TextView(context)
            textView.setTextColor(Color.BLACK)
            textView.textSize = 17.0f
            textView.text = text
            textView.id = View.generateViewId()
            ingredientsTextViews.add(textView)
            ingredientsConstraintLayout.addView(textView)
            val constraintSet = ConstraintSet()
            constraintSet.connect(textView.id, ConstraintSet.TOP, lastView.id, ConstraintSet.BOTTOM)
            constraintSet.connect(textView.id, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT)
            constraintSet.connect(textView.id, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT)
            if (ingredient == ingredients.last()) {
                constraintSet.connect(textView.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
            }
            constraintSet.constrainHeight(textView.id, WRAP_CONTENT)
            constraintSet.applyTo(ingredientsConstraintLayout)
            lastView = textView
        }
    }

}

fun View.doOnApplyWindowInsets(f: (View, WindowInsets, InitialPadding) -> Unit) {
    // Create a snapshot of the view's padding state
    val initialPadding = recordInitialPaddingForView(this)
    // Set an actual OnApplyWindowInsetsListener which proxies to the given
    // lambda, also passing in the original padding state
    setOnApplyWindowInsetsListener { v, insets ->
        f(v, insets, initialPadding)
        // Always return the insets, so that children can also use them
        insets
    }
    // request some insets
    requestApplyInsetsWhenAttached()
}

fun View.requestApplyInsetsWhenAttached() {
    if (isAttachedToWindow) {
        // We're already attached, just request as normal
        requestApplyInsets()
    } else {
        // We're not attached to the hierarchy, add a listener to
        // request when we are
        addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View) {
                v.removeOnAttachStateChangeListener(this)
                v.requestApplyInsets()
            }

            override fun onViewDetachedFromWindow(v: View) = Unit
        })
    }
}

data class InitialPadding(
    val left: Int, val top: Int,
    val right: Int, val bottom: Int
)

private fun recordInitialPaddingForView(view: View) = InitialPadding(
    view.paddingLeft,
    view.paddingTop,
    view.paddingRight,
    view.paddingBottom
)

private class PictureImageView(context: Context): androidx.appcompat.widget.AppCompatImageView(
    context
) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = measuredWidth
        setMeasuredDimension(width, width)
    }

}

class ScoreView(context: Context): FrameLayout(context) {

    val verticalLinearLayout = LinearLayout(context)
    val textView = TextView(context)
    var scoreStarsView = InteractiveScoreStarsView(context)
    val progressBar = ProgressBar(context)

    init {
        addView(
            verticalLinearLayout, LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
            )
        )

        verticalLinearLayout.orientation = LinearLayout.VERTICAL
        setBackgroundColor(Color.BLUE)
        val textViewLayoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT
        )
        textViewLayoutParams.setMargins(dp(0), dp(24), dp(0), dp(0))
        verticalLinearLayout.addView(textView, textViewLayoutParams)
        textView.setTextColor(Color.WHITE)
        textView.textSize = 17.0f
        textView.gravity = Gravity.CENTER

        val scoreStarsViewLayoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT
        )
        scoreStarsViewLayoutParams.setMargins(dp(0), dp(8), dp(0), dp(24))
        verticalLinearLayout.addView(scoreStarsView, scoreStarsViewLayoutParams)

        addView(progressBar, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT))
        progressBar.visibility = View.INVISIBLE
        progressBar.isClickable = true
    }

}

class InteractiveScoreStarsView(context: Context): LinearLayout(context) {

    val starButtons = arrayListOf<Button>()

    private fun starButton(): Button {
        val button = Button(context)
        button.setBackgroundResource(R.drawable.ic_star_white)
        return button
    }

    fun setSelectedScore(score: Float) {
        val count = score.roundToInt()
        for (i in 0 until starButtons.count()) {
            val button = starButtons.get(i)
            if (i < count) {
                button.alpha = 1.0f
            } else {
                button.alpha = 0.5f
            }
        }
    }

    init {
        orientation = HORIZONTAL
        gravity = CENTER
        for (x in 0..4) {
            val starButton = starButton()
            val starButtonLayoutParams = LayoutParams(dp(48), dp(48))
            starButtons.add(starButton)
            addView(starButton, starButtonLayoutParams)
        }
    }

}