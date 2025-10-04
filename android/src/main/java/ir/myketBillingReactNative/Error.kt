package ir.myketBillingReactNative

import org.json.JSONObject

sealed class Error(val message: String) {

  class InvalidPublicKeyException : Error(
    message = "Public key is null or empty! check the parameter; visit https://myket.ir/kb/pages/get-pki-public-key/ for more information",
  )

  class InitializationException(code: String, message: String) : Error(
    message = message + "ErrorCode is :" + code
  )

  class NotInstalledException : Error(
    message = "Myket application NotInstalled!"
  )

  class RaceConditionException : Error(
    message = "Another async operation in progress!"
  )

  class FetchError(message: String, private val responseCode: Int) : Error(message) {
    override fun toString(): String {
      return JSONObject().apply {
        put("message", message)
        put("responseCode", responseCode)
      }.toString()

    }
  }

  class InvalidSkuException : Error(
    message = "parameter 'sku' is null or empty! check the parameter..."
  )

  override fun toString(): String {
    return JSONObject().apply { put("message", message) }.toString()
  }

}
