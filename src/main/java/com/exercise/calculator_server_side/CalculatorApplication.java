package com.exercise.calculator_server_side;

import com.exercise.calculator_server_side.services.SocketServerHandlerImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class CalculatorApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(CalculatorApplication.class, args);
        //server starts to listening the port
        SocketServerHandlerImpl server = (SocketServerHandlerImpl) applicationContext.getBean(
                "SocketServerHandler");
    }
}
