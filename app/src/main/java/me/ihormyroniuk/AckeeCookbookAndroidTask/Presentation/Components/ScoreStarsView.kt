package me.ihormyroniuk.AckeeCookbookAndroidTask.Presentation.Components

import android.content.Context
import android.widget.ImageView
import android.widget.LinearLayout
import me.ihormyroniuk.AckeeCookbookAndroidTask.Http.dp
import me.ihormyroniuk.AckeeCookbookAndroidTask.Presentation.Images.Images
import kotlin.math.roundToInt

class ScoreStarsView(context: Context): LinearLayout(context) {

    val starsImageView = ArrayList<ImageView>(5)
    var starSizeDp = dp(12)

    init {
        orientation = HORIZONTAL
    }

    fun setScore(score: Float) {
        for(starImageView in starsImageView) {
            removeView(starImageView)
        }
        starsImageView.clear()
        val count = score.roundToInt()
        for(number in 0 until count) {
            val starImageView = ImageView(context)
            starImageView.setImageResource(Images.star())
            addView(starImageView, LayoutParams(starSizeDp, LayoutParams.MATCH_PARENT))
            starsImageView.add(starImageView)
        }
    }

}