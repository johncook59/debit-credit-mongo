package zarg.debitcredit.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import zarg.debitcredit.domain.Transaction;

@Repository
public interface TransactionRepository extends MongoRepository<Transaction, Long> {
}
