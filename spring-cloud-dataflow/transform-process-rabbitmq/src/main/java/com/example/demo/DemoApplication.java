package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.integration.annotation.ServiceActivator;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableBinding(Processor.class)
@Slf4j
public class DemoApplication {


	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
    

	@ServiceActivator(inputChannel = Processor.INPUT, outputChannel = Processor.OUTPUT)
    public String transform(byte[] payLoad) {
        log.info("Processor", payLoad);
        
        //Byte 를 String 타입 및 소문자로 변환해서 리턴
        String byteToString = new String(payLoad,0,payLoad.length);
        return byteToString.toLowerCase();
    }
}
