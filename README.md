<h1 align="center">E-Commerce System</h1>

<p align="center"> A solution to to the Fawry full stack development internship challenge </p>

<div align="center">

<a href="https://github.com/TarekSaeed0/ecommerce-system/pulse">
  <picture>
    <source media="(prefers-color-scheme: dark)"  srcset="https://img.shields.io/github/last-commit/TarekSaeed0/ecommerce-system?style=for-the-badge&labelColor=%23151b23&color=%234493f8">
    <source media="(prefers-color-scheme: light)" srcset="https://img.shields.io/github/last-commit/TarekSaeed0/ecommerce-system?style=for-the-badge&labelColor=%23f6f8fa&color=%230969da">
    <img alt="GitHub Last Commit" src="https://img.shields.io/github/last-commit/TarekSaeed0/ecommerce-system?style=for-the-badge&labelColor=%23151b23&color=%234493f8">
  </picture>
</a>
<a href="https://github.com/TarekSaeed0/ecommerce-system/stargazers">
  <picture>
    <source media="(prefers-color-scheme: dark)"  srcset="https://img.shields.io/github/stars/TarekSaeed0/ecommerce-system?style=for-the-badge&labelColor=%23151b23&color=%234493f8">
    <source media="(prefers-color-scheme: light)" srcset="https://img.shields.io/github/stars/TarekSaeed0/ecommerce-system?style=for-the-badge&labelColor=%23f6f8fa&color=%230969da">
    <img alt="GitHub Repo stars" src="https://img.shields.io/github/stars/TarekSaeed0/ecommerce-system?style=for-the-badge&labelColor=%23151b23&color=%234493f8">
  </picture>
    </a>
</div>

## Assumptions and Design Decisions

- **Requirement:** Products can be of different types, such as physical products that require shipping and have weight, or products that can expire.

  A product can be of multiple types, for example, a product can both require shipping and may expire.

  **Assumption:** Additional types can be added in the future without requiring changes to the existing code.

  **Design Decision:** Using inheritance to represent this would require creating a new class for each combination of product types, which would lead to a large number of classes and difficult to maintain code.

  to solve this problem, the design uses composition instead, with products containing a dynamic set of additional information, and wrapper classes that implement specific interfaces for products with a specific type of additional information.

  this design is also type safe, as access to the information of a product is by the information type.

  the type-based access for product information was a bit inspired by the std::get function in C++ for accessing elements of a tuple by their type.

  adding a new type of product in the future would only require creating a class for the additional information and a wrapper class that implements the corresponding interface, without requiring any changes to the existing code.

- **Assumption:** When a product is added to the cart, the product's available quantity is not reduced, the quantity is only reduced when the customer checks out.

## Code Structure

### Modules

The classes mentioned here is not an exhaustive list. POJO classes, Exception classes and classes not integral to the core functionality are not listed here, It's recommended to refer to the source code for a complete list.

- ### Customer Module
  - **Customer Class:** represents a customer that has a name and a balance, it provides methods for depositing and withdrawing from the customer's balance.

- ### Product Module
  - **Product Interface:** represents a product that has a name, price, quantity, and additional information.
  - **Product Information Interface:** represents additional information about a product. A marker interface with no methods, it is used to indicate that a class contains additional information about a product.
  - **Basic Product Class:** an implementation of the Product interface that represents a basic product with a dynamic set of information.
  - **Product With Information Class:** a generic wrapper class that represents a product with a specific type of information, it implements the Product interface.

    it validates in the constructor that the passed product contains the required information, and is mainly used as a base class for product wrapper classes that implement other interfaces, such as Expirable or Shippable.

- ### Expiration Module
  - **Expirable Interface:** represents a product that has an expiration date, it provides a method for checking if the product is expired.
  - **Expiration Information Class:** represents information about a product's expiration details, contains the product's expiration date, it implements the Product Information interface.
  - **Expirable Product Class:** a wrapper class that represents an expirable product, it extends the Product With Information class and implements the Expirable interface.

- ### Shipping Module
  - **Shippable Interface:** represents a physical product that has weight and requires shipping.
  - **Shipping Information Class:** represents information about a product's shipping details, contains the product's weight, it implements the Product Information interface.
  - **Shippable Product Class:** a wrapper class that represents a shippable product, it extends the Product With Information class and implements the Shippable interface.
  - **Shipping Service Interface:** represents a shipping service that takes a list of shippable products and returns shipping details, such as the total package weight and shipping cost.
  - **Simple Shipping Service Class:** an implementation of the Shipping Service interface that calculates the total package weight and shipping cost based on a fixed rate per kilogram.

- ### Cart Module
  - **Cart Class:** represents a shopping cart that contains a list of items, where an item is a product and a quantity, it provides methods for adding and removing items from the cart.
  - **Cart Validator:** provides method for checking that a cart is valid for checkout. checks if a cart is empty, contains out of stock products, or contains expired products.

- ### Checkout Module
  - **Checkout Service Interface:** represents a checkout service that takes a cart and a customer, and processes the checkout, deducting the total cost from the customer's balance, and returns checkout details.
  - **Simple Checkout Service Class:** an implementation of the Checkout Service interface that takes an object implementing the Shipping Service interface and uses the Cart Validator to validate the cart, calculates the total cost of the products in the cart, plus shipping cost, and deducts the total cost from the customer's balance.

- ### Receipt Module
  - **Receipt Class:** represents a generic receipt, containing a title, a list of items and summary details such as subtotal and shipping cost.
  - **Receipt Builder Class:** a builder class for incrementally building a receipt, it provides methods for adding items to the receipt and adding totals, and a build method that returns the built receipt.
  - **Receipt Generator Interface:** represents a receipt generator that takes checkout details and generates receipts.
  - **Simple Receipt Generator Class:** an implementation of the Receipt Generator interface that generates a shipping notice receipt (if applicable) and a payment receipt.
  - **Receipt Formatter Interface:** represents a receipt formatter that takes a receipt and returns a formatted string representation of the receipt.
  - **Receipt Formatter Class:** an implementation of the Receipt Formatter interface that formats a receipt nicely with aligned items and totals.

## Example

### Code

```java
ShippingService shippingService = new SimpleShippingService(10);
CheckoutService checkoutService =
    new SimpleCheckoutService(shippingService);

ReceiptGenerator receiptGenerator = new SimpleReceiptGenerator();
ReceiptFormatter receiptFormatter = new SimpleReceiptFormatter();

Product laptop = new BasicProduct("Laptop", 999.99, 10)
				.withInformation(new ShippingInformation(2.5));

Product cheese = new BasicProduct("Cheese", 5.99, 20)
    .withInformation(
        new ExpirationInformation(LocalDateTime.now().plusDays(7)))
    .withInformation(new ShippingInformation(0.5));

Product scratchCard = new BasicProduct("Scratch Card", 1.00, 100);

try {
  Customer customer = new Customer("Tarek", 1500);

  Cart cart = new Cart();

  cart.add(laptop, 1);
  cart.add(cheese, 1);
  cart.add(scratchCard, 2);

  CheckoutDetails details = checkoutService.checkout(customer, cart);

  List<Receipt> receipts = receiptGenerator.generateReceipts(details);
  for (Receipt receipt : receipts) {
    System.out.println(receiptFormatter.format(receipt));
  }
} catch (Exception e) {
  System.out.println("Error: " + e.getMessage());
}
```

### Output

```
**     Shipping Notice     **
1x Cheese                500g
1x Laptop               2.5kg
-----------------------------
Total Package Weight      3kg

**     Checkout Receipt     **
2x Scratch Card             $2
1x Cheese                $5.99
1x Laptop              $999.99
------------------------------
Subtotal             $1,007.98
Shipping Cost              $30
Paid Amount          $1,037.98
Remaining Balance      $462.02
```

## Building

```sh
git clone https://github.com/TarekSaeed0/ecommerce-system.git
cd ecommerce-system
mvn compile
mvn exec:java
```
