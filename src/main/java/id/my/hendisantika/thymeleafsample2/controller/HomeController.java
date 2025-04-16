package id.my.hendisantika.thymeleafsample2.controller;

import id.my.hendisantika.thymeleafsample2.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

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

}
