package org.example.board.tutorial.domain;

public class ProductVO {

    private String name;    // 상품명
    private double price;   // 가격

    // 생성자
    public ProductVO(String name, double price) {
        this.name = name;
        this.price = price;
    }

    // getter & setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // toString

    @Override
    public String toString() {
        return "ProductVO{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
