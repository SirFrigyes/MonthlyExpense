/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Modell.Saving;
import Service.SavingService;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
public class SavingController extends HttpServlet {

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

            if (request.getParameter("task").equals("addNewSaving")) {
                JSONObject returnValue = new JSONObject();

                if (!request.getParameter("savingName").isEmpty()
                    && !request.getParameter("savingValue").isEmpty()
                    && !request.getParameter("isActive").isEmpty()
                    && !request.getParameter("personID").isEmpty()) {
                    try {
                        String savingName = request.getParameter("savingName");
                        Integer savingValue = Integer.parseInt(request.getParameter("savingValue"));
                        Integer isActive = Integer.parseInt(request.getParameter("isActive"));
                        Integer personID = Integer.parseInt(request.getParameter("personID"));

                        Saving sav = new Saving(0, savingName, savingValue, isActive, personID);

                        returnValue.put("result", SavingService.addNewSaving(sav));
                    } catch (Exception ex) {
                        returnValue.put("result", ex.getMessage());
                    }
                } else {
                    returnValue.put("result", "Valamelyik mező nincs kitöltve");
                }
                out.print(returnValue.toString());
            }
            
            if (request.getParameter("task").equals("getAllActiveSaving")) {
                JSONArray returnValue = new JSONArray();
                List<Saving> savings = SavingService.getAllActiveSaving();

                if (savings.isEmpty()) {
                    JSONObject object = new JSONObject();
                    object.put("Result:", "Nincs active Saving");
                    returnValue.put(object);
                    out.print(returnValue.toString());
                } else {
                    for (Saving sav : savings) {
                        returnValue.put(sav.toJson());
                    }
                    out.print(returnValue.toString());
                }
            }
            
            if (request.getParameter("task").equals("logicalDeleteSavingById")) {
                JSONObject returnValue = new JSONObject();

                if (!request.getParameter("savingID").isEmpty()) {
                    Integer id = Integer.parseInt(request.getParameter("savingID").toString());

                    String result = SavingService.logicalDeleteSavingById(id);
                    returnValue.put("result", result);
                } else {
                    returnValue.put("result", "Nem adtál meg id-t!");
                }
                out.print(returnValue.toString());
            }
            
            if (request.getParameter("task").equals("updateSaving")) {
                JSONObject returnValue = new JSONObject();

                if (!request.getParameter("savingID").isEmpty() && 
                    !request.getParameter("savingName").isEmpty() && 
                    !request.getParameter("savingValue").isEmpty() &&
                    !request.getParameter("isActive").isEmpty() &&
                    !request.getParameter("personID").isEmpty()) {
                    
                    Integer savingID = Integer.parseInt(request.getParameter("savingID"));
                    String savingName = request.getParameter("savingName");
                    Integer savingValue = Integer.parseInt(request.getParameter("savingValue"));
                    Integer isActive = Integer.parseInt(request.getParameter("isActive"));
                    Integer personID = Integer.parseInt(request.getParameter("personID"));

                    Saving sav = new Saving(savingID, savingName, savingValue, isActive, personID);
                    String result = SavingService.updateSaving(sav);
                    returnValue.put("result", result);
                } else {
                    returnValue.put("result", "Valamelyik mezőt üresen hagytad!");
                }
                out.print(returnValue.toString());
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
            Logger.getLogger(SavingController.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(SavingController.class.getName()).log(Level.SEVERE, null, ex);
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
