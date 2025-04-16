package id.my.hendisantika.thymeleafsample2;

import id.my.hendisantika.thymeleafsample2.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@Testcontainers
@SpringBootTest(
        properties = {
                "management.endpoint.health.show-details=always",
                "spring.datasource.url=jdbc:tc:mysql:9.0:///customer_db"
        },
        webEnvironment = RANDOM_PORT
)
class SpringBootThymeleafSample2ApplicationTests {

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    void deleteAll() {
        customerRepository.deleteAll();
    }

}
