package me.ihormyroniuk.AckeeCookbookAndroidTask.WebApi

import org.json.JSONObject

open class ApiVersion1Endpoint(protocol: String, host: String) {

    open val protocol: String = protocol
    open val host: String = host

    open fun error(jsonObject: JSONObject): ApiVersion1Error {
        val message = jsonObject.getString("message")
        val errorJsonObject = jsonObject.getJSONObject("err")
        val code = errorJsonObject.getInt("errorCode")
        val status = errorJsonObject.getInt("status")
        val name = errorJsonObject.getString("name")
        val error = ApiVersion1Error(code, status, name, message)
        return error
    }
}

data class ApiVersion1Error(val code: Int, val status: Int, val name: String, val message: String) {

}