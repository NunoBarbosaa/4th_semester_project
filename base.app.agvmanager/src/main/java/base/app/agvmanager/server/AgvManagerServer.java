package base.app.agvmanager.server;

import base.app.agvmanager.server.protocol.AgvManagerProtocolParser;
import eapli.base.infrastructure.spomsp.commonrequest.ProtocolV1Request;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import java.io.*;
import java.net.Socket;
import java.util.Date;

public class AgvManagerServer {
    private static final Logger LOG = LogManager.getLogger(AgvManagerServer.class);
    private static final String TRUSTED_STORE = "certs/server_agvmanager.jks";
    private static final String KEYSTORE_PASS =  "12345678";

    private static class AgvManagerThread extends Thread{
        private Socket socket;

        public AgvManagerThread(Socket socket){
            this.socket = socket;
        }

        @Override
        public void run() {
            LOG.debug("New Connection from {}:{}", socket.getInetAddress().getHostAddress(), socket.getPort());
            System.out.println("New connection from " + socket.getInetAddress().getHostAddress());
            try(PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                DataOutputStream sOut = new DataOutputStream(socket.getOutputStream())){
            String inputLine;

            while((inputLine = in.readLine()) != null){
                ProtocolV1Request request = AgvManagerProtocolParser.parseRequest(inputLine);
                if(!request.isToDigitalTwin())
                    out.println(request.execute());
                else {
                    String msg = request.execute();
                    sOut.write(request.header());
                    sOut.write(msg.getBytes());
                }
                if(request.isGoodbye()) {
                    System.out.println(new Date() + " INFO: Socket closed");
                    socket.close();
                    break;
                }
            }
            }catch (IOException e){
                LOG.error(e);
            }
            socket=null;
        }
    }

    private void listen(final int port) {
        SSLServerSocket serverSocket;
        Socket clientSocket;

        System.setProperty("javax.net.ssl.trustStore", TRUSTED_STORE);
        System.setProperty("javax.net.ssl.trustStorePassword", KEYSTORE_PASS);


        System.setProperty("javax.net.ssl.keyStore", TRUSTED_STORE);
        System.setProperty("javax.net.ssl.keyStorePassword", KEYSTORE_PASS);


        try {
            SSLServerSocketFactory sslFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();

            serverSocket = (SSLServerSocket) sslFactory.createServerSocket(port);
            serverSocket.setNeedClientAuth(false);
            while (true) {
                clientSocket = serverSocket.accept();
                new AgvManagerThread(clientSocket).start();
            }
        } catch (final IOException e) {
            e.printStackTrace();
            LOG.error(e);
        }
    }

    /**
     * Wait for connections.
     *
     * @param port
     * @param blocking
     *            if {@code false} the socket runs in its own thread and does not block calling
     *            thread.
     */
    public void start(final int port, final boolean blocking) {
        if (blocking) {
            listen(port);
        } else {
            new Thread(() -> listen(port)).start();
        }
    }
}
