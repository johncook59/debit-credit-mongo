package zarg.debitcredit;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication()
public class DebitCreditApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(DebitCreditApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }
}
