package eapli.base.warehouse.HTTPServer;

import javax.net.ssl.SSLSocket;
import java.io.*;
import java.net.Socket;

public class HTTPAjaxRequest extends Thread {
    String baseFolder;
    SSLSocket sock;
    DataInputStream inS;
    DataOutputStream outS;

    public HTTPAjaxRequest(SSLSocket s, String f) {
        sock = s;
        baseFolder = f;
    }

    public void run() {

        try {
            outS = new DataOutputStream(sock.getOutputStream());
            inS = new DataInputStream(sock.getInputStream());
        }
        catch(IOException ex) {
            System.out.println("Thread error on data streams creation");
        }

        try {
            HTTPMessage request = new HTTPMessage(inS);
            HTTPMessage response = new HTTPMessage();

            if (request.getMethod().equals("GET")) {
                if (request.getURI().equals("/warehouse")) {
                    response.setContentFromString(HTTPServerAjax.getWarehouseRepresentationInHTML(), "text/html");
                    response.setResponseStatus("200 Ok");
                }
                else {
                    String fullname = baseFolder + "/";
                    if (request.getURI().equals("/")) {
                        fullname = fullname + "index.html";
                    }
                    else {
                        fullname = fullname + request.getURI();
                    }
                    if (response.setContentFromFile(fullname)) {
                        response.setResponseStatus("200 Ok");
                    }
                    else {
                        response.setContentFromString(
                                "<html><body><h1>404 File not found</h1></body></html>",
                                "text/html");
                        response.setResponseStatus("404 Not Found");
                    }
                }
                response.send(outS);
            }
            else { // NOT GET
                if (request.getMethod().equals("PUT") && request.getURI().startsWith("/length/")) {
                    HTTPServerAjax.length(request.getURI().substring(8));
                    response.setResponseStatus("200 Ok");
                }
                else if (request.getMethod().equals("PUT") && request.getURI().startsWith("/width/")) {
                    HTTPServerAjax.width(request.getURI().substring(7));
                    response.setResponseStatus("200 Ok");
                }
                else if (request.getMethod().equals("PUT") && request.getURI().startsWith("/square/")) {
                    HTTPServerAjax.square(request.getURI().substring(8));
                    response.setResponseStatus("200 Ok");
                }
                else if (request.getMethod().equals("PUT") && request.getURI().startsWith("/docks/")) {
                    HTTPServerAjax.docks(request.getURI().substring(7));
                    response.setResponseStatus("200 Ok");
                }
                else if (request.getMethod().equals("PUT") && request.getURI().startsWith("/aisles/")) {
                    HTTPServerAjax.aisles(request.getURI().substring(8));
                    response.setResponseStatus("200 Ok");
                }
                else if (request.getMethod().equals("PUT") && request.getURI().startsWith("/agvs/")) {
                    HTTPServerAjax.agvs(request.getURI().substring(6));
                    response.setResponseStatus("200 Ok");
                }
                else {
                    response.setContentFromString(
                            "<html><body><h1>ERROR: 405 Method Not Allowed</h1></body></html>",
                            "text/html");
                    response.setResponseStatus("405 Method Not Allowed");
                }
                response.send(outS);
            }
        }
        catch(IOException ex) {
            System.out.println("Thread error when reading request");
        }

        try {
            sock.close();
        }
        catch(IOException ex) {
            System.out.println("CLOSE IOException");
        }
    }
}