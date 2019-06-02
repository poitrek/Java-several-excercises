package model;

import exceptions.PointArrayException;
        
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * The model class of the program. Performs the actual calculation of integral.
 *
 * @author Piotr Bienias
 * @version 1.1
 */
public class Model {
    
    
    // The number of nodes/values of integrated function  
    private int numberOfElements;
    
    // Array of nodes (x-coordinates of given points)
    private double[] nodes;
    
    // Array of values (y-coordinates of given points)
    private double[] values;
    
    // The result of integration
    private double result;
    
    /**
     * Returns the result of the integration
     * @return 
     */
    public double getResult()
    {
        return result;
    }
    
    /**
     * Sets arrays of nodes and values according to passed parameters
     * @param arraySize size of both arrays
     * @param _nodes array of nodes (x-coordinates)
     * @param _values array of values
     * @throws exceptions.PointArrayException if node values are not given in ascending order
     */
    public void setNodesAndValues(int arraySize, double[] _nodes, double[] _values) throws PointArrayException
    {
        nodes = _nodes.clone();
        values = _values.clone();
        numberOfElements = arraySize;
        
        for (int i = 0; i < arraySize - 1; i++)
        {
            if (nodes[i+1] <= nodes[i])
            {
                throw new PointArrayException("Array of nodes should be given in ascending order");                        
            }
        }        
        
    }
    
    /**
     * Performs the process of calculating integral, saves it to Result
     */
    public void calculateIntegral()
    {
        result = 0;
        
        for (int i = 0; i < numberOfElements - 1; i++)
        {
            result += (values[i] + values[i+1])*(nodes[i+1] - nodes[i]) / 2.0;
        }
        
    } 
    
}
