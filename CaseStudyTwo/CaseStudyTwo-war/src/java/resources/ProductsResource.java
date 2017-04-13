package resources;

import dtos.ProductEJBDTO;
import ejbs.ProductFacadeBean;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * REST Service for Expenses
 *
 * @author Matt
 */
@Path("product")
@RequestScoped
public class ProductsResource {

    @Context
    private UriInfo context;

    @Context
    ServletContext servletContext;

    @EJB
    private ProductFacadeBean pfb;
    

    /**
     * Creates a new instance of ProductResource
     */
    public ProductsResource() {
    }

    @GET
    @Produces("application/json")
    public List<ProductEJBDTO> getAll() {
        return pfb.getAll();
    }

    @GET
    @Produces("application/json")
    @Path("/{id}")
    public ProductEJBDTO get(@PathParam("id") String id) {
        return pfb.getOne(id);
    }

    @POST
    @Consumes("application/json")
    public Response add(ProductEJBDTO product) throws IOException {
        int id = pfb.add(product);

        URI uri = context.getAbsolutePath();
        return Response.created(uri).entity(id).build();
    }

    @PUT
    @Consumes("application/json")
    public Response update(ProductEJBDTO product) throws IOException {

        int rowsUpdated = pfb.update(product);
        URI uri = context.getAbsolutePath();
        return Response.created(uri).entity(rowsUpdated).build();
    }

    @DELETE
    @Path("/{id}")
    @Consumes("application/json")
    public Response delete(@PathParam("id") String id) {
        int numOfRowsDeleted = pfb.delete(id);
        URI uri = context.getAbsolutePath();
        return Response.created(uri).entity(numOfRowsDeleted).build();
    }


}
