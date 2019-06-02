/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.lab.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author Piotr Bienias
 * @version 3.0
 */
public class Client {
    
     /**
     * Socket representing connection to the server
     */
    private Socket socket;
    /**
     * Buffered input character stream
     */
    private BufferedReader input;
    /**
     * Formatted output character stream
     */
    private PrintWriter output;    
    
    private int port;
    
    private String address;
    
    
    Client(String address, int port) throws IOException {
        this.socket = new Socket(address, port);
        this.output = new PrintWriter(
                new BufferedWriter(
                        new OutputStreamWriter(
                                socket.getOutputStream())), true);
        this.input = new BufferedReader(
                new InputStreamReader(
                        socket.getInputStream()));
        
    }
    
    /**
     * Realizes the connection to the server.     * 
     * @throws java.io.IOException when a connection error occurs
     */
    public void realize() throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String in = input.readLine();
            if (in != null)
                System.out.println(in);
            else
                break;
            System.out.print("Type a command: ");
            String str = scanner.nextLine();
            output.println(str);
        }
    }    
    
}
