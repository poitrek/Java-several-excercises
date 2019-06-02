package view;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import exceptions.PointArrayException;
import model.Model;
/**
 * The view class of the program. Handles viewing output results given by the model.
 * 
 * @author Piotr Bienias
 * @version 1.0
 */
public class View {
    
    /**
     * Shows the output result of the program performed by passed model
     * @param model program model
     */
    public void output(Model model)
    {
        print("The calculated integral is equal to ");
        print(model.getResult());
        print("\n");
    }
    
    /**
     * Prints an object
     * @param o any object
     */
    public void print(Object o)
    {
        System.out.print(o);
    }
    
}
