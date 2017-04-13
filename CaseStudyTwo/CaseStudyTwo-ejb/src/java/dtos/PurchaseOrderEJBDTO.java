package dtos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;

public class PurchaseOrderEJBDTO implements Serializable {

    private int id;
    private int vendorid;
    private ArrayList<PurchaseOrderLineItemEJBDTO> items;
    private BigDecimal total;
    private String date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVendorid() {
        return vendorid;
    }

    public void setVendorid(int vendorid) {
        this.vendorid = vendorid;
    }

    public ArrayList<PurchaseOrderLineItemEJBDTO> getItems() {
        return items;
    }

    public void setItems(ArrayList<PurchaseOrderLineItemEJBDTO> items) {
        this.items = items;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

 

}
