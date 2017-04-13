/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import dtos.ProductDTO;
import dtos.PurchaseOrderLineItemDTO;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.sql.DataSource;

/**
 *
 * @author Matt
 */
public class ProductModel implements Serializable {

    public ProductModel() {
    }

    public ArrayList<ProductDTO> getProducts(DataSource ds) {
        PreparedStatement pstmt;
        Connection con = null;
        String sql = "SELECT * FROM Products";
        ArrayList<ProductDTO> products = new ArrayList<>();
        try {
            con = ds.getConnection();
            pstmt = con.prepareStatement(sql);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    ProductDTO product = new ProductDTO();
                    product.setId(rs.getString("id"));
                    product.setVendorid((rs.getInt("vendorid")));
                    product.setVendorsku(rs.getString("vendorsku"));
                    product.setName(rs.getString("name"));
                    product.setCostprice(rs.getBigDecimal("costprice"));
                    product.setMsrp(rs.getBigDecimal("msrp"));
                    product.setRop(rs.getInt("rop"));
                    product.setEoq(rs.getInt("eoq"));
                    product.setQoh(rs.getInt("qoh"));
                    product.setQoo(rs.getInt("qoo"));
                    product.setQrcodetxt(rs.getString("qrcodetxt"));
                    product.setQrcode(rs.getBytes("qrcode"));
                    //set qrcode
                    //product.setQrcodetext(rs.getString("qrCodeText"));   
                    products.add(product);
                }
            }
            con.close();
        } catch (SQLException se) {
            //handle errors for JDBC
            System.out.println("SQL issue " + se.getMessage());
        } catch (Exception e) {
            //handle other errors
            System.out.println("other issue " + e.getMessage());
        } finally {
            //finally block to close resources
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException se) {
                System.out.println("SQL issue on close " + se.getMessage());
            }
        }//end finally try
        return products;
    }//end getProducts

    public int updateProduct(ProductDTO details, DataSource ds) {
        PreparedStatement pstmt;
        Connection con = null;
        int rowsUpdated = -1;
        String sql = "UPDATE Products SET vendorid = ?, name = ?, costprice = ?, msrp = ?, rop = ?, eoq = ?, qoh = ?, qoo = ?, qrcodetxt = ?, qrcode = ? WHERE id = ?";
        try {
            con = ds.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, details.getVendorid());
            //pstmt.setString(2, details.getVendorSKU());
            pstmt.setString(2, details.getName());
            pstmt.setBigDecimal(3, details.getCostprice());
            pstmt.setBigDecimal(4, details.getMsrp());
            pstmt.setInt(5, details.getRop());
            pstmt.setInt(6, details.getEoq());
            pstmt.setInt(7, details.getQoh());
            pstmt.setInt(8, details.getQoo());
            pstmt.setString(9, details.getQrcodetxt());
            pstmt.setBytes(10, details.getQrcode());
            pstmt.setString(11, details.getId());
            //get qrcode blob
            //pstmt.setString(9, details.getQrcodetext());

            rowsUpdated = pstmt.executeUpdate();
        } catch (SQLException se) {
            //handle errors for JDBC
            System.out.println("SQL issue " + se.getMessage());
        } //handle other errors
        catch (Exception e) {
            System.out.println("Error " + e.getMessage());
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
        return rowsUpdated;
    }

    public int deleteProduct(String id, DataSource ds) {
        PreparedStatement pstmt;
        Connection con = null;
        int rowsDeleted = -1;
        String sql = "DELETE FROM Products WHERE id = ?";
        try {
            con = ds.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, id);
            rowsDeleted = pstmt.executeUpdate();
        } catch (SQLException se) {
            //handle errors for JDBC
            System.out.println("SQL issue " + se.getMessage());
        } catch (Exception e) {
            //handle other errors
            System.out.println("other issue " + e.getMessage());
        } finally {
            //finally block to close resources
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException se) {
                System.out.println("SQL issue on close " + se.getMessage());
            }
        }//end finally try
        return rowsDeleted;
    }

    public int addProduct(ProductDTO details, DataSource ds) {
        PreparedStatement pstmt;
        Connection con = null;
        String msg = "";
        int rowsAdded = -1;
        String sql = "INSERT INTO Products (id,vendorid,name,costprice,msrp,rop,eoq,qoh,qoo, vendorSKU, qrcodetxt, qrcode) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            con = ds.getConnection();
            pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, details.getId());
            pstmt.setInt(2, details.getVendorid());
            //pstmt.setString(3, details.getVendorSKU());
            pstmt.setString(3, details.getName());
            pstmt.setBigDecimal(4, details.getCostprice());
            pstmt.setBigDecimal(5, details.getMsrp());
            pstmt.setInt(6, details.getRop());
            pstmt.setInt(7, details.getEoq());
            pstmt.setInt(8, details.getQoh());
            pstmt.setInt(9, details.getQoo());
            pstmt.setString(10, "a");
            pstmt.setString(11, details.getQrcodetxt());
            pstmt.setBytes(12, details.getQrcode());
            //pstmt.execute();
            rowsAdded = pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                rs.next();
            }
            con.close();
        } catch (SQLException se) {
            System.out.println("SQL issue " + se.getMessage());
        } catch (Exception e) {
            System.out.println("other issue " + e.getMessage());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException se) {
                System.out.println("SQL issue on close " + se.getMessage());
            }
        }
        return rowsAdded;
    }

    public ProductDTO getProduct(String id, DataSource ds) {
        PreparedStatement pstmt;
        Connection con = null;
        String sql = "SELECT * FROM Products WHERE Id = " + "'" + id + "'";
        ProductDTO product = new ProductDTO();
        try {
            con = ds.getConnection();
            pstmt = con.prepareStatement(sql);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    product.setId(rs.getString("id"));
                    product.setName(rs.getString("name"));
                    product.setVendorid(rs.getInt("vendorid"));
                    //product.setVendorsku(rs.getString("vendorsku"));
                    product.setVendorsku("a");
                    product.setCostprice(rs.getBigDecimal("costprice"));
                    product.setMsrp(rs.getBigDecimal("msrp"));
                    product.setRop(rs.getInt("rop"));
                    product.setEoq(rs.getInt("eoq"));
                    product.setQoh(rs.getInt("qoh"));
                    product.setQoo(rs.getInt("qoo"));
                    product.setQrcodetxt(rs.getString("qrcodetxt"));
                    product.setQrcode(rs.getBytes("qrcode"));
                }
            }
            con.close();
        } catch (SQLException se) {
//Handle errors for JDBC
            System.out.println("SQL issue " + se.getMessage());
        } catch (Exception e) {
//Handle other errors
            System.out.println("Other issue " + e.getMessage() + e.toString());
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
        return product;
    }

}
