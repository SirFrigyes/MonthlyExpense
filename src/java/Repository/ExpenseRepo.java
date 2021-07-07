/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repository;

import Modell.DBCon;
import Modell.Expense;
import Modell.Person;
import java.time.LocalDate;
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
public class ExpenseRepo {

    public static String addNewExpense(Expense exp) {
        try {
            EntityManager em = DBCon.getDBCon();

            StoredProcedureQuery spq = em.createStoredProcedureQuery("addNewExpense");

            spq.registerStoredProcedureParameter("expenseNameIN", String.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("expenseTypeIN", String.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("expenseValueIN", Integer.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("expenseDateIN", Date.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("isActive", Integer.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("personIDIN", Integer.class, ParameterMode.IN);

            spq.setParameter("expenseNameIN", exp.getExpenseName());
            spq.setParameter("expenseTypeIN", exp.getExpenseType());
            spq.setParameter("expenseValueIN", exp.getExpenseValue());
            spq.setParameter("expenseDateIN", exp.getExpenseDate());
            spq.setParameter("isActive", exp.getIsActive());
            spq.setParameter("personIDIN", exp.getPersonID());

            spq.execute();

            return "Siker";
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    public static List<Expense> getAllActiveExpense() {
        List<Expense> result = new ArrayList();
        try {
            EntityManager em = DBCon.getDBCon();

            StoredProcedureQuery spq = em.createStoredProcedureQuery("getAllActiveExpense");

            List<Object[]> expenses = spq.getResultList();
            for (Object[] expense : expenses) {
                Integer id = Integer.parseInt(expense[0].toString());
                Expense exp = em.find(Expense.class, id);
                result.add(exp);
            }
        } catch (Exception ex) {
        } finally {
            return result;
        }
    }

    public static boolean logicalDeleteExpenseById(Integer id) {
        try {
            EntityManager em = DBCon.getDBCon();
            StoredProcedureQuery spq = em.createStoredProcedureQuery("logicalDeleteExpenseById");
            spq.registerStoredProcedureParameter("idIN", Integer.class, ParameterMode.IN);
            spq.setParameter("idIN", id);

            spq.execute();
            return true;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public static String updateExpense(Expense input) {
        try {
            EntityManager em = DBCon.getDBCon();
            em.getTransaction().begin();
            Expense uj = em.find(Expense.class, input.getExpenseID());
            uj.setExpenseName(input.getExpenseName());
            uj.setExpenseType(input.getExpenseType());
            uj.setExpenseValue(input.getExpenseValue());
            uj.setExpenseDate(input.getExpenseDate());
            uj.setIsActive(input.getIsActive());
            uj.setPersonID(input.getPersonID());
            em.getTransaction().commit();
            return "Siker";
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    public static List<Expense> getAllExpensesByPerson(Integer idIN) {
        List<Expense> result = new ArrayList();
        try {
            EntityManager em = DBCon.getDBCon();

            StoredProcedureQuery spq = em.createStoredProcedureQuery("getAllExpensesByPerson");
            spq.registerStoredProcedureParameter("idIN", Integer.class, ParameterMode.IN);
            spq.setParameter("idIN", idIN);

            List<Object[]> expenses = spq.getResultList();
            for (Object[] expense : expenses) {
                Integer id = Integer.parseInt(expense[0].toString());
                Expense exp = em.find(Expense.class, id);
                result.add(exp);
            }
        } catch (Exception ex) {
        } finally {
            return result;
        }
    }

}
