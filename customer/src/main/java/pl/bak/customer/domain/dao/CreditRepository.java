package pl.bak.customer.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.bak.customer.model.Credit;

public interface CreditRepository extends JpaRepository<Credit, Integer> {
}
