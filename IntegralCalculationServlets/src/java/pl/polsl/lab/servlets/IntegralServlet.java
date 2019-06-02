/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.lab.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import pl.polsl.lab.exceptions.PointArrayException;
import pl.polsl.lab.model.IntegralModel;

/**
 * Main servlet class of the application 
 * 
 * @author Piotr Bienias
 * @version 1.0
 */
@WebServlet(name = "IntegralServlet", urlPatterns = {"/IntegralServlet"})
public class IntegralServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
                        
            HttpSession session = request.getSession(true);
            IntegralModel model = (IntegralModel)session.getAttribute("model");

            if (model == null) {
                // Create model and set to session attributes
                model = new IntegralModel();
                session.setAttribute("model", model);
            }

            try {
                // Get arrays of nodes and values as strings
                String nodesString = request.getParameter("nodes");
                String valuesString = request.getParameter("values");

                // Split both strings by spaces to arrays
                String [] nodesStringSplit = nodesString.split("\\s");
                String [] valuesStringSplit = valuesString.split("\\s");
                nodesStringSplit = nodesString.trim().split("\\s+");
                valuesStringSplit = valuesString.trim().split("\\s+");

                if (nodesStringSplit.length != valuesStringSplit.length) {
                    throw new PointArrayException("Nodes and values aren't the same size!");
                }

                // Parse arrays to double and set to model
                Vector<Double> nodes = new Vector<>();
                Vector<Double> values = new Vector<>();

                for (int i = 0; i < nodesStringSplit.length; i++) {
                    nodes.add(Double.parseDouble(nodesStringSplit[i]));
                    values.add(Double.parseDouble(valuesStringSplit[i]));
                }

                model.setPoints(nodes, values);

                model.calculateIntegral();
                
                model.updateHistory();

                out.println("Calculated integral is equal to " + model.getResult());
                
                response.addCookie(new Cookie("lastCalculation", String.valueOf(model.getResult())));
                response.addCookie(new Cookie("lastCalculationTime", new java.util.Date().toString()));

            }
            catch (PointArrayException  e) {
                out.println(e.getMessage());
                //response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Error");
            }
            catch (NumberFormatException e) {
                out.println("Wrong input data. Type an array of double values using  a ' ' as a separator.");
            }
        
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
