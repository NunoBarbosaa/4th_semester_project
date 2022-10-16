package eapli.base.productmanagement.application;

import eapli.base.categorymanagement.domain.Category;
import eapli.base.categorymanagement.repository.CategoryRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.infrastructure.warehouse.WarehouseContainer;
import eapli.base.productmanagement.domain.*;
import eapli.base.productmanagement.repositories.ProductRepository;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.base.utils.Description;
import eapli.base.warehouse.domain.Aisle;
import eapli.base.warehouse.domain.Warehouse;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class SpecifyNewProductController {


    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final ProductRepository repo = PersistenceContext.repositories().products();
    private final CategoryRepository categoryRepository = PersistenceContext.repositories().categories();


    public Product specifyNewProduct(String intCode, Description shortD, Description extendedD, Barcode barcode) {
        authz.isAuthenticatedUserAuthorizedTo(BaseRoles.SALES_CLERK);
        return new Product(intCode, shortD, extendedD, barcode);
    }
    public Product specifyNewProduct(String intCode, Description shortD, Description extendedD, Barcode barcode, double weight, double volume, double pN, double p) {
        authz.isAuthenticatedUserAuthorizedTo(BaseRoles.SALES_CLERK);
        return new Product(intCode, shortD, extendedD, barcode,weight,volume,pN,p);
    }

    public void setProductionCode(String productionCode, Product p) {
        p.setProductionCode(productionCode);
    }

    public void setTechnicalDescription(Description tech, Product p) {
        p.setTechnicalDescription(tech);
    }

    public void setVolume(double vol, Product p) {
        p.setVolume(vol);
    }

    public void setWeight(double weight, Product p) {
        p.setWeight(weight);
    }

    public void setImages(List<byte[]> images, Product p) {
        p.setImages(images);
    }

    public void setBarcode(Barcode barcode, Product p) {
        p.setBarcode(barcode);
    }

    public void setReference(Reference ref, Product p) {
        p.setReference(ref);
    }

    public void setBrand(Brand brand, Product p) {
        p.setBrand(brand);
    }

    public void setPrice(Price price, Product p) {
        p.setPrice(price);
    }

    public void setCategory(Category c, Product p) {
        p.setCategory(c);
    }

    public void setLocation(WarehouseLocation w, Product p) {
        p.setWarehouseLocation(w);
    }

    public Product saveRepository(Product p) {
        return repo.save(p);
    }

    public Iterable<Category> getAllCategories() {
        return categoryRepository.activeCategories();
    }

    public boolean createWarehouseLocation(int aisle, int row, int shelf, Product p) {
        Warehouse w = WarehouseContainer.activeWarehouse();
        Set<Aisle> aisles = w.getAisles();
        Iterator<Aisle> it = aisles.iterator();
        int aisleExist = 0;
        while (it.hasNext()) {
            Aisle a = it.next();
            if (a.getId() == aisle)
                aisleExist = 1;
            if (a.getRowsIdAndRespectiveNumberOfShelves().containsKey(row)) {
                if (a.getRowsIdAndRespectiveNumberOfShelves().get(row) >= shelf && aisleExist == 1) {
                    setLocation(new WarehouseLocation(aisle, row, shelf), p);
                    return true;
                }
            }
        }
        return false;
    }

    public void loadPictures(File file, Product p, int counter, JFrame frame, List<byte[]> pics) {
        byte[] pic = new byte[(int) file.length()];
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(pic);
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Unsupported Picture! Try other");
        }
        pics.add(pic);
        BufferedImage bf = null;
        try {
            InputStream inputStream = new ByteArrayInputStream(pics.get(counter));
            bf = ImageIO.read(inputStream);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        frame.getContentPane().add(new JLabel(new ImageIcon(bf)));
        frame.pack();
        frame.setVisible(true);
    }


}
