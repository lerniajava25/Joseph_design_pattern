package org.example;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Product product = new Product.Builder()
                .id("id1")
                .name("SuperWidget")
                .category(Category.TOOLS)
                .rating(8)
                .price(1000.0)
                .build();

        System.out.println("Original product price: " + product.getPrice());

        Sellable discountedProduct = new DiscountDecorator(product, 20);

        System.out.println("Discounted product price: " + discountedProduct.getPrice());
        System.out.println("Product name: " + discountedProduct.getName());
        System.out.println("Product id: " + discountedProduct.getId());
    }
}

enum Category {
    TOOLS,
    ELECTRONICS,
    CLOTHING
}

interface Sellable {
    String getName();

    double getPrice();

    String getId();
}

class Product implements Sellable {
    private final String id;
    private final String name;
    private final Category category;
    private final int rating;
    private final double price;
    private final LocalDate createdDate;
    private final LocalDate modifiedDate;

    private Product(
            String id,
            String name,
            Category category,
            int rating,
            double price,
            LocalDate createdDate,
            LocalDate modifiedDate) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.rating = rating;
        this.price = price;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public static class Builder {
        private String id;
        private String name;
        private Category category;
        private int rating;
        private double price;
        private LocalDate createdDate = LocalDate.now();
        private LocalDate modifiedDate = LocalDate.now();

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder category(Category category) {
            this.category = category;
            return this;
        }

        public Builder rating(int rating) {
            this.rating = rating;
            return this;
        }

        public Builder price(double price) {
            this.price = price;
            return this;
        }

        public Builder createdDate(LocalDate createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public Builder modifiedDate(LocalDate modifiedDate) {
            this.modifiedDate = modifiedDate;
            return this;
        }

        public Product build() {
            if (id == null || id.isBlank()) {
                throw new IllegalArgumentException("Id cannot be empty");
            }
            if (name == null || name.isBlank()) {
                throw new IllegalArgumentException("Name cannot be empty");
            }
            if (category == null) {
                throw new IllegalArgumentException("Category cannot be null");
            }
            if (rating < 0 || rating > 10) {
                throw new IllegalArgumentException("Rating must be between 0 and 10");
            }
            if (!Double.isFinite(price) || price < 0) {
                throw new IllegalArgumentException(
                        "Price must be a finite, non-negative number");
            }
            if (createdDate == null || modifiedDate == null) {
                throw new IllegalArgumentException("Dates cannot be null");
            }
            if (modifiedDate.isBefore(createdDate)) {
                throw new IllegalArgumentException(
                        "Modified date cannot be before created date");
            }
            return new Product(id, name, category, rating, price, createdDate, modifiedDate);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", category=" + category +
                ", rating=" + rating +
                ", price=" + price +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                '}';
    }
}

abstract class ProductDecorator implements Sellable {
    protected final Sellable decoratedProduct;

    protected ProductDecorator(Sellable decoratedProduct) {
        if (decoratedProduct == null) {
            throw new IllegalArgumentException("Decorated product cannot be null");
        }
        this.decoratedProduct = decoratedProduct;
    }

    @Override
    public String getName() {
        return decoratedProduct.getName();
    }

    @Override
    public double getPrice() {
        return decoratedProduct.getPrice();
    }

    @Override
    public String getId() {
        return decoratedProduct.getId();
    }
}

class DiscountDecorator extends ProductDecorator {
    private final double discountPercentage;

    public DiscountDecorator(Sellable product, double discountPercentage) {
        super(product);
        if (!Double.isFinite(discountPercentage)
                || discountPercentage < 0
                || discountPercentage > 100) {
            throw new IllegalArgumentException(
                    "Discount percentage must be a finite number between 0 and 100");
        }
        this.discountPercentage = discountPercentage;
    }

    @Override
    public double getPrice() {
        return decoratedProduct.getPrice()
                * (1 - discountPercentage / 100);
    }
}
