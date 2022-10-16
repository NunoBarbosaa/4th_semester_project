package base.app.agv.server;

import base.app.agv.server.protocol.DigitalTwinProtocolParser;
import eapli.base.infrastructure.spomsp.commonrequest.ProtocolV1Request;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class AgvDigitalTwinServer {
    private static final Logger LOG = LogManager.getLogger(AgvDigitalTwinServer.class);

    private static class DigitalTwinThread extends Thread{
        private Socket socket;

        public DigitalTwinThread(Socket socket){
            this.socket = socket;
        }

        @Override
        public void run() {
            LOG.debug("New Connection from {}:{}", socket.getInetAddress().getHostAddress(), socket.getPort());
            System.out.println(new Date() +" New connection from " + socket.getInetAddress().getHostAddress());
            try(PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))){
                String inputLine;

                while((inputLine = in.readLine()) != null){
                    ProtocolV1Request request = DigitalTwinProtocolParser.parseRequest(inputLine);
                    out.println(request.execute());
                    if(request.isGoodbye()) {
                        socket.close();
                        break;
                    }
                }
            }catch (IOException e){
                LOG.error(e);
            }
        }
    }

    private void listen(final int port) {
        try (var serverSocket = new ServerSocket(port)) {
            while (true) {
                final var clientSocket = serverSocket.accept();
                new DigitalTwinThread(clientSocket).start();
            }
        } catch (final IOException e) {
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
