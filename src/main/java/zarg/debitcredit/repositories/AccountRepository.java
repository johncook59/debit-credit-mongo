package zarg.debitcredit.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import zarg.debitcredit.domain.Account;

import java.util.Optional;

@Repository
public interface AccountRepository extends MongoRepository<Account, Long> {
    Optional<Account> findByBid(String bid);
}
