/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Modell.Expense;
import Modell.Person;
import Repository.ExpenseRepo;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;
import java.util.List;

/**
 *
 * @author cfrig
 */
public class ExpenseService {

    public static String addNewExpense(Expense exp) throws ParseException {

        String hiba = "";

        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        Date minDate = dateformat.parse("1900-01-01");
        Date maxDate = new Date();

        if (exp.getExpenseDate().before(minDate)) {
            hiba = "A minimum dátum 1900.01.01.-re van állítva, ennél későbbit adj meg.";
        }
        if (exp.getExpenseDate().after(maxDate)) {
            hiba = "A jelenlegi dátumnál ne adj meg későbbit, az időutazás még nem lehetséges.";
        }
        if (exp.getExpenseValue() <= 0) {
            hiba = "ExpenseValue 0-nál nem nagyobb!";
        }
        Person p = new Person();
        if (p.getPersonById(exp.getPersonID()) == null) {
            hiba = "Nincs ilyen id-vel rendelkező Person.";
        }

        if (hiba == "") {
            String result = ExpenseRepo.addNewExpense(exp);
            if (result == "Siker") {
                return "Expense sikeresen rögzítve!";
            } else {
                return "Az adatok helyesek, de a rögzítés nem sikerült, " + result;
            }
        } else {
            return hiba;
        }
    }

    public static List<Expense> getAllActiveExpense() {
        return ExpenseRepo.getAllActiveExpense();
    }

    public static String logicalDeleteExpenseById(Integer id) {
        Expense exp = new Expense();
        try {
            exp = exp.getExpenseById(id);
            if (exp != null) {
                if (exp.getIsActive() == 1) {
                    if (ExpenseRepo.logicalDeleteExpenseById(id)) {
                        return "A logikai törlés sikeres volt.";
                    } else {
                        return "Az id helyes, de a logikai törlés nem sikerült.";
                    }
                } else if (exp.getIsActive() == 0) {
                    return "A megadott id-val rendelkező Expense már törölve lett.";
                } else {
                    return "A megadott id-vel rendelkező Expense isActive paramétere rosszul lett megadva.";
                }
            } else {
                return "Nincs ilyen id-vel rendelkező Expense.";
            }
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    public static String updateExpense(Expense exp) throws ParseException {

        try {
            String hiba = "";

            SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
            Date minDate = dateformat.parse("1900-01-01");
            Date maxDate = new Date();

            if (exp.getExpenseDate().before(minDate)) {
                hiba = "A minimum dátum 1900.01.01.-re van állítva, ennél későbbit adj meg.";
            }
            if (exp.getExpenseDate().after(maxDate)) {
                hiba = "A jelenlegi dátumnál ne adj meg későbbit, az időutazás még nem lehetséges.";
            }
            if (exp.getExpenseValue() <= 0) {
                hiba = "ExpenseValue nem lehet 0-nál kisebb vagy vele egyenlő!";
            }
            Person p = new Person();
            if (p.getPersonById(exp.getPersonID()) == null) {
                hiba = "Nincs ilyen id-vel rendelkező Person.";
            }

            if (hiba == "") {
                String message = ExpenseRepo.updateExpense(exp);
                if (message == "Siker") {
                    return "Sikeres update!";
                } else {
                    return "Az adatok megfelelőek, de az update nem sikerült! " + message;
                }
            } else {
                return hiba;
            }
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }
    
    

}
