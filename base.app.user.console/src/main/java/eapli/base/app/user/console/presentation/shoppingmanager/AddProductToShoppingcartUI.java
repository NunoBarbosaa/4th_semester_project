package eapli.base.app.user.console.presentation.shoppingmanager;

import eapli.base.usermanagement.application.ClientOrderController;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;

import java.io.IOException;
import java.util.*;

public class AddProductToShoppingcartUI extends AbstractUI {


    ClientOrderController controller=new ClientOrderController();
    @Override
    protected boolean doShow() {

        try {
            controller.startsCommunication();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        String categories = null;

        String email=controller.getEmail();

        try {
            controller.sendEmail(email);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        int option=0;
        do{
            System.out.println("1- Add product to shopping cart");
            System.out.println("2- View/Remove product from shopping cart");
            System.out.println("0- Exit");
            Scanner in = new Scanner(System.in);
            option=in.nextInt();

            if(option==1){

                try {
                    categories=controller.getAllCategories();

                    List<String> categorieslistFinal = List.of(categories.split(";|#"));
                    List<String> alphanumericCodes = new ArrayList<>();
                    int j= 1;
                    for(int i =0; i<categorieslistFinal.size();i++)
                    {
                        System.out.println(j + "- " + categorieslistFinal.get(i));
                        i++;
                        alphanumericCodes.add(categorieslistFinal.get(i));
                        j++;
                    }

                    System.out.println("0 - All");
                    int option1= Console.readInteger("What filter you want to apply?");
                    String product="";
                    if (option1==0){
                        product = controller.getProductsFiltered("all","");
                    } else {
                        String filter= alphanumericCodes.get(option1-1);
                        product =controller.getProductsFiltered("Category",filter);
                    }

                    List<String> productList = List.of(product.split(";"));
                    for (int i = 0; i < productList.size(); i++) {
                        System.out.println(i+1 +" - "+ productList.get(i));
                    }
                    int option2 = Console.readInteger("What product you want to add to the shopping cart?");
                    String burla= productList.get(option2-1);
                    List<String> codProd = List.of(burla.split(" "));
                    int quantity = Console.readInteger("What quantity?");
                    controller.sendProduct(codProd.get(0),quantity);

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }


            }else if(option==2){
                try {

                    String products;
                    products=controller.getShoppingChart();
                    if(products.isEmpty()){
                        System.out.println("Shopping cart's empty.");
                    }
                    else{
                    List<String> scProducts= Arrays.asList(products.split(";"));
                    System.out.println(scProducts);
                    List<String> productsCode = new ArrayList<>();
                    System.out.println("ID -name - quantity - price");
                    int y=1;
                    for (int i = 0; i < scProducts.size() ; i++) {
                        System.out.println("Product " +y+"-"+scProducts.get(i));
                        List<String> product=Arrays.asList(scProducts.get(i).split("#"));
                        productsCode.add(product.get(0));
                        y++;

                    }

                    int removeQuestion = Console.readInteger("Do you wish to remove any items? 1-Yes 2-No");
                    if(removeQuestion==1){
                        int removeProd= Console.readInteger("Which product do you want to remove?");
                        int quantity = Console.readInteger("In what quantity?");
                        controller.removeProduct(productsCode.get(removeProd-1),quantity);

                    }}

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

            }

        }while(option!=0);


        try {
            controller.disconnect();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        return false;
    }

    @Override
    public String headline() {
        return "List of Products:";
    }

}
