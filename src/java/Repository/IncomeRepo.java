/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repository;

import Modell.DBCon;
import Modell.Income;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

/**
 *
 * @author cfrig
 */
public class IncomeRepo {
    
    public static String addNewIncome(Income inc){
        try{
            EntityManager em = DBCon.getDBCon();
            
            StoredProcedureQuery spq = em.createStoredProcedureQuery("addNewIncome");
            
            spq.registerStoredProcedureParameter("incomeNameIN", String.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("incomeTypeIN", String.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("incomeValueIN", Integer.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("incomeDateIN", Date.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("isActive", Integer.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("personIDIN", Integer.class, ParameterMode.IN);
            
            spq.setParameter("incomeNameIN", inc.getIncomeName());
            spq.setParameter("incomeTypeIN", inc.getIncomeType());
            spq.setParameter("incomeValueIN", inc.getIncomeValue());
            spq.setParameter("incomeDateIN", inc.getIncomeDate());
            spq.setParameter("isActive", inc.getIsActive());
            spq.setParameter("personIDIN", inc.getPersonID());
            
            spq.execute();
            
            return "Siker";
        }
        catch(Exception ex){
            return ex.getMessage();
        }
    }
    
    public static List<Income> getAllActiveIncome(){
        List<Income> result = new ArrayList();
        try{
            EntityManager em = DBCon.getDBCon();
            
            StoredProcedureQuery spq = em.createStoredProcedureQuery("getAllActiveIncome");
            
            List<Object[]> incomes = spq.getResultList();
            for(Object[] income : incomes){
                Integer id = Integer.parseInt(income[0].toString());
                Income inc = em.find(Income.class, id);
                result.add(inc);
            }
        }
        catch(Exception ex){
        }
        finally{
            return result;
        }
    }
    
    public static boolean logicalDeleteIncomeById(Integer id){
        try{
            EntityManager em = DBCon.getDBCon();
            StoredProcedureQuery spq = em.createStoredProcedureQuery("logicalDeleteIncomeById");
            spq.registerStoredProcedureParameter("idIN", Integer.class, ParameterMode.IN);
            spq.setParameter("idIN", id);
            
            spq.execute();
            return true;
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }
    
    public static String updateIncome(Income input) {
        try {
            EntityManager em = DBCon.getDBCon();
            em.getTransaction().begin();
            Income uj = em.find(Income.class, input.getIncomeID());
            uj.setIncomeName(input.getIncomeName());
            uj.setIncomeType(input.getIncomeType());
            uj.setIncomeValue(input.getIncomeValue());
            uj.setIncomeDate(input.getIncomeDate());
            uj.setIsActive(input.getIsActive());
            uj.setPersonID(input.getPersonID());
            em.getTransaction().commit();
            return "Siker";
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }
    
    public static List<Income> getAllIncomesByPerson(Integer idIN) {
        List<Income> result = new ArrayList();
        try {
            EntityManager em = DBCon.getDBCon();

            StoredProcedureQuery spq = em.createStoredProcedureQuery("getAllIncomesByPerson");
            spq.registerStoredProcedureParameter("idIN", Integer.class, ParameterMode.IN);
            spq.setParameter("idIN", idIN);

            List<Object[]> incomes = spq.getResultList();
            for (Object[] income : incomes) {
                Integer id = Integer.parseInt(income[0].toString());
                Income inc = em.find(Income.class, id);
                result.add(inc);
            }
        } catch (Exception ex) {
        } finally {
            return result;
        }
    }
    
}
