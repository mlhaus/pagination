/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hauschildt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author k0519415
 */
@WebServlet(name = "PaginationServlet", urlPatterns = {"/directory"})
public class PaginationServlet extends HttpServlet {
    private static final String FILE_PATH = "WEB-INF/assets/";
    private static final String FILE_NAME = "people.csv";
    private static SortedSet<Person> people;
    
    private void readFromFile(HttpServletRequest request, HttpServletResponse response) throws ParserConfigurationException, MalformedURLException, IOException, SAXException {
        if(people == null) {
            try(Scanner in = new Scanner(new File(getServletContext().getRealPath(FILE_PATH + FILE_NAME)))){
                people = new TreeSet<>();
                int lineCount = 0;
                String line = in.nextLine();
                String[] fields;
                String firstName;
                String lastName;
                String picture;
                while(in.hasNextLine()){
                    lineCount++;
                    line = in.nextLine();
                    fields = line.split(",");
                    firstName = fields[1];
                    lastName = fields[2];
                    picture = fields[3];
                    people.add(new Person(firstName, lastName, picture));
                }
            } catch(FileNotFoundException fnfe){
                response.setContentType("text/html;charset=UTF-8");
                try (PrintWriter out = response.getWriter()) {
                    out.println("<!DOCTYPE html>");
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>Data error</title>");            
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<h1>Error reading data</h1>");
                    out.println("</body>");
                    out.println("</html>");
                }
                return;
            }
        }
    }

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
        try {
            readFromFile(request, response);
        } catch (ParserConfigurationException | IOException | SAXException ex) {
            return;
        } 
        int page = 1;
        int peoplePerPage = 12;
        int begin = 0;
        int end = 0;
        int maxPages = people.size() / peoplePerPage;
        if(people.size() % peoplePerPage != 0) {
            maxPages++;
        }
        String pageStr = request.getParameter("page");
        if(pageStr != null && !pageStr.equals("")) {
            try {
                page = Integer.parseInt(pageStr);
                if(page < 1){
                    page = 1;
                } else if(page > maxPages) {
                    page = maxPages;
                }
            } catch(NumberFormatException e) {
                page = 1;
            }
        }
        begin = (page - 1) * peoplePerPage;
        end = begin + peoplePerPage - 1;
        request.setAttribute("begin", begin);
        request.setAttribute("end", end);
        request.setAttribute("maxPages", maxPages);
        request.setAttribute("currentPage", page);
        
        request.setAttribute("people", people);
        request.getRequestDispatcher("/WEB-INF/jsp/view/directory.jsp").forward(request, response);
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
