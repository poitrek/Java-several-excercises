/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

/**
 * The class of partial trapezoid (trapezium) used to calculate
 * a single area under the function between two specified points
 *
 * @author Piotr
 * @version 2.0
 */
public class PartialTrapezoid {
    
    /**
     * Constructor
     * @param p1 first point
     * @param p2 second point
     */
    public PartialTrapezoid(Point2D<Double> p1, Point2D<Double> p2)
    {
        beginPoint = new Point2D<>(p1.x, p1.y);
        endPoint = new Point2D<>(p2.x, p2.y);
    }
    
    // Begin point of the function interval
    public Point2D<Double> beginPoint;
    
    // End point of the function interval
    public Point2D<Double> endPoint;
    
    /**
     * @return the area of the trapezoid
     */
    public double calculateArea()
    {
        return (beginPoint.y + endPoint.y) * (endPoint.x - beginPoint.x) / 2.0;
    }
    

}
