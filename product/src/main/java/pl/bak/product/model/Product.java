package pl.bak.product.model;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "Product")
@Table(
        name = "product"
)
public class Product {

    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    @Column(
            name = "credit_id",
            updatable = false
    )
    private Integer id;

    @Column(
            name = "product_name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String productName;

    @Column(
            name = "value",
            nullable = false
    )
    private int value;

    @OneToOne
    @MapsId
    @JoinColumn(name = "credit_id")
    private Credit credit;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Credit getCredit() {
        return credit;
    }

    public void setCredit(Credit credit) {
        this.credit = credit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return value == product.value && Objects.equals(id, product.id) && Objects.equals(productName, product.productName) && Objects.equals(credit, product.credit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productName, value, credit);
    }
}
