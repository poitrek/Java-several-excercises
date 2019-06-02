/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.lab.model;

/**
 * A generic type of two dimensional point
 * @author Piotr
 * @version 4.0
 * @param <NumberType> any numeric type
 */
public class Point2D <NumberType extends Number> {
    
    /**
     * Constructor
     * @param _x x-coordinate to set
     * @param _y y-coordinate to set
     */
    public Point2D(NumberType _x, NumberType _y)
    {
        x = _x;
        y = _y;
    }
    
    /**
     * Copying constructor
     */
    public Point2D(Point2D<NumberType> point2d) {
        x = point2d.x;
        y = point2d.y;
    }
    
    // X-coordinate (abscissa)
    public NumberType x;
    
    // Y-coordinate (ordinate)
    public NumberType y;
    
}
