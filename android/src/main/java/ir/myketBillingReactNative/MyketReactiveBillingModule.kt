package ir.myketBillingReactNative

import android.app.Activity
import com.facebook.react.bridge.*

class MyketReactiveBillingModule(private val reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

  override fun getName(): String = "MyketReactiveBillingModule"

  @ReactMethod
  fun connectPayment(rsaPublicKey: String?, failureCallback: Callback, successCallback: Callback) {
    initializeCallBack(reactContext, rsaPublicKey, {
      failureCallback.invoke(it)
    }, {
      successCallback.invoke(it)
    })
  }

  @ReactMethod
  fun enableDebuggingLogging(boolean: Boolean?) {
    enableDebug(boolean)
  }

  @ReactMethod
  fun disconnectPayment(callback: Callback) {
    disposeCallBack {
      callback.invoke(it)
    }
  }

  @ReactMethod
  fun getPurchaseUpdate(querySkuDetails: Boolean?, moreSkus: ReadableArray?, failureCallback: Callback, successCallback: Callback) {
    queryInventoryCallBack(querySkuDetails, moreSkus?.toStringList(), {
      failureCallback.invoke(it)
    }, {
      successCallback.invoke(it)
    })
  }

  @ReactMethod
  fun launchProcessFlow(sku: String?, developerPayload: String?, failureCallback: Callback, successCallback: Callback) {
    startActivity(sku, developerPayload, failureCallback, successCallback)
  }

  @ReactMethod
  fun usePurchase(purchaseString: String?, failureCallback: Callback, successCallback: Callback) {
    consumeAsyncCallBack(purchaseString?.parseToPurchase(), {
      failureCallback.invoke(it)
    }, {
      successCallback.invoke(it)
    })
  }

  private fun startActivity(
    sku: String?,
    developerPayload: String?,
    failureCallback: Callback,
    successCallback: Callback
  ) {
    if (isNotInitializedOrCurrentlyActive()) {
      failureCallback.invoke(IllegalStateException("Payment not connected!"))
      return
    }

    val activity = reactApplicationContext.currentActivity
      ?: run {
        failureCallback.invoke(IllegalStateException("Activity not found!"))
        return
      }

    PaymentActivity.start(activity as Activity, sku, developerPayload, failureCallback, successCallback)
  }
}
