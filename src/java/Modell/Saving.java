/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modell;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.json.JSONObject;

/**
 *
 * @author cfrig
 */
@Entity
@Table(name = "saving")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Saving.findAll", query = "SELECT s FROM Saving s"),
    @NamedQuery(name = "Saving.findBySavingID", query = "SELECT s FROM Saving s WHERE s.savingID = :savingID"),
    @NamedQuery(name = "Saving.findBySavingName", query = "SELECT s FROM Saving s WHERE s.savingName = :savingName"),
    @NamedQuery(name = "Saving.findBySavingValue", query = "SELECT s FROM Saving s WHERE s.savingValue = :savingValue"),
    @NamedQuery(name = "Saving.findByIsActive", query = "SELECT s FROM Saving s WHERE s.isActive = :isActive"),
    @NamedQuery(name = "Saving.findByPersonID", query = "SELECT s FROM Saving s WHERE s.personID = :personID")})
public class Saving implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "savingName")
    private String savingName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "savingValue")
    private int savingValue;
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
    @Column(name = "savingID")
    private Integer savingID;

    public Saving() {
    }

    public Saving(Integer savingID) {
        this.savingID = savingID;
    }

    public Saving(Integer savingID, String savingName, int savingValue, Integer isActive, int personID) {
        this.savingID = savingID;
        this.savingName = savingName;
        this.savingValue = savingValue;
        this.isActive = isActive;
        this.personID = personID;
    }
    
    public void addToValue(Integer extra){
        this.savingValue += extra;
    }

    public Integer getSavingID() {
        return savingID;
    }

    public void setSavingID(Integer savingID) {
        this.savingID = savingID;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (savingID != null ? savingID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Saving)) {
            return false;
        }
        Saving other = (Saving) object;
        if ((this.savingID == null && other.savingID != null) || (this.savingID != null && !this.savingID.equals(other.savingID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modell.Saving[ savingID=" + savingID + " ]";
    }

    public String getSavingName() {
        return savingName;
    }

    public void setSavingName(String savingName) {
        this.savingName = savingName;
    }

    public int getSavingValue() {
        return savingValue;
    }

    public void setSavingValue(int savingValue) {
        this.savingValue = savingValue;
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

    public Saving getSavingById(Integer id) {
        EntityManager em = DBCon.getDBCon();
        return em.find(Saving.class, id);
    }

    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        object.put("savingName", this.savingName);
        object.put("savingValue", this.savingValue);
        object.put("isActive", this.isActive);
        object.put("personID", this.personID);
        return object;
    }
    
}
