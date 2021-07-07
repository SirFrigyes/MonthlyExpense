/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modell;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.json.JSONObject;

/**
 *
 * @author cfrig
 */
@Entity
@Table(name = "expense")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Expense.findAll", query = "SELECT e FROM Expense e"),
    @NamedQuery(name = "Expense.findByExpenseID", query = "SELECT e FROM Expense e WHERE e.expenseID = :expenseID"),
    @NamedQuery(name = "Expense.findByExpenseName", query = "SELECT e FROM Expense e WHERE e.expenseName = :expenseName"),
    @NamedQuery(name = "Expense.findByExpenseType", query = "SELECT e FROM Expense e WHERE e.expenseType = :expenseType"),
    @NamedQuery(name = "Expense.findByExpenseDate", query = "SELECT e FROM Expense e WHERE e.expenseDate = :expenseDate"),
    @NamedQuery(name = "Expense.findByIsActive", query = "SELECT e FROM Expense e WHERE e.isActive = :isActive")})
public class Expense implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "expenseName")
    private String expenseName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "expenseType")
    private String expenseType;
    @Basic(optional = false)
    @NotNull
    @Column(name = "expenseValue")
    private int expenseValue;
    @Basic(optional = false)
    @NotNull
    @Column(name = "expenseDate")
    @Temporal(TemporalType.DATE)
    private Date expenseDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "isActive")
    private Integer isActive;
    @Basic(optional = false)
    @NotNull
    @Column(name = "personID")
    private int personID;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "expenseID")
    private Integer expenseID;
    

    public Expense() {
    }

    public Expense(Integer expenseID) {
        this.expenseID = expenseID;
    }

    public Expense(Integer expenseID, String expenseName, String expenseType, Integer expenseValue, Date expenseDate, Integer isActive, Integer personID) {
        this.expenseID = expenseID;
        this.expenseName = expenseName;
        this.expenseType = expenseType;
        this.expenseValue = expenseValue;
        this.expenseDate = expenseDate;
        this.isActive = isActive;
        this.personID = personID;
    }
    
    public JSONObject toJson(){
        JSONObject object = new JSONObject();
        object.put("expenseName", this.expenseName);
        object.put("expenseType", this.expenseType);
        object.put("expenseValue", this.expenseValue);
        object.put("expenseDate", this.expenseDate);
        object.put("isActive", this.isActive);
        object.put("personID", this.personID);
        return object;
    }
    
    public Expense getExpenseById(int id){
        EntityManager em = DBCon.getDBCon();
        return em.find(Expense.class, id);
    }

    public Integer getExpenseID() {
        return expenseID;
    }

    public void setExpenseID(Integer expenseID) {
        this.expenseID = expenseID;
    }


    public Integer getPersonID() {
        return personID;
    }

    public void setPersonID(Integer personID) {
        this.personID = personID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (expenseID != null ? expenseID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Expense)) {
            return false;
        }
        Expense other = (Expense) object;
        if ((this.expenseID == null && other.expenseID != null) || (this.expenseID != null && !this.expenseID.equals(other.expenseID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modell.Expense[ expenseID=" + expenseID + " ]";
    }

    public String getExpenseName() {
        return expenseName;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }

    public String getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(String expenseType) {
        this.expenseType = expenseType;
    }

    public int getExpenseValue() {
        return expenseValue;
    }

    public void setExpenseValue(int expenseValue) {
        this.expenseValue = expenseValue;
    }

    public Date getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(Date expenseDate) {
        this.expenseDate = expenseDate;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }
    
}
