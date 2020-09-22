package me.ihormyroniuk.AckeeCookbookAndroidTask.Presentation.Screens.RecipesList

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.telephony.UiccCardInfo
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.ConstraintSet.WRAP_CONTENT
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import me.ihormyroniuk.AckeeCookbookAndroidTask.Http.dp
import me.ihormyroniuk.AckeeCookbookAndroidTask.R


class RecipesListScreenBarView(context: Context): ConstraintLayout(context) {

    val titleTextView: TextView = TextView(context)
    var addRecipeButton: ImageButton = ImageButton(context)

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
        setupTitleTextView()
        setupAddRecipeButton()
    }

    fun setupTitleTextView() {
        titleTextView.id = View.generateViewId()
        titleTextView.setTextColor(Color.BLACK)
        titleTextView.textSize = 20f
        titleTextView.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD)
        titleTextView.gravity = View.TEXT_ALIGNMENT_CENTER
    }

    fun setupAddRecipeButton() {
        addRecipeButton.setBackgroundColor(Color.WHITE)
        addRecipeButton.setImageResource(R.drawable.ic_add_blue)
    }

    fun layout() {
        layoutTitleTextView()
        layoutAddRecipeButton()
    }

    fun layoutTitleTextView() {
        addView(titleTextView)
        val constraintSet = ConstraintSet()
        constraintSet.connect(titleTextView.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
        constraintSet.connect(titleTextView.id, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, dp(24))
        constraintSet.connect(titleTextView.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
        constraintSet.constrainWidth(titleTextView.id, WRAP_CONTENT)
        constraintSet.constrainHeight(titleTextView.id, WRAP_CONTENT)
        constraintSet.applyTo(this)
    }

    fun layoutAddRecipeButton() {
        addRecipeButton.id = View.generateViewId()
        addView(addRecipeButton)
        val constraintSet = ConstraintSet()
        constraintSet.connect(addRecipeButton.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
        constraintSet.connect(addRecipeButton.id, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, dp(24))
        constraintSet.connect(addRecipeButton.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
        constraintSet.constrainWidth(addRecipeButton.id, dp(24))
        constraintSet.constrainHeight(addRecipeButton.id, dp(24))
        constraintSet.applyTo(this)
    }

}

class RecipesListScreenView(context: Context): ConstraintLayout(context) {

    val barView: RecipesListScreenBarView = RecipesListScreenBarView(context)
    val swipeRefreshLayout: SwipeRefreshLayout = SwipeRefreshLayout(context)
    val recyclerView: RecyclerView = RecyclerView(context)

    //region Layout
    //

    init {
        layout()
    }

    fun layout() {
        barView.id = View.generateViewId()
        swipeRefreshLayout.id = View.generateViewId()
        recyclerView.id = View.generateViewId()
        layoutBarView()
        layoutSwipeRefreshLayout()
        layoutRecyclerView()
    }

    fun layoutBarView() {
        addView(barView)
        val constraintSet = ConstraintSet()
        constraintSet.connect(barView.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
        constraintSet.constrainHeight(barView.id, dp(56))
        constraintSet.connect(barView.id, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT)
        constraintSet.connect(barView.id, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT)
        constraintSet.applyTo(this)
    }

    fun layoutSwipeRefreshLayout() {
        addView(swipeRefreshLayout)
        val constraintSet = ConstraintSet()
        constraintSet.connect(swipeRefreshLayout.id, ConstraintSet.TOP, barView.id, ConstraintSet.BOTTOM)
        constraintSet.connect(swipeRefreshLayout.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
        constraintSet.connect(swipeRefreshLayout.id, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT)
        constraintSet.connect(swipeRefreshLayout.id, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT)
        constraintSet.applyTo(this)
    }

    fun layoutRecyclerView() {
        swipeRefreshLayout.addView(recyclerView, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT))
    }

    //endregion

}