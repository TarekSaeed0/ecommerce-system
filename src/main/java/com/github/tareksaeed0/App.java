package com.github.tareksaeed0;

import java.time.LocalDateTime;

public class App {
    public static void main(String[] args) {
        Product laptop = new ShippableProduct("Laptop", 999.99, 10, 2.5);
        Product cheese = new ExpirableShippableProduct("Cheese", 5.99, 20,
                LocalDateTime.now().plusDays(7), 0.5);
        Product scratchCard = new Product("Scratch Card", 1.00, 100);

        Customer customer = new Customer("Tarek", 1500);

        System.out.println("Customer: " + customer.getName());
        System.out.println("Balance: $" + customer.getBalance());

        Cart cart = new Cart();
        cart.add(laptop, 1);
        cart.add(cheese, 3);
        cart.add(scratchCard, 2);

        for (Cart.Item item : cart) {
            System.out.println(
                    item.getQuantity() + "x " + item.getProduct().getName());
        }
    }
}
