/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.PurchaseOrderEJBDTO;
import dtos.PurchaseOrderLineItemEJBDTO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolationException;
import models.VendorsModel;
import models.PurchaseorderlineitemsModel;
import models.PurchaseordersModel;
import models.ProductsModel;

/**
 *
 * @author Matt
 */
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Stateless
@LocalBean
public class PurchaseOrderFacadeBean extends AbstractFacade<PurchaseordersModel> {

    private final UtilityMethods util;
    @PersistenceContext
    private EntityManager em;

    @Resource
    private EJBContext context;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PurchaseOrderFacadeBean() {
        super(PurchaseordersModel.class);
        util = new UtilityMethods();
    }

    /**
     * @param id - int representing employee id
     * @return List of DTOs representing all employees
     */
    public List<PurchaseOrderEJBDTO> getAllForVendor(int id) {
        ArrayList<PurchaseOrderEJBDTO> allreportinfo = new ArrayList<>();
        try {
            VendorsModel emod = em.find(VendorsModel.class, id);
            Query qry = em.createNamedQuery("PurchaseordersModel.findByVendor");
            qry.setParameter("vendorid", emod);
            List<PurchaseordersModel> reports = qry.getResultList();
            // report loop
            for (PurchaseordersModel report : reports) {
                PurchaseOrderEJBDTO repDTO = new PurchaseOrderEJBDTO();
                repDTO.setVendorid(id);
                repDTO.setDate(util.formatDate(report.getPodate()));
                repDTO.setId(report.getId());
                repDTO.setTotal(report.getAmount());
                
                ArrayList<PurchaseOrderLineItemEJBDTO> items = new ArrayList<>();
                // report item loop
                for (PurchaseorderlineitemsModel item
                        : report.getPurchaseorderlineitemsModelCollection()) {
                    PurchaseOrderLineItemEJBDTO lineinfo = new PurchaseOrderLineItemEJBDTO();
                    ProductsModel exmod = em.find(ProductsModel.class,item.getProductid());//.getId()

                    lineinfo.setName(exmod.getName());
                    lineinfo.setPurchaseorderid(report.getId());
                    lineinfo.setId(item.getId());
                    lineinfo.setQty(item.getQty());
                    lineinfo.setPrice(exmod.getCostprice());
                    lineinfo.setProductid(exmod.getId());
                    
                    items.add(lineinfo);
                }
                repDTO.setItems(items);
                allreportinfo.add(repDTO);
            }
        } catch (Exception e) {
            //Handle other errors
            System.out.println("other issue " + e.getMessage());
        }
        return allreportinfo;
    }

    /**
     * @param id representing the report we're interested in
     * @return all details for single report
     */
    public PurchaseOrderEJBDTO get(int id) {
        ArrayList<PurchaseOrderLineItemEJBDTO> items;
        PurchaseordersModel repmod;
        PurchaseOrderEJBDTO dto = new PurchaseOrderEJBDTO();
        try {
            repmod = find(id);
            dto.setDate(util.formatDate(repmod.getPodate()));
            dto.setId(repmod.getId());
            double tmpTotal = 0.0D;
            dto.setVendorid(repmod.getVendorid().getId());
            items = new ArrayList<>();
            // line item loop
            for (PurchaseorderlineitemsModel line : repmod.getPurchaseorderlineitemsModelCollection()) {
                PurchaseOrderLineItemEJBDTO lineinfo = new PurchaseOrderLineItemEJBDTO();
                lineinfo.setPurchaseorderid(id);
                lineinfo.setProductid(line.getProductid());//.getId()
                lineinfo.setPrice(line.getPrice());
                lineinfo.setId(line.getId());
                lineinfo.setQty(line.getQty());
                items.add(lineinfo);
            }
            dto.setItems(items);
            dto.setTotal(BigDecimal.valueOf(tmpTotal));
        } catch (Exception e) {
            System.out.println("Error getting line info from ProductReportFacade - ");
        }
        return dto;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public int add(PurchaseOrderEJBDTO dto) {
        PurchaseordersModel pom;
        VendorsModel vmod;
        int reportId = -1;
        try {
            vmod = em.find(VendorsModel.class, dto.getVendorid());
            pom = new PurchaseordersModel();
            pom.setPodate(new Date());
            pom.setVendorid(vmod);
            pom.setAmount(dto.getTotal());
            create(pom);
            em.flush();
            reportId = pom.getId();
            Collection<PurchaseorderlineitemsModel> tmpLines = new ArrayList<>();

            for (PurchaseOrderLineItemEJBDTO item : dto.getItems()) {
                PurchaseorderlineitemsModel polim = new PurchaseorderlineitemsModel();
                ProductsModel ex = em.find(ProductsModel.class, item.getProductid());
                ex.setQoo(ex.getQoo() + item.getQty());
                em.persist(ex);
                //polim.setId(item.getId());//MIGHT HAVE TO REMOVE THIS
                
                polim.setPoid(pom);
                polim.setProductid(ex.getId());
                polim.setQty(item.getQty());
                polim.setPrice(item.getPrice());
                tmpLines.add(polim);
            }
            // add to entity
            pom.setPurchaseorderlineitemsModelCollection(tmpLines);
            create(pom);
        } catch (ConstraintViolationException cve) {
            util.handleConstraintViolation(cve);
            reportId = -1;
        } catch (Exception e) {
            context.setRollbackOnly();
            System.out.println(e.getMessage());
            reportId = -1;
        }
        return reportId;
    }

}
