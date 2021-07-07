/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Modell.Income;
import Modell.Person;
import Repository.IncomeRepo;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author cfrig
 */
public class IncomeService {

    public static String addNewIncome(Income inc) throws ParseException {

        String hiba = "";

        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        Date minDate = dateformat.parse("1900-01-01");
        Date maxDate = new Date();

        if (inc.getIncomeDate().before(minDate)) {
            hiba = "A minimum dátum 1900.01.01.-re van állítva, ennél későbbit adj meg.";
        }
        if (inc.getIncomeDate().after(maxDate)) {
            hiba = "A jelenlegi dátumnál ne adj meg későbbit, az időutazás még nem lehetséges.";
        }
        if (inc.getIncomeValue() <= 0) {
            hiba = "IncomeValue nullánál nem nagyobb!";
        }
        Person p = new Person();
        if (p.getPersonById(inc.getPersonID()) == null) {
            hiba = "Nincs ilyen id-vel rendelkező Person.";
        }

        if (hiba == "") {
            String result = IncomeRepo.addNewIncome(inc);
            if (result == "Siker") {
                return "Income sikeresen rögzítve!";
            } else {
                return "Az adatok helyesek, de a rögzítés nem sikerült, " + result;
            }
        } else {
            return hiba;
        }
    }

    public static List<Income> getAllActiveIncome() {
        return IncomeRepo.getAllActiveIncome();
    }

    public static String logicalDeleteIncomeById(Integer id) {
        Income inc = new Income();
        try {
            inc = inc.getIncomeById(id);
            if (inc != null) {
                if (inc.getIsActive() == 1) {
                    if (IncomeRepo.logicalDeleteIncomeById(id)) {
                        return "A logikai törlés sikeres volt.";
                    } else {
                        return "Az id helyes, de a logikai törlés nem sikerült.";
                    }
                } else if (inc.getIsActive() == 0) {
                    return "A megadott id-val rendelkező Income már törölve lett.";
                } else {
                    return "A megadott id-vel rendelkező Income isActive paramétere rosszul lett megadva.";
                }
            } else {
                return "Nincs ilyen id-vel rendelkező Income.";
            }
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    public static String updateIncome(Income inc) throws ParseException {

        try {
            String hiba = "";

            SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
            Date minDate = dateformat.parse("1900-01-01");
            Date maxDate = new Date();

            if (inc.getIncomeDate().before(minDate)) {
                hiba = "A minimum dátum 1900.01.01.-re van állítva, ennél későbbit adj meg.";
            }
            if (inc.getIncomeDate().after(maxDate)) {
                hiba = "A jelenlegi dátumnál ne adj meg későbbit, az időutazás még nem lehetséges.";
            }
            if (inc.getIncomeValue() <= 0) {
                hiba = "IncomeValue nem lehet 0-nál kisebb vagy vele egyenlő!";
            }
            Person p = new Person();
            if (p.getPersonById(inc.getPersonID()) == null) {
                hiba = "Nincs ilyen id-vel rendelkező Person.";
            }

            if (hiba == "") {
                String message = IncomeRepo.updateIncome(inc);
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
