package me.ihormyroniuk.AckeeCookbookAndroidTask.Presentation.Screens.RecipesList

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.widget.TextViewCompat
import me.ihormyroniuk.AckeeCookbookAndroidTask.Http.dp
import me.ihormyroniuk.AckeeCookbookAndroidTask.Presentation.Colors.Colors
import me.ihormyroniuk.AckeeCookbookAndroidTask.Presentation.Components.ScoreStarsView
import me.ihormyroniuk.AckeeCookbookAndroidTask.Presentation.Images.Images

class RecipesListScreenRecipeView(context: Context): LinearLayout(context) {

    //region Views
    //

    val contentLinearLayout = LinearLayout(context)
    val pictureImageView = ImageView(context)
    val rightContentLinearLayout = LinearLayout(context)
    val nameTextView = AppCompatTextView(context)
    val scoreDurationLinearLayout = LinearLayout(context)
    val scoreStarsView = ScoreStarsView(context)
    val durationLinearLayout = LinearLayout(context)
    val durationImageView = ImageView(context)
    val durationTextView = TextView(context)
    val separatorView = View(context)

    //endregion

    //region Initializer
    //

    init {
        setup()
        layout()
    }

    //endregion

    //region Setup
    //

    fun setup() {
        setupNameTextView()
        scoreStarsView.starsColor = Colors.pink
        setupDurationImageView()
        setupDurationTextView()
        setupSeparatorView()
    }

    fun setupNameTextView() {
        nameTextView.textSize = 14.0f
        nameTextView.ellipsize = TextUtils.TruncateAt.END
        TextViewCompat.setAutoSizeTextTypeWithDefaults(nameTextView, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);
        nameTextView.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD)
        nameTextView.setTextColor(Color.BLUE)
    }

    fun setupDurationImageView() {
        durationImageView.setImageResource(Images.time())
    }

    fun setupDurationTextView() {
        durationTextView.textSize = 12.0f
    }

    fun setupSeparatorView() {
        separatorView.setBackgroundColor(Color.LTGRAY)
    }

    //endregion

    //region Layout
    //

    fun layout() {

        orientation = VERTICAL
        layoutContentLinearLayout()
        layoutPictureImageView()
        layoutRightContentLinearLayout()
        layoutNameTextView()
        layoutScoreDurationLinearLayout()
        layoutScoreStarsView()
        layoutDurationLinearLayout()
        layoutDurationImageView()
        layoutDurationTextView()
        layoutSeparatorView()
    }

    fun layoutContentLinearLayout() {
        val layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, dp(76))
        layoutParams.setMargins(dp(24), dp(16), dp(24), dp(16))
        addView(contentLinearLayout, layoutParams)
        contentLinearLayout.orientation = HORIZONTAL
    }

    fun layoutPictureImageView() {
        val layoutParams = LayoutParams(dp(76), dp(76))
        contentLinearLayout.addView(pictureImageView, layoutParams)
        pictureImageView.setImageResource(Images.random())
        pictureImageView.setPadding(0, 0, dp(4), 0)
    }

    fun layoutRightContentLinearLayout() {
        val layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, dp(76))
        layoutParams.setMargins(dp(4), dp(0), dp(0), dp(0))
        contentLinearLayout.addView(rightContentLinearLayout, layoutParams)
        rightContentLinearLayout.orientation = VERTICAL
    }

    fun layoutNameTextView() {
        val layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        rightContentLinearLayout.addView(nameTextView, layoutParams)
    }

    fun layoutScoreDurationLinearLayout() {
        val layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
        rightContentLinearLayout.addView(scoreDurationLinearLayout, layoutParams)
        scoreDurationLinearLayout.orientation = VERTICAL
        scoreDurationLinearLayout.gravity = Gravity.BOTTOM
    }

    fun layoutScoreStarsView() {
        val layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, dp(12))
        layoutParams.setMargins(0, 0, 0, dp(4))
        scoreDurationLinearLayout.addView(scoreStarsView, layoutParams)
    }

    fun layoutDurationLinearLayout() {
        val layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        scoreDurationLinearLayout.addView(durationLinearLayout, layoutParams)
        durationLinearLayout.orientation = HORIZONTAL
        durationLinearLayout.gravity = Gravity.CENTER_VERTICAL
    }

    fun layoutDurationImageView() {
        val layoutParams = LayoutParams(dp(12), dp(12))
        durationLinearLayout.addView(durationImageView, layoutParams)
    }

    fun layoutDurationTextView() {
        val layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        layoutParams.setMargins(dp(4), 0, 0, 0)
        durationLinearLayout.addView(durationTextView, layoutParams)
    }

    fun layoutSeparatorView() {
        val layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, dp(1))
        addView(separatorView, layoutParams)
    }

    //endregion
}