package id.my.hendisantika.thymeleafsample2;

import id.my.hendisantika.thymeleafsample2.model.Customer;
import id.my.hendisantika.thymeleafsample2.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Slf4j
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
                customer1.setFirstName("Itadori");
                customer1.setLastName("Yuji");
                customer1.setEmail("yuji@jujutsukaisen.com");
                customer1.setPhone("123-456-7890");

                Customer customer2 = new Customer();
                customer2.setFirstName("Fushiguro");
                customer2.setLastName("Megumi");
                customer2.setEmail("megumi@jujutsukaisen.com");
                customer2.setPhone("987-654-3210");

                Customer customer3 = new Customer();
                customer3.setFirstName("Satoru");
                customer3.setLastName("Gojo");
                customer3.setEmail("go@jujutsukaisen.com");
                customer3.setPhone("555-123-4567");

                Customer customer4 = new Customer();
                customer4.setFirstName("Kugisaki");
                customer4.setLastName("Naobara");
                customer4.setEmail("naobara@jujutsukaisen.com");
                customer4.setPhone("444-555-6666");

                Customer customer5 = new Customer();
                customer5.setFirstName("Sukuna");
                customer5.setLastName("Ryoumen");
                customer5.setEmail("sukuna@jujutsukaisen.com");
                customer5.setPhone("777-888-9999");

                Customer customer6 = new Customer();
                customer6.setFirstName("Suguru");
                customer6.setLastName("Geto");
                customer6.setEmail("geto@jujutsukaisen.com");
                customer6.setPhone("777-888-9999");

                // Save customers to database
                customerRepository.save(customer1);
                customerRepository.save(customer2);
                customerRepository.save(customer3);
                customerRepository.save(customer4);
                customerRepository.save(customer5);
                customerRepository.save(customer6);

                log.info("Sample customer data has been initialized.");
            }
        };
    }
}
