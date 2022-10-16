package eapli.base.app.backoffice.console.presentation.productmanagement;

import eapli.base.categorymanagement.domain.Category;
import eapli.base.productmanagement.application.SpecifyNewProductController;
import eapli.base.productmanagement.domain.*;
import eapli.base.utils.Description;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;

public class SpecifyNewProductUI extends AbstractUI {

    @Override
    protected boolean doShow() {
        int stay = 1;
        int images_stay = 1;

        SpecifyNewProductController controller = new SpecifyNewProductController();

        Map<Integer, String> mp = new TreeMap<>();
        Map<Integer, Category> categories = new TreeMap<>();

        mp.put(0, "Production Code");
        mp.put(1, "Technical Description");
        mp.put(2, "Volume");
        mp.put(3, "Weight");
        mp.put(4, "Images");
        mp.put(5, "Barcode");
        mp.put(6, "Reference");
        mp.put(7, "Brand");
        mp.put(8, "Price");
        mp.put(9, "Warehouse Location");
        mp.put(10, "Save Product");

        Product p;

        String intCode, sDesc, eDesc, barc;

        double weight, volume, priceNo,price;

        intCode = Console.readLine("Internal code:");

        sDesc = Console.readLine("Short description");

        eDesc = Console.readLine("Extended description");

        barc = Console.readLine("Barcode (EAN13)");

        weight = Console.readDouble("Weight:");

        volume = Console.readDouble("Volume:");

        priceNo = Console.readDouble("Price with no taxes:");
        price = Console.readDouble("Price with taxes:");

        Iterator<Category> it = controller.getAllCategories().iterator();

        int j = 1, chosenCategory;

        while (it.hasNext()) {
            Category c = it.next();
            categories.put(j, c);
            System.out.println(j + " - " + c.description());
            j++;
        }

        chosenCategory = Console.readInteger("Choose the category to be associated to the product");


        String ver = Console.readLine("All mandatory data has been inputted, do you wish to continue to specify more attributes for the product? (Y|N)");

        try {
            p = controller.specifyNewProduct(intCode, new Description(sDesc), new Description(eDesc), new Barcode(barc),weight,volume,priceNo,price);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Can not create product, please try again");
            return false;
        }

        controller.setCategory(categories.get(chosenCategory), p);


        if (ver.equals("Y")) {

            while (stay == 1) {
                for (int i = 0; i < mp.size(); i++) {
                    System.out.println((i + 1) + " - " + mp.get(i));
                }
                int option = Console.readInteger("Type in option number");

                switch (option) {
                    case 1:
                        String prodCode = Console.readLine("Input production code:");
                        controller.setProductionCode(prodCode, p);
                        break;
                    case 2:
                        Description tech = new Description(Console.readLine("Input techincal description"));
                        controller.setTechnicalDescription(tech, p);
                        break;
                    case 3:
                        double volumee = Console.readDouble("Input product volume:");
                        controller.setVolume(volumee, p);
                        break;
                    case 4:
                        double weightt = Console.readDouble("Input product weight:");
                        controller.setWeight(weightt, p);
                        break;
                    case 5:
                        int counter = 0;
                        JFileChooser chooser = new JFileChooser();
                        JFrame frame = new JFrame();
                        frame.getContentPane().setLayout(new FlowLayout());
                        List<byte[]> pics = new ArrayList<>();
                        while (images_stay == 1) {
                            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                                controller.loadPictures(chooser.getSelectedFile(),p,counter,frame,pics);
                                String optio = Console.readLine("Do you wish to add more images? (Y|N)");
                                if (optio.equals("N"))
                                    images_stay = 0;
                            } else {
                                images_stay = 0;
                            }
                            counter++;
                        }
                        controller.setImages(pics,p);
                        break;
                    case 6:
                        Barcode barcode = new Barcode(Console.readLine("Input barcode data (EAN13)"));
                        controller.setBarcode(barcode, p);
                        break;
                    case 7:
                        Reference ref = new Reference(Console.readLine("Input reference:"));
                        controller.setReference(ref, p);
                        break;
                    case 8:
                        Brand brand = new Brand(Console.readLine("Input brand name"));
                        controller.setBrand(brand, p);
                        break;
                    case 9:
                        Price pricee = new Price(Console.readDouble("Input price without taxes"), Console.readDouble("Input price with taxes"));
                        controller.setPrice(pricee, p);
                        break;
                    case 10:
                        int idAisle = Console.readInteger("Please input aisle identifier"), idRow = Console.readInteger("Please input row identifier"), shelf = Console.readInteger("Please input shelf number");
                        if (!controller.createWarehouseLocation(idAisle, idRow, shelf, p))
                            System.out.println("Provided Location does not exist in the warehouse! Please try again");
                        break;
                    case 11:
                        stay = 0;
                        break;
                }

            }
        }
        try {
            controller.saveRepository(p);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(p);
        System.out.println("Product has been successfully created!");
        return true;
    }

    @Override
    public String headline() {
        return "Specify new Product";
    }
}
