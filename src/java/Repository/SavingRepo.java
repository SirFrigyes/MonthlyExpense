/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repository;

import Modell.DBCon;
import Modell.Saving;
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
public class SavingRepo {

    public static String addNewIncome(Saving sav) {
        try {
            EntityManager em = DBCon.getDBCon();

            StoredProcedureQuery spq = em.createStoredProcedureQuery("addNewSaving");

            spq.registerStoredProcedureParameter("savingNameIN", String.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("savingValueIN", Integer.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("isActive", Integer.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("personIDIN", Integer.class, ParameterMode.IN);

            spq.setParameter("savingNameIN", sav.getSavingName());
            spq.setParameter("savingValueIN", sav.getSavingValue());
            spq.setParameter("isActive", sav.getIsActive());
            spq.setParameter("personIDIN", sav.getPersonID());

            spq.execute();

            return "Siker";
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    public static List<Saving> getAllActiveSaving() {
        List<Saving> result = new ArrayList();
        try {
            EntityManager em = DBCon.getDBCon();

            StoredProcedureQuery spq = em.createStoredProcedureQuery("getAllActiveSaving");

            List<Object[]> savings = spq.getResultList();
            for (Object[] saving : savings) {
                Integer id = Integer.parseInt(saving[0].toString());
                Saving sav = em.find(Saving.class, id);
                result.add(sav);
            }
        } catch (Exception ex) {
        } finally {
            return result;
        }
    }

    public static boolean logicalDeleteSavingById(Integer id) {
        try {
            EntityManager em = DBCon.getDBCon();
            StoredProcedureQuery spq = em.createStoredProcedureQuery("logicalDeleteSavingById");
            spq.registerStoredProcedureParameter("idIN", Integer.class, ParameterMode.IN);
            spq.setParameter("idIN", id);

            spq.execute();
            return true;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public static String updateSaving(Saving input) {
        try {
            EntityManager em = DBCon.getDBCon();
            em.getTransaction().begin();
            Saving uj = em.find(Saving.class, input.getSavingID());
            uj.setSavingName(input.getSavingName());
            uj.setSavingValue(input.getSavingValue());
            uj.setIsActive(input.getIsActive());
            uj.setPersonID(input.getPersonID());
            em.getTransaction().commit();
            return "Siker";
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    public static boolean addLeftoverToSaving(Integer id, Integer extra) {
        Saving sav = new Saving();
        try {
            sav = sav.getSavingById(id);
            if (sav != null && extra > 0) {
                sav.setSavingValue(sav.getSavingValue() + extra);
                return true;
            } else {
                return false;
            }
        }
        catch(Exception ex){
            return false;
        }
    }

}
