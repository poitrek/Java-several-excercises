package pl.polsl.lab.exceptions;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * An exception class which is thrown in one of two cases:
 * - the size of the array of nodes/values passed as parameter doesn't match the quantity
 *      given with the first argument
 * - the array of nodes (x-coordinates) is not given in ascending order
 * - arrays of nodes and values aren't same size
 * 
 * @author Piotr Bienias
 * @version 4.0
 */
public class PointArrayException extends Exception {
    
    /**
     * Default constructor
     */
    public PointArrayException(){
        
    }
    
    /**
     * Constructor
     * @param Msg text message to be displayed when thrown
     */
    public PointArrayException(String Msg){
        super(Msg);
    }
    
}
