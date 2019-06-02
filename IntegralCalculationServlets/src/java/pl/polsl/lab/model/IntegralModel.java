package pl.polsl.lab.model;
import java.util.*;
import pl.polsl.lab.exceptions.PointArrayException;
        
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * The model class of the program. Performs the actual calculation of integral.
 *
 * @author Piotr Bienias
 * @version 4.0
 */
public class IntegralModel {
    

    @Deprecated
    // The number of nodes/values of integrated function  
    private int numberOfElements;
    
    @Deprecated
    // Array of nodes (x-coordinates of given points)
    private double[] nodes;
    
    @Deprecated
    // Array of values (y-coordinates of given points)
    private double[] values;

  
    
    /**
     * Class representing a single element of history
     */
    public class SingleHistory {
        
        // Vector of points that were put in the sample
        private Vector<Point2D<Double>> points;
        
        // The output value that given vector of points has resulted in
        private Double result;
        
        // Single history constructor
        public SingleHistory(Vector<Point2D<Double>> _points, Double _result){
            this.points = _points;
            this.result = _result;
        }
        
        // Prints properly the single history element
        public String print() {
            String str = "Given points: ";            
            for (Point2D<Double> point : points) {
                str += "(" + point.x + ", " + point.y + ") ";
            }
            str += "; result: " + result;
            return str;            
        }
        
    }
    
    /**
     * The actual history of all model usages (containg lists of given points
     * and results)             
     */
    private Vector<SingleHistory> history;
    
    
    // Vector of partial trapezoids the integral consists of    
    private Vector<PartialTrapezoid> trapezoids;
    
    public IntegralModel() {
        this.trapezoids = new Vector<PartialTrapezoid>();
        this.history = new Vector<SingleHistory>();
    }
    
    
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
    
    // Returns the history
    public Vector<SingleHistory> getHistory() {
        return this.history;
    }
    
    /**
     * Sets all of partial trapezoids' member points according to passed strings of points coordinates
     * @param nodes vector of x-coordinates of points
     * @param values vector of y-coordinates of points
     * @throws pl.polsl.lab.exceptions.PointArrayException if given points' X-coordinates are not sorted in ascending order
     */
    public void setPoints(Vector<Double> nodes, Vector<Double> values) throws PointArrayException
    {                
        if (nodes == null || values == null)
        {
            throw new PointArrayException("Vector of nodes/values is null");
        }
        
        if (nodes.size() != values.size())
        {
            throw new PointArrayException("Vectors of nodes and values aren't same size");
        }
        
        trapezoids.clear();
        
        for (int i = 0; i < nodes.size() - 1; i++)
        {
            Point2D<Double> first = new Point2D<>(nodes.get(i), values.get(i));
            Point2D<Double> second = new Point2D<>(nodes.get(i+1), values.get(i+1));
            
            if (second.x <= first.x)
            {
                throw new PointArrayException("Array of points should be given in ascending order of their X-coordinates");
            }
            
            PartialTrapezoid pt = new PartialTrapezoid(first, second);
            
            trapezoids.add(pt);
        }
    }
    
     /**
     * Sets all of partial trapezoids' member points according to passed vector of points
     * @param points array of points (nodes) of considered function
     * @throws pl.polsl.lab.exceptions.PointArrayException if given points' X-coordinates are not sorted in ascending order
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
    
    /**
     * Updates the history from current model state
     */
    public void updateHistory() {
        
        Vector<Point2D<Double>> pointVector = new Vector<>();
        
        // Add all points from trapezoid vector to new vector of points
        for (PartialTrapezoid pt : trapezoids) {
            pointVector.add(new Point2D<>(pt.beginPoint));
        }        
        pointVector.add(new Point2D<>(trapezoids.lastElement().endPoint));
        
        // Add a new single history element
        history.add(new SingleHistory(pointVector, result));
                
    }
    
}
