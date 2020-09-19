package me.ihormyroniuk.AckeeCookbookAndroidTask.Http

import android.util.TypedValue
import android.view.View

fun View.dp(px: Int): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px.toFloat(), context.resources.displayMetrics).toInt()
}

fun View.dp(px: Float): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, context.resources.displayMetrics).toInt()
}