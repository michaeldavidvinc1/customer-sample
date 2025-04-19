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

//    @Test
//    void testCustomerPhotoInitialization() {
//        // Create and save test customers with photos
//        Customer johnDoe = new Customer();
//        johnDoe.setFirstName("John");
//        johnDoe.setLastName("Doe");
//        johnDoe.setEmail("john.doe@example.com");
//        johnDoe.setPhone("123-456-7890");
//        johnDoe.setPhoto("Jetbrains.jpeg");
//        customerRepository.save(johnDoe);
//
//        Customer janeSmith = new Customer();
//        janeSmith.setFirstName("Jane");
//        janeSmith.setLastName("Smith");
//        janeSmith.setEmail("jane.smith@example.com");
//        janeSmith.setPhone("987-654-3210");
//        janeSmith.setPhoto("avatar-default.jpg");
//        customerRepository.save(janeSmith);
//
//        // Verify that the customers have photos set
//        List<Customer> customers = (List<Customer>) customerRepository.findAll();
//
//        // Find John Doe who should have Jetbrains.jpeg as photo
//        boolean foundJohnDoe = false;
//        for (Customer customer : customers) {
//            if ("John".equals(customer.getFirstName()) && "Doe".equals(customer.getLastName())) {
//                foundJohnDoe = true;
//                assertEquals("Jetbrains.jpeg", customer.getPhoto(), "John Doe should have Jetbrains.jpeg as photo");
//                break;
//            }
//        }
//
//        assertTrue(foundJohnDoe, "Should have found John Doe with photo");
//
//        // Verify Jane Smith has default avatar
//        boolean foundJaneSmith = false;
//        for (Customer customer : customers) {
//            if ("Jane".equals(customer.getFirstName()) && "Smith".equals(customer.getLastName())) {
//                foundJaneSmith = true;
//                assertEquals("avatar-default.jpg", customer.getPhoto(), "Jane Smith should have default avatar as photo");
//                break;
//            }
//        }
//
//        assertTrue(foundJaneSmith, "Should have found Jane Smith with default photo");
//    }
}
