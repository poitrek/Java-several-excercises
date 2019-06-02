package model;
import classes.*;
import java.util.*;
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
 * @version 2.0
 */
public class Model {
    
    @Deprecated
    // The number of nodes/values of integrated function  
    private int numberOfElements;
    
    @Deprecated
    // Array of nodes (x-coordinates of given points)
    private double[] nodes;
    
    @Deprecated
    // Array of values (y-coordinates of given points)
    private double[] values;
    
    // Vector of partial trapezoids the integral consists of    
    private Vector<PartialTrapezoid> trapezoids;
    
    public Vector<PartialTrapezoid> getTrapezoids()
    {
        return this.trapezoids;
    }
    
    // The result of the integration
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
     * Sets all of partial trapezoids' member points according to passed vector of points
     * @param points array of points (nodes) of considered function
     * @throws exceptions.PointArrayException if given points' X-coordinates are not sorted in ascending order
     */
    public void setPoints(Vector<Point2D<Double>> points) throws PointArrayException
    {
        if (points == null)
        {
            throw new PointArrayException("Array of points is null");
        }
        
        trapezoids = new Vector<>();
        
        for (int i = 0; i < points.size() - 1; i++)
        {
            Point2D<Double> first = points.get(i);
            Point2D<Double> second = points.get(i+1);
            
            if (second.x <= first.x)
            {
                throw new PointArrayException("Array of points should be given in ascending order of their X-coordinates");
            }
            
            PartialTrapezoid pt = new PartialTrapezoid(points.get(i), points.get(i+1));
            
            trapezoids.add(pt);
        }
    }
    
    /**
     * Performs the process of calculating integral, saves it to Result
     */
    public void calculateIntegral()
    {
        result = 0;
        
        /**
         * Summing up areas for of all partial trapezoids
         */
        for (PartialTrapezoid pt : trapezoids)
        {
            result += pt.calculateArea();
        }
    } 
    
}
