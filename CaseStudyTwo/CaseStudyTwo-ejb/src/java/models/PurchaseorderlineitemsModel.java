/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Matt
 */
@Entity
@Table(name = "PURCHASEORDERLINEITEMS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PurchaseorderlineitemsModel.findAll", query = "SELECT p FROM PurchaseorderlineitemsModel p"),
    @NamedQuery(name = "PurchaseorderlineitemsModel.findById", query = "SELECT p FROM PurchaseorderlineitemsModel p WHERE p.id = :id"),
    @NamedQuery(name = "PurchaseorderlineitemsModel.findByProductid", query = "SELECT p FROM PurchaseorderlineitemsModel p WHERE p.productid = :productid"),
    @NamedQuery(name = "PurchaseorderlineitemsModel.findByQty", query = "SELECT p FROM PurchaseorderlineitemsModel p WHERE p.qty = :qty"),
    @NamedQuery(name = "PurchaseorderlineitemsModel.findByPrice", query = "SELECT p FROM PurchaseorderlineitemsModel p WHERE p.price = :price")})
public class PurchaseorderlineitemsModel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "PRODUCTID")
    private String productid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "QTY")
    private int qty;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRICE")
    private BigDecimal price;
    @JoinColumn(name = "POID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private PurchaseordersModel poid;

    public PurchaseorderlineitemsModel() {
    }

    public PurchaseorderlineitemsModel(Integer id) {
        this.id = id;
    }

    public PurchaseorderlineitemsModel(Integer id, String productid, int qty, BigDecimal price) {
        this.id = id;
        this.productid = productid;
        this.qty = qty;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public PurchaseordersModel getPoid() {
        return poid;
    }

    public void setPoid(PurchaseordersModel poid) {
        this.poid = poid;
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
        if (!(object instanceof PurchaseorderlineitemsModel)) {
            return false;
        }
        PurchaseorderlineitemsModel other = (PurchaseorderlineitemsModel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.PurchaseorderlineitemsModel[ id=" + id + " ]";
    }
    
}
