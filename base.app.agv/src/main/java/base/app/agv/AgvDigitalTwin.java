package base.app.agv;


import base.app.agv.server.AgvDigitalTwinServer;
import eapli.base.Application;
import eapli.base.app.common.console.BaseApplication;
import eapli.framework.infrastructure.eventpubsub.EventDispatcher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class AgvDigitalTwin extends BaseApplication {
    private static final Logger LOGGER = LogManager.getLogger(AgvDigitalTwin.class);

    public static void main(String[] args) {
        new AgvDigitalTwin().doMain(args);
    }

    @Override
    protected void doMain(String[] args) {
        final var server = new AgvDigitalTwinServer();
        LOGGER.info("Starting the server socket");
        server.start(Integer.parseInt(Application.settings().getProperty("agvdigitaltwin.port")), false);

    }

    @Override
    protected String appTitle() {
        return "AGV Manager";
    }

    @Override
    protected String appGoodbye() {
        return "Bye, Bye";
    }

    @Override
    protected void doSetupEventHandlers(EventDispatcher dispatcher) {

    }
}
