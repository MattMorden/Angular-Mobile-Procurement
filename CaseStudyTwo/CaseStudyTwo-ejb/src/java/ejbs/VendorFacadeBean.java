package ejbs;

import dtos.VendorEJBDTO;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import models.VendorsModel;

/**
 * @author Matt
 */
@Stateless
@LocalBean
public class VendorFacadeBean extends AbstractFacade<VendorsModel> {

    @PersistenceContext
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    private final UtilityMethods util;

    public VendorFacadeBean() {
        super(VendorsModel.class);
        util = new UtilityMethods();
    }

    /**
     * @param dto employee DTO of all new information
     * @return integer representing the PK of the new employee row.
     */
    public int add(VendorEJBDTO dto) {
        VendorsModel mod = util.loadModelFromDTO(dto, new VendorsModel(), em);
        create(mod);
        em.flush();
        return mod.getId();
    }

    /**
     * @param id integer represent employee PK
     * @return integer representing the number of rows deleted.
     */
    public int delete(int id) {
        int rowsDeleted = -1;
        try {
            remove(find(id));
            em.flush();
            rowsDeleted = 1;
        } catch (ConstraintViolationException cve) {
            util.handleConstraintViolation(cve);
        } catch (Exception e) {
            System.out.println("problem deleting " + e.getMessage());
        }
        return rowsDeleted;
    }

    /**
     * @param dto employee DTO of all updated information
     * @return int representing the number of rows updated.
     */
    public int update(VendorEJBDTO dto) {
        int vendorUpdated = -1;
        try {
            edit(util.loadModelFromDTO(dto, find(dto.getId()), em));
            em.flush();
            vendorUpdated = 1;
        } catch (ConstraintViolationException cve) {
            util.handleConstraintViolation(cve);
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }
        return vendorUpdated;
    }

    /**
     * @return List of DTOs representing all employees
     */
    public List<VendorEJBDTO> getAll() {
        List<VendorEJBDTO> vendorsDTO = new ArrayList();
        try {
            for (VendorsModel entity : findAll()) {
                vendorsDTO.add(util.loadDTOFromModel(new VendorEJBDTO(), entity, em));
            }
        } catch (ConstraintViolationException cve) {
            util.handleConstraintViolation(cve);
        } catch (Exception e) {
            //Handle other errors
            System.out.println("other issue " + e.getMessage());
        }
        return vendorsDTO;
    }

    public VendorEJBDTO getOne(int id) {
        return util.loadDTOFromModel(new VendorEJBDTO(), find(id), em);
    }

}
