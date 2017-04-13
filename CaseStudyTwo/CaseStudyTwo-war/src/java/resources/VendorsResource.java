package resources;

import dtos.VendorEJBDTO;
import java.net.URI;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.POST;
import javax.ws.rs.core.Response;
import ejbs.VendorFacadeBean;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * REST Service for Employees
 *
 * @author Matt
 */
@Path("vendor")
@RequestScoped
public class VendorsResource {

    @EJB
    private VendorFacadeBean vendorFacadeBean;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of EmployeeResource
     */
    public VendorsResource() {
    }

    @POST
    @Consumes("application/json")
    public Response add(VendorEJBDTO vendor) {
        int id = vendorFacadeBean.add(vendor);
        URI uri = context.getAbsolutePath();
        return Response.created(uri).entity(id).build();
    }

    @DELETE
    @Path("/{id}")
    @Consumes("application/json")
    public Response delete(@PathParam("id") int id) {
        int numOfRowsDeleted = vendorFacadeBean.delete(id);
        URI uri = context.getAbsolutePath();
        return Response.created(uri).entity(numOfRowsDeleted).build();
    }

    @PUT
    @Consumes("application/json")
    public Response update(VendorEJBDTO vendor) {
        int numOfRowsUpdated = vendorFacadeBean.update(vendor);
        URI uri = context.getAbsolutePath();
        return Response.created(uri).entity(numOfRowsUpdated).build();
    }

    @GET
    @Produces("application/json")
    public List<VendorEJBDTO> getAll() {
        return vendorFacadeBean.getAll();
    }

}
