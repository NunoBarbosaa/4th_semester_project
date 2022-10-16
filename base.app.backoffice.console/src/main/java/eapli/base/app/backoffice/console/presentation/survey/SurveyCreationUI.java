package eapli.base.app.backoffice.console.presentation.survey;

import eapli.base.surveys.application.SurveyCreationController;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;

public class SurveyCreationUI extends AbstractUI {

    private final SurveyCreationController controller = new SurveyCreationController();

    @Override
    protected boolean doShow() {
        String fileName = "";
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            fileName = fileChooser.getSelectedFile().getAbsoluteFile().getAbsolutePath();
        }

        try {
            if(controller.createSurvey(fileName)) {
                System.out.println("INFO: Selected file was successfully turned into a survey");
                if(!controller.isSurveyOnDataBase()){
                    if(Console.readBoolean("\nDo you want to send the survey to customers?")){
                        if(controller.saveSurvey()) {
                            controller.setTargetCustomers();
                            System.out.println("INFO: The Survey is now available to the targeted customers");
                        }else {
                            System.out.println("ERROR: Something went wrong. Please try again later.");
                        }
                    }else{
                        System.out.println("INFO: Understood");
                    }
                }else{
                    System.out.println("\nINFO: This survey cannot be sent to customers because one with the same ID already exists. Please create a new one or change the ID");
                }
            }else
                System.out.println("INFO: Unable to create survey from the file provided");
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: File was not found");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        return true;
    }

    @Override
    public String headline() {
        return "Survey Creation";
    }
}
