export type Inventory = {
  allProducts: SkuDetails[];
  allPurchases: Purchase[];
};
export type SkuDetails = {
  itemType: string;
  sku: string;
  type: string;
  price: string;
  title: string;
  description: string;
  json: string;
};
export type Purchase = {
  itemType: string;
  orderId: string;
  packageName: string;
  sku: string;
  purchaseTime: string;
  purchaseState: number;
  developerPayload: string;
  token: string;
  originalJson: string;
  signature: string;
};
export function parseModels(json: any): any {
  if (!json) return json;

  const obj = typeof json === 'string' ? JSON.parse(json) : json;
  if (Array.isArray(obj)) {
    return obj.map(parseModels);
  }

  return obj;
}
