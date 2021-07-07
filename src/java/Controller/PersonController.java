/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Modell.Person;
import Service.PersonService;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author cfrig
 */
public class PersonController extends HttpServlet {

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
        response.setContentType("application/json;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        try (PrintWriter out = response.getWriter()) {

            //Add
            if (request.getParameter("task").equals("addNewPerson")) {
                JSONObject returnValue = new JSONObject();

                if (!request.getParameter("personLastName").isEmpty()
                        && !request.getParameter("personFirstName").isEmpty()
                        && !request.getParameter("isActive").isEmpty()) {
                    String personLastName = request.getParameter("personLastName");
                    String personFirstName = request.getParameter("personFirstName");
                    Integer isActive = Integer.parseInt(request.getParameter("isActive"));

                    Person p = new Person(0, personLastName, personFirstName, isActive);

                    String result = PersonService.addNewPerson(p);
                    returnValue.put("result", result);
                } else {
                    returnValue.put("result", "A mezők nincsenek megfelelően kitöltve.");
                }
                out.print(returnValue.toString());
            }

            //getAllActive
            if (request.getParameter("task").equals("getAllActivePerson")) {
                JSONArray returnValue = new JSONArray();
                List<Person> people = PersonService.getAllActivePerson();

                if (people.isEmpty()) {
                    JSONObject object = new JSONObject();
                    object.put("Result:", "Nincs active Person");
                    returnValue.put(object);
                    out.print(returnValue.toString());
                } else {
                    for (Person p : people) {
                        returnValue.put(p.toJson());
                    }
                    out.print(returnValue.toString());
                }
            }

            //logicalDelete
            if (request.getParameter("task").equals("logicalDeletePersonById")) {
                JSONObject returnValue = new JSONObject();

                if (!request.getParameter("personID").isEmpty()) {
                    Integer id = Integer.parseInt(request.getParameter("personID").toString());

                    String result = PersonService.logicalDeleteById(id);
                    returnValue.put("result", result);
                } else {
                    returnValue.put("result", "Nem adtál meg id-t!");
                }
                out.print(returnValue.toString());
            }

            //update
            if (request.getParameter("task").equals("updatePerson")) {
                JSONObject returnValue = new JSONObject();

                if (!request.getParameter("personID").isEmpty() && !request.getParameter("personLastName").isEmpty() && !request.getParameter("personFirstName").isEmpty()) {
                    Integer personID = Integer.parseInt(request.getParameter("personID"));
                    String personLastName = request.getParameter("personLastName");
                    String personFirstName = request.getParameter("personFirstName");

                    Person p = new Person(personID, personLastName, personFirstName, 0);
                    String result = PersonService.updatePerson(p);
                    returnValue.put("result", result);
                } else {
                    returnValue.put("result", "Valamelyik mezőt üresen hagytad!");
                }
                out.print(returnValue.toString());
            }

            if (request.getParameter("task").equals("getMonthlyLeftover")) {
                JSONObject returnValue = new JSONObject();
                if (!request.getParameter("personID").isEmpty()
                    && !request.getParameter("date").isEmpty()){
                    Integer personID = Integer.parseInt(request.getParameter("personID"));
                    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = dateformat.parse(request.getParameter("date"));
                    
                    Person p = new Person();
                    p = p.getPersonById(personID);
                    
                    if(p != null){
                        Integer leftover = PersonService.getMonthlyLeftover(p, date);
                        returnValue.put("result", leftover.toString());
                    }
                    else{
                        returnValue.put("result", "Nincs ilyen id-val rendelkező Person");
                    }
                }
                else{
                    returnValue.put("result", "Valamelyik mezőt üresen hagytad!");
                }
                out.print(returnValue.toString());
            }

        } catch (Exception ex) {
            System.out.println("JSON exception van");
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
