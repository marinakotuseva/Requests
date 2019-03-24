package ru.ocrv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.ocrv.controller.RequestController;
import ru.ocrv.entity.Request;
import ru.ocrv.repo.RequestRepository;

@SpringBootApplication
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);


        //Request r = new Request("Some descr");
    }
}