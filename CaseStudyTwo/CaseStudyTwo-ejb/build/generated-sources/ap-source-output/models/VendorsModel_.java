package models;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import models.ProductsModel;
import models.PurchaseordersModel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-12-09T13:27:00")
@StaticMetamodel(VendorsModel.class)
public class VendorsModel_ { 

    public static volatile CollectionAttribute<VendorsModel, ProductsModel> productsModelCollection;
    public static volatile SingularAttribute<VendorsModel, String> province;
    public static volatile SingularAttribute<VendorsModel, String> city;
    public static volatile SingularAttribute<VendorsModel, String> phone;
    public static volatile SingularAttribute<VendorsModel, String> address1;
    public static volatile SingularAttribute<VendorsModel, String> postalcode;
    public static volatile SingularAttribute<VendorsModel, String> name;
    public static volatile SingularAttribute<VendorsModel, Integer> id;
    public static volatile SingularAttribute<VendorsModel, String> type;
    public static volatile SingularAttribute<VendorsModel, String> email;
    public static volatile CollectionAttribute<VendorsModel, PurchaseordersModel> purchaseordersModelCollection;

}