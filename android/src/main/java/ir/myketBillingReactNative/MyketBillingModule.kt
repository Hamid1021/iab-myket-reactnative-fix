package ir.myketBillingReactNative

import android.app.Activity
import com.facebook.react.bridge.*
import ir.myket.billingclient.util.Purchase

class MyketBillingModule(private val reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

  override fun getName(): String = "MyketBillingModule"

  @ReactMethod
  fun connectPayment(rsaPublicKey: String?, failureCallback: Callback, successCallback: Callback) {
    initializeCallBack(reactContext, rsaPublicKey, {
      failureCallback.invoke(it)
    }, {
      successCallback.invoke(it)
    })
  }

  @ReactMethod
  fun disconnectPayment(callback: Callback) {
    disposeCallBack {
      callback.invoke(it)
    }
  }

  @ReactMethod
  fun getPurchaseUpdate(
    querySkuDetails: Boolean?,
    moreSkus: ReadableArray?,
    failureCallback: Callback,
    successCallback: Callback
  ) {
    queryInventoryCallBack(querySkuDetails, moreSkus?.toStringList(), {
      failureCallback.invoke(it)
    }, {
      successCallback.invoke(it)
    })
  }

  @ReactMethod
  fun launchProcessFlow(
    sku: String,
    developerPayload: String?,
    failureCallback: Callback,
    successCallback: Callback
  ) {
    startActivity(sku, developerPayload, failureCallback, successCallback)
  }

  @ReactMethod
  fun usePurchase(purchase: Purchase, failureCallback: Callback, successCallback: Callback) {
    consumeAsyncCallBack(purchase, { failureCallback.invoke(it) }, { successCallback.invoke(it) })
  }

  private fun startActivity(
    sku: String,
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
