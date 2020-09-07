package com.exercise.calculator_server_side;

import com.exercise.calculator_server_side.services.SocketServerHandlerImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * This server-side of the Calculator application.
 */
@SpringBootApplication
public class CalculatorApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(CalculatorApplication.class, args);
        //creates new sever, this server starts listening on specific port and handle requests from the client
        SocketServerHandlerImpl server = (SocketServerHandlerImpl) applicationContext.getBean(
                "SocketServerHandler");
    }
}
