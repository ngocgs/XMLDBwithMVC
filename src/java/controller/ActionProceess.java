/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import model.Dao;
import model.ProcessingXML;
import nu.xom.ParsingException;
import nu.xom.ValidityException;

/**
 *
 * @author nguye
 */
public class ActionProceess extends HttpServlet {

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
        String action = request.getParameter("act");
        String error = "";
        HttpSession session = request.getSession();
        String url = getServletContext().getRealPath("/");

        if (action.equals("getdata")) {
            System.out.println("===== getdata =====");
            Dao dao = new Dao();
            try {
                dao.writeXML(url);
                request.setAttribute("info", "creating file is ok!");
                session.setAttribute("wrote", "ok");
                getServletContext().getRequestDispatcher("/Products.xml").forward(request, response);
            } catch (SQLException e) {
                error = e.getMessage();
            } catch (ParserConfigurationException ex) {
                error = ex.getMessage();
            } catch (TransformerException ex) {
                error = ex.getMessage();
            } catch (NumberFormatException ex) {
                error = ex.getMessage();
            }
        } else if (action.equals("showdata")) {
            System.out.println("===== showdata =====");
            if (session.getAttribute("wrote") != null) {
                ProcessingXML xulyXML = new ProcessingXML();
                try {
                    ArrayList al = xulyXML.readXML(url);
                    request.setAttribute("products", al);
                    getServletContext().getRequestDispatcher("/product.jsp").forward(request, response);
                } catch (ParsingException ex) {
                    ex.getMessage();
                } catch (NumberFormatException ex) {
                    error = ex.getMessage();
                }
            } else {
                request.setAttribute("info", "you should \"get data\" first !");
                getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
            }

        } else if (action.equals("filtedata")) {
            System.out.println("===== filtedata =====");
            if (session.getAttribute("wrote") != null) {
                String filter = request.getParameter("filter");

                try {
                    int IntFilter = Integer.parseInt(filter);
                    System.out.println( "size :" + IntFilter);

                } catch (NumberFormatException numberFormatException) {
                    request.setAttribute("info", "you should enter numberic forat for value to find data!");
                    getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
                }

                System.out.println("next");
                ProcessingXML xulyXML = new ProcessingXML();
                try {
                    ArrayList al = xulyXML.filtedContent(url, filter);
                    System.out.println( "size" + al.size());
                    
                    request.setAttribute("products", al);
                    getServletContext().getRequestDispatcher("/product.jsp").forward(request, response);
                } catch (ValidityException ex) {
                    System.out.println(ex.getMessage());
                } catch (ParsingException ex) {
                    System.out.println(ex.getMessage());
                }
            } else {
                request.setAttribute("info", "you should \"get data\" first !");
                getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
            }
        }
        if (!"".equals(error)) {
            request.setAttribute("error", error);
            getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
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
