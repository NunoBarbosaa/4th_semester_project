package base.app.ordersserver;

import base.app.ordersserver.thread.OrderServerThread;
import eapli.base.Application;
import eapli.base.app.common.console.BaseApplication;
import eapli.framework.infrastructure.eventpubsub.EventDispatcher;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import javax.net.ssl.*;


public class OrdersServer extends BaseApplication {

    private final String SERVER_SSL_CERT_FILE = "server.pem";
    private final String SERVER_SSL_KEY_FILE = "server.key";
    private final String AUTH_CLIENTS_SSL_CERTS_FILE = "authentic-clients.pem";


    private static final String TRUSTED_STORE = "certs/client_customerapp.jks";
    private static final String KEYSTORE_PASS = "12345678";

    public static void main(String[] args) throws IOException {
        new OrdersServer().doMain(args);
    }

    @Override
    protected void doMain(String[] args) throws IOException {

        SSLServerSocket serverSocket;
        Socket clientSocket;
        //socket = new ServerSocket(Integer.parseInt(Application.settings().getProperty("ordersserver.port")));
        System.setProperty("javax.net.ssl.keyStore", TRUSTED_STORE);
        System.setProperty("javax.net.ssl.keyStorePassword", KEYSTORE_PASS);
        System.setProperty("javax.net.ssl.trustStore",TRUSTED_STORE);
        System.setProperty("javax.net.ssl.trustStorePassword", KEYSTORE_PASS);

        // Use this certificate and private key for client certificate when requested by
        // the server
        try {
            SSLServerSocketFactory sf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            int port = Integer.parseInt(Application.settings().getProperty("ordersserver.port"));
            serverSocket = (SSLServerSocket) sf.createServerSocket(port);
            serverSocket.setNeedClientAuth(true);
            while (true) {
                clientSocket = serverSocket.accept();
                new Thread(new OrderServerThread(clientSocket)).start();
            }
        } catch (Exception e) {
            System.out.println("ERROR: Failed to open server socket.");
            e.printStackTrace();
        }
        System.out.println("Order server running...");

        /*
        SSLContext ctx;

        Integer port = Integer.parseInt(Application.settings().getProperty("ordersserver.port"));
        SSLSocketFactory sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket sslSocket = (SSLSocket) sslSocketFactory.createSocket("localhost",port);


        if (socket != null) {
            while (true) {
                clientSocket =  socket.accept();
                new Thread(new OrderServerThread(clientSocket)).start();
            }
        } else {
            System.out.println("ERROR: Connection problem");
        }*/
    }

    @Override
    protected String appTitle() {
        return "Order Server";
    }

    @Override
    protected String appGoodbye() {
        return "Bye bye";
    }

    @Override
    protected void doSetupEventHandlers(EventDispatcher dispatcher) {

    }

}
