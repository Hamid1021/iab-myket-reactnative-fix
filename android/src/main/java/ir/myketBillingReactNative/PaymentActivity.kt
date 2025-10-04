package ir.myketBillingReactNative

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.facebook.react.ReactActivity
import com.facebook.react.bridge.Callback
import com.facebook.react.bridge.Promise

class PaymentActivity : ReactActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    launchProcessFlowCallBack(this, sku, developerPayload, {
      promise?.reject(it)
      failureCallBack?.invoke(it)
      finish()
    }, {
      promise?.resolve(it)
      successCallBack?.invoke(it)
      finish()
    })

  }

  companion object {
    private var sku: String? = null
    private var promise: Promise? = null
    private var failureCallBack: Callback? = null
    private var successCallBack: Callback? = null
    private var developerPayload: String? = null

    @JvmStatic
    fun start(activity: Activity, sku: String?, developerPayload: String?, promise: Promise) {
      try {
        Companion.sku = sku
        Companion.developerPayload = developerPayload
        Companion.promise = promise
        val intent = Intent(activity, PaymentActivity::class.java)
        activity.startActivity(intent)
      } catch (e: Exception) {
        promise.reject(e.message.toString())
      }
    }

    @JvmStatic
    fun start(activity: Activity, sku: String?, developerPayload: String?, failureCallBack: Callback, successCallBack: Callback) {
      try {
        Companion.sku = sku
        promise = null
        Companion.developerPayload = developerPayload
        Companion.failureCallBack = failureCallBack
        Companion.successCallBack = successCallBack
        val intent = Intent(activity, PaymentActivity::class.java)
        activity.startActivity(intent)
      } catch (e: Exception) {
        failureCallBack.invoke(e.message.toString())
      }
    }
  }
}
