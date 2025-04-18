package id.my.hendisantika.thymeleafsample2;

import id.my.hendisantika.thymeleafsample2.model.Customer;
import id.my.hendisantika.thymeleafsample2.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootThymeleafSample2Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootThymeleafSample2Application.class, args);
    }

    @Bean
    public CommandLineRunner initData(CustomerRepository customerRepository) {
        return args -> {
            // Check if data already exists
            if (customerRepository.count() == 0) {
                // Create sample customers
                Customer customer1 = new Customer();
                customer1.setFirstName("John");
                customer1.setLastName("Doe");
                customer1.setEmail("john.doe@example.com");
                customer1.setPhone("123-456-7890");

                Customer customer2 = new Customer();
                customer2.setFirstName("Jane");
                customer2.setLastName("Smith");
                customer2.setEmail("jane.smith@example.com");
                customer2.setPhone("987-654-3210");

                Customer customer3 = new Customer();
                customer3.setFirstName("Michael");
                customer3.setLastName("Johnson");
                customer3.setEmail("michael.johnson@example.com");
                customer3.setPhone("555-123-4567");

                Customer customer4 = new Customer();
                customer4.setFirstName("Emily");
                customer4.setLastName("Williams");
                customer4.setEmail("emily.williams@example.com");
                customer4.setPhone("444-555-6666");

                Customer customer5 = new Customer();
                customer5.setFirstName("David");
                customer5.setLastName("Brown");
                customer5.setEmail("david.brown@example.com");
                customer5.setPhone("777-888-9999");

                // Save customers to database
                customerRepository.save(customer1);
                customerRepository.save(customer2);
                customerRepository.save(customer3);
                customerRepository.save(customer4);
                customerRepository.save(customer5);

                System.out.println("Sample customer data has been initialized.");
            }
        };
    }
}
