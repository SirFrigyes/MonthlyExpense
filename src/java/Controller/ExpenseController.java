/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Modell.DBCon;
import Modell.Expense;
import Modell.Person;
import Repository.ExpenseRepo;
import Service.ExpenseService;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
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
public class ExpenseController extends HttpServlet {

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
            //addNewExpense
            if (request.getParameter("task").equals("addNewExpense")) {
                JSONObject returnValue = new JSONObject();

                if (!request.getParameter("expenseName").isEmpty()
                        && !request.getParameter("expenseType").isEmpty()
                        && !request.getParameter("expenseValue").isEmpty()
                        && !request.getParameter("expenseDate").isEmpty()
                        && !request.getParameter("isActive").isEmpty()
                        && !request.getParameter("personID").isEmpty()) {
                    try {
                        String expenseName = request.getParameter("expenseName");
                        String expenseType = request.getParameter("expenseType");
                        Integer expenseValue = Integer.parseInt(request.getParameter("expenseValue"));

                        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
                        Date expenseDate = dateformat.parse(request.getParameter("expenseDate"));

                        Integer isActive = Integer.parseInt(request.getParameter("isActive"));
                        Integer personID = Integer.parseInt(request.getParameter("personID"));

                        Expense exp = new Expense(0, expenseName, expenseType, expenseValue, expenseDate, isActive, personID);

                        returnValue.put("result", ExpenseService.addNewExpense(exp));
                    } catch (Exception ex) {
                        returnValue.put("result", ex.getMessage());
                    }
                } else {
                    returnValue.put("result", "Valamelyik mező nincs kitöltve");
                }
                out.print(returnValue.toString());
            }

            if (request.getParameter("task").equals("getAllActiveExpense")) {
                JSONArray returnValue = new JSONArray();
                List<Expense> expenses = ExpenseService.getAllActiveExpense();

                if (expenses.isEmpty()) {
                    JSONObject object = new JSONObject();
                    object.put("Result:", "Nincs active Expense");
                    returnValue.put(object);
                    out.print(returnValue.toString());
                } else {
                    for (Expense exp : expenses) {
                        returnValue.put(exp.toJson());
                    }
                    out.print(returnValue.toString());
                }
            }

            if (request.getParameter("task").equals("logicalDeleteExpenseById")) {
                JSONObject returnValue = new JSONObject();

                if (!request.getParameter("expenseID").isEmpty()) {
                    Integer id = Integer.parseInt(request.getParameter("expenseID").toString());

                    String result = ExpenseService.logicalDeleteExpenseById(id);
                    returnValue.put("result", result);
                } else {
                    returnValue.put("result", "Nem adtál meg id-t!");
                }
                out.print(returnValue.toString());
            }

            if (request.getParameter("task").equals("updateExpense")) {
                JSONObject returnValue = new JSONObject();

                if (!request.getParameter("expenseID").isEmpty()
                        && !request.getParameter("expenseName").isEmpty()
                        && !request.getParameter("expenseType").isEmpty()
                        && !request.getParameter("expenseValue").isEmpty()
                        && !request.getParameter("expenseDate").isEmpty()
                        && !request.getParameter("isActive").isEmpty()
                        && !request.getParameter("personID").isEmpty()) {

                    Integer expenseID = Integer.parseInt(request.getParameter("expenseID"));
                    String expenseName = request.getParameter("expenseName");
                    String expenseType = request.getParameter("expenseType");
                    Integer expenseValue = Integer.parseInt(request.getParameter("expenseValue"));

                    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
                    Date expenseDate = dateformat.parse(request.getParameter("expenseDate"));

                    Integer isActive = Integer.parseInt(request.getParameter("isActive"));
                    Integer personID = Integer.parseInt(request.getParameter("personID"));

                    Expense exp = new Expense(expenseID, expenseName, expenseType, expenseValue, expenseDate, isActive, personID);
                    String result = ExpenseService.updateExpense(exp);
                    returnValue.put("result", result);
                } else {
                    returnValue.put("result", "Valamelyik mezőt üresen hagytad!");
                }
                out.print(returnValue.toString());
            }

            if (request.getParameter("task").equals("getAllExpensesByPerson")) {
                JSONArray returnValue = new JSONArray();
                if (!request.getParameter("personID").isEmpty()) {
                    Integer personID = Integer.parseInt(request.getParameter("personID"));
                    Person p = new Person();
                    if (p.getPersonById(personID) != null) {
                        List<Expense> expenses = ExpenseRepo.getAllExpensesByPerson(personID);
                        if (expenses.isEmpty()) {
                            JSONObject object = new JSONObject();
                            object.put("Result:", "Nincs active Expense");
                            returnValue.put(object);
                            out.print(returnValue.toString());
                        } else {
                            for (Expense exp : expenses) {
                                returnValue.put(exp.toJson());
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
            Logger.getLogger(ExpenseController.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ExpenseController.class.getName()).log(Level.SEVERE, null, ex);
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
