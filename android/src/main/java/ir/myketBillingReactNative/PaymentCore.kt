package ir.myketBillingReactNative

import android.app.Activity
import android.content.Context
import ir.myket.billingclient.IabHelper
import ir.myket.billingclient.util.IabResult
import ir.myket.billingclient.util.Inventory
import ir.myket.billingclient.util.Purchase


private var mHelper: IabHelper? = null


fun initializeCallBack(context: Context, iabPublicKey: String?, failureCallback: (String)->Unit, successCallback: (String)->Unit) {
  if (iabPublicKey.isNullOrEmpty()) {
    failureCallback.invoke(Error.InvalidPublicKeyException().toString())
    return
  }

  mHelper = IabHelper(context, iabPublicKey)
  mHelper?.startSetup {
    if (it.isSuccess)
      successCallback.invoke("initialize complete....")
    else failureCallback.invoke(Error.InitializationException(it.response.toString(), it.message).toString())
  }

}

fun enableDebug(boolean: Boolean?){
  mHelper?.enableDebugLogging(boolean?:true)
}

fun queryInventoryCallBack(querySkuDetails: Boolean?, moreSkus: MutableList<String>? = null, failureCallback: (String)->Unit, successCallback: (String)->Unit) {
  try {
    mHelper?.queryInventoryAsync(querySkuDetails
      ?: false, moreSkus) { result: IabResult, inventory: Inventory? ->
      if (mHelper == null) return@queryInventoryAsync
      if (result.isFailure && !result.isSuccess) {
        failureCallback.invoke(Error.FetchError(result.message,result.response).toString())
      } else {
        inventory?.let {
          successCallback.invoke(it.toJson())
        }?: run {
          failureCallback.invoke("Null Response")
        }
      }
    }
  } catch (e: Exception) {
    failureCallback.invoke(Error.RaceConditionException().toString())
  }
}



fun launchProcessFlowCallBack(currentActivity: Activity, sku: String?, developerPayload: String?, failureCallback: (String)->Unit, successCallback: (String)->Unit) {
  try {
    if (sku.isNullOrEmpty()){
      failureCallback.invoke(Error.InvalidSkuException().message)
      return
    }
    mHelper?.launchPurchaseFlow(currentActivity, sku, { result, info ->
      if (mHelper == null) return@launchPurchaseFlow
      if (result.isFailure && !result.isSuccess) {
        failureCallback.invoke(Error.FetchError(result.message,result.response).toString())
      } else {
        successCallback.invoke(info.toJson().toString())
      }
    }, developerPayload)
  } catch (e: Exception) {
    failureCallback.invoke(Error.RaceConditionException().toString())
  }
}

fun consumeAsyncCallBack(purchase: Purchase?, failureCallback: (String)->Unit,successCallback: (String)->Unit) {
  try {
    mHelper?.consumeAsync(purchase) { purchaseResult, result ->
      if (mHelper == null) return@consumeAsync
      if (result.isFailure && !result.isSuccess) {
        failureCallback.invoke(Error.FetchError(result.message,result.response).toString())
      } else {
        successCallback.invoke(purchaseResult.toJson().toString())
      }
    }
  } catch (e: Exception) {
    failureCallback.invoke(Error.RaceConditionException().toString())
  }
}

fun disposeCallBack(callBack: (Any?)->Unit) {
  if (mHelper != null) {
      mHelper?.dispose()
      mHelper = null
      callBack.invoke(null)
  }
}

fun isNotInitializedOrCurrentlyActive(): Boolean = mHelper == null































