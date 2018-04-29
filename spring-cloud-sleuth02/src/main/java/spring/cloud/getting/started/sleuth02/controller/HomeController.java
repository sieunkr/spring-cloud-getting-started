package spring.cloud.getting.started.sleuth02.controller;

import brave.Span;
import brave.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;
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


    @Autowired
    private Tracer tracer;

    @RequestMapping("/external/coffees")
    public String delay(@RequestHeader(value="X-B3-TraceId") String traceId) {



        //신규 SPAN 생성 테스트
        Span newSpan = tracer.newTrace().name("New-SPAN").start();
        try (Tracer.SpanInScope ws = tracer.withSpanInScope(newSpan.start())) {
        }finally {
            newSpan.finish();
        }



        try {
            //강제로 지연 오류 발생
            if(true) {
                Thread.sleep(7000);
                log.info("딜레이 오류 발생");
                return restTemplate.getForObject("http://localhost:8083/internal/coffees", String.class);
            }
        } catch (HttpServerErrorException e) {
            return "HttpServerErrorException";
        } catch (InterruptedException e) {
            e.printStackTrace();
            return "InterruptedException";
        }

        return null;
    }
}