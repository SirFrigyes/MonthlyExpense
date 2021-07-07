/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repository;

import Modell.DBCon;
import Modell.Person;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

/**
 *
 * @author cfrig
 */
public class PersonRepo {

    public static boolean addNewPerson(Person p) {
        try {
            EntityManager em = DBCon.getDBCon();

            StoredProcedureQuery spq = em.createStoredProcedureQuery("addNewPerson");

            spq.registerStoredProcedureParameter("personLastNameIN", String.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("personFirstNameIN", String.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("isActiveIN", Integer.class, ParameterMode.IN);

            spq.setParameter("personLastNameIN", p.getPersonLastName());
            spq.setParameter("personFirstNameIN", p.getPersonFirstName());
            spq.setParameter("isActiveIN", p.getIsActive());

            spq.execute();
            return true;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public static List<Person> getAllActivePerson() {
        List<Person> result = new ArrayList();
        try {
            EntityManager em = DBCon.getDBCon();

            StoredProcedureQuery spq = em.createStoredProcedureQuery("getAllActivePerson");

            List<Object[]> people = spq.getResultList();
            for (Object[] person : people) {
                int id = Integer.parseInt(person[0].toString());
                Person p = em.find(Person.class, id);
                result.add(p);
            }
        } catch (Exception ex) {
        } finally {
            return result;
        }
    }

    public static boolean logicalDeletePersonById(Integer id) {
        try {
            EntityManager em = DBCon.getDBCon();

            StoredProcedureQuery spq = em.createStoredProcedureQuery("logicalDeletePersonById");
            spq.registerStoredProcedureParameter("idIN", Integer.class, ParameterMode.IN);
            spq.setParameter("idIN", id);

            spq.execute();
            return true;

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public static boolean updatePerson(Person input) {
        try {
            EntityManager em = DBCon.getDBCon();
            em.getTransaction().begin();
            Person uj = em.find(Person.class, input.getPersonID());
            uj.setPersonLastName(input.getPersonLastName());
            uj.setPersonFirstName(input.getPersonFirstName());
            em.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
    

}
