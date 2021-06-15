package com.practice.userservice.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@RestController
@RefreshScope
public class SpringClientTestController {

    @Value("${msg:Hello from user-service}")
    private String msg;

    private String bookUrl = "http://book-service/client-config";

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/client-config")
    public String getBooks() {
        return msg;
    }

    /*
    execution.isolation.thread.timeoutInMilliseconds : running thread if takes more than given ms; default is 1s.
    circuitBreaker.errorThresholdPercentage : error percentage after which ckt will be opened and all requests will go to fallback.
    circuitBreaker.requestVolumeThreshold : min number of requests in the rolling window that if failed within window will open the circuit.
    circuitBreaker.sleepWindowInMilliseconds : time duration after circuit open, the requests will go to fallback.
    metrics.rollingStats.timeInMilliseconds : the rolling window; default is 10s
    */
    @GetMapping("/connect-book-service")
    @HystrixCommand(fallbackMethod = "callBookService_Fallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "60000")})
    public String getDataFromBookService(){
        return restTemplate.getForObject(bookUrl,String.class);
    }

    @SuppressWarnings("unused")
    private String callBookService_Fallback() {

        System.out.println("Book Service is down!!! fallback route enabled...");

        return "CIRCUIT BREAKER ENABLED!!! No Response From Book Service at this moment. " +
                " Service will be back shortly - " + new Date();
    }
}
