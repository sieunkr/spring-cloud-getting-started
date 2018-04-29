package spring.cloud.sleuth.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    private static Logger log = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping("/internal/coffees")
    public String coffeeList(@RequestHeader(value="X-B3-TraceId") String traceId) throws Exception {
        //임의로 장애를 발생시켜보자.
        if(true){
            throw new Exception("장애 발생");
        }
        return "아메리카노, 라떼, 모카";
    }
}
