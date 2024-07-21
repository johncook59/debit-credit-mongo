package zarg.debitcredit.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import zarg.debitcredit.domain.Account;
import zarg.debitcredit.repositories.AccountRepository;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Testcontainers
class DefaultDebitCreditServiceTest {
    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0");

    @Autowired
    DebitCreditService service;

    @Autowired
    AccountRepository accountRepository;

    private Account account;

    @DynamicPropertySource
    static void containersProperties(DynamicPropertyRegistry registry) {
        mongoDBContainer.start();
        registry.add("spring.data.mongodb.host", mongoDBContainer::getHost);
        registry.add("spring.data.mongodb.port", mongoDBContainer::getFirstMappedPort);
    }

    @BeforeEach
    void setup() {
        account = accountRepository.save(Account.Builder.builder()
                .balance(new BigDecimal("10.00"))
                .name("Test")
                .bid(UUID.randomUUID().toString())
                .build());
    }

    @Test
    void shouldUpdateWhenCalledSequentially() {
        String bid = account.getBid();
        service.credit(bid, BigDecimal.ONE);
        service.credit(bid, BigDecimal.ONE);
        Account updated = accountRepository.findByBid(account.getBid()).get();
        assertEquals(new BigDecimal("12.00"), updated.getBalance());
        assertEquals(3, updated.getVersion());
    }

    @Test
    void shouldThrowLockExceptionWithParallelUpdatesAndOptimisticLocking() {
        String bid = account.getBid();
        assertThrows(OptimisticLockingFailureException.class,
                () -> IntStream.rangeClosed(0, 10).parallel()
                        .forEach(i -> service.credit(bid, BigDecimal.ONE)));
    }
}