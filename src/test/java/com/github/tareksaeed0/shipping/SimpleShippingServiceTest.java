package com.github.tareksaeed0.shipping;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.Test;

public class SimpleShippingServiceTest {
  private static class MockShippable implements Shippable {
    private final String name;
    private final double weight;

    public MockShippable(String name, double weight) {
      this.name = name;
      this.weight = weight;
    }

    @Override
    public String getName() {
      return name;
    }

    @Override
    public double getWeight() {
      return weight;
    }
  }

  @Test
  void cantCreateServiceWithNegativeRate() {
    assertThrows(IllegalArgumentException.class,
        () -> new SimpleShippingService(-1));
  }

  @Test
  void calculatesTotalWeightAndShippingCost() {
    SimpleShippingService service = new SimpleShippingService(10);
    List<Shippable> shippables = List.of(new MockShippable("Box A", 1.25),
        new MockShippable("Box B", 0.75));

    ShippingDetails details = service.ship(shippables);

    assertEquals(2, details.getShippables().size());
    assertEquals(2.0, details.getTotalPackageWeight(), 0.0001);
    assertEquals(20.0, details.getShippingCost(), 0.0001);
  }
}
