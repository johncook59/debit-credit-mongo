package zarg.debitcredit.service;

import zarg.debitcredit.domain.Transaction;

import java.math.BigDecimal;

public interface DebitCreditService {
    Transaction credit(String accountBid, BigDecimal amount);

    Transaction debit(String accountBid, BigDecimal amount);

    BigDecimal balance(String accountBid);
}
