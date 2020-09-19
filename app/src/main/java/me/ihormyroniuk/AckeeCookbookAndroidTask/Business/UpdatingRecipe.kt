package me.ihormyroniuk.AckeeCookbookAndroidTask.Business

data class UpdatingRecipe(val id: String, val name: String, val description: String, val info: String, val ingredients: ArrayList<String>, val duration: Int, val score: Float) {

}