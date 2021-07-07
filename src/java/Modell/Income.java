/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modell;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "income")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Income.findAll", query = "SELECT i FROM Income i"),
    @NamedQuery(name = "Income.findByIncomeID", query = "SELECT i FROM Income i WHERE i.incomeID = :incomeID"),
    @NamedQuery(name = "Income.findByIncomeName", query = "SELECT i FROM Income i WHERE i.incomeName = :incomeName"),
    @NamedQuery(name = "Income.findByIncomeType", query = "SELECT i FROM Income i WHERE i.incomeType = :incomeType"),
    @NamedQuery(name = "Income.findByIncomeValue", query = "SELECT i FROM Income i WHERE i.incomeValue = :incomeValue"),
    @NamedQuery(name = "Income.findByIncomeDate", query = "SELECT i FROM Income i WHERE i.incomeDate = :incomeDate"),
    @NamedQuery(name = "Income.findByIsActive", query = "SELECT i FROM Income i WHERE i.isActive = :isActive")})
public class Income implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "incomeName")
    private String incomeName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "incomeType")
    private String incomeType;
    @Basic(optional = false)
    @NotNull
    @Column(name = "incomeValue")
    private int incomeValue;
    @Basic(optional = false)
    @NotNull
    @Column(name = "incomeDate")
    @Temporal(TemporalType.DATE)
    private Date incomeDate;
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
    @Column(name = "incomeID")
    private Integer incomeID;
   
    public Income() {
    }

    public Income(Integer incomeID) {
        this.incomeID = incomeID;
    }

    public Income(Integer incomeID, String incomeName, String incomeType, int incomeValue, Date incomeDate, Integer isActive, Integer personID) {
        this.incomeID = incomeID;
        this.incomeName = incomeName;
        this.incomeType = incomeType;
        this.incomeValue = incomeValue;
        this.incomeDate = incomeDate;
        this.isActive = isActive;
        this.personID = personID;
    }
    
    public JSONObject toJson(){
        JSONObject object = new JSONObject();
        object.put("incomeName", this.incomeName);
        object.put("incomeType", this.incomeType);
        object.put("incomeValue", this.incomeValue);
        object.put("incomeDate", this.incomeDate);
        object.put("isActive", this.isActive);
        object.put("personID", this.personID);
        return object;
    }
    
    public Income getIncomeById(int id){
        EntityManager em = DBCon.getDBCon();
        return em.find(Income.class, id);
    }

    public Integer getIncomeID() {
        return incomeID;
    }

    public void setIncomeID(Integer incomeID) {
        this.incomeID = incomeID;
    }



    public void setPersonID(Integer personID) {
        this.personID = personID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (incomeID != null ? incomeID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Income)) {
            return false;
        }
        Income other = (Income) object;
        if ((this.incomeID == null && other.incomeID != null) || (this.incomeID != null && !this.incomeID.equals(other.incomeID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modell.Income[ incomeID=" + incomeID + " ]";
    }

    public String getIncomeName() {
        return incomeName;
    }

    public void setIncomeName(String incomeName) {
        this.incomeName = incomeName;
    }

    public String getIncomeType() {
        return incomeType;
    }

    public void setIncomeType(String incomeType) {
        this.incomeType = incomeType;
    }

    public int getIncomeValue() {
        return incomeValue;
    }

    public void setIncomeValue(int incomeValue) {
        this.incomeValue = incomeValue;
    }

    public Date getIncomeDate() {
        return incomeDate;
    }

    public void setIncomeDate(Date incomeDate) {
        this.incomeDate = incomeDate;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public int getPersonID() {
        return personID;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }
    
}
