/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.lab.server;

import pl.polsl.lab.model.IntegralModel;
import java.net.*;
import java.io.*;

/**
 *
 * @author Piotr Bienias
 * @version 3.0
 */
public class Server implements Closeable {

    /**
     * port number
     */
    private final int port;

    /**
     * field represents the socket waiting for client connections
     */
    private final ServerSocket serverSocket;
    
    
    private IntegralModel model;
    

    /**
     * Creates the server socket
     *
     * @throws IOException when prot is already bind
     */
    Server(int port) throws IOException {
        this.port = port;
        this.serverSocket = new ServerSocket(port);
        this.model = new IntegralModel();
    }

    /**
     * Runs the server
     */
    public void runServer() {

        try {
            System.out.println("Server started");
            while (true) {
                Socket socket = this.serverSocket.accept();
                try (SingleService singleService = new SingleService(socket, model)) {
                    singleService.realize();
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    
    /**
     * Closes the socket
     * 
     * @throws IOException when a connection error occurs.
     */
    @Override
    public void close() throws IOException {
        if (serverSocket != null) {
            serverSocket.close();
        }
    }
}
