package eapli.base.app.backoffice.console.presentation.productmanagement;

import eapli.base.categorymanagement.application.ListCategoryService;
import eapli.base.categorymanagement.domain.Category;
import eapli.base.productmanagement.application.ViewProductInfoController;
import eapli.base.productmanagement.application.ViewProductsCatalogController;
import eapli.base.productmanagement.domain.Product;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;


import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class ViewProductsCatalogUI extends AbstractUI {
    @Override
    protected boolean doShow() {

        ViewProductsCatalogController controller = new ViewProductsCatalogController();
        ViewProductInfoController viewController = new ViewProductInfoController();

        ListCategoryService service = new ListCategoryService();

        Map<Integer, String> mp = new TreeMap<>();

        mp.put(1, "Category");
        mp.put(2, "Description");
        mp.put(3, "Brand");
        mp.put(4, "Price");
        mp.put(5, "View all Products");
        mp.put(6, "Internal Code - Single Product");
        mp.put(7, "Barcode - Single Product");

        int filter_stay = 1;

        while (filter_stay == 1) {

            for (Map.Entry e : mp.entrySet()) {
                System.out.println(e.getKey() + " - " + e.getValue());
            }

            int option = Console.readInteger("What filter would you like to apply to the products that will be searched from the catalog?");

            Iterable<Product> products = null;


            switch (option) {
                case 1:
                    int cou = 1, cou2 = 1;
                    Iterator<Category> itt = service.allCategories().iterator();
                    Iterator<Category> itt2 = service.allCategories().iterator();
                    while (itt.hasNext()) {
                        System.out.println(cou + " - " + itt.next().description());
                        cou++;
                    }
                    boolean exit = false;
                    cou = Console.readInteger("Choose desired number");
                    while (itt2.hasNext() && exit == false) {
                        Category c = itt2.next();
                        if (cou2 == cou) {
                            products = controller.getProductsFilter(mp.get(option), c.identity());
                            exit = true;
                        }
                        cou2++;
                    }
                    break;
                case 2, 3:
                    String key = Console.readLine("What is/are the keyword(s) you are looking to apply to the filter?");
                    products = controller.getProductsFilter(mp.get(option), key);
                    break;
                case 4:
                    String order = Console.readLine("What is the order you'd like the products to be displayed? (ASC/DESC)");
                    System.out.println(order);
                    products = controller.getProductsFilter(mp.get(option), order);
                    break;
                case 5:
                    products = controller.getProductsFilter("all", "");
                    break;
                case 6, 7:
                    String code = Console.readLine("Insert desired search code");
                    products = controller.getProductsFilter(mp.get(option),code);
            }

            Iterator<Product> it;

            Map<Integer, Product> tempMap = new TreeMap<>();

            try {
                it = products.iterator();
            } catch (NullPointerException e) {
                System.out.println("An error has occured, please try again");
                System.out.println(e.getMessage());
                return false;
            }
            if(!it.hasNext()){
                System.out.println("No data was found with the search criteria provided");
            }
            else {
                int i = 1;
                while (it.hasNext()) {
                    Product p = it.next();
                    tempMap.put(i, p);
                    System.out.println(i + " - " + p + "\n");
                    i++;
                }
            }
            String choice = Console.readLine("Do you wish to choose another filter? (Y|N) Or get full attributes from a displayed product? (Type '1')");
            if (choice.equals("N"))
                filter_stay = 0;
            if (choice.equals("1")) {
                String id = Console.readLine("Please input the option number from the product you wish to search for further details (as seen above)");
                System.out.println(viewController.productAllInformation(tempMap.get(Integer.parseInt(id))));
                String pic = Console.readLine("Do you wish to see the product's pictures? (Y|N)");
                if (pic.equals("Y")) {
                    try {
                        System.out.println("Images!");
                        if(!viewController.displayPictures(tempMap.get(Integer.parseInt(id))))
                            System.out.println("Product has no registered pictures!");;
                    } catch (InterruptedException e) {
                        System.out.println("Product has no registered pictures");
                    }
                }
                String choice2 = Console.readLine("1 - Continue searching catalog \n2 - Exit");
                if (choice2.equals("2"))
                    filter_stay = 0;
            }


        }
        return false;
    }

    @Override
    public String headline() {
        return "Products Catalog";
    }
}
