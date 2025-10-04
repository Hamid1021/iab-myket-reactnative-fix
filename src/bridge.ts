import { NativeModules } from 'react-native';
import { Inventory, parseModels, Purchase } from './Model';
/*
 * MyketReactiveBillingModule is provided with function returning Promise from android native modules
 * if you want to use Api's with function as a parameter (Callback functions) MyketBillingModule
*/
const { MyketReactiveBillingModule } = NativeModules;


export default {
  /**
   * Connecting and initializing Myket In app billing
   * @date 2/1/2023 - 1:02:43 PM
   *
   * @async
   * @param {(string | null | undefined)} rsaKey - base64 public RSA key provided from myket store panel 
   * @returns {Promise<String>}
   */
  async connect(rsaKey: string | null | undefined): Promise<String> {
    return MyketReactiveBillingModule.connectPayment(rsaKey);
  },

  /**
   * Enable Debugging in Android logcat
   * @date 2/1/2023 - 12:59:37 PM
   * @param {(boolean | null | undefined)} enableDebug - boolean value for enable android logging
   * @returns {*}
   * It is vital to enable debugging in non release variant to see all the events occured in the In app Purchase to enhance debugging
   */
  enableDebugging(enableDebug: boolean | null | undefined) {
    return MyketReactiveBillingModule.enableDebuggingLogging(enableDebug);
  },
  /**
   * Release the resources occupied by the SDK and mhelper
   * @date 2/1/2023 - 1:30:42 PM
   * @returns {Promise<void>}
   */
  disconnect(): Promise<void> {
    return MyketReactiveBillingModule.disconnectPayment();
  },
  /**
   * Query inventory for skuDetails and previously bought items
   * @date 2/1/2023 - 1:08:22 PM
   *
   * @param {(boolean | null | undefined)} querySkuDetails - pass true if you want to get the sku details,pass false if you want to avoid calling another service
   * @param {(string[] | null | undefined)} moreSkus - list of array to define wich skuItem's details need to get fetched 
   * @returns {Promise<Inventory>}
   */
  queryInventory(querySkuDetails: boolean | null | undefined, moreSkus: string[] | null | undefined): Promise<Inventory> {
    return MyketReactiveBillingModule
      .getPurchaseUpdate(querySkuDetails, moreSkus)
      .then(parseModels);
  },
  /**
   * Launches an Activity and shows the myket billing dialog
   * @date 2/1/2023 - 1:19:54 PM
   *
   * @param {(string | null |undefined)} sku - The SKU of the item that user intended to buy
   * @param {(string | null | undefined)} developerPayload - Generate a string and pass it to the function and compare to the response of purchase for security reasons
   * @returns {Promise<Purchase>}
   */
  purchaseProduct(sku: string | null |undefined, developerPayload: string | null | undefined): Promise<Purchase> {
    return MyketReactiveBillingModule
      .launchProcessFlow(sku, developerPayload)
      .then(parseModels);
  },
  /**
   * Consume a Purchase, make sure you consume purchuses returned from query Inventory
   * @date 2/1/2023 - 1:34:28 PM
   *
   * @param {Purchase} purchase - Result of purchase (User already bought) and need to consumed
   * @returns {Promise<void>}
   */
  consumePurchase(purchase: Purchase): Promise<void> {
    return MyketReactiveBillingModule
      .usePurchase(JSON.stringify(purchase))
      .then(parseModels);
  }
};
