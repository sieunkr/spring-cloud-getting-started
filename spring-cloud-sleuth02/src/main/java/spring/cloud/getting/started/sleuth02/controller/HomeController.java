package spring.cloud.getting.started.sleuth02.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HomeController {

    private static Logger log = LoggerFactory.getLogger(HomeController.class);


    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @RequestMapping("/delay/01")
    public String delay() {

        try {
            Thread.sleep(7000);


            if(true) {
                log.info("딜레이 오류 발생");
                //throw new IllegalArgumentException("null key");
            }

            return "error";

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "";
    }

}