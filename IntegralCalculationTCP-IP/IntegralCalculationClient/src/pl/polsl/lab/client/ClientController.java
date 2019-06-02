/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.lab.client;

import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Main class of the project. Controls the client class.
 *
 * @author Piotr Bienias
 * @version 3.0
 */
public class ClientController {
    
    /**
     * Main method of client application
     * @param args all parameters are ignored
     */
    public static void main(String [] args) {        
        
        Properties properties = new Properties();
        try (FileInputStream in = new FileInputStream("server.properties")) {
            properties.load(in);
            String address = properties.getProperty("address", "localhost");
            int port = Integer.parseInt(properties.getProperty("port", "1122"));
            Client client = new Client(address, port);
            client.realize();
        }
        catch(IOException e) {
            System.err.println(e);
        }
        
        
    }
    
    
}
