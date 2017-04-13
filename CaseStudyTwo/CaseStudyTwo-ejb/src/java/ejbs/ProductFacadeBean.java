/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.ProductEJBDTO;
import dtos.VendorEJBDTO;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import models.ProductsModel;

/**
 *
 * @author Matt
 */
@Stateless
public class ProductFacadeBean extends AbstractFacade<ProductsModel> {

    @PersistenceContext
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    private final UtilityMethods util;

    public ProductFacadeBean() {
        super(ProductsModel.class);
        util = new UtilityMethods();
    }

    /**
     * @param id int represent expense PK
     * @return int representing the number of rows deleted.
     */
    public int delete(String id) {
        int rowsDeleted = -1;
        try {
            remove(find(id));
            rowsDeleted = 1;
        } catch (ConstraintViolationException cve) {
            util.handleConstraintViolation(cve);
        } catch (Exception e) {
            System.out.println("problem deleting " + e.getMessage());
        }
        return rowsDeleted;
    }

    /**
     * @param dto expense DTO of all new information
     * @return int representing the PK of the new expense row.
     */
    public int add(ProductEJBDTO dto) {
        ProductsModel mod = util.loadModelFromDTO(dto, new ProductsModel(), em);
        int rowsAdded = -1;
        try {
            create(mod);
            em.flush();
            rowsAdded = 1;
        } catch (ConstraintViolationException cve) {
            util.handleConstraintViolation(cve);
        } catch (Exception e) {
            System.out.println("problem adding " + e.getMessage());
        }
        return rowsAdded;
    }

    /**
     * @param dto expense DTO of all updated information
     * @return int representing the number of rows updated.
     */
    public int update(ProductEJBDTO dto) {
        int prodUpdated = -1;
        try {
            edit(util.loadModelFromDTO(dto, find(dto.getId()), em));
            em.flush();
            prodUpdated = 1;
        } catch (ConstraintViolationException cve) {
            util.handleConstraintViolation(cve);
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }
        return prodUpdated;
    }

    /**
     * @return List of DTOs representing all expenses
     */
    public List<ProductEJBDTO> getAll() {
        List<ProductEJBDTO> productsDTO = new ArrayList();
        try {
            for (ProductsModel e : findAll()) {
                productsDTO.add(util.loadDTOFromModel(new ProductEJBDTO(), e, em));
            }
        } catch (ConstraintViolationException cve) {
            util.handleConstraintViolation(cve);
        } catch (Exception e) {
            //Handle other errors
            System.out.println("other issue " + e.getMessage());
        }
        return productsDTO;
    }
    

    /**
     * @param id int representing PK of expense table
     * @return DTO representing a single expense
     */
    public ProductEJBDTO getOne(String id) {
        return util.loadDTOFromModel(new ProductEJBDTO(), find(id), em);
    }
}
