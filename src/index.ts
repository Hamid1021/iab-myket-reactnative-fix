import { useEffect } from 'react';
import bridge from './bridge';


/**
 * Recommended to use 'myket' object for Class Component
 * @date 2/1/2023 - 1:43:34 PM
 *
 * @type {{ connect(rsaKey: string): any; disconnect(): any; purchaseProduct: (sku: string, developerPayload: string) => any; consumePurchase: (purchase: any) => any; queryPurchaseProduct: (querySkuDetails: boolean, moreSkus: {}) => any; }}
 */
const myket = {
  connect(rsaKey: string | null | undefined) {
    return bridge.connect(rsaKey);
  },
  disconnect() {
    return bridge.disconnect();
  },
  enableDebugging: bridge.enableDebugging,
  purchaseProduct: bridge.purchaseProduct,
  consumePurchase: bridge.consumePurchase,
  queryPurchaseProduct: bridge.queryInventory,
};

/**
 * Recommended to use 'useMyket' for Functional Component
 * @date 2/1/2023 - 1:47:16 PM
 *
 * @export
 * @param {(string | null)} rsaKey - base64 public RSA key provided from myket store panel 
 * @returns {{ connect(rsaKey: string): any; disconnect(): any; purchaseProduct: (sku: string, developerPayload: string) => any; consumePurchase: (purchase: any) => any; queryPurchaseProduct: (querySkuDetails: boolean, moreSkus: {}) => any; }}
 */
export function useMyket(rsaKey: string | null) {
  useEffect(() => {
    myket.connect(rsaKey);
    return () => {
      myket.disconnect();
    };
  }, []);

  return myket;
}

export default myket