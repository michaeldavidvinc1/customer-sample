package id.my.hendisantika.thymeleafsample2.controller;

import id.my.hendisantika.thymeleafsample2.model.Customer;
import id.my.hendisantika.thymeleafsample2.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-thymeleaf-sample2
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 16/04/25
 * Time: 07.20
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final CustomerService customerService;

    @GetMapping({"/"})
    public String home(@RequestParam(value = "name", defaultValue = "") String name, Model model) {
        List<Customer> customerList = customerService.getAllCustomers();

        // Add IP address and hostname for footer
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            model.addAttribute("ipAddress", inetAddress.getHostAddress());
            model.addAttribute("hostname", inetAddress.getHostName());
        } catch (UnknownHostException e) {
            model.addAttribute("ipAddress", "Unknown");
            model.addAttribute("hostname", "Unknown");
        }

        model.addAttribute("customerList", customerList);
        return "home";
    }

    @GetMapping("create")
    public String create(Model model) {
        Customer customer = new Customer();
        model.addAttribute("customer", customer);

        // Add IP address and hostname for footer
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            model.addAttribute("ipAddress", inetAddress.getHostAddress());
            model.addAttribute("hostname", inetAddress.getHostName());
        } catch (UnknownHostException e) {
            model.addAttribute("ipAddress", "Unknown");
            model.addAttribute("hostname", "Unknown");
        }

        return "create";
    }

    @PostMapping("save")
    public String save(
            @Valid @ModelAttribute("customer") Customer customer,
            BindingResult bindingResult, Model model,
            RedirectAttributes redirectAttributes) throws IOException {

        if (bindingResult.hasErrors()) {
            return "/create";
        }
        customerService.save(customer);
        redirectAttributes.addFlashAttribute("message", "Customer saved successfully");
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String customer(@PathVariable Long id, Model model) {
        Optional<Customer> customer = customerService.findById(id);

        customer.ifPresent(value -> model.addAttribute("customer", value));

        // Add IP address and hostname for footer
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            model.addAttribute("ipAddress", inetAddress.getHostAddress());
            model.addAttribute("hostname", inetAddress.getHostName());
        } catch (UnknownHostException e) {
            model.addAttribute("ipAddress", "Unknown");
            model.addAttribute("hostname", "Unknown");
        }

        return "show";
    }

    @GetMapping("{id}/edit")
    public String edit(@PathVariable long id, Model model) {
        Customer customer = customerService.findById(id).orElse(null);
        model.addAttribute("customer", customer);

        // Add IP address and hostname for footer
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            model.addAttribute("ipAddress", inetAddress.getHostAddress());
            model.addAttribute("hostname", inetAddress.getHostName());
        } catch (UnknownHostException e) {
            model.addAttribute("ipAddress", "Unknown");
            model.addAttribute("hostname", "Unknown");
        }

        return "create";
    }

    @GetMapping("{id}/delete")
    public String delete(@PathVariable long id, RedirectAttributes redirectAttributes) {
        customerService.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Customer deleted successfully");
        return "redirect:/";
    }
}
