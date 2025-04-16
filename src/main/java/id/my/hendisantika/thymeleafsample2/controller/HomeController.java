package id.my.hendisantika.thymeleafsample2.controller;

import id.my.hendisantika.thymeleafsample2.model.Customer;
import id.my.hendisantika.thymeleafsample2.service.CustomerService;
import id.my.hendisantika.thymeleafsample2.service.FileUploadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

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

    @GetMapping("/")
    public String home(@RequestParam(value = "name", defaultValue = "") String name, Model model) {
        List<Customer> customerList = customerService.getAllCustomers();

        model.addAttribute("customerList", customerList);
        return "home";
    }

    @GetMapping("create")
    public String create(Model model) {
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        return "create";
    }

    @PostMapping("save")
    public String save(
            @Valid @ModelAttribute("customer") Customer customer,
            BindingResult bindingResult, Model model,
            RedirectAttributes redirectAttributes,
            @RequestParam("image") MultipartFile multipartFile) throws IOException {

        if (bindingResult.hasErrors()) {
            return "/create";
        }

        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

            customer.setPhoto(fileName);

            Customer savedCustomer = customerService.save(customer);

            String uploadDir = "customer-photos/" + savedCustomer.getId();
            FileUploadService.saveFile(uploadDir, fileName, multipartFile);
        }
        redirectAttributes.addFlashAttribute("message", "Customer saved successfully");
        return "redirect:/";
    }

}
