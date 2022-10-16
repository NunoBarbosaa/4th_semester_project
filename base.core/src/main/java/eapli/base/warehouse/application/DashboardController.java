package eapli.base.warehouse.application;

import eapli.base.Application;
import eapli.base.infrastructure.warehouse.WarehouseContainer;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.base.warehouse.HTTPServer.HTTPMessage;
import eapli.base.warehouse.HTTPServer.HTTPServerAjax;
import eapli.base.warehouse.application.services.AgvOrderAssignmentService;
import eapli.base.warehouse.domain.*;
import eapli.base.warehouse.dto.AgvDto;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.awt.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.List;

public class DashboardController {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final AgvOrderAssignmentService service = new AgvOrderAssignmentService();

    static private SSLSocket sock;
    static private InetAddress serverIP;
    static private int serverPort;
    static private DataOutputStream sOut;
    static private DataInputStream sIn;

    private HTTPMessage request = new HTTPMessage();

    private static final String TRUSTED_STORE = "certs/client_backoffice.jks";
    private static final String KEYSTORE_PASS =  "12345678";

    public boolean startsCommunication() throws Exception {
        authz.isAuthenticatedUserAuthorizedTo(BaseRoles.WAREHOUSE_EMPLOYEE);
        return service.startsCommunication();
    }

    public void sendDateWarehouse() throws IOException {
        Warehouse warehouse = WarehouseContainer.activeWarehouse();

        request.setRequestMethod("PUT");
        String length = "/length/" + warehouse.length().quantity().intValue();
        request.setURI(length);
        request.send(sOut);
        String width = "/width/" + warehouse.width().quantity().intValue();
        request.setURI(width);
        request.send(sOut);
        String square = "/square/" + warehouse.squareDimension().quantity().intValue();
        request.setURI(square);
        request.send(sOut);

        // string to docks

        StringBuilder docks = new StringBuilder();
        docks.append("/docks/");
        for (AgvDock agvDock : warehouse.docks()){
            constructStringBuilder(docks, agvDock.startsFrom(), agvDock.endsOn(), agvDock.depth());
            docks.append("%");
        }

        request.setURI(docks.toString());
        request.send(sOut);

        // string to aisles

        int index;
        StringBuilder aisles = new StringBuilder();
        aisles.append("/aisles/");
        for (Aisle aisle : warehouse.getAisles()){
            aisles.append(aisle.rows().size());
            aisles.append(";");
            constructStringBuilder(aisles, aisle.startsFrom(), aisle.endsOn(), aisle.depth());
            aisles.append(";");
            index = 0;
            for (AisleRow aisleRow : aisle.rows()){
                index++;
                aisles.append(aisleRow.startsOn().lSquare().intValue());
                aisles.append(";");
                aisles.append(aisleRow.startsOn().wSquare().intValue());
                aisles.append(";");
                aisles.append(aisleRow.endsOn().lSquare().intValue());
                aisles.append(";");
                aisles.append(aisleRow.endsOn().wSquare().intValue());
                if(index < aisle.rows().size()) aisles.append(";");
            }
            aisles.append("%");
        }

        request.setURI(aisles.toString());
        request.send(sOut);

        getAGVS();
    }

    public void getAGVS(){
        List<AgvDto> list;
        StringBuilder stringBuilder;
        try {
            while (true) {
                stringBuilder = new StringBuilder("/agvs/");
                list = service.getAGVS();
                for(AgvDto agvDto : list){
                    stringBuilder.append((int) agvDto.getLsquare());
                    stringBuilder.append(";");
                    stringBuilder.append((int) agvDto.getWsquare());
                    stringBuilder.append("%");
                }
                System.out.println(stringBuilder);
                request.setURI(list.toString());
                request.send(sOut);
                Thread.sleep(500);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void constructStringBuilder(StringBuilder string, Square square1, Square square2, Square square3) {
        string.append(square1.lSquare().intValue());
        string.append(";");
        string.append(square1.wSquare().intValue());
        string.append(";");
        string.append(square2.lSquare().intValue());
        string.append(";");
        string.append(square2.wSquare().intValue());
        string.append(";");
        string.append(square3.lSquare().intValue());
        string.append(";");
        string.append(square3.wSquare().intValue());
    }

    public boolean disconnect() throws IOException {
        return service.disconnect();
    }

    public void openDashboard() throws IOException {
        try {
            Desktop desktop = Desktop.getDesktop();
            URI url = new URI("localhost:80/");
            desktop.browse(url);
        } catch (Exception e){
            throw new IllegalArgumentException("ERROR.");
        }

        System.setProperty("javax.net.ssl.trustStore", TRUSTED_STORE);
        System.setProperty("javax.net.ssl.trustStorePassword", KEYSTORE_PASS);

        // Use this certificate and private key for client certificate when requested by the server
        System.setProperty("javax.net.ssl.keyStore", TRUSTED_STORE);
        System.setProperty("javax.net.ssl.keyStorePassword", KEYSTORE_PASS);

        try { serverIP = InetAddress.getByName("localhost"); }
        catch(UnknownHostException ex) {
            System.out.println("Invalid SERVER-ADDRESS.");
            System.exit(1);
        }

        try { serverPort = 80; }
        catch(NumberFormatException ex) {
            System.out.println("Invalid port.");
            System.exit(1);
        }

        SSLSocketFactory sf = (SSLSocketFactory) SSLSocketFactory.getDefault();

        try {
            sock = (SSLSocket) sf.createSocket(serverIP, serverPort);
        }
        catch(IOException ex) {
            System.out.println("Failed to connect.");
            System.exit(1);
        }

        sOut = new DataOutputStream(sock.getOutputStream());
        sIn = new DataInputStream(sock.getInputStream());

        sendDateWarehouse();
    }
}
