package me.ihormyroniuk.AckeeCookbookAndroidTask.Presentation.Colors

import android.graphics.Color

enum class Colors {;

    companion object {
        fun blue(): Int {
            return Color.argb(1, 0, (0.1176 * 255).toInt(), (0.9607 * 255).toInt())
        }

        val pink: Int
            get() {
                return Color.rgb(234, 60, 247)
            }
    }
}