package com.exercise.calculator_server_side.services;

/**
 * Handle socket server connection, incoming requests from client and responses.
 */
public interface SocketServerHandlerService {

    /**
     * Starts to listening on defined port
     */
    void start();

    /**
     * Stop to listening
     */
    void stop();

}
