package me.ihormyroniuk.AckeeCookbookAndroidTask.Presentation

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout


class RecipesListScreenBarView(context: Context): LinearLayout(context) {

    val horizontalLinearLayout: LinearLayout = LinearLayout(context)
    val titleTextView: TextView = TextView(context)
    var addRecipeImageButton: ImageButton = ImageButton(context)

    init {
        orientation = LinearLayout.VERTICAL
        horizontalLinearLayout.orientation = LinearLayout.HORIZONTAL
        horizontalLinearLayout.gravity = Gravity.CENTER_VERTICAL
        addView(horizontalLinearLayout, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT))
        horizontalLinearLayout.setPadding(dp(24), 0, dp(24), 0)
        horizontalLinearLayout.addView(titleTextView, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT))
        setupTitleTextView()
        horizontalLinearLayout.addView(addRecipeImageButton, LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT))

        elevation = dp(8).toFloat()
        val backgroundDrawable = GradientDrawable()
        backgroundDrawable.setColor(Color.WHITE)
        backgroundDrawable.shape = GradientDrawable.RECTANGLE
        background = backgroundDrawable
    }

    private fun setupTitleTextView() {
        titleTextView.textSize = 26f
    }

}

class RecipesListScreenView(context: Context): LinearLayout(context) {

    val barView: RecipesListScreenBarView = RecipesListScreenBarView(context)
    val recyclerView: RecyclerView = RecyclerView(context)
    val swipeRefreshLayout: SwipeRefreshLayout = SwipeRefreshLayout(context)

    init {
        orientation = LinearLayout.VERTICAL
        addView(barView, LayoutParams(LayoutParams.MATCH_PARENT, dp(56)))
        addView(swipeRefreshLayout, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT))
        swipeRefreshLayout.addView(recyclerView, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT))
    }

}

class RecipeListItemView(context: Context): LinearLayout(context) {

    var pictureImageView: ImageView
    var nameTextView: TextView

    init {
        orientation = LinearLayout.HORIZONTAL
        pictureImageView = ImageView(context)
        addView(pictureImageView, LayoutParams(dp(76), dp(76)))
        nameTextView = TextView(context)
        addView(nameTextView, LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT))
    }

}

class RecipeListItemWithDivider(context: Context): LinearLayout(context) {

    var recipeView: RecipeListItemView
    var separatorView: View

    init {
        orientation = LinearLayout.VERTICAL
        recipeView = RecipeListItemView(context)
        recipeView.setPadding(dp(24), dp(16), dp(24), dp(16))
        addView(recipeView, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT))
        separatorView = View(context)
        separatorView.setBackgroundColor(Color.LTGRAY)
        addView(separatorView, LayoutParams(LayoutParams.MATCH_PARENT, dp(1)))
    }

}

fun View.dp(px: Int): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px.toFloat(), context.resources.displayMetrics).toInt()
}

fun View.dp(px: Float): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, context.resources.displayMetrics).toInt()
}