package base.app.ordersserver.thread;

import eapli.base.categorymanagement.application.ListCategoryService;
import eapli.base.categorymanagement.domain.Category;
import eapli.base.customermanagement.domain.Customer;
import eapli.base.customermanagement.repositories.CustomerRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.infrastructure.spomsp.CommProtocol;
import eapli.base.ordermanagement.domain.Order;
import eapli.base.ordermanagement.domain.OrderItem;
import eapli.base.ordermanagement.repositories.OrderRepository;
import eapli.base.productmanagement.application.ViewProductsCatalogController;
import eapli.base.productmanagement.domain.Product;
import eapli.base.productmanagement.repositories.ProductRepository;
import eapli.base.shoppingcart.domain.ShoppingCart;
import eapli.base.shoppingcart.domain.ShoppingCartItem;
import eapli.base.shoppingcart.repositories.ShoppingCartRepository;
import eapli.base.surveys.domain.Survey;
import eapli.base.surveys.domain.SurveyAnswer;
import eapli.base.surveys.repositories.SurveyAnswersRepository;
import eapli.base.surveys.repositories.SurveyRepository;
import eapli.base.surveys.repositories.SurveyTargetRepository;


import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class OrderServerThread implements Runnable {

    private Socket socket;

    private DataOutputStream sOut;
    private DataInputStream sIn;
    private BufferedOutputStream bufferedOut;
    private BufferedInputStream bufferedIn;

    private Customer currentCustomer;
    private ShoppingCart cart;

    private final OrderRepository orderRepository = PersistenceContext.repositories().orders();
    private final ProductRepository productRepo = PersistenceContext.repositories().products();
    private final ShoppingCartRepository cartRepository = PersistenceContext.repositories().shoppingCart();
    private final CustomerRepository customerRepository = PersistenceContext.repositories().customers();

    private final SurveyTargetRepository surveyTargetRepository = PersistenceContext.repositories().surveyTargets();

    private final SurveyAnswersRepository surveyAnswersRepository = PersistenceContext.repositories().surveyAnswers();

    private final ListCategoryService service = new ListCategoryService();

    private final ViewProductsCatalogController viewCatalogController = new ViewProductsCatalogController();


    public OrderServerThread(Socket socket) {
        this.socket = socket;
    }


    @Override
    public void run() {
        InetAddress clientAddress = socket.getInetAddress();

        System.out.printf("%n%nNew Connection from: %s%n", clientAddress.getHostAddress());
        try {
            sOut = new DataOutputStream(socket.getOutputStream());
            sIn = new DataInputStream(socket.getInputStream());
            bufferedOut = new BufferedOutputStream(sOut);
            bufferedIn = new BufferedInputStream(sIn);

            while (socket.isConnected()) {
                byte[] arr = sIn.readNBytes(4);
                if (replyMessage(arr, bufferedOut)) break;
            }
            socket.close();
            System.out.println("INFO: Connection Closed");
        } catch (IOException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private boolean replyMessage(byte[] arr, BufferedOutputStream bufferedOut) throws IOException {
        if (arr[0] == CommProtocol.PROTOCOL_V1 && arr[1] == CommProtocol.COMM_TEST_CODE) {
            System.out.println("INFO: CommTest received");
            bufferedOut.write(CommProtocol.ACK_MESSAGE_V1);
            bufferedOut.flush();
        }

        if (arr[0] == CommProtocol.PROTOCOL_V1 && arr[1] == CommProtocol.DISCONN_CODE) {
            System.out.println("INFO: End of session request received");
            bufferedOut.write(CommProtocol.ACK_MESSAGE_V1);
            bufferedOut.flush();
            return true;
        }

        if (arr[0] == CommProtocol.PROTOCOL_V1 && arr[1] == CommProtocol.CUSTOMER_ID_CODE) {
            System.out.println("INFO: Customer connected");
            handleClient(arr);
        }


        if (arr[0] == CommProtocol.PROTOCOL_V1 && arr[1] == CommProtocol.CART_ADD_CODE) {
            System.out.println("INFO: Adding product to cart");
            handleCartUpdate(arr);
        }


        if (arr[0] == CommProtocol.PROTOCOL_V1 && arr[1] == CommProtocol.CART_REMOVE_CODE) {
            System.out.println("INFO: Removing product from cart");
            handleCartRemove(arr);
        }

        if (arr[0] == CommProtocol.PROTOCOL_V1 && arr[1] == CommProtocol.ACK_CODE) {
            System.out.println("INFO: Acknowledge received!");
        }


        if (arr[0] == CommProtocol.PROTOCOL_V1 && arr[1] == CommProtocol.CATALOG_CODE) {
            System.out.println("INFO: Catalog provided");
            handleProductCatalog(arr);
        }

        if (arr[0] == CommProtocol.PROTOCOL_V1 && arr[1] == CommProtocol.CATEGORIES_CODE) {
            System.out.println("INFO: Categories provided");
            handleCategories();
        }
        if (arr[0] == CommProtocol.PROTOCOL_V1 && arr[1] == CommProtocol.SHOPCHART_CODE) {
            System.out.println("INFO: Shop cart provided");
            handleShoppingCart();
        }
        if (arr[0] == CommProtocol.PROTOCOL_V1 && arr[1] == CommProtocol.CLIENT_ORDERS_CODE) {
            System.out.println("INFO: Orders being handled");
            handleClientOrders();
        }
        if (arr[0] == CommProtocol.PROTOCOL_V1 && arr[1] == CommProtocol.ORDER_DETAILED_INFO_CODE) {
            System.out.println("INFO: Order being detailed");
            handleOrdersProducts(arr);
        }

        if(arr[0] == CommProtocol.PROTOCOL_V1 && arr[1] == CommProtocol.NR_SURVEYS_REQUEST) {
            System.out.println("INFO: Received request for number of customer unanswered surveys");
            handleNrSurveysRequest(arr);
        }

        if(arr[0] == CommProtocol.PROTOCOL_V1 && arr[1] == CommProtocol.LIST_SURVEYS_REQUEST) {
            System.out.println("INFO: Received request for customer unanswered surveys");
            handleUnansweredSurveysRequest(arr);
        }

        if(arr[0] == CommProtocol.PROTOCOL_V1 && arr[1] == CommProtocol.SAVE_ANSWERS) {
            System.out.println("INFO: Received request to save customer's survey answers");
            handleSaveSurveyAnswers(arr);
        }
        return false;
    }

    private void handleSaveSurveyAnswers(byte[] arr) throws IOException {
        ObjectInputStream oIn = new ObjectInputStream(socket.getInputStream());

        try {
            List<SurveyAnswer> answers = (List<SurveyAnswer>) oIn.readObject();
            String surveyId = answers.get(0).surveyId();
            String sysUser = answers.get(0).answeredBy();

            for(SurveyAnswer answer : answers){
                surveyAnswersRepository.save(answer);
            }

            if(surveyTargetRepository.updateTargetedCustomer(surveyId, sysUser)){
                sOut.write(CommProtocol.ACK_MESSAGE_V1);
                sOut.flush();
            }else{
                sOut.write(new byte[]{1,CommProtocol.ACK_CODE_ERROR,0,0});
                sOut.flush();
            }

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleUnansweredSurveysRequest(byte[] arr) {
        int dataSize = (arr[2] & 0xff) + 256 * (arr[3] & 0xff);

        try {
            byte[] data = bufferedIn.readNBytes(dataSize);
            String customerUsername = new String(data);
            List<Survey> surveys = surveyTargetRepository.surveysForCustomer(customerUsername);

            ObjectOutputStream oOut = new ObjectOutputStream(socket.getOutputStream());

            oOut.writeObject(surveys);
            oOut.flush();

        }catch (IOException e){
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void handleNrSurveysRequest(byte[] arr) {
        int dataSize = (arr[2] & 0xff) + 256 * (arr[3] & 0xff);

        try {
            byte[] data = bufferedIn.readNBytes(dataSize);
            String customerUsername = new String(data);
            int surveysCount = surveyTargetRepository.nrSurveysForCustomer(customerUsername);

            bufferedOut.write(CommProtocol.PROTOCOL_V1);
            bufferedOut.write(CommProtocol.NR_SURVEYS_REPLY);
            bufferedOut.write(new byte[]{1,0});
            bufferedOut.write((byte)surveysCount);
            bufferedOut.flush();
        } catch (IOException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void handleClient(byte[] arr) {
        int dataSize = (arr[2] & 0xff) + 256 * (arr[3] & 0xff);

        try {
            byte[] data = bufferedIn.readNBytes(dataSize);
            String[] dataSplit = new String(data).split(";");
            String id = dataSplit[0];
            Customer cust = customerRepository.findByEmail(id);
            if (cust == null) {
                System.out.println("CUSTOMER NOT FOUND\n");
                bufferedOut.write(new byte[]{CommProtocol.PROTOCOL_V1, CommProtocol.ACK_CODE_ERROR, 0, 0});
                bufferedOut.flush();
                return;
            }
            this.currentCustomer = cust;
            ShoppingCart sc = null;
            int i = 0;
            try {
                sc = cartRepository.findByClient(currentCustomer.identity().toString(), ShoppingCart.class).getSingleResult();
            } catch (Exception e) {

                ShoppingCart newCart = new ShoppingCart(currentCustomer);
                cartRepository.save(newCart);
                this.cart = newCart;
                i++;
            }
            if (i == 0)
                this.cart = sc;
            bufferedOut.write(new byte[]{CommProtocol.PROTOCOL_V1, CommProtocol.ACK_CODE, 0, 0});
            bufferedOut.flush();
            System.out.println("INFO: Customer successfully connected");
        } catch (IOException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    //id;quantidade
    private void handleCartUpdate(byte[] arr) {
        int dataSize = (arr[2] & 0xff) + 256 * (arr[3] & 0xff);

        try {
            byte[] data = bufferedIn.readNBytes(dataSize);
            String[] dataSplit = new String(data).split(";");
            String productId = dataSplit[0];
            int quantity = Integer.parseInt(dataSplit[1]);
            Product p = productRepo.findById(productId, Product.class).getSingleResult();
            ShoppingCartItem newItem = new ShoppingCartItem(quantity, p);
            if (!cart.alreadyContains(newItem)) {
                cart.addItem(newItem);
            }
            cartRepository.save(cart);
            bufferedOut.write(new byte[]{CommProtocol.PROTOCOL_V1, CommProtocol.ACK_CODE, 0, 0});
            bufferedOut.flush();
            System.out.println("INFO: Cart updated");
        } catch (IOException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }


    private void handleCartRemove(byte[] arr) {
        int dataSize = (arr[2] & 0xff) + 256 * (arr[3] & 0xff);

        try {
            byte[] data = bufferedIn.readNBytes(dataSize);
            String[] dataSplit = new String(data).split(";");
            String productId = dataSplit[0];
            ShoppingCartItem p = cartRepository.findCartItem(productId, ShoppingCartItem.class).getSingleResult();
            cart.removeItem(p);
            cartRepository.save(cart);
            cartRepository.removeShoppingCartItem(p, ShoppingCart.class);
            bufferedOut.write(new byte[]{CommProtocol.PROTOCOL_V1, CommProtocol.ACK_CODE, 0, 0});
            bufferedOut.flush();
            System.out.println("INFO: Cart updated (remove)");
        } catch (IOException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    //Envio:tipodefiltro(igual a controller);filtro
    //Recebo:ic - description - price - extendedDescription;
    private void handleProductCatalog(byte arr[]) {
        int dataSize = (arr[2] & 0xff) + 256 * (arr[3] & 0xff);

        try {
            byte[] data = bufferedIn.readNBytes(dataSize);
            String[] dataSplit = new String(data).split(";");
            String filter = dataSplit[0];
            String key = "";
            if (dataSplit.length != 1) {
                key = dataSplit[1];
            }
            Iterable<Product> products = viewCatalogController.getProductsFilter(filter, key);

            Iterator<Product> it = products.iterator();

            if (!it.hasNext()) {
                System.out.println("No products were found!");
                String sc = "";
                bufferedOut.write(new byte[]{CommProtocol.PROTOCOL_V1, CommProtocol.CATALOG_CODE, 0, 0});
                bufferedOut.write(sc.getBytes());
                bufferedOut.flush();
                return;
            }
            StringBuilder sc = new StringBuilder();

            while (it.hasNext()) {
                Product p = it.next();
                sc.append(p.getInternalCode()).append(" - ").append(p.getShortDescription()).append(" - ").append(p.getPrice().getPriceWithTaxes()).append(" - ").append(p.getExtendedDescription()).append(";");
            }

            System.out.println(sc);
            sc.deleteCharAt(sc.length() - 1);
            int[] sizeInt = CommProtocol.dataSizeCalculator(sc.toString());
            byte[] size = new byte[]{(byte)sizeInt[0], (byte)sizeInt[1]};
            bufferedOut.write(new byte[]{CommProtocol.PROTOCOL_V1, CommProtocol.CATALOG_CODE, size[0], size[1]});
            bufferedOut.write(sc.toString().getBytes());
            bufferedOut.flush();
        } catch (IOException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    //description;description2
    private void handleCategories() {
        Iterable<Category> categories = service.allCategories();
        Iterator<Category> it = categories.iterator();
        StringBuilder sc = new StringBuilder();

        while (it.hasNext()) {
            Category c = it.next();
            sc.append(c.description()).append("#").append(c.identity()).append(";");
        }
        sc.deleteCharAt(sc.length() - 1);
        int[] sizeInt = CommProtocol.dataSizeCalculator(sc.toString());
        byte[] size = new byte[]{(byte) sizeInt[0], (byte) sizeInt[1]};
        try {
            bufferedOut.write(new byte[]{CommProtocol.PROTOCOL_V1, CommProtocol.CATEGORIES_CODE, size[0], size[1]});
            bufferedOut.write(sc.toString().getBytes());
            bufferedOut.flush();
        } catch (IOException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void handleShoppingCart() throws IOException {
        Iterable<ShoppingCartItem> tp = this.cart.getItemList();
        // Iterable<ShoppingCartItem> tp = cartRepository.getShoppingCart(currentCustomer.identity().toString(),ShoppingCartItem.class).getResultList();
        Iterator<ShoppingCartItem> it = tp.iterator();
        StringBuilder sc = new StringBuilder();
        if (!it.hasNext()) {
            bufferedOut.write(new byte[]{CommProtocol.PROTOCOL_V1, CommProtocol.ACK_CODE, 0, 0});
            bufferedOut.flush();
            return;
        }

        while (it.hasNext()) {
            ShoppingCartItem c = it.next();
            sc.append(c.getId()).append("#").append(c.getShortDesc()).append("-").append(c.getQuantity()).append("-").append(c.getTotalPrice()).append(";");
        }
        System.out.println(sc);

        sc.deleteCharAt(sc.length() - 1);
        int[] sizeInt = CommProtocol.dataSizeCalculator(sc.toString());
        byte[] size = new byte[]{(byte) sizeInt[0], (byte) sizeInt[1]};
        try {
            bufferedOut.write(new byte[]{CommProtocol.PROTOCOL_V1, CommProtocol.SHOPCHART_CODE, size[0], size[1]});
            bufferedOut.write(sc.toString().getBytes());
            bufferedOut.flush();
        } catch (IOException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void handleClientOrders() throws IOException {
        Iterable<Order> orders = orderRepository.findClientOrders(currentCustomer.identity().toString());
        Iterator<Order> it = orders.iterator();
        if (!it.hasNext()) {
            bufferedOut.write(new byte[]{CommProtocol.PROTOCOL_V1, CommProtocol.ACK_CODE, 0, 0});
            bufferedOut.flush();
            return;
        }
        StringBuilder sc = new StringBuilder();
        while (it.hasNext()) {
            Order c = it.next();
            System.out.println(c);
            sc.append(c.identity()).append("#").append(c.getDateTime().toString()).append("-").append(c.totalPrice().priceWithTaxes()).append("-").append(c.getOrderStatus().toString()).append(";");
        }
        int[] sizeInt = CommProtocol.dataSizeCalculator(sc.toString());
        byte[] size = new byte[]{(byte) sizeInt[0], (byte) sizeInt[1]};
        try {
            bufferedOut.write(new byte[]{CommProtocol.PROTOCOL_V1, CommProtocol.ACK_CODE, size[0], size[1]});
            bufferedOut.write(sc.toString().getBytes());
            bufferedOut.flush();
        } catch (IOException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        return;
    }

    private void handleOrdersProducts(byte[] arr) throws IOException {
        int dataSize = (arr[2] & 0xff) + 256 * (arr[3] & 0xff);
        byte[] data = null;
        try {
            data = bufferedIn.readNBytes(dataSize);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        String[] dataSplit = new String(data).split(";");
        String filter = dataSplit[0];

        StringBuilder sc = new StringBuilder();
        Iterable<OrderItem> itemsOrder = orderRepository.findOrderProducts(filter);
        Iterator<OrderItem> it = itemsOrder.iterator();
        int i = 1;
        while (it.hasNext()) {
            OrderItem c = it.next();
            String productDescription = productRepo.findById(c.product().identity(), Product.class).getSingleResult().getShortDescription().toString();
            sc.append(i).append(" - ").append(productDescription).append("-").append(c.quantity()).append("-").append(c.totalPrice().priceWithTaxes()).append(";");
        }
        int[] sizeInt = CommProtocol.dataSizeCalculator(sc.toString());
        byte[] size = new byte[]{(byte) sizeInt[0], (byte) sizeInt[1]};
        try {
            bufferedOut.write(new byte[]{CommProtocol.PROTOCOL_V1, CommProtocol.CATALOG_CODE, size[0], size[1]});
            bufferedOut.write(sc.toString().getBytes());
            bufferedOut.flush();
        } catch (IOException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

}
