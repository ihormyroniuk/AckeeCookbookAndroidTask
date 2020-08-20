package me.ihormyroniuk.AckeeCookbookAndroidTask.Presentation

import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class RecipesListScreenView(context: Context): LinearLayout(context) {

    val recyclerView: RecyclerView = RecyclerView(context)
    val swipeRefreshLayout: SwipeRefreshLayout = SwipeRefreshLayout(context)

    init {
        orientation = LinearLayout.VERTICAL
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
        //pictureImageView.setImageResource(R.drawable.img_big)
        addView(pictureImageView, LayoutParams(76.dp(context), 76.dp(context)))
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
        recipeView.setPadding(24.dp(context), 16.dp(context), 24.dp(context), 16.dp(context))
        addView(recipeView, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT))
        separatorView = View(context)
        separatorView.setBackgroundColor(Color.LTGRAY)
        addView(separatorView, LayoutParams(LayoutParams.MATCH_PARENT, 1.dp(context)))
    }

}

fun Int.dp(context: Context): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), context.resources.displayMetrics).toInt()
}