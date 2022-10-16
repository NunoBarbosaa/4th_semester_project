package base.app.agvmanager;

import base.app.agvmanager.server.AgvManagerServer;
import base.app.agvmanager.server.SchedulerOrder;
import eapli.base.Application;
import eapli.base.app.common.console.BaseApplication;
import eapli.framework.infrastructure.eventpubsub.EventDispatcher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Timer;

public class AgvManager extends BaseApplication {
    private static final Logger LOGGER = LogManager.getLogger(AgvManager.class);

    public static void main(String[] args) {
        new AgvManager().doMain(args);
    }

    @Override
    protected void doMain(String[] args) {

        if(Application.settings().getProperty("agvmanager.AutomaticAssignment").equalsIgnoreCase("on")){
            Timer time = new Timer();
            SchedulerOrder sch=new SchedulerOrder();
            time.schedule(sch,1000*20,1000*60);
            System.out.println("Automatic order Assignment: ON");
        }else{
            System.out.println("Automatic order Assignment: OFF");
        }



        final var server = new AgvManagerServer();
        LOGGER.info("Starting the server socket");
        server.start(Integer.parseInt(Application.settings().getProperty("agvmanager.port")), false);

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
