package spring.cloud.ribbon.router.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import spring.cloud.ribbon.router.config.HelloConfig;

@RestController
@RibbonClient(name = "say-hello", configuration = HelloConfig.class)
public class HelloController
{
    @LoadBalanced
    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/hi")
    public String hi() {
        String returnValue = this.restTemplate.getForObject("http://hello/say", String.class);
        return returnValue;
    }

}
