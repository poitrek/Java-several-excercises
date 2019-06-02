/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.lab.server;

import java.io.*;
import java.net.Socket;
import java.util.Vector;
import pl.polsl.lab.model.*;
import pl.polsl.lab.exceptions.*;

/**
 * The server class servicing a single connection
 * 
 * @author Piotr Bienias
 * @version 3.0
 */
class SingleService implements Closeable {

    /**
     * Socket representing connection to the client
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
    
    
    //Application model    
    private IntegralModel model;
    
    // Flag signifying that any data has been loaded yet and can be calculated
    private boolean dataLoadedYet;
    
    // Flag signifying that the integral has been calculatet yet and can be displayd
    private boolean integralCalculatedYet;

    /**
     * The constructor of instance of the SingleService class. Use the socket as
     * a parameter.
     *
     * @param socket socket representing connection to the client
     * @param model application model
     */
    public SingleService(Socket socket, IntegralModel model) throws IOException {
        this.socket = socket;
        this.model = model;
        output = new PrintWriter(
                new BufferedWriter(
                        new OutputStreamWriter(
                                socket.getOutputStream())), true);
        input = new BufferedReader(
                new InputStreamReader(
                        socket.getInputStream()));
        
        dataLoadedYet = false;
        integralCalculatedYet = false;
    }

    /**
     * Realizes the service
     */
    public void realize() {
        try {
            output.println("Welcome to IntegralCalculation Server.");

            while (true) 
            {        
                String str = input.readLine();
                
                if (str == null || str.equals("")){
                    continue;
                }
                else{                  
                    String arguments [] = str.split(" ");
                    String command = arguments[0];
                    
                    boolean quit = false;
                    switch(command.toLowerCase())
                    {

                        case "quit":
                            if (testCommandArguments(arguments)){
                                quit = true;
                            }
                            break;
                        case "loaddata":
                            if (arguments.length - 1 < 3) {
                                output.println("This command takes at least 3 arguments!");
                            }
                            else {                            
                                try{
                                    loadData(arguments);
                                    output.println("Data has been properly loaded.");
                                    dataLoadedYet = true;
                                }
                                catch(PointArrayException e){
                                    output.println(e.getMessage());
                                }
                                catch(NumberFormatException e){
                                    output.println("Wrong input format: array of points should be Integer/Double.");
                                }
                            }
                            break;
                        case "calculate":
                            if (testCommandArguments(arguments)){
                                if (dataLoadedYet){
                                    model.calculateIntegral();
                                    output.println("The integral has been correctly calculated");
                                    integralCalculatedYet = true;
                                }
                                else{
                                    output.println("No data has been loaded yet");
                                }
                            }                            
                            break;
                        case "display":
                            if (testCommandArguments(arguments)){
                                if (integralCalculatedYet){
                                    output.println("Calculated integral is equal to " + model.getResult());
                                }
                                else{
                                    output.println("No result to display yet. Please load data and calculate first");
                                }
                            }
                            break;
                        case "help":
                            output.println("LOADDATA [NUMBER_OF_NODES, X1, X2,..., XN, Y1, Y2,..., YN]"
                                    + ", CALCULATE, DISPLAY, QUIT, HELP");
                            break;
                            
                        default:
                            output.println("Unrecognized command. Use 'help' to list all available commands");
                            break;
                    }

                    if(quit){
                        break;
                    }
                }
            }
            System.out.println("closing...");
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        } 
        finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }
    
    /**
     * Method testing if there are any arguments within given command 
     * that doesn't need them
     * @param args command arguments
     * @return true if no arguments are passed
     */
    private Boolean testCommandArguments(String [] args){
        if (args.length - 1 > 0) {
            output.println("This command takes no arguments!");
            return false;
        }
        else {
            return true;
        }
    }
    
    /**
     * Method handling loading data (set of points) to the model
     * @param args command arguments; args[0] is the command itself
     * @throws PointArrayException if given number of points doesn't match
     *  array size, or points' X-coordinates aren't passed in ascending order
     */
    private void loadData(String [] args) throws PointArrayException
    {        
        // Index buffer due to command, which is the first element of args
        int buffer = 1;
        
        int numberOfPoints = Integer.parseInt(args[buffer]);
        int expectedArrayLength = 2 * numberOfPoints + 1;

        if ((args.length - buffer) != expectedArrayLength)
        {
            throw new PointArrayException("Nodes/values array size doesn't match the quantity given with"
                    + " the first argument");
        }

        Vector<Point2D<Double>> points = new Vector<>();

        for (int i = 0; i < numberOfPoints; i++)
        {
            double x = Double.parseDouble(args[i + 1 + buffer]);
            double y = Double.parseDouble(args[numberOfPoints + i + 1 + buffer]);
            
            points.add(new Point2D<Double>(x, y));
        }
        
        model.setPoints(points);        
        
    }
    
    /*
    Closes the service by closing socket
    */
    @Override
    public void close() throws IOException {
        if (socket != null) {
            socket.close();
        }
    }
}

