package spring.cloud.getting.started.sleuth.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.cloud.getting.started.sleuth.service.HomeService;

@RestController
public class HomeController {

    private static Logger log = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    HomeService homeService;

    @RequestMapping("/")
    public String home() {
        return homeService.coffeeList();
    }
}
