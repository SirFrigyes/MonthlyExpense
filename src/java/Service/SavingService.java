/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Modell.Person;
import Modell.Saving;
import Repository.SavingRepo;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author cfrig
 */
public class SavingService {

    public static String addNewSaving(Saving sav) throws ParseException {

        String hiba = "";

        Person p = new Person();
        if (p.getPersonById(sav.getPersonID()) == null) {
            hiba = "Nincs ilyen id-vel rendelkező Person.";
        }

        if (hiba == "") {
            String result = SavingRepo.addNewIncome(sav);
            if (result == "Siker") {
                return "Saving sikeresen rögzítve!";
            } else {
                return "Az adatok helyesek, de a rögzítés nem sikerült, " + result;
            }
        } else {
            return hiba;
        }
    }

    public static List<Saving> getAllActiveSaving() {
        return SavingRepo.getAllActiveSaving();
    }

    public static String logicalDeleteSavingById(Integer id) {
        Saving sav = new Saving();
        try {
            sav = sav.getSavingById(id);
            if (sav != null) {
                if (sav.getIsActive() == 1) {
                    if (SavingRepo.logicalDeleteSavingById(id)) {
                        return "A logikai törlés sikeres volt.";
                    } else {
                        return "Az id helyes, de a logikai törlés nem sikerült.";
                    }
                } else if (sav.getIsActive() == 0) {
                    return "A megadott id-val rendelkező Saving már törölve lett.";
                } else {
                    return "A megadott id-vel rendelkező Saving isActive paramétere rosszul lett megadva.";
                }
            } else {
                return "Nincs ilyen id-vel rendelkező Saving.";
            }
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    public static String updateSaving(Saving sav) throws ParseException {

        try {
            String hiba = "";

            Person p = new Person();
            if (p.getPersonById(sav.getPersonID()) == null) {
                hiba = "Nincs ilyen id-vel rendelkező Person.";
            }

            if (hiba == "") {
                String message = SavingRepo.updateSaving(sav);
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
    
    /*public static String addLeftoverToSaving(Person p, Date date){
        
    }*/

}
