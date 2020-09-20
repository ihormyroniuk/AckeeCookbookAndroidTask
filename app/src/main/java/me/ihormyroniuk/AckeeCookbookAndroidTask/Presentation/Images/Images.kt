package me.ihormyroniuk.AckeeCookbookAndroidTask.Presentation.Images

import android.media.Image

enum class Images {;

    companion object {
        fun random(): Int {
            return me.ihormyroniuk.AckeeCookbookAndroidTask.R.drawable.ic_recipe_small
        }

        fun picture(): Int {
            return me.ihormyroniuk.AckeeCookbookAndroidTask.R.drawable.ic_recipe_large
        }

        fun star(): Int {
            return me.ihormyroniuk.AckeeCookbookAndroidTask.R.drawable.ic_star
        }

        fun time(): Int {
            return me.ihormyroniuk.AckeeCookbookAndroidTask.R.drawable.ic_time
        }
    }
}