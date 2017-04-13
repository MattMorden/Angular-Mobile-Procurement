/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Matt
 */
public class PurchaseOrderDTO implements Serializable {

    private int id;
    private int vendorid;
    private BigDecimal total;
    private String podate;

    public int getVendorid() {
        return vendorid;
    }

    public void setVendorid(int vendorid) {
        this.vendorid = vendorid;
    }

    public String getPodate() {
        return podate;
    }

    public void setPodate(String podate) {
        this.podate = podate;
    }
    private ArrayList<PurchaseOrderLineItemDTO> items;

    public ArrayList<PurchaseOrderLineItemDTO> getItems() {
        return items;
    }

    public void setItems(ArrayList<PurchaseOrderLineItemDTO> items) {
        this.items = items;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal amount) {
        this.total = amount;
    }

}
