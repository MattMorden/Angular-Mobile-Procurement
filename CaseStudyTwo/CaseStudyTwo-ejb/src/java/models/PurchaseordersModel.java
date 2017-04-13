/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Matt
 */
@Entity
@Table(name = "PURCHASEORDERS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PurchaseordersModel.findAll", query = "SELECT p FROM PurchaseordersModel p"),
    @NamedQuery(name = "PurchaseordersModel.findById", query = "SELECT p FROM PurchaseordersModel p WHERE p.id = :id"),
    @NamedQuery(name = "PurchaseordersModel.findByAmount", query = "SELECT p FROM PurchaseordersModel p WHERE p.amount = :amount"),
    @NamedQuery(name = "PurchaseordersModel.findByVendor", query = "SELECT p FROM PurchaseordersModel p WHERE p.vendorid = :vendorid"),
    @NamedQuery(name = "PurchaseordersModel.findByPodate", query = "SELECT p FROM PurchaseordersModel p WHERE p.podate = :podate")})
public class PurchaseordersModel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "AMOUNT")
    private BigDecimal amount;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PODATE")
    @Temporal(TemporalType.DATE)
    private Date podate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "poid")
    private Collection<PurchaseorderlineitemsModel> purchaseorderlineitemsModelCollection;
    @JoinColumn(name = "VENDORID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private VendorsModel vendorid;

    public PurchaseordersModel() {
    }

    public PurchaseordersModel(Integer id) {
        this.id = id;
    }

    public PurchaseordersModel(Integer id, BigDecimal amount, Date podate) {
        this.id = id;
        this.amount = amount;
        this.podate = podate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getPodate() {
        return podate;
    }

    public void setPodate(Date podate) {
        this.podate = podate;
    }

    @XmlTransient
    public Collection<PurchaseorderlineitemsModel> getPurchaseorderlineitemsModelCollection() {
        return purchaseorderlineitemsModelCollection;
    }

    public void setPurchaseorderlineitemsModelCollection(Collection<PurchaseorderlineitemsModel> purchaseorderlineitemsModelCollection) {
        this.purchaseorderlineitemsModelCollection = purchaseorderlineitemsModelCollection;
    }

    public VendorsModel getVendorid() {
        return vendorid;
    }

    public void setVendorid(VendorsModel vendorid) {
        this.vendorid = vendorid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PurchaseordersModel)) {
            return false;
        }
        PurchaseordersModel other = (PurchaseordersModel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.PurchaseordersModel[ id=" + id + " ]";
    }
    
}
