package zarg.debitcredit.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.time.Instant;

@Document(collection = "transactions")
public class Transaction {

    @Id
    private String id;

    private String bid;

    @Version
    private long version = 0;

    public TransactionType type;

    private BigDecimal amount;

    private BigDecimal balance;

    @Field(name = "account_bid")
    private String accountBid;

    @Field(name = "processed_dt")
    private Instant processed;

    public String getId() {
        return id;
    }

    public String getBid() {
        return bid;
    }

    public long getVersion() {
        return version;
    }

    public TransactionType getType() {
        return type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getAccountBid() {
        return accountBid;
    }

    public Instant getProcessed() {
        return processed;
    }


    public static final class Builder {
        private String id;
        private String bid;
        private long version;
        private TransactionType type;
        private BigDecimal amount;
        private BigDecimal balance;
        private String accountBid;
        private Instant processed;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder bid(String bid) {
            this.bid = bid;
            return this;
        }

        public Builder version(long version) {
            this.version = version;
            return this;
        }

        public Builder type(TransactionType type) {
            this.type = type;
            return this;
        }

        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Builder balance(BigDecimal balance) {
            this.balance = balance;
            return this;
        }

        public Builder accountBid(String accountBid) {
            this.accountBid = accountBid;
            return this;
        }

        public Builder processed(Instant processed) {
            this.processed = processed;
            return this;
        }

        public Transaction build() {
            Transaction transaction = new Transaction();
            transaction.amount = this.amount;
            transaction.id = this.id;
            transaction.processed = this.processed;
            transaction.accountBid = this.accountBid;
            transaction.version = this.version;
            transaction.type = this.type;
            transaction.bid = this.bid;
            transaction.balance = this.balance;
            return transaction;
        }
    }
}
