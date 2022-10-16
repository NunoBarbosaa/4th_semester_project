package eapli.base.app.backoffice.console.presentation.order;

import eapli.base.app.backoffice.console.presentation.customer.RegisterCustomerUI;
import eapli.base.app.backoffice.console.presentation.productmanagement.ViewProductsCatalogUI;
import eapli.base.customermanagement.application.ListCustomerInformationController;
import eapli.base.customermanagement.dto.AddressDTO;
import eapli.base.ordermanagement.application.AddOrderController;
import eapli.base.ordermanagement.domain.ShipmentMethod;
import eapli.base.ordermanagement.dto.OrderItemDTO;
import eapli.base.productmanagement.application.ViewProductsCatalogController;
import eapli.base.productmanagement.domain.Product;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class AddOrderUI extends AbstractUI {

    private final ViewProductsCatalogController viewProductsCatalogController = new ViewProductsCatalogController();
    private final AddOrderController addOrderController = new AddOrderController();
    private final ListCustomerInformationController listCustomerInformationController = new ListCustomerInformationController();
    public static final String BILLING_ADDRESS = "BILLING";
    public static final String DELIVERING_ADDRESS = "DELIVERING";


    @Override
    protected boolean doShow() {
        boolean error = true, vatInvalid;
        int option, tryAgain = -1;
        String vat;
        do{

            // vat of the customer

            do {
                vat = Console.readLine("\nCustomer VAT: ");
                try{
                    addOrderController.checkIfCustomerIsValid(vat);
                    vatInvalid = true;
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.out.println("\nInvalid VAT.");
                    System.out.println("1 - Pretend to add a Customer\n2 - Try again?\n");
                    option = Console.readOption(1, 2, 2);
                    if(option == 1){
                        RegisterCustomerUI registerCustomerUI = new RegisterCustomerUI();
                        registerCustomerUI.show();
                    }
                    vatInvalid = false;
                }
            } while(!vatInvalid);

            // catalog

            System.out.println("\nDo you want to see the products catalog?");
            System.out.println("1 - Yes\n2 - No");
            int optionCatalog = Console.readOption(1, 2, 2);
            if (optionCatalog == 1) {
                ViewProductsCatalogUI viewProductsCatalogUI = new ViewProductsCatalogUI();
                viewProductsCatalogUI.show();
            }
            System.out.println();

            // product

            String internalCode;
            int quantity, optionAddMoreProducts;
            boolean invalidProduct;
            double priceWithTaxes = 0, priceWithoutTaxes = 0, totalPriceWithTaxes = 0, totalPriceWithouTaxes = 0;
            Iterable<Product> product;
            OrderItemDTO orderItemDTO;
            List<OrderItemDTO> orderItemDTOList = new ArrayList<>();
            do{
                do {
                    internalCode = Console.readLine("Product Internal Code:");
                    try {
                        product = viewProductsCatalogController.getProductsFilter("Internal Code - Single Product", internalCode);
                        priceWithTaxes = product.iterator().next().getPrice().getPriceWithTaxes();
                        priceWithoutTaxes = product.iterator().next().getPrice().getPriceWithoutTaxes();
                        invalidProduct = false;
                    } catch (Exception e) {
                        System.out.println("Product does not exist.\n");
                        invalidProduct = true;
                    }
                } while (invalidProduct);
                quantity = Console.readInteger("Quantity:");
                totalPriceWithouTaxes += priceWithoutTaxes * quantity;
                totalPriceWithTaxes += priceWithTaxes * quantity;
                orderItemDTO = new OrderItemDTO(internalCode, quantity, priceWithoutTaxes, priceWithTaxes);
                orderItemDTOList.add(orderItemDTO);
                System.out.println("\nDo you want to add more products?");
                System.out.println("1 - Yes\n2 - No");
                optionAddMoreProducts = Console.readOption(1, 2, 2);
                System.out.println();
            } while (optionAddMoreProducts == 1);

            // addresses

            List<AddressDTO> addressDTOList = listCustomerInformationController.customerAddresses(vat);

            List<AddressDTO> billingAddressesDTOList = subListOfAddresses(addressDTOList, BILLING_ADDRESS);
            int index, indexAddress, optionBillingAddress, optionDeliveringAddress;
            AddressDTO billingAddress, deliveringAddress;
            System.out.println("Do you want to use the client billing postal address?");
            System.out.println("1 - Yes\n2 - No");
            optionBillingAddress = Console.readOption(1, 2, 2);
            System.out.println();
            if(optionBillingAddress == 1){
                if(billingAddressesDTOList.isEmpty()) {
                    System.out.println("Customer does not have billing addresses.\n");
                    billingAddress = readAddress(BILLING_ADDRESS);
                }
                else {
                    index = listAddress(billingAddressesDTOList);
                    do {
                        System.out.println("\nInput the index of the address.");
                        indexAddress = Console.readInteger("Index: ");
                    } while (indexAddress > index || indexAddress < 1);
                    billingAddress = addressDTOList.get(indexAddress - 1);
                }
            }
            else{
                billingAddress = readAddress(BILLING_ADDRESS);
            }
            List<AddressDTO> deliveringAddressesDTOList = subListOfAddresses(addressDTOList, DELIVERING_ADDRESS);
            System.out.println("\nDo you want to use the client delivering postal address?");
            System.out.println("1 - Yes\n2 - No");
            optionDeliveringAddress = Console.readOption(1, 2, 2);
            System.out.println();
            if (optionDeliveringAddress == 1){
                if(deliveringAddressesDTOList.isEmpty()) {
                    System.out.println("Customer does not have delivering addresses.\n");
                    deliveringAddress = readAddress(DELIVERING_ADDRESS);
                }
                else {
                    System.out.println();
                    index = listAddress(deliveringAddressesDTOList);
                    do {
                        System.out.println("\nInput the index of the address.");
                        indexAddress = Console.readInteger("Index: ");
                    } while (indexAddress > index || indexAddress < 1);
                    deliveringAddress = addressDTOList.get(indexAddress - 1);
                }
            }
            else{
                deliveringAddress = readAddress(DELIVERING_ADDRESS);
            }

            // shipment method

            int shipmentMethod, indexMethod = 1;
            System.out.println("\nShipment Method:");
            for (ShipmentMethod sm : EnumSet.allOf(ShipmentMethod.class)) {
                System.out.println(indexMethod + " - " + sm);
                indexMethod++;
            }
            do{
                shipmentMethod = Console.readInteger("\nMethod: ");
            } while(shipmentMethod > indexMethod || shipmentMethod < 1);

            // payment method

            int paymentMethod;
            System.out.println("\nPayment Method:\n1 - Paypal\n2 - Apple Pay\n");
            do {
                paymentMethod = Console.readInteger("Method: ");
            } while (paymentMethod > 2 || paymentMethod < 1);

            try {
                addOrderController.newOrder(vat, orderItemDTOList, totalPriceWithTaxes, totalPriceWithouTaxes,
                        billingAddress, deliveringAddress, shipmentMethod, paymentMethod);
                error = false;
            } catch (Exception e) {
                System.out.println("\n"+ e.getMessage());
                System.out.println("Do you want to try again?");
                System.out.println("1 - Yes\n2 - No");
                tryAgain = Console.readOption(1, 2, 2);
                if (tryAgain == 1){
                    System.out.println();
                }
                else{
                    error = false;
                }
            }
        } while(error);
        if(tryAgain == -1){
            System.out.println("\nSuccess of the operation.");
            return true;
        }
        else return false;
    }

    private List<AddressDTO> subListOfAddresses(List<AddressDTO> addressDTOList, String type) {
        List<AddressDTO> list = new ArrayList<>();
        for (AddressDTO addressDTO : addressDTOList){
            if(addressDTO.type().equals(type)){
                list.add(addressDTO);
            }
        }
        return list;
    }

    private int listAddress(List<AddressDTO> addressDTOList) {
        int index = 0;
        for (AddressDTO addressDTO : addressDTOList) {
            System.out.println("\nIndex: " + (index + 1));
            System.out.println("Postal Code: " + addressDTO.postalCode());
            System.out.println("Street: " + addressDTO.street());
            System.out.println("Door Number: " + addressDTO.doorNumber());
            System.out.println("City: " + addressDTO.city());
            System.out.println("Country: " + addressDTO.country());
            index++;
            if(index > 1) System.out.println();
        }
        return index;
    }

    private AddressDTO readAddress(String type) {
        String postalCode = Console.readLine("Postal Code: ");
        String street = Console.readLine("Street: ");
        int doorNumber = Console.readInteger("Door Number: ");
        String city = Console.readLine("City: ");
        String country = Console.readLine("Country: ");
        return new AddressDTO(postalCode, street, doorNumber, city, country, type);
    }

    @Override
    public String headline() {
        return "Add order";
    }
}
