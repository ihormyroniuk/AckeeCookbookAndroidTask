package me.ihormyroniuk.AckeeCookbookAndroidTask.WebApi

open class Api {

    class Http {

        class Scheme {
            companion object {
                val https = "https"
            }
        }

        class Host {
            companion object {
                val mockServer = "private-anon-819a83e00e-cookbook3.apiary-mock.com"
                val debuggingProxy = "private-anon-819a83e00e-cookbook3.apiary-proxy.com"
                val production = "cookbook.ack.ee"
            }
        }

        class Method {
            companion object {
                val get = "GET"
                val post = "POST"
                val put = "PUT"
                val delete = "DELETE"
            }
        }

        class Header {
            companion object {
                val contentType = "Content-Type"
                val contentTypeJson = "application/json"
            }
        }

    }

}