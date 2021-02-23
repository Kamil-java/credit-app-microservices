package pl.bak.product.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.bak.product.model.Credit;

public interface CreditRepository extends JpaRepository<Credit, Integer> {
}
