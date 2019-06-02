/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelTest;

import java.util.Vector;
import org.junit.*;
import static org.junit.Assert.*;
import pl.polsl.lab.model.*;
import pl.polsl.lab.exceptions.PointArrayException;

/**
 * Test class of program's model. Tests methods setPoints(), and CalculateInegral()
 * 
 * @author Piotr Bienias
 * @version 2.0
 */
public class ModelTest {
    
    IntegralModel model;
    
    // Precision for asserting equality between double values
    double delta = 0.01;
    
    @Before
    public void setup()
    {
        model = new IntegralModel();
    }
    
    
    @Test
    public void testSetNodesAndValues()
    {
        try {
            Vector<Double> nullNodeVector = null;
            Vector<Double> valueVector = new Vector<>();
            valueVector.add(new Double(1.0));
            
            model.setPoints(nullNodeVector, valueVector);
            fail("A PointArrayException should be thrown when the vector of nodes is null");
            
            
        }
        catch(PointArrayException ex)
        {
            System.err.println(ex);
        }
        
        try {
            model = new IntegralModel();
            
            Vector<Double> nodeVector = new Vector<>();
            nodeVector.add(new Double(1.0));
            Vector<Double> nullValueVector = null;
            
            model.setPoints(nodeVector, nullValueVector);
            fail("A PointArrayException should be thrown when the vector of values is null");
                        
        }
        catch(PointArrayException ex)
        {
            System.err.println(ex);
        }
        
        try {
            model = new IntegralModel();
            
            Vector<Double> nodeVector = new Vector<>();
            Vector<Double> valueVector = new Vector();            
            nodeVector.add(0.0);
            nodeVector.add(1.0);
            nodeVector.add(2.0);
            
            valueVector.add(-1.5);
            valueVector.add(3.0);
            valueVector.add(1.0);
            valueVector.add(5.0);            
            
            model.setPoints(nodeVector, valueVector);
            fail("A PointArrayException should be thrown when vectors "
                    + "of nodes and values aren't same size");
                        
        }
        catch(PointArrayException ex)
        {
            System.err.println(ex);
        }
        
        try {
            model = new IntegralModel();
            
            Vector<Double> emptyNodeVector = new Vector<>();
            Vector<Double> emptyValueVector = new Vector<>();
            
            model.setPoints(emptyNodeVector, emptyValueVector);
            assertTrue("Trapezoid vector is empty", model.getTrapezoids().isEmpty());
            
            
            model = new IntegralModel();
            
            Vector<Double> oneNodeVector = new Vector<>();
            Vector<Double> oneValueVector = new Vector<>();

            oneNodeVector.add(10.0);
            oneValueVector.add(5.0);
            
            model.setPoints(oneNodeVector, oneValueVector);
            assertTrue("Trapezoid vector is empty", model.getTrapezoids().isEmpty());
            
            
            model = new IntegralModel();
            
            Vector<Double> sampleValueVector = new Vector<>();
            Vector<Double> wrongOrderNodeVector = new Vector();
            
            sampleValueVector.add(4.0);
            sampleValueVector.add(9.0);
            sampleValueVector.add(2.0);
            sampleValueVector.add(-5.0);
            sampleValueVector.add(1.0);
            
            wrongOrderNodeVector.add(1.0);
            wrongOrderNodeVector.add(2.0);
            wrongOrderNodeVector.add(4.0);
            wrongOrderNodeVector.add(5.0);
            wrongOrderNodeVector.add(3.0);
                        
            model.setPoints(wrongOrderNodeVector, sampleValueVector);            
            fail("An exception should be thrown when points are being set in non-ascending order");
        }
        catch(PointArrayException ex)
        {
            System.err.println(ex);
        }
        
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
            
            model = new IntegralModel();

            Vector<Point2D<Double>> emptyPointVector = new Vector<>();
            
            model.setPoints(emptyPointVector);
            assertTrue("Trapezoid vector is empty", model.getTrapezoids().isEmpty());
            
            
            model = new IntegralModel();
            
            Vector<Point2D<Double>> onePointVector = new Vector<>();            
            onePointVector.add(new Point2D<> (10.0, 7.5));
            
            model.setPoints(onePointVector);
            assertTrue("Trapezoid vector is empty", model.getTrapezoids().isEmpty());
            
            
            model = new IntegralModel();
            
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
            model = new IntegralModel();

            Vector<Point2D<Double>> emptyVector = new Vector<>();
            model.setPoints(emptyVector);
            
            model.calculateIntegral();
            
            assertEquals(0.0, model.getResult(), delta);
            
            model = new IntegralModel();
            
            Vector<Point2D<Double>> onePointVector = new Vector<>();
            onePointVector.add(new Point2D<>(0.0, 5.0));
            
            model.setPoints(onePointVector);
            
            model.calculateIntegral();
            
            assertEquals(0.0, model.getResult(), delta);
            
            model = new IntegralModel();
            
            Vector<Point2D<Double>> twoPointsVector = new Vector<>();
            twoPointsVector.add(new Point2D<>(2.0, 1.0));
            twoPointsVector.add(new Point2D<>(4.0, 3.0));
            
            model.setPoints(twoPointsVector);
            model.calculateIntegral();
            
            assertEquals(4.0, model.getResult(), delta);
            
            model = new IntegralModel();
            
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
