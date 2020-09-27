package me.ihormyroniuk.AckeeCookbookAndroidTask.Business

data class RecipeDetails(val id: String, val name: String, val description: String, val info: String, val ingredients: ArrayList<String>, val duration: Int, val score: Float) {

    val recipeInList: RecipeInList
        get() {
            val recipeInList = RecipeInList(id, name, duration, score)
            return recipeInList
        }

}