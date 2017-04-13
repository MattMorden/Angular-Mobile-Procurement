/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;


import dtos.PurchaseOrderEJBDTO;
import ejbs.PurchaseOrderFacadeBean;
import java.net.URI;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Matt
 */
@Path("purchaseorder")
@RequestScoped
public class PurchaseOrderResource {

    @Context
    private UriInfo context;

    // resource already defined in Glassfish
    //@Resource(lookup = "jdbc/Info5059db")
    //DataSource ds;

    //@EJB
    //private ProductReportFacadeBean prfb;
    
    @EJB
    private PurchaseOrderFacadeBean pofb;

    /**
     * Creates a new instance of ExpenseResource
     */
    public PurchaseOrderResource() {
    }

    /*
    @POST
    @Consumes("application/json")
    public Response addProductPurchase(PurchaseOrderDTO purchase) {
        PurchaseOrderModel model = new PurchaseOrderModel();
        int id = model.addProductPurchase(purchase, ds);
        URI uri = context.getAbsolutePath();
        return Response.created(uri).entity(id).build();
    }
     */

    
    @POST
    @Consumes("application/json")
    public Response add(PurchaseOrderEJBDTO report) {
        int id = pofb.add(report);
        URI uri = context.getAbsolutePath();
        return Response.created(uri).entity(id).build();
    }

    @GET
    @Path("/{vendorid}")
    @Produces("application/json")
    public List<PurchaseOrderEJBDTO> getAllForVendor(@PathParam("vendorid") int vendorid) {
        return pofb.getAllForVendor(vendorid);
    }

}
