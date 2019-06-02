/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.lab.server;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Main class of the application. Controls the server
 * 
 * @author Piotr Bienias
 * @version 3.0
 */
public class ServerController {
    
    /**
     * Main method of server application
     * @param args all parameters are ignored
     */
    public static void main(String [] args) {
        
        Properties properties = new Properties();
        try (FileInputStream in = new FileInputStream("server.properties")) {
            properties.load(in);
            // Default port is 1122
            int port = Integer.parseInt(properties.getProperty("port", "1122"));
            Server server = new Server(port);
            server.runServer();
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    
    
}
