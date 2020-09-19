package me.ihormyroniuk.AckeeCookbookAndroidTask.Http

sealed class Result<out Success, out Failure>

data class Success<out Success>(val success: Success) : Result<Success, Nothing>()
data class Failure<out Failure>(val failure: Failure) : Result<Nothing, Failure>()