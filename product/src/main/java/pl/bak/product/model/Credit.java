package pl.bak.product.model;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "Credit")
@Table(
        name = "credit",
        schema = "credit_db",
        catalog = "credit_db"
)
public class Credit {

    @Id
    @SequenceGenerator(
            name = "credit_sequence",
            sequenceName = "credit_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "credit_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Integer id;

    @Column(
            name = "credit_name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String creditName;

    @OneToOne(mappedBy = "credit", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Product product;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreditName() {
        return creditName;
    }

    public void setCreditName(String creditName) {
        this.creditName = creditName;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Credit credit = (Credit) o;
        return Objects.equals(id, credit.id) && Objects.equals(creditName, credit.creditName) && Objects.equals(product, credit.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, creditName, product);
    }
}
