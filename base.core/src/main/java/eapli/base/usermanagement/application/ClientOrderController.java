package eapli.base.usermanagement.application;

import eapli.base.Application;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.infrastructure.spomsp.CommProtocol;
import eapli.base.ordermanagement.domain.Order;
import eapli.base.ordermanagement.dto.OrderDTO;
import eapli.base.ordermanagement.repositories.OrderRepository;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.base.warehouse.application.services.OrderService;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

public class ClientOrderController {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();

    private DataOutputStream sOut;
    private DataInputStream sIn;
    private BufferedOutputStream bufferedOut;
    private BufferedInputStream bufferedIn;

    private SSLSocket socket;

    private static final String TRUSTED_STORE = "certs/client_customerapp.jks";
    private static final String KEYSTORE_PASS = "12345678";

    public void startsCommunication() throws IOException {

        // Trust these certificates provided by servers
        System.setProperty("javax.net.ssl.trustStore", TRUSTED_STORE);
        System.setProperty("javax.net.ssl.trustStorePassword", KEYSTORE_PASS);

        // Use this certificate and private key for client certificate when requested by
        // the server
        System.setProperty("javax.net.ssl.keyStore", TRUSTED_STORE);
        System.setProperty("javax.net.ssl.keyStorePassword", KEYSTORE_PASS);

        SSLSocketFactory sf = (SSLSocketFactory) SSLSocketFactory.getDefault();

        socket = (SSLSocket) sf.createSocket(Application.settings().getProperty("ordersserver.address"),
                Integer.parseInt(Application.settings().getProperty("ordersserver.port")));

        socket.startHandshake();

        sendCommtestMessage();
    }

    public String getEmail(){
        return authz.session().get().authenticatedUser().email().toString();
    }

    private void sendCommtestMessage() throws IOException {
        if (socket != null) {
            sIn = new DataInputStream(socket.getInputStream());
            sOut = new DataOutputStream(socket.getOutputStream());
            bufferedOut = new BufferedOutputStream(sOut);
            bufferedIn = new BufferedInputStream(sIn);

            bufferedOut.write(new byte[]{CommProtocol.PROTOCOL_V1, CommProtocol.COMM_TEST_CODE, 0, 0});
            bufferedOut.flush();

            byte[] acknowledgeMessage = bufferedIn.readNBytes(4);
            if (!Arrays.equals(acknowledgeMessage, CommProtocol.ACK_MESSAGE_V1)) {
                System.out.println("ERROR: Failed to send commtest message.");
            }
        }
    }

    public boolean sendEmail(String user) throws IOException {
        int[] sizeInt = CommProtocol.dataSizeCalculator(user);
        byte[] size = new byte[]{(byte)sizeInt[0], (byte)sizeInt[1]};
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append(user).append(";");
        bufferedOut.write(new byte[]{CommProtocol.PROTOCOL_V1, CommProtocol.CUSTOMER_ID_CODE,size[0],size[1]});
        bufferedOut.write(user.getBytes());
        bufferedOut.flush();

        byte[] ack = bufferedIn.readNBytes(4);

        return Arrays.equals(ack, CommProtocol.ACK_MESSAGE_V1);
    }

    public String getClientOrdersStatus() throws IOException {
        String ordersStatus;
        bufferedOut.write(new byte[]{CommProtocol.PROTOCOL_V1, CommProtocol.CLIENT_ORDERS_CODE, 0, 0});
        bufferedOut.flush();
        byte[] readNBytes = bufferedIn.readNBytes(4);
        int dataSize = (readNBytes[2] & 0xff) + 256 * (readNBytes[3] & 0xff);
        byte[] totalInformation = bufferedIn.readNBytes(dataSize);
        ordersStatus = new String(totalInformation);
        return ordersStatus;
    }


    public String getAllCategories() throws IOException {
        //NomeCategoria#CodigoAlfanumerico;NomeCategoria2#CodigoAlfanumerico2;
        String categories;
        bufferedOut.write(new byte[]{CommProtocol.PROTOCOL_V1, CommProtocol.CATEGORIES_CODE, 0, 0});
        bufferedOut.flush();
        byte[] readNBytes = bufferedIn.readNBytes(4);
        byte[] totalInformation = bufferedIn.readNBytes(readNBytes[2] + 256 * readNBytes[3]);
        categories = new String(totalInformation);
        return categories;
    }
    //Filter type tem de estar igual á us do Xavier(Mostrar catalogo)
    //Nome para filtrar na bd
    //Se nao houber filtro filterType="all"
    public String getProductsFiltered(String filterType, String filtro) throws IOException {
        String products;
        StringBuilder s = new StringBuilder();
        s.append(filterType);
        s.append(";");
        s.append(filtro);
        int[] sizeInt = CommProtocol.dataSizeCalculator(s.toString());
        byte[] size = new byte[]{(byte)sizeInt[0], (byte)sizeInt[1]};

        bufferedOut.write(new byte[]{CommProtocol.PROTOCOL_V1,CommProtocol.CATALOG_CODE,size[0],size[1]});
        bufferedOut.write(s.toString().getBytes());
        bufferedOut.flush();
        byte[] readNBytes = bufferedIn.readNBytes(4);
        int dataSize = (readNBytes[2] & 0xff) + 256 * (readNBytes[3] & 0xff);
        byte[] totalInformation = bufferedIn.readNBytes(dataSize);
        products = new String(totalInformation);
        return products;
    }

    public String getShoppingChart() throws IOException {
        String products;

        bufferedOut.write(new byte[]{CommProtocol.PROTOCOL_V1,CommProtocol.SHOPCHART_CODE,0,0});
        bufferedOut.flush();
        byte[] readNBytes = bufferedIn.readNBytes(4);
        int dataSize = (readNBytes[2] & 0xff) + 256 * (readNBytes[3] & 0xff);
        byte[] totalInformation = bufferedIn.readNBytes(dataSize);
        products = new String(totalInformation);
        return products;
    }

    public String getOrderProducts(String OrderID) throws IOException {
        int[] sizeInt = CommProtocol.dataSizeCalculator(OrderID);
        byte[] size = new byte[]{(byte)sizeInt[0], (byte)sizeInt[1]};
        String products;
        bufferedOut.write(new byte[]{CommProtocol.PROTOCOL_V1,CommProtocol.ORDER_DETAILED_INFO_CODE,size[0],size[1]});
        bufferedOut.write(OrderID.getBytes());
        bufferedOut.flush();
        byte[] readNBytes = bufferedIn.readNBytes(4);
        int dataSize = (readNBytes[2] & 0xff) + 256 * (readNBytes[3] & 0xff);
        byte[] totalInformation = bufferedIn.readNBytes(dataSize);
        products = new String(totalInformation);
        return products;
    }

    public boolean sendProduct(String productId, int quantity) throws IOException {
        StringBuilder s = new StringBuilder();
        s.append(productId);
        s.append(";");
        s.append(quantity);
        int[] sizeInt = CommProtocol.dataSizeCalculator(s.toString());
        byte[] size = new byte[]{(byte)sizeInt[0], (byte)sizeInt[1]};

        bufferedOut.write(new byte[]{CommProtocol.PROTOCOL_V1, CommProtocol.CART_ADD_CODE});
        bufferedOut.write(size);
        bufferedOut.write(s.toString().getBytes());
        bufferedOut.flush();

        byte[] ack = bufferedIn.readNBytes(4);
        return Arrays.equals(ack, CommProtocol.ACK_MESSAGE_V1);
    }

    public boolean removeProduct(String productId, int quantity) throws IOException {
        StringBuilder s = new StringBuilder();
        s.append(productId);
        s.append(";");
        s.append(quantity);
        int[] sizeInt = CommProtocol.dataSizeCalculator(s.toString());
        byte[] size = new byte[]{(byte)sizeInt[0], (byte)sizeInt[1]};

        bufferedOut.write(new byte[]{CommProtocol.PROTOCOL_V1, CommProtocol.CART_REMOVE_CODE});
        bufferedOut.write(size);
        bufferedOut.write(s.toString().getBytes());
        bufferedOut.flush();

        byte[] ack = bufferedIn.readNBytes(4);
        return Arrays.equals(ack, CommProtocol.ACK_MESSAGE_V1);
    }




    public void disconnect() throws IOException {
        bufferedOut.write(new byte[]{CommProtocol.PROTOCOL_V1, CommProtocol.DISCONN_CODE, 0, 0});
        bufferedOut.flush();

        byte[] acknowledgeMessage = bufferedIn.readNBytes(4);
        if (Arrays.equals(acknowledgeMessage, CommProtocol.ACK_MESSAGE_V1)) {
            socket.close();
        }
    }



}
