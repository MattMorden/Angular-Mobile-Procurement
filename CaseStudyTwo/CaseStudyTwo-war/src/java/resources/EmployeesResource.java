package resources;

import dtos.EmployeeDTO;
import java.util.ArrayList;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.sql.DataSource;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import models.EmployeeModel;

/**
 * REST Service for Employees
 *
 * @author Matt
 */
@Path("employee")
@RequestScoped
public class EmployeesResource {

    @Context
    private UriInfo context;

    // resource already defined in Glassfish
    @Resource(lookup = "jdbc/Info5059db")
    DataSource ds;

    /**
     * Creates a new instance of EmployeeResource
     */
    public EmployeesResource() {
    }

    /**
     * Return all employees as JSON array
     */
    @GET
    @Produces("application/json")
    public ArrayList<EmployeeDTO> getEmployeesJson() {
        EmployeeModel model = new EmployeeModel();
        ArrayList<EmployeeDTO> employees = model.getEmployees(ds);
        return employees;
    }
}
