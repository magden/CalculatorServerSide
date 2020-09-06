package com.exercise.calculator_server_side.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This service handles: connections and sharing information between server and clients.
 */
@Service("SocketServerHandler")
public class SocketServerHandlerImpl implements SocketServerHandlerService {
    Logger logger = LoggerFactory.getLogger(SocketServerHandlerImpl.class);

    private final CalculatorService calculatorService;
    private ServerSocket serverSocket;
    @Value("${server.socket.port}")
    private int port;

    /**
     * @param calculatorService the calculator service that will perform the calculation
     */
    public SocketServerHandlerImpl(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    /**
     * Starts listening on defined port
     */
    @PostConstruct
    public void start() {
        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                //each new accepted socket from client inits new thread to handle this request
                new SocketClientHandler(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            stop();
        }
    }

    /**
     * Stop listening and close connection
     */
    public void stop() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }


    /**
     * Thread that handles request from client. Each income request sends to the calculator  service.
     * And result sends back to the client.
     */
    private class SocketClientHandler extends Thread {

        private final Socket clientSocket;

        /**
         * @param socket the socket from the clieent
         */
        public SocketClientHandler(Socket socket) {
            logger.info("Opened session source inet addr: " + socket.getInetAddress());
            this.clientSocket = socket;
            sendHelloResponse();
        }

        /**
         * Sends "Welcome" message to the client
         */
        private void sendHelloResponse() {
            try {
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                out.println("Welcome to the calculator server");
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }

        /**
         * At this step the session with client is already opened, so reads input from client, gives this input
         * to the calculator service and sends result to the client.
         */
        public void run() {
            try {
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    //calculate the input
                    String response = calculatorService.calculate(inputLine);
                    logger.info("Responding to client: " + response);
                    out.println(response);
                }
                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
    }

}
