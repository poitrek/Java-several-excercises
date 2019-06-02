/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;



import exceptions.PointArrayException;
import model.Model;
import view.View;


/**
 * The main class of the project. Handles data input and realizes the communication between model and view.
 * 
 * 
 * @author Piotr Bienias
 * @version 1.1
 */
public class Controller {

    // Boolean value indicating if an error has occured
    public static boolean error = false;
    /**
     * @param args the command line arguments
     * First argument - number of function nodes/values (Integer),
     * then array of nodes and array of corresponding values of the function (Double),
     * both arrays of size given with the first argument
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        
        Model model = new Model();
        View view = new View();
        
        //boolean error = false;
        try
        {
            Controller.dataInput(args, model);
        }
        catch (NumberFormatException e)
        {
            System.err.println("Incorrect input data - should be Integer/Double");
            Controller.error = true;
        }
        catch(PointArrayException ex)
        {
            System.err.println(ex);
            Controller.error = true;
        }
        
        if (!error)
        {
            model.calculateIntegral();

            view.output(model);
        }        
        
    }
    
    /**
     * Handles data input of the program and sets it to proper model's members
     * @param args program arguments
     * @param model program model
     * @throws PointArrayException if given array size doesn't match the number of size elements
     */
    public static void dataInput(String[] args, Model model) throws PointArrayException
    {
        
        int arraySize = Integer.parseInt(args[0]);

        int expectedArrayLength = 2 * arraySize + 1;

        if (args.length != expectedArrayLength)
        {
            throw new PointArrayException("Nodes/values array size doesn't match the quantity given with"
                    + " the first argument");
        }

        double[] Nodes = new double[arraySize];
        double[] Values = new double[arraySize];

        for (int i = 0; i < arraySize; i++)
        {
            Nodes[i] = Double.parseDouble(args[i+1]);
        }

        for (int i = 0; i < arraySize; i++)
        {
            Values[i] = Double.parseDouble(args[arraySize + i + 1]);
        }
        try{
            model.setNodesAndValues(arraySize, Nodes, Values);  
        }
        catch(PointArrayException ex)
        {
            System.err.println(ex);
            error = true;
        }
        
 
    }
    
}
