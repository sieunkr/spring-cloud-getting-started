package spring.cloud.hystrix.server.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class helloController {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @HystrixCommand(fallbackMethod = "getDataFallBack")
    public String firstPage() {
        throw new RuntimeException("오류 발생");
    }

    public String getDataFallBack() {
        return "fallback 실행";
    }
}

