/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modell;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.json.JSONObject;

/**
 *
 * @author cfrig
 */
@Entity
@Table(name = "person")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Person.findAll", query = "SELECT p FROM Person p"),
    @NamedQuery(name = "Person.findByPersonID", query = "SELECT p FROM Person p WHERE p.personID = :personID"),
    @NamedQuery(name = "Person.findByPersonLastName", query = "SELECT p FROM Person p WHERE p.personLastName = :personLastName"),
    @NamedQuery(name = "Person.findByPersonFirstName", query = "SELECT p FROM Person p WHERE p.personFirstName = :personFirstName"),
    @NamedQuery(name = "Person.findByIsActive", query = "SELECT p FROM Person p WHERE p.isActive = :isActive")})
public class Person implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "personLastName")
    private String personLastName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "personFirstName")
    private String personFirstName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "isActive")
    private Integer isActive;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "personID")
    private Integer personID;

    public Person() {
    }

    public Person(Integer personID) {
        this.personID = personID;
    }

    public Person(Integer personID, String personLastName, String personFirstName, Integer isActive) {
        this.personID = personID;
        this.personLastName = personLastName;
        this.personFirstName = personFirstName;
        this.isActive = isActive;
    }
    
    public JSONObject toJson(){
        JSONObject object = new JSONObject();
        object.put("personID", this.personID);
        object.put("personLastName", this.personLastName);
        object.put("personFirstName", this.personFirstName);
        object.put("isActive", this.isActive);
        return object;
    }
    
    public Person getPersonById(int id){
        EntityManager em = DBCon.getDBCon();
        return em.find(Person.class, id);
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
        hash += (personID != null ? personID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Person)) {
            return false;
        }
        Person other = (Person) object;
        if ((this.personID == null && other.personID != null) || (this.personID != null && !this.personID.equals(other.personID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modell.Person[ personID=" + personID + " ]";
    }

    public String getPersonLastName() {
        return personLastName;
    }

    public void setPersonLastName(String personLastName) {
        this.personLastName = personLastName;
    }

    public String getPersonFirstName() {
        return personFirstName;
    }

    public void setPersonFirstName(String personFirstName) {
        this.personFirstName = personFirstName;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }
    
}
