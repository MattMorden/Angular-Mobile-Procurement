package models;

import dtos.EmployeeDTO;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.enterprise.context.RequestScoped;
import javax.sql.DataSource;

/*
* EmployeeModel.java
*
* Created on Aug 31, 2016, 3:03 PM
* Purpose: Contains methods for supporting db access for employee information
* Usually consumed by the resoutce class via DTO
* Revisions:
 */
@RequestScoped
public class EmployeeModel implements Serializable {

    public EmployeeModel() {
    }

    public ArrayList<EmployeeDTO> getEmployees(DataSource ds) {
        PreparedStatement pstmt;
        Connection con = null;
        String sql = "SELECT * FROM Employees";
        ArrayList<EmployeeDTO> employees = new ArrayList<>();
        try {
            con = ds.getConnection();
            pstmt = con.prepareStatement(sql);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    EmployeeDTO employee = new EmployeeDTO();
                    employee.setId(rs.getInt("Id"));
                    employee.setEmail(rs.getString("Email"));
                    employee.setTitle(rs.getString("Title"));
                    employee.setEmail(rs.getString("Email"));
                    employee.setFirstname(rs.getString("FirstName"));
                    employee.setLastname(rs.getString("LastName"));
                    employee.setPhoneno(rs.getString("Phoneno"));
                    employees.add(employee);
                }
            }
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
        return employees;
    }
}
