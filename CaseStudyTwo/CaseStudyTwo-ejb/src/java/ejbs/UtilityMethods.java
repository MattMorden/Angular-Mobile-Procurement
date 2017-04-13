package ejbs;

import dtos.VendorEJBDTO;
import dtos.ProductEJBDTO;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import models.VendorsModel;
import models.ProductsModel;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

/**
 * @author Matt
 */
public class UtilityMethods {

    public VendorsModel loadModelFromDTO(VendorEJBDTO dto, VendorsModel mod, EntityManager em) {
        mod.setId(dto.getId());
        mod.setAddress1(dto.getAddress1());
        mod.setCity(dto.getCity());
        mod.setProvince(dto.getProvince());
        mod.setPostalcode(dto.getPostalcode());
        mod.setPhone(dto.getPhone());
        mod.setType(dto.getType());
        mod.setName(dto.getName());
        mod.setEmail(dto.getEmail());
        return mod;
    }

    public VendorEJBDTO loadDTOFromModel(VendorEJBDTO dto, VendorsModel mod, EntityManager em) {
        dto.setId(mod.getId());
        dto.setAddress1(mod.getAddress1());
        dto.setCity(mod.getCity());
        dto.setProvince(mod.getProvince());
        dto.setPostalcode(mod.getPostalcode());
        dto.setPhone(mod.getPhone());
        dto.setType(mod.getType());
        dto.setName(mod.getName());
        dto.setEmail(mod.getEmail());
        return dto;
    }

    public ProductsModel loadModelFromDTO(ProductEJBDTO dto, ProductsModel mod, EntityManager em) {
        VendorsModel empmod = em.find(VendorsModel.class, dto.getVendorid());
        mod.setVendorid(empmod);
        mod.setId(dto.getId());
        mod.setVendorsku("a");
        //mod.setVendorid(dto.getVendorid().);
        mod.setName(dto.getName());
        mod.setCostprice(dto.getCostprice());
        mod.setMsrp(dto.getMsrp());
        mod.setRop(dto.getRop());
        mod.setEoq(dto.getEoq());
        mod.setQoh(dto.getQoh());
        mod.setQoo(dto.getQoo());
        mod.setQrcodetxt(dto.getQrcodetxt());
        mod.setQrcode(QRCode.from(dto.getQrcodetxt()).to(ImageType.PNG).stream().toByteArray());

        return mod;
    }

    public ProductEJBDTO loadDTOFromModel(ProductEJBDTO dto, ProductsModel mod, EntityManager em) {
        dto.setId(mod.getId());
        dto.setVendorid(mod.getVendorid().getId());
        dto.setVendorsku("a");
        dto.setName(mod.getName());
        dto.setCostprice(mod.getCostprice());
        dto.setMsrp(mod.getMsrp());
        dto.setRop(mod.getRop());
        dto.setEoq(mod.getEoq());
        dto.setQoh(mod.getQoh());
        dto.setQoo(mod.getQoo());
        dto.setQrcodetxt(mod.getQrcodetxt());
        dto.setQrcode((byte[]) mod.getQrcode());
        return dto;
    }

    public void handleConstraintViolation(ConstraintViolationException cve) {
        Set<ConstraintViolation<?>> cvs = cve.getConstraintViolations();
        for (ConstraintViolation<?> cv : cvs) {
            System.out.println("------------------------------------------------");
            System.out.println("Violation: " + cv.getMessage());
            System.out.println("Entity: " + cv.getRootBeanClass().getSimpleName());
            // The violation occurred on a leaf bean (embeddable)
            if (cv.getLeafBean() != null && cv.getRootBean() != cv.getLeafBean()) {
                System.out.println("Embeddable: "
                        + cv.getLeafBean().getClass().getSimpleName());
            }
            System.out.println("Attribute: " + cv.getPropertyPath());
            System.out.println("Invalid value: " + cv.getInvalidValue());
        }
    }

    public String formatDate(Date date) {
        String formattedDate = "";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            formattedDate = df.format(date);
        } catch (Exception e) {
        }
        return formattedDate;
    }

    public Date formatDate(String date) {
        Date formattedDate = null;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            formattedDate = df.parse(date);
        } catch (Exception e) {
        }
        return formattedDate;
    }

}
