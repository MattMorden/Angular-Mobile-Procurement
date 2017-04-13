package dtos;

import java.io.Serializable;
import java.math.BigDecimal;

public class PurchaseOrderLineItemEJBDTO implements Serializable {
    private int id;
    private int purchaseorderid;
    private String productid;
    private String name;
    private BigDecimal price;
    private int qty;
    //private int vendorid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPurchaseorderid() {
        return purchaseorderid;
    }

    public void setPurchaseorderid(int purchaseorderid) {
        this.purchaseorderid = purchaseorderid;
    }


    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
    
    
}
