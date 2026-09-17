package org.example;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MainTest {

    @Test
    void builderCreatesProductWithExpectedValues() {
        LocalDate createdDate = LocalDate.of(2026, 1, 1);
        LocalDate modifiedDate = LocalDate.of(2026, 1, 2);

        Product product = new Product.Builder()
                .id("id1")
                .name("SuperWidget")
                .category(Category.TOOLS)
                .rating(8)
                .price(1000.0)
                .createdDate(createdDate)
                .modifiedDate(modifiedDate)
                .build();

        assertEquals("id1", product.getId());
        assertEquals("SuperWidget", product.getName());
        assertEquals(1000.0, product.getPrice());
        assertTrue(product.toString().contains("createdDate=2026-01-01"));
        assertTrue(product.toString().contains("modifiedDate=2026-01-02"));
    }

    @Test
    void discountDecoratorReturnsDiscountedPrice() {
        Product product = new Product.Builder()
                .id("id1")
                .name("SuperWidget")
                .category(Category.TOOLS)
                .rating(8)
                .price(1000.0)
                .build();

        Sellable discountedProduct = new DiscountDecorator(product, 20);

        assertEquals(1000.0, product.getPrice());
        assertEquals(800.0, discountedProduct.getPrice());
        assertEquals(product.getName(), discountedProduct.getName());
        assertEquals(product.getId(), discountedProduct.getId());
    }

    @Test
    void builderRejectsNegativePrice() {
        assertThrows(IllegalArgumentException.class, () -> new Product.Builder()
                .id("id1")
                .name("SuperWidget")
                .category(Category.TOOLS)
                .rating(8)
                .price(-1.0)
                .build());
    }

    @Test
    void discountDecoratorRejectsInvalidPercentage() {
        Product product = new Product.Builder()
                .id("id1")
                .name("SuperWidget")
                .category(Category.TOOLS)
                .rating(8)
                .price(1000.0)
                .build();

        assertThrows(IllegalArgumentException.class,
                () -> new DiscountDecorator(product, 101));
    }

    @Test
    void mainMethodRunsSuccessfully() {
        assertDoesNotThrow(() -> Main.main(new String[0]));
    }
}
