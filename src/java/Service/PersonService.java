/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Modell.Expense;
import Modell.Income;
import Modell.Person;
import static Repository.ExpenseRepo.getAllExpensesByPerson;
import static Repository.IncomeRepo.getAllIncomesByPerson;
import Repository.PersonRepo;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author cfrig
 */
public class PersonService {
    
    public static String addNewPerson(Person p){
        String hiba = "";
        
        if(!p.getPersonLastName().chars().allMatch(Character::isLetter)){
            hiba = "LastName nem csak betűkből áll!";
        }
        if(!p.getPersonFirstName().chars().allMatch(Character::isLetter)){
            hiba = "FirstName nem csak betűkből áll!";
        }
        if(!Character.isUpperCase(p.getPersonLastName().charAt(0)) || !Character.isUpperCase(p.getPersonFirstName().charAt(0))){
            hiba = "A nevek nem nagybetűvel kezdődnek.";
        }
        
        if(hiba == ""){
            if(PersonRepo.addNewPerson(p)){
                return "Sikeres rögzítés!";
            }
            else{
                return "Az adatok megfelelőek, de a rögzítés nem sikerült!";
            }
        }
        else{
            return hiba;
        }
    }
    
    public static List<Person> getAllActivePerson(){
        return PersonRepo.getAllActivePerson();
    }
    
    public static String logicalDeleteById(Integer id){
        Person p = new Person();
        try{
            p = p.getPersonById(id);
            if(p != null){
                if(p.getIsActive() == 1){
                    if(PersonRepo.logicalDeletePersonById(id)){
                    return "A logikai törlés sikeres volt.";
                    }
                    else{
                        return "Az id helyes, de a logikai törlés nem sikerült.";
                    }
                }
                else if(p.getIsActive() == 0){
                    return "A megadott id-val rendelkező Person már törölve lett.";
                }
                else{
                    return "A megadott id-vel rendelkező személy isActive paramétere rosszul lett megadva.";
                }
            }
            else{
                return "Nincs ilyen id-vel rendelkező Person.";
            }
        }
        catch(Exception ex){
            return ex.getMessage();
        }
    }
    
    public static String updatePerson(Person p){
        String hiba = "";
        
        if(!p.getPersonLastName().chars().allMatch(Character::isLetter)){
            hiba = "LastName nem csak betűkből áll!";
        }
        if(!p.getPersonFirstName().chars().allMatch(Character::isLetter)){
            hiba = "FirstName nem csak betűkből áll!";
        }
        if(!Character.isUpperCase(p.getPersonLastName().charAt(0)) || !Character.isUpperCase(p.getPersonFirstName().charAt(0))){
            hiba = "A nevek nem nagybetűvel kezdődnek.";
        }
        
        if(hiba == ""){
            if(PersonRepo.updatePerson(p)){
                return "Sikeres update!";
            }
            else{
                return "Az adatok megfelelőek, de az update nem sikerült!";
            }
        }
        else{
            return hiba;
        }  
    }
    
    public static Integer getMonthlyLeftover(Person p, Date date){
        List<Expense> expenses = new ArrayList();
        Integer expValSum = 0;
        List<Income> incomes = new ArrayList();
        Integer incValSum = 0;
        
        expenses = getAllExpensesByPerson(p.getPersonID());
        incomes = getAllIncomesByPerson(p.getPersonID());
        
        for(Expense exp : expenses){
            if(exp.getExpenseDate().getYear() == date.getYear() && exp.getExpenseDate().getMonth() == date.getMonth()){
                expValSum += exp.getExpenseValue();
            }
        }
        for(Income inc : incomes){
            if(inc.getIncomeDate().getYear() == date.getYear() && inc.getIncomeDate().getMonth() == date.getMonth()){
                incValSum += inc.getIncomeValue();
            }
        }
        return incValSum - expValSum;
    }
}
