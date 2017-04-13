/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import dtos.PurchaseOrderDTO;
import dtos.PurchaseOrderLineItemDTO;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author Matt
 */
@Named(value = "purchaseordermodel")
@RequestScoped
public class PurchaseOrderModel implements Serializable {

    public PurchaseOrderModel() {
    }

    @SuppressWarnings({"null", "ConstantConditions"})
    public int addProductPurchase(PurchaseOrderDTO purchase, DataSource ds) {
        Connection con = null;
        PreparedStatement pstmt;
        int reportId = -1;
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YYYY");
        String curDate = sdf.format(Calendar.getInstance().getTime());
        String msg = "";
        String sql = "INSERT INTO PURCHASEORDERS (VENDORID, AMOUNT, PODATE) VALUES (?,?,?)";
        try {
            con = ds.getConnection();
            con.setAutoCommit(false); // needed for trans rollback
            pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, purchase.getVendorid());
            pstmt.setBigDecimal(2, purchase.getTotal());
            pstmt.setString(3, curDate);
            pstmt.execute();
            ResultSet rs = pstmt.getGeneratedKeys();
            rs.next();
            reportId = rs.getInt(1);
            rs.close();
            pstmt.close();
            for (PurchaseOrderLineItemDTO item : purchase.getItems()) {
                sql = "INSERT INTO PurchaseOrderLineItems (Poid,ProductId, qty, price) VALUES (?,?,?,?)";
                pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                pstmt.setInt(1, reportId);
                pstmt.setString(2, item.getProductid());
                pstmt.setInt(3, item.getQuantity());
                pstmt.setBigDecimal(4, item.getPrice());
                pstmt.execute();
                rs = pstmt.getGeneratedKeys();
                rs.next();
                int itemno = rs.getInt(1);
                if (itemno > 0) {
                }
                rs.close();
                pstmt.close();
            } // end for
            con.commit();
            con.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            System.out.println("SQL issue " + se.getMessage());
            msg = "Report not added ! - " + se.getMessage();
            try {
                con.rollback();
            } catch (SQLException sqx) {
                System.out.println("Rollback failed - " + sqx.getMessage());
            }
        } catch (Exception e) {
            //Handle other errors
            System.out.println("other issue " + e.getMessage());
        } finally {
            //finally block used to close resources
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException se) {
                System.out.println("SQL issue on close " + se.getMessage());
            }//end finally try
        }
        return reportId;
    }

    /**
     * builds Report DTO for desired report
     *
     * @param id representing the report we're interested in
     * @param ds
     * @return all details for all PO created for vendor
     */
    public PurchaseOrderDTO getPurchaseOrder(int id, DataSource ds) {
        String sql = "SELECT * FROM PurchaseOrders WHERE Id = ?";
        PreparedStatement stmt;
        ResultSet rs;
        Connection con = null;
        PurchaseOrderDTO details = new PurchaseOrderDTO();
        try {
            con = ds.getConnection();
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            rs.next();
            details.setId(id);
            details.setVendorid(rs.getInt("vendorid"));
            details.setTotal(rs.getBigDecimal("amount"));
            details.setPodate(rs.getString("podate"));
            stmt.close();
            rs.close();
            con.close();
            details.setItems(getPurchseOrderItems(id, ds));
        } catch (SQLException se) {
            //Handle errors for JDBC
            System.out.println("SQL issue " + se.getMessage());
        } catch (Exception e) {
            //Handle other errors
            System.out.println("other issue " + e.getMessage());
        } finally {
            //finally block used to close resources
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException se) {
                System.out.println("SQL issue on close " + se.getMessage());
            }//end finally try
        }
        return details;
    }

    /**
     * builds item DTO for all desired report items
     *
     * @param id representing the report we're interested in
     * @return all details for all PO created for vendor
     */
    private ArrayList<PurchaseOrderLineItemDTO> getPurchseOrderItems(int id, DataSource ds) {
        String sql = "SELECT * FROM purchaseorderlineitems WHERE POID = ?";
        PreparedStatement stmt;
        ResultSet rs;
        Connection con = null;
        ArrayList<PurchaseOrderLineItemDTO> items = new ArrayList();
        try {
            con = ds.getConnection();
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            while (rs.next()) {
                PurchaseOrderLineItemDTO item = new PurchaseOrderLineItemDTO();
                item.setId(id);
                item.setProductid(rs.getString("productid"));
                item.setQuantity(rs.getInt("qty"));
                item.setPrice(rs.getBigDecimal("price"));
                items.add(item);
            }
            stmt.close();
            rs.close();
            con.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            System.out.println("SQL issue " + se.getMessage());
        } catch (Exception e) {
            //Handle other errors
            System.out.println("other issue " + e.getMessage());
        } finally {
            //finally block used to close resources
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException se) {
                System.out.println("SQL issue on close " + se.getMessage());
            }//end finally try
        }
        return items;
    }

}
