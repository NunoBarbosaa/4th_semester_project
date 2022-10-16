package eapli.base.app.backoffice.console.presentation.warehouse;

import eapli.base.warehouse.application.DashboardController;
import eapli.framework.presentation.console.AbstractUI;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class WarehouseDashboardUI extends AbstractUI {

    DashboardController controller = new DashboardController();

    @Override
    protected boolean doShow() {
        String url = "http://localhost:80/";

        try {
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                try {
                    desktop.browse(new URI(url));
                } catch (IOException | URISyntaxException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                Runtime runtime = Runtime.getRuntime();
                try {
                    runtime.exec("xdg-open " + url);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            controller.startsCommunication();
            controller.openDashboard();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public String headline() {
        return "Warehouse Dashboard";
    }
}
