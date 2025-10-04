package ir.myketBillingReactNative


import com.facebook.react.bridge.*


class MyketReactiveBillingModule(private val reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

  override fun getName(): String = "MyketReactiveBillingModule"


  @ReactMethod
  fun connectPayment(rsaPublicKey: String?, promise: Promise) {
    initializeCallBack(reactContext, rsaPublicKey, { promise.reject(it) }, { promise.resolve(it) })
  }

  @ReactMethod
  fun enableDebuggingLogging(boolean: Boolean?) {
    enableDebug(boolean)
  }


  @ReactMethod
  fun disconnectPayment(promise: Promise) {
    disposeCallBack { promise.resolve(it) }
  }

  @ReactMethod
  fun getPurchaseUpdate(querySkuDetails: Boolean?, moreSkus: ReadableArray?, promise: Promise) {
    queryInventoryCallBack(querySkuDetails, moreSkus?.toStringList(), { promise.reject(it) }, { promise.resolve(it) })
  }

  @ReactMethod
  fun launchProcessFlow(sku: String?, developerPayload: String?, promise: Promise) {
    startActivity(sku, developerPayload, promise)
  }

  @ReactMethod
  fun usePurchase(purchaseString: String?, promise: Promise) {
    consumeAsyncCallBack(purchaseString?.parseToPurchase(), { promise.reject(it) }, { promise.resolve(it) })
  }

  private fun startActivity(
      sku: String,
      developerPayload: String?,
      promise: Promise
  ) {
      if (isNotInitializedOrCurrentlyActive()) {
          promise.reject("E_NOT_CONNECTED", "Payment not connected!")
          return
      }

      val activity = reactApplicationContext.currentActivity
          ?: run {
              promise.reject("E_NO_ACTIVITY", "Activity not found!")
              return
          }
          
      PaymentActivity.start(activity as Activity, sku, developerPayload, promise)
  }
}
