package eapli.base.app.backoffice.console.presentation.clientuser;

import eapli.base.clientusermanagement.application.GenerateCredentialsController;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;

import java.util.ArrayList;
import java.util.List;

public class GenerateCredentialsUI extends AbstractUI {

    private final GenerateCredentialsController generateCredentialsController = new GenerateCredentialsController();

    @Override
    protected boolean doShow() {
        List<SystemUser> list;
        int index;
        int option, continuar;
        boolean invalid = true;
        do {
            index = 1;
            list = generateCredentialsController.getUsers();
            if(list.isEmpty()){
                System.out.println("\nNo more Users to generate credentials.\n");
                return false;
            }
            System.out.println();
            for (SystemUser systemUser : list) {
                System.out.println("ID: " + index++);
                System.out.println("Name: " + systemUser.name());
                System.out.println("Email: " + systemUser.email());
                System.out.println();
            }
            do {
                option = Console.readInteger("ID: ");
                if (option < 1 || option > list.size()) {
                    System.out.println("ID invalid.\n");
                } else invalid = false;
            } while (invalid);
            generateCredentialsController.generate(list.get(option - 1));
            System.out.println("\nPretend to generate credentials for another User?");
            System.out.println("1 - Yes\n2 - No");
            continuar = Console.readOption(1, 2, 2);
        } while (continuar == 1);
        return true;
    }

    @Override
    public String headline() {
        return "Generate Credentials User.";
    }
}
