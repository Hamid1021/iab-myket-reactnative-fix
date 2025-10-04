package ir.myketBillingReactNative

import com.facebook.react.bridge.ReadableArray
import ir.myket.billingclient.util.Inventory
import ir.myket.billingclient.util.Purchase
import ir.myket.billingclient.util.SkuDetails
import org.json.JSONArray
import org.json.JSONObject


fun Inventory.toJson(): String {
  return JSONObject().apply {
    put("allProducts",allProducts.toTypedArray().toJsonArray())
    put("allPurchases",allPurchases.toTypedArray().toJsonArray())
  }.toString()
}

fun  Array<SkuDetails>.toJsonArray():JSONArray{
  return JSONArray().apply {
    forEach {
      put(it.toJson())
    }
  }
}
fun  Array<Purchase>.toJsonArray():JSONArray{
  return JSONArray().apply {
    forEach {
      put(it.toJson())
    }
  }
}

fun SkuDetails.toProperJson():JSONObject{
  return JSONObject().apply {
    put("sku",sku)
    put("type",type)
    put("price",price)
    put("title",title)
    put("description",description)
  }
}

fun Purchase.toJson(): JSONObject {
  return JSONObject().apply {
    put("itemType", itemType)
    put("orderId", orderId)
    put("packageName", packageName)
    put("sku", sku)
    put("purchaseTime", purchaseTime)
    put("purchaseState", purchaseState)
    put("developerPayload", developerPayload)
    put("token", token)
    put("originalJson", originalJson)
    put("signature", signature)
  }
}

fun ReadableArray.toStringList():MutableList<String>{
  return toArrayList().map {
    it as String
  }.toMutableList()
}

fun String.parseToPurchase(): Purchase {
  val jo = JSONObject(this)
  return Purchase(jo.getString("itemType"), jo.getString("originalJson"), jo.getString("signature"))
}
