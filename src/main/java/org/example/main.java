package org.example;

import java.time.LocalDate;

public class main {
    public static void main(String[] args) {

        Product product = new Product.Builder()
                .id("id1")
                .name("SuperWidget")
                .category(Category.TOOLS)
                .rating(8)
                .build();

        System.out.println(product);
    }
}

enum Category {
    TOOLS,
    ELECTRONICS,
    CLOTHING
}

class Product {
    private final String id;
    private final String name;
    private final Category category;
    private final int rating;
    private final LocalDate createdDate;
    private final LocalDate modifiedDate;

    private Product(
            String id,
            String name,
            Category category,
            int rating,
            LocalDate createdDate,
            LocalDate modifiedDate) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.rating = rating;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public static class Builder {

        private String id;
        private String name;
        private Category category;
        private int rating;
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
            if (createdDate == null || modifiedDate == null) {
                throw new IllegalArgumentException("Dates cannot be null");
            }
            return new Product(id, name, category, rating, createdDate, modifiedDate);
        }
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", rating=" + rating +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                '}';
    }
}
