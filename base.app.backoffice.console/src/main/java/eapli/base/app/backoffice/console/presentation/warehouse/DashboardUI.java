package eapli.base.app.backoffice.console.presentation.warehouse;

import eapli.base.warehouse.application.DashboardController;
import eapli.framework.presentation.console.AbstractUI;

import java.io.IOException;

public class DashboardUI extends AbstractUI {

    private final DashboardController dashboardController = new DashboardController();

    @Override
    protected boolean doShow() {
        try {
            dashboardController.openDashboard();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public String headline() {
        return "Dashboard";
    }
}
