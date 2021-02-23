package pl.bak.credit.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.bak.credit.model.Credit;

@Repository
@Transactional(readOnly = true)
public interface CreditRepository extends JpaRepository<Credit, Integer> {
}
