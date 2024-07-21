package zarg.debitcredit.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import zarg.debitcredit.domain.Account;

import java.math.BigDecimal;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Testcontainers
class AccountRepositoryTest {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0");

    @Autowired
    AccountRepository accountRepository;

    @DynamicPropertySource
    static void containersProperties(DynamicPropertyRegistry registry) {
        mongoDBContainer.start();
        registry.add("spring.data.mongodb.host", mongoDBContainer::getHost);
        registry.add("spring.data.mongodb.port", mongoDBContainer::getFirstMappedPort);
    }

    @Test
    void shouldThrowExceptionOnSaveWithDuplicateKey() {
        final String id = "1";
        Account first = Account.Builder.builder()
                .id(id)
                .balance(new BigDecimal("10.00"))
                .name("First")
                .bid(id)
                .build();
        Account second = Account.Builder.builder()
                .id(id)
                .balance(new BigDecimal("20.00"))
                .name("Second")
                .bid(id)
                .build();

        accountRepository.save(first);
        assertThrows(DuplicateKeyException.class,
                () -> IntStream.rangeClosed(0, 10).parallel()
                        .forEach(i -> accountRepository.save(second)));

    }
}