package eapli.base.app.backoffice.console.presentation.warehouse;

import eapli.base.warehouse.application.ConfigureAgvController;
import eapli.base.warehouse.domain.AgvDock;
import eapli.base.warehouse.domain.AgvStatus;
import eapli.base.warehouse.dto.AgvDto;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ConfigureAgvUI extends AbstractUI {

    private final ConfigureAgvController theController = new ConfigureAgvController();

    @Override
    protected boolean doShow() {
        final Iterable<AgvDock> docks = this.theController.getAvailableAgvDocks();
        boolean tryAgain;
        do {
            try {

                if (!docks.iterator().hasNext()) {
                    System.out.println("The warehouse does not have any AGV dock available");
                    return true;
                }
                final SelectWidget<AgvDock> selector = new SelectWidget<>("AGV Docks:", docks,
                        new AgvDockPrinter());
                selector.show();
                if (selector.selectedElement() == null) {
                    System.out.println("INFO: You did not selected any AGV dock. Exiting...");
                    return false;
                }
                final AgvDock selectedDock = selector.selectedElement();

                String id = Console.readLine("AGV ID: (max 8 chars)");
                String description = Console.readLine("Description: (max 30 chars)");
                String model = Console.readLine("Model: (max 50 chars)");
                double maxWeight = Console.readDouble("Maximum Weight: (in Kg)");
                double maxVolume = Console.readDouble("Maximum Volume: (in cubic meters)");
                double autonomy = Console.readDouble("Autonomy with full battery: (in hours)");
                final Iterable<AgvStatus> statusIterable = Arrays.stream(AgvStatus.values()).collect(Collectors.toList());

                final SelectWidget<AgvStatus> statusWidget = new SelectWidget<>("Current AGV status", statusIterable,
                        new StatusPrinter());
                statusWidget.show();

                if (statusWidget.selectedElement() == null) {
                    System.out.println("INFO: You did not selected any status. Exiting...");
                    return false;
                }
                final AgvStatus status = statusWidget.selectedElement();

                final AgvDto dto = new AgvDto(id, description, model, maxWeight, maxVolume, autonomy, status);
                dto.setDock(selectedDock);


                if (theController.configureAgv(dto)) {
                    System.out.println("AGV configured successfully.");
                    return true;
                } else {
                    System.out.println("An error occurred and the AGV was not saved.");
                    if (!Console.readLine("Want to try again? (Y/N)").equalsIgnoreCase("y")) {
                        System.out.println("Exiting...");
                        tryAgain = false;
                    } else {
                        tryAgain = true;
                    }
                }
            }catch (Exception e){
                System.out.println("ERROR: " + e.getMessage());
                if (Console.readLine("Want to try again? (Y/N)").equalsIgnoreCase("y")) {
                    tryAgain = true;
                } else {
                    System.out.println("Exiting...");
                    return true;
                }
            }
        }while (tryAgain);

        return true;

    }

    @Override
    public String headline() {
        return "AGV configuration";
    }
}
