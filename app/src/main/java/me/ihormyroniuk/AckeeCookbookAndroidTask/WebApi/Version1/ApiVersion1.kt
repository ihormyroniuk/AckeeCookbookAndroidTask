package me.ihormyroniuk.AckeeCookbookAndroidTask.WebApi.Version1

import me.ihormyroniuk.AckeeCookbookAndroidTask.WebApi.Api
import me.ihormyroniuk.AckeeCookbookAndroidTask.WebApi.Version1.GetRecipes.ApiVersion1EndpointGetRecipes
import me.ihormyroniuk.AckeeCookbookAndroidTask.WebApi.Version1.GetRecipes.Portion

class ApiVersion1(val protocol: String, val host: String): Api() {

    companion object {
        fun mockServer(): ApiVersion1 {
            return ApiVersion1(Api.Http.Scheme.https, Http.Host.mockServer)
        }

        fun debuggingProxy(): ApiVersion1 {
            return ApiVersion1(Api.Http.Scheme.https, Http.Host.debuggingProxy)
        }

        fun production(): ApiVersion1 {
            return ApiVersion1(Api.Http.Scheme.https, Http.Host.production)
        }
    }

    fun getRecipes(portion: Portion): ApiVersion1EndpointGetRecipes {
        val endpoint = ApiVersion1EndpointGetRecipes(protocol, host, portion)
        return endpoint
    }

    fun getRecipe(): ApiVersion1EndpointGetRecipe {
        val endpoint = ApiVersion1EndpointGetRecipe(protocol, host)
        return endpoint
    }

    fun addNewRatingEndpoint(): ApiVersion1EndpointAddNewRating {
        val endpoint = ApiVersion1EndpointAddNewRating(protocol, host)
        return endpoint
    }

    fun deleteRecipe(): ApiVersion1EndpointDeleteRecipe {
        val endpoint = ApiVersion1EndpointDeleteRecipe(protocol, host)
        return endpoint
    }

    fun updateRecipe(): ApiVersion1EndpointUpdateRecipe {
        val endpoint = ApiVersion1EndpointUpdateRecipe(protocol, host)
        return endpoint
    }

    fun createNewRecipe(): ApiVersion1EndpointCreateNewRecipe {
        val endpoint = ApiVersion1EndpointCreateNewRecipe(protocol, host)
        return endpoint
    }

}