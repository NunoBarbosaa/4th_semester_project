package eapli.base.warehouse.HTTPServer;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.IOException;

public class HTTPServerAjax {
    static private final String BASE_FOLDER="base.core/src/main/java/eapli/base//warehouse//HTTPServer//www";
    static private SSLServerSocket sock;
    static private int PORT = 80;
    private static final String TRUSTED_STORE = "certs/server_agvmanager.jks";
    private static final String KEYSTORE_PASS = "12345678";

    public static void main(String[] args) throws Exception {
        SSLSocket cliSock;

        System.setProperty("javax.net.ssl.trustStore", TRUSTED_STORE);
        System.setProperty("javax.net.ssl.trustStorePassword", KEYSTORE_PASS);

        System.setProperty("javax.net.ssl.keyStore", TRUSTED_STORE);
        System.setProperty("javax.net.ssl.keyStorePassword", KEYSTORE_PASS);

        try {
            SSLServerSocketFactory sslf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            sock = (SSLServerSocket) sslf.createServerSocket(PORT);
        }
        catch(IOException ex) {
            System.out.println("Server failed to open local port " + PORT + ".");
            System.exit(1);
        }

        accessesCounter = 0;
        while(true) {
            cliSock = (SSLSocket) sock.accept();
            HTTPAjaxRequest req = new HTTPAjaxRequest(cliSock, BASE_FOLDER);
            req.start();
            incAccessesCounter();
        }
    }

    // DATA ACCESSED BY THREADS - LOCKING REQUIRED

    private static int length;
    private static int width;
    private static int square;
    private static int[][] docks;
    private static int[][] aisles;
    private static int[][] agvs;
    private static int accessesCounter;

    public static synchronized String getWarehouseRepresentationInHTML() {
        StringBuilder textHtml = new StringBuilder("<table class='table'>");
        boolean isDock, isAisle, isAGV, endSubAisle, bottomAisle;
        int id;
        int aux;
        if(square != 0 && docks != null && aisles != null && agvs != null){
            for(int i = 1; i <= (length / square); i++) {
                textHtml.append("<tr class='tr'>");
                for (int j = 1; j <= (width / square); j++) {
                    isDock = false;
                    isAisle = false;
                    isAGV = false;
                    endSubAisle = false;
                    bottomAisle = false;
                    for (int[] dock : docks) {
                        if (dock[0] <= j && dock[4] >= j && ((dock[1] <= i && dock[5] >= i) || (dock[1] >= i && dock[5] <= i))) {
                            isDock = true;
                            break;
                        }
                    }
                    for(int[] aisle : aisles){
                        if (aisle[1] <= j && aisle[5] >= j && ((aisle[2] <= i && aisle[6] >= i) || (aisle[2] >= i && aisle[6] <= i))) {
                            if (aisle[1] <= j && aisle[5] >= j && aisle[2] <= i && aisle[6] == i){
                                bottomAisle = true;
                            }
                            isAisle = true;
                            break;
                        }
                    }
                    for(int[] agv : agvs){
                        if (agv[0] == i && agv[1] == j) {
                            isAGV = true;
                            break;
                        }
                    }
                    if(isDock && isAGV) {
                        textHtml.append("<td class='dock'>&#9899</td>");
                    }
                    else if(isDock) textHtml.append("<td class='dock'>&nbsp</td>");
                    else if(isAisle) {
                        for(int[] aisle : aisles){
                            aux = 9;
                            for (int a = 0; a < aisle[0]; a++){
                                if(aisle[aux] == j && (aisle[aux + 1] == i || aisle[aux + 1] == (i + 1) || aisle[aux + 1] == (i - 1))){
                                    endSubAisle = true;
                                    break;
                                }
                                aux += 4;
                            }
                        }
                        if(endSubAisle && bottomAisle) textHtml.append("<td class='aisle with-end-sub-aisle-and-bottom-border'>&nbsp</td>");
                        else if(endSubAisle) textHtml.append("<td class='aisle end-sub-aisle'>&nbsp</td>");
                        else if(bottomAisle) textHtml.append("<td class='aisle with-bottom-border'>&nbsp</td>");
                        else textHtml.append("<td class='aisle'>&nbsp</td>");
                    }
                    else textHtml.append("<td class='td' style='border: 1px solid black;'>&nbsp</td>");
                }
                textHtml.append("</tr>");
            }
        }
        textHtml.append("<p style='text-align: center;'>HTTP server accesses counter: ").append(accessesCounter).append("</p>");
        return textHtml.toString();
    }

    public static void length(String i) {
        length = Integer.parseInt(i);
    }

    public static void width(String i) {
        width = Integer.parseInt(i);
    }

    public static void square(String i) {
        square = Integer.parseInt(i);
    }

    public static void docks(String i) {
        docks = split(i, 7);
    }

    public static void aisles(String i) { aisles = split(i, length * 4 + 6); }

    public static void agvs(String s) {
        String[] message = s.split("%");
        String[] agv;
        int[][] allAGVS = new int[message.length][2];
        for(int i = 0; i < message.length; i++){
            agv = message[i].split(";");
            for(int j = 0; j < agv.length; j++){
                allAGVS[i][j] = Integer.parseInt(agv[j]);
            }
        }
        agvs = allAGVS;
    }

    private static int[][] split(String s, int len){
        String[] message = s.split("%");
        String[] item;
        int[][] allItems = new int[message.length][len];
        for(int i = 0; i < message.length; i++){
            item = message[i].split(";");
            for(int j = 0; j < item.length; j++){
                allItems[i][j] = Integer.parseInt(item[j]);
            }
        }
        return allItems;
    }

    private static synchronized void incAccessesCounter() { accessesCounter++; }
}
