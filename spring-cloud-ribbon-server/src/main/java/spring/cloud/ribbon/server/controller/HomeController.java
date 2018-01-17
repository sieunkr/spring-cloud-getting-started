package spring.cloud.ribbon.server.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @RequestMapping(value = "/say")
    public String greet() {

        return "테스트입니다.";
    }
}
