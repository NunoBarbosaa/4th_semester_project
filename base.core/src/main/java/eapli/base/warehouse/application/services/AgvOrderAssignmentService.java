package eapli.base.warehouse.application.services;

import eapli.base.Application;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.infrastructure.spomsp.CommProtocol;
import eapli.base.ordermanagement.domain.Order;
import eapli.base.ordermanagement.dto.OrderDTO;
import eapli.base.ordermanagement.repositories.OrderRepository;
import eapli.base.warehouse.dto.AgvDto;


import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AgvOrderAssignmentService {

    private final OrderRepository orderRepository = PersistenceContext.repositories().orders();
    private static final String TYPE = "UNPREPARED";
    private final OrderService orderService = new OrderService();
    private PrintWriter sOut;
    private BufferedReader sIn;

    private SSLSocket socket;

    private static final String TRUSTED_STORE = "certs/client_backoffice.jks";
    private static final String KEYSTORE_PASS = "12345678";

    public boolean startsCommunication() throws IOException {

        // Trust these certificates provided by servers
        System.setProperty("javax.net.ssl.trustStore", TRUSTED_STORE);
        System.setProperty("javax.net.ssl.trustStorePassword", KEYSTORE_PASS);

        // Use this certificate and private key for client certificate when requested by
        // the server
        System.setProperty("javax.net.ssl.keyStore", TRUSTED_STORE);
        System.setProperty("javax.net.ssl.keyStorePassword", KEYSTORE_PASS);

        SSLSocketFactory sf = (SSLSocketFactory) SSLSocketFactory.getDefault();

        socket = (SSLSocket) sf.createSocket(Application.settings().getProperty("agvmanager.address"),
                Integer.parseInt(Application.settings().getProperty("agvmanager.port")));

        socket.startHandshake();
        return sendCommtestMessage();
    }

    private boolean sendCommtestMessage() throws IOException {
        if (socket != null) {
            sIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            sOut = new PrintWriter(socket.getOutputStream(), true);

            sOut.println("1,0,0,0");

            String data = sIn.readLine();
            return data.equals("1,2,0,0");
        }
        else return false;
    }

    public List<OrderDTO> getOrders() {
        return orderService.getOrders(TYPE);
    }

    public Order checkIfOrderIsValid(long id) {
        return orderRepository.findByOrderId(id);
    }

    public List<String> recv() throws IOException {
        final var resp = new ArrayList<String>();

        var eof = false;
        //sIn.readLine();
        do {
            final String inputLine = sIn.readLine();
            if (inputLine != null) {
                if (inputLine.equals("")) {
                    eof = true;
                } else {
                    resp.add(inputLine);
                }
            }
        } while (!eof);

        //LOGGER.debug("Received message:\n----\n{}\n----", resp);

        return resp;
    }
    
    public List<AgvDto> getAGVS() throws IOException {
        sOut.println("1,8,0,0");
        List<String> strings;
        if((strings = recv()).isEmpty())
            return Collections.emptyList();
        
        return stringListParsed(strings);
    }

    private List<AgvDto> stringListParsed(List<String> strings) {
        final List<AgvDto> ret = new ArrayList<>();

        strings.remove(0); // removes header
        strings.forEach(s -> ret.add(parseResponseMessageLineGetAvailableAgvs(s)));
        return ret;
    }

    private AgvDto parseResponseMessageLineGetAvailableAgvs(final String s) {
        final String[] tokens = s.split(",");
        return new AgvDto(removeDoubleQuotes(tokens[0]), Double.parseDouble(tokens[1]), Double.parseDouble(tokens[2]),
                Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]), removeDoubleQuotes(tokens[5]), Integer.parseInt(tokens[6]));

    }

    private String removeDoubleQuotes(final String token) {
        return token.replace("\"", "").trim();
    }


    public boolean send(Long orderId, String agvID) throws IOException {
        StringBuilder s = new StringBuilder();
        s.append(orderId);
        s.append(",\"");
        s.append(agvID).append("\"");
        int[] size = CommProtocol.dataSizeCalculator(s.toString());

        StringBuilder message = new StringBuilder();
        message.append(CommProtocol.PROTOCOL_V1).append(",").append(CommProtocol.TASKASSIGN_CODE).append(",");
        message.append(size[0]).append(",").append(size[1]).append(",");
        message.append(s);

        sOut.println(message);
        return sIn.readLine().equals("1,2,0,0");

    }

    public boolean disconnect() throws IOException {
        sOut.println("1,1,0,0");

        String data = sIn.readLine();
        if (data.equals("1,2,0,0")) {
            socket.close();
            return true;
        }
        else
            return false;
    }
}
