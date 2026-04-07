package com.github.tareksaeed0;

import java.time.LocalDateTime;
import com.github.tareksaeed0.expiration.ExpirationInformation;
import com.github.tareksaeed0.product.BasicProduct;
import com.github.tareksaeed0.product.Product;
import com.github.tareksaeed0.shipping.ShippingInformation;

public class App {
    public static void main(String[] args) {
        Product laptop = new BasicProduct("Laptop", 999.99, 10)
                .withInformation(new ShippingInformation(2.5));

        Product cheese = new BasicProduct("Cheese", 5.99, 20)
                .withInformation(new ExpirationInformation(
                        LocalDateTime.now().plusDays(7)))
                .withInformation(new ShippingInformation(0.5));

        Product scratchCard = new BasicProduct("Scratch Card", 1.00, 100);

        Customer customer = new Customer("Tarek", 1500);

        System.out.println("Customer: " + customer.getName());
        System.out.println("Balance: $" + customer.getBalance());

        Cart cart = new Cart();
        cart.add(laptop, 1);
        cart.add(cheese, 3);
        cart.add(scratchCard, 2);

        for (Cart.Item item : cart) {
            Product product = item.getProduct();
            int quantity = item.getQuantity();

            System.out.println(quantity + "x " + product.getName());
            if (product.hasInformation(ShippingInformation.class)) {
                ShippingInformation shippingInfo =
                        product.getInformation(ShippingInformation.class);
                System.out.println("\tShipping weight of " + product.getName()
                        + " is " + shippingInfo.getWeight() + " kg");
            }

            if (product.hasInformation(ExpirationInformation.class)) {
                ExpirationInformation expirationInfo =
                        product.getInformation(ExpirationInformation.class);
                System.out.println("\tExpiration date of " + product.getName()
                        + " is " + expirationInfo.getExpirationDate());
            }
        }

        double totalPackageWeight = cart.stream()
                .filter(item -> item.getProduct()
                        .hasInformation(ShippingInformation.class))
                .mapToDouble(item -> item.getQuantity() * item.getProduct()
                        .getInformation(ShippingInformation.class).getWeight())
                .sum();
        System.out
                .println("Total package weight " + totalPackageWeight + " kg");
    }
}
