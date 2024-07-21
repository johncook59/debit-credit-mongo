package zarg.debitcredit.service;

import org.springframework.stereotype.Service;
import zarg.debitcredit.domain.Account;
import zarg.debitcredit.domain.Transaction;
import zarg.debitcredit.domain.TransactionType;
import zarg.debitcredit.repositories.AccountRepository;
import zarg.debitcredit.repositories.TransactionRepository;

import java.math.BigDecimal;
import java.time.Instant;

import static zarg.debitcredit.domain.TransactionType.CREDIT;
import static zarg.debitcredit.domain.TransactionType.DEBIT;

@Service
class DefaultDebitCreditService implements DebitCreditService {

    private static final String FAILED_TO_FIND_ACCOUNT = "Failed to find account %s";
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    DefaultDebitCreditService(AccountRepository accountRepository,
                              TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction credit(String accountBid, BigDecimal amount) {
        Account account = accountRepository.findByBid(accountBid)
                .orElseThrow(() -> new EntityNotFoundException(
                        FAILED_TO_FIND_ACCOUNT.formatted(accountBid)));

        return updateBalance(account, amount, CREDIT);
    }

    @Override
    public Transaction debit(String accountBid, BigDecimal amount) {
        Account account = accountRepository.findByBid(accountBid)
                .orElseThrow(() -> new EntityNotFoundException(
                        FAILED_TO_FIND_ACCOUNT.formatted(accountBid)));

        return updateBalance(account, amount, DEBIT);
    }

    @Override
    public BigDecimal balance(String accountBid) {
        Account account = accountRepository.findByBid(accountBid)
                .orElseThrow(() -> new EntityNotFoundException(
                        FAILED_TO_FIND_ACCOUNT.formatted(accountBid)));

        return account.getBalance();
    }

    private Transaction updateBalance(Account account, BigDecimal amount,
                                      TransactionType transactionType) {
        account.setBalance(account.getBalance().add(transactionType == CREDIT ? amount : amount.negate()));
        accountRepository.save(account);

        Transaction transaction = Transaction.Builder.builder()
                .accountBid(account.getBid())
                .amount(amount)
                .balance(account.getBalance())
                .type(transactionType)
                .processed(Instant.now())
                .build();
        return transactionRepository.save(transaction);
    }
}
