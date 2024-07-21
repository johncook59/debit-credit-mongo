package zarg.debitcredit.domain;


import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(collection = "accounts")
public class Account {

    @Id
    private String id;

    private String bid;

    @Version
    private long version = 0;

    private BigDecimal balance;

    private String name;

    public String getId() {
        return id;
    }

    public String getBid() {
        return bid;
    }

    public long getVersion() {
        return version;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Account setBalance(BigDecimal balance) {
        this.balance = balance;
        return this;
    }

    public String getName() {
        return name;
    }

    public static final class Builder {
        private String id;
        private String bid;
        private long version;
        private BigDecimal balance;
        private String name;

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

        public Builder balance(BigDecimal balance) {
            this.balance = balance;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Account build() {
            Account account = new Account();
            account.name = this.name;
            account.id = this.id;
            account.version = this.version;
            account.bid = this.bid;
            account.balance = this.balance;
            return account;
        }
    }
}
