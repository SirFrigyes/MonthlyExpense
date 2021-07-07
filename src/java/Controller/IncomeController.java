/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Modell.Income;
import Modell.Person;
import Repository.IncomeRepo;
import Service.IncomeService;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class IncomeController extends HttpServlet {

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
            throws ServletException, IOException, ParseException {
        response.setContentType("application/json;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {

            if (request.getParameter("task").equals("addNewIncome")) {
                JSONObject returnValue = new JSONObject();

                if (!request.getParameter("incomeName").isEmpty()
                        && !request.getParameter("incomeType").isEmpty()
                        && !request.getParameter("incomeValue").isEmpty()
                        && !request.getParameter("incomeDate").isEmpty()
                        && !request.getParameter("isActive").isEmpty()
                        && !request.getParameter("personID").isEmpty()) {
                    try {
                        String incomeName = request.getParameter("incomeName");
                        String incomeType = request.getParameter("incomeType");
                        Integer incomeValue = Integer.parseInt(request.getParameter("incomeValue"));

                        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
                        Date incomeDate = dateformat.parse(request.getParameter("incomeDate"));

                        Integer isActive = Integer.parseInt(request.getParameter("isActive"));
                        Integer personID = Integer.parseInt(request.getParameter("personID"));

                        Income exp = new Income(0, incomeName, incomeType, incomeValue, incomeDate, isActive, personID);

                        returnValue.put("result", IncomeService.addNewIncome(exp));
                    } catch (Exception ex) {
                        returnValue.put("result", ex.getMessage());
                    }
                } else {
                    returnValue.put("result", "Valamelyik mező nincs kitöltve");
                }
                out.print(returnValue.toString());
            }

            if (request.getParameter("task").equals("getAllActiveIncome")) {
                JSONArray returnValue = new JSONArray();
                List<Income> incomes = IncomeService.getAllActiveIncome();

                if (incomes.isEmpty()) {
                    JSONObject object = new JSONObject();
                    object.put("Result:", "Nincs active Income");
                    returnValue.put(object);
                    out.print(returnValue.toString());
                } else {
                    for (Income inc : incomes) {
                        returnValue.put(inc.toJson());
                    }
                    out.print(returnValue.toString());
                }
            }
            
            if (request.getParameter("task").equals("logicalDeleteIncomeById")) {
                JSONObject returnValue = new JSONObject();

                if (!request.getParameter("incomeID").isEmpty()) {
                    Integer id = Integer.parseInt(request.getParameter("incomeID").toString());

                    String result = IncomeService.logicalDeleteIncomeById(id);
                    returnValue.put("result", result);
                } else {
                    returnValue.put("result", "Nem adtál meg id-t!");
                }
                out.print(returnValue.toString());
            }
            
            if (request.getParameter("task").equals("updateIncome")) {
                JSONObject returnValue = new JSONObject();

                if (!request.getParameter("incomeID").isEmpty() && 
                    !request.getParameter("incomeName").isEmpty() && 
                    !request.getParameter("incomeType").isEmpty() &&
                    !request.getParameter("incomeValue").isEmpty() &&
                    !request.getParameter("incomeDate").isEmpty() &&
                    !request.getParameter("isActive").isEmpty() &&
                    !request.getParameter("personID").isEmpty()) {
                    
                    Integer incomeID = Integer.parseInt(request.getParameter("incomeID"));
                    String incomeName = request.getParameter("incomeName");
                    String incomeType = request.getParameter("incomeType");
                    Integer incomeValue = Integer.parseInt(request.getParameter("incomeValue"));

                    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
                    Date incomeDate = dateformat.parse(request.getParameter("incomeDate"));

                    Integer isActive = Integer.parseInt(request.getParameter("isActive"));
                    Integer personID = Integer.parseInt(request.getParameter("personID"));

                    Income inc = new Income(incomeID, incomeName, incomeType, incomeValue, incomeDate, isActive, personID);
                    String result = IncomeService.updateIncome(inc);
                    returnValue.put("result", result);
                } else {
                    returnValue.put("result", "Valamelyik mezőt üresen hagytad!");
                }
                out.print(returnValue.toString());
            }
            
            if (request.getParameter("task").equals("getAllIncomesByPerson")) {
                JSONArray returnValue = new JSONArray();
                if (!request.getParameter("personID").isEmpty()) {
                    Integer personID = Integer.parseInt(request.getParameter("personID"));
                    Person p = new Person();
                    if (p.getPersonById(personID) != null) {
                        List<Income> incomes = IncomeRepo.getAllIncomesByPerson(personID);
                        if (incomes.isEmpty()) {
                            JSONObject object = new JSONObject();
                            object.put("Result:", "Nincs active Income");
                            returnValue.put(object);
                            out.print(returnValue.toString());
                        } else {
                            for (Income inc : incomes) {
                                returnValue.put(inc.toJson());
                            }
                            out.print(returnValue.toString());
                        }
                    } else {
                        JSONObject object = new JSONObject();
                        object.put("Result:", "Nincs ilyen id-vel rendelkező person");
                        returnValue.put(object);
                        out.print(returnValue.toString());
                    }
                } else {
                    JSONObject object = new JSONObject();
                    object.put("Result:", "Nem adtál meg id-t");
                    returnValue.put(object);
                    out.print(returnValue.toString());
                }
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
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(IncomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(IncomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
