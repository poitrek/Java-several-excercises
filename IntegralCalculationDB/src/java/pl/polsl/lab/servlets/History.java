/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.lab.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import pl.polsl.lab.model.IntegralModel;
import pl.polsl.lab.model.IntegralModel.SingleHistory;
import java.util.Vector;
/**
 * Servlet class handling history of calculations
 * 
 * @author Piotr Bienias
 * @version 2.0
 */
public class History extends HttpServlet {
    
    /**
     * Default constructor
     * Calls constructor of parent class and creates the table
     */
    

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
                        
            try {
                // Load the JDBC Driver
                Class.forName("org.apache.derby.jdbc.ClientDriver");
            } catch (ClassNotFoundException cnfe) {
                System.err.println(cnfe.getMessage());
                return;
            }

            // Make a connection to DB
            try (Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/integration", "lab", "pass")) {

                // Print site and table headers
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<h2>History of calculations</h2>");
                out.println("<style>table, th, td {border: 1px solid black;}</style>");
                out.println("</head>");
                out.println("<body>");
                out.println("<table>");
                out.println("<tr>");
                out.println("<th>Id</th>");
                out.println("<th>List of points</th>");
                out.println("<th>Result</th>");
                out.println("</tr>");

                // Select data from the table
                Statement statement = con.createStatement();
                ResultSet rs = statement.executeQuery("SELECT * FROM Calculations");
                
                // Print the resultset into the html table
                while (rs.next()) {
                    out.println("<tr>");
                    out.println("<td>" + rs.getInt("id") + "</td>");
                    out.println("<td>" + rs.getString("points") + "</td>");
                    out.println("<td>" + rs.getFloat("result") + "</td>");
                    out.println("</tr>");
                }
                rs.close();

                out.println("</table>");
                out.println("</body>");
                out.println("</html");

            } catch (SQLException sqle) {
                out.println(sqle.getMessage());
            }
                                                        
        }
    }
    
    /**
     * Creates table of history
     */
    @Deprecated
    private void createTable() {
        
        // Load the JDBC Driver
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            
        }
        catch (ClassNotFoundException cnfe) {
            System.err.println(cnfe.getMessage());
        }
        
        // Make a connection to DB
        try (Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/integration", "lab", "pass")) {
            
            Statement statement = con.createStatement();
            statement.executeUpdate("CREATE TABLE Calculations "
                    + "(id INTEGER, points VARCHAR(100), result FLOAT)");
            
            System.out.println("Table created successfully");            
            
        }
        catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        }
        
    }
    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
