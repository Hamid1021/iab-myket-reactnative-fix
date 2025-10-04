# Myket IAB React native

React native in app billing SDK for React native.


## Dependency
```sh
npm i iab-myket-reactnative
```

## Configuration
To activate Myket reactNative Billing SDK, open the **android** directory located in your root project and add this piece of code to app level **build.gradle** file:
```groovy
def marketApplicationId = "ir.mservices.market"
def marketBindAddress = "ir.mservices.market.InAppBillingService.BIND"
manifestPlaceholders = [marketApplicationId: "${marketApplicationId}",
marketBindAddress : "${marketBindAddress}",
marketPermission : "${marketApplicationId}.BILLING"]
```
if your project doesn't have and **android** directory, make one using the command below:
```sh
expo eject
```
**Note** that application must have the same application Id as you definded in the Myket panel.


## Functional Components
```js
import {useMyket} from "iab-myket-reactnative/src/index";

function MyComponent() {
  const myketInstance = useMyket(myInAppBillingKey);
  // ...
  const someHandler = async () => {
    const inventory = await myketInstance.queryPurchaseProduct(true, productIds);
    console.log(JSON.JSON.stringify(inventory));
  };
  // ...
}
```
## Class Components

```js
import  myket  from  'iab-myket-reactnative';
class  App extends Component 
	componentDidMount() {
		this.didMount = true;
			myket.connect(base64RSA).catch((error) => {
				/**
				* myket is not installed or RSA public key is null or empty
				*/

				this.log(error)
		    }).then(this.retrieverProducts);

	}
	componentWillUnmount() {
		myket.disconnect().catch(() =>  this.log);
	}
}
```

## Complete Example

For better understanding see the Trivial drive app in [here ](http://the) which provided a complete react-native implementation.

## API Documentation

### connect/disconnect
- *Connect* and initialize  myket billing , take RSA public key as a parameter, find more information about public key [here](https://myket.ir/kb/pages/get-pki-public-key/).
- *disconnect* will release the resources occupied by the SDK and mhelper.
### enableDebugging(isEnable:boolean):void
- Enable Debugging in Android logcat.
### purchaseProduct( sku:Boolean , developerPayload :String ):Promise &lt;Purchase&gt;
  - Launches an Activity and shows the myket billing dialog.
  
 - *developerPayload* : Generate a string and pass it to the function and compare to the response of purchase for security reasons.
- *sku* : The SKU of the item that user intended to buy.
### consumePurchase( purchase ): Promise&lt;Void&gt;
- Consume a Purchase, the result of purchasing the product must be consumed by the application.
### queryPurchaseProduct(querySkuDetails,moreSku):Promise &lt;Inventory &gt;
 - query inventory for details of sku's and previously bought items that hasn't consumed.
for some reasons , user may bought a SKU but hasn't consumed by the application , make sure you call consumed purchases immediately after  calling queryPurchaseProduct.
- *moreSkus* : list of array to define wich skuItem's details need to get fetched.
- *querySkuDetails* : pass true if you want to get the sku details,pass false if you want to avoid calling another service.

```ts
export  type  Inventory = {
	allProducts: SkuDetails[];
	allPurchases: Purchase[];
};

export  type  SkuDetails = {
	itemType: string;
	sku: string;
	type: string;
	price: string;
	title: string;
	description: string;
	json: string;
};

export  type  Purchase = {
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
```
# Error schema
the following is the error schema returned from almost every API of the SDK; in case you want to handle errors for more customized scenario : 
```js
{
	"message":"String"
	"responseCode":"Int"
}
```

# License
MIT