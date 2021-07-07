/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modell;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author cfrig
 */
public class DBCon {
    
    public static EntityManager getDBCon(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MonthlyExpensePU");
        EntityManager em = emf.createEntityManager();
        return em;
    }
    
}
