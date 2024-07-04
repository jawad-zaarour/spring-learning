package zaarour.dev.cashcard;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jdbc.repository.query.Query;

/**
 * The cash card repository.
 *
 * <p>Spring Data JDBC provides the implementation for this interface
 * at runtime.
 *
 * @author Felipe Gutierrez
 * @author Josh Cummings
 */
public interface CashCardRepository extends CrudRepository<CashCard, Long> {
    Iterable<CashCard> findByOwner(String owner);

    @Query("select * from cash_card cc where cc.owner = :#{authentication.name}")
    Iterable<CashCard> findAll();
}
