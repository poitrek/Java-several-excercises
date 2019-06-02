/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelTest;

import java.util.Vector;
import org.junit.*;
import static org.junit.Assert.*;
import model.Model;
import classes.*;
import exceptions.PointArrayException;

/**
 * Test class of program's model. Tests methods setPoints(), and CalculateInegral()
 * 
 * @author Piotr Bienias
 * @version 2.0
 */
public class ModelTest {
    
    Model model;
    
    // Precision for asserting equality between double values
    double delta = 0.01;
    
    @Before
    public void setup()
    {
        model = new Model();
    }
    
    @Test
    public void testSetPoints()
    {
                
        try{            
            
            Vector<Point2D<Double>> nullPointVector = null;
            
            model.setPoints(nullPointVector);
            fail("A PointArrayException should be thrown when the vector of points is null");
            
        } 
        catch(PointArrayException ex)
        {
            System.err.println(ex);
        }
        
        try{
            
            model = new Model();

            Vector<Point2D<Double>> emptyPointVector = new Vector<>();
            
            model.setPoints(emptyPointVector);
            assertTrue("Trapezoid vector is empty", model.getTrapezoids().isEmpty());
            
            
            model = new Model();
            
            Vector<Point2D<Double>> onePointVector = new Vector<>();            
            onePointVector.add(new Point2D<> (10.0, 7.5));
            
            model.setPoints(onePointVector);
            assertTrue("Trapezoid vector is empty", model.getTrapezoids().isEmpty());
            
            
            model = new Model();
            
            Vector<Point2D<Double>> wrongOrderVector = new Vector<>();            
            wrongOrderVector.add(new Point2D<>(0.0, 7.0));
            wrongOrderVector.add(new Point2D<>(1.0, 11.0));
            wrongOrderVector.add(new Point2D<>(4.0, -5.5));
            wrongOrderVector.add(new Point2D<>(2.0, -3.2));
            wrongOrderVector.add(new Point2D<>(3.0, 3.14));
            wrongOrderVector.add(new Point2D<>(6.0, 20.5));
            
            model.setPoints(wrongOrderVector);            
            fail("An exception should be thrown when points are being set in non-ascending order");
                        
        }
        catch(PointArrayException ex)
        {
            System.err.println(ex);
        }
        
    }
    
    
    
    @Test
    public void testCalculateIntegral()
    {
        try{
            model = new Model();

            Vector<Point2D<Double>> emptyVector = new Vector<>();
            model.setPoints(emptyVector);
            
            model.calculateIntegral();
            
            assertEquals(0.0, model.getResult(), delta);
            
            
            model = new Model();
            
            Vector<Point2D<Double>> twoPointsVector = new Vector<>();
            twoPointsVector.add(new Point2D<>(2.0, 1.0));
            twoPointsVector.add(new Point2D<>(4.0, 3.0));
            
            model.setPoints(twoPointsVector);
            model.calculateIntegral();
            
            assertEquals(4.0, model.getResult(), delta);
            
            model = new Model();
            
            Vector<Point2D<Double>> sampleVector = new Vector<>();
            sampleVector.add(new Point2D<>(0.0, 2.0));
            sampleVector.add(new Point2D<>(0.5, 1.0));
            sampleVector.add(new Point2D<>(1.0, -0.5));
            sampleVector.add(new Point2D<>(1.5, -2.5));
            sampleVector.add(new Point2D<>(2.0, -4.25));
            sampleVector.add(new Point2D<>(2.5, -5.0));
            sampleVector.add(new Point2D<>(2.8, -5.45));
                                    
            model.setPoints(sampleVector);
            model.calculateIntegral();
            
            assertEquals(-5.44, model.getResult(), delta);
                                  
        }
        catch(PointArrayException ex)
        {
            System.err.print(ex);
        }
    }
    
}
