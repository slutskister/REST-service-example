package test.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // same as @Configuration @EnableAutoConfiguration @ComponentScan
public class PurchaseListService
{
    public static void main(String[] args)
    {
        SpringApplication.run(PurchaseListService.class, args);
    }
}
