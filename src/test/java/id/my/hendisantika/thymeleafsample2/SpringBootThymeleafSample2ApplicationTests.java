package id.my.hendisantika.thymeleafsample2;

import id.my.hendisantika.thymeleafsample2.model.Customer;
import id.my.hendisantika.thymeleafsample2.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(
        properties = {
                "management.endpoint.health.show-details=always",
                "spring.datasource.url=jdbc:h2:mem:testdb",
                "spring.datasource.driver-class-name=org.h2.Driver",
                "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect"
        },
        webEnvironment = RANDOM_PORT
)
class SpringBootThymeleafSample2ApplicationTests {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void testCustomerDataAccess() {
        // Save a test customer
        Customer testCustomer = new Customer();
        testCustomer.setFirstName("Test");
        testCustomer.setLastName("User");
        testCustomer.setEmail("test.user@example.com");
        testCustomer.setPhone("555-123-4567");

        customerRepository.save(testCustomer);

        // Verify we can retrieve the customer
        List<Customer> customers = (List<Customer>) customerRepository.findAll();
        assertTrue(customers.size() > 0, "Should have at least one customer");

        boolean foundTestUser = false;
        for (Customer customer : customers) {
            if ("Test".equals(customer.getFirstName()) && "User".equals(customer.getLastName())) {
                foundTestUser = true;
                assertEquals("test.user@example.com", customer.getEmail());
                assertEquals("555-123-4567", customer.getPhone());
                break;
            }
        }

        assertTrue(foundTestUser, "Should have found the test user");
    }
}
