/*
 * Copyright (c) 2013-2019 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package eapli.base.app.user.console.presentation;

import eapli.base.app.common.console.presentation.authz.MyUserMenu;
import eapli.base.app.user.console.presentation.clientOrders.CheckOrdersUI;
import eapli.base.app.user.console.presentation.shoppingmanager.AddProductToShoppingcartUI;
import eapli.base.app.user.console.presentation.survey.AnswerSurveyUI;
import eapli.base.surveys.application.CustomerSurveyController;
import eapli.base.surveys.application.SurveyCreationController;
import eapli.framework.actions.Actions;
import eapli.framework.actions.menu.Menu;
import eapli.framework.actions.menu.MenuItem;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.presentation.console.ExitWithMessageAction;
import eapli.framework.presentation.console.ShowMessageAction;
import eapli.framework.presentation.console.menu.MenuItemRenderer;
import eapli.framework.presentation.console.menu.MenuRenderer;
import eapli.framework.presentation.console.menu.VerticalMenuRenderer;

import java.io.IOException;

/**
 * @author Paulo Gandra Sousa
 */
class MainMenu extends ClientUserBaseUI {

    private static final String SEPARATOR_LABEL = "--------------";

    private static final String RETURN = "Return ";

    private static final String NOT_IMPLEMENTED_YET = "Not implemented yet";

    private static final int EXIT_OPTION = 0;
    private static final int ADD_PRODUCT_TO_SHOPPING_CART=1;
    private static final int VIEW_OPEN_ORDERS =2;
    // MAIN MENU
    private static final int MY_USER_OPTION = 1;
    private static final int COSTUMER_SHOPPING=2;

    private static final int SURVEYS_MENU = 3;

    private static final int ANSWER_SURVEY = 1;

    public final String ANSI_RESET = "\u001B[0m";
    public final String ANSI_RED = "\u001B[31m";

    private final AuthorizationService authz =
            AuthzRegistry.authorizationService();

    private final CustomerSurveyController surveysController = new CustomerSurveyController();

    @Override
    public boolean show() {
        drawFormTitle();
        return doShow();
    }

    /**
     * @return true if the user selected the exit option
     */
    @Override
    public boolean doShow() {
        final Menu menu = buildMainMenu();
        final MenuRenderer renderer =
                new VerticalMenuRenderer(menu, MenuItemRenderer.DEFAULT);
        return renderer.render();
    }

    private Menu buildMainMenu() {
        final Menu mainMenu = new Menu();

        final Menu myUserMenu = new MyUserMenu();
        final Menu costumersMenu = customerMenu();
        final Menu surveysMenu = surveysMenu();
        mainMenu.addSubMenu(MY_USER_OPTION, myUserMenu);
        mainMenu.addSubMenu(COSTUMER_SHOPPING,costumersMenu);
        if(surveysMenu != null)
            mainMenu.addSubMenu(SURVEYS_MENU, surveysMenu);

        mainMenu.addItem(MenuItem.separator(SEPARATOR_LABEL));



        mainMenu.addItem(EXIT_OPTION, "Exit", new ExitWithMessageAction("Bye, Bye"));

        return mainMenu;
    }

    private Menu surveysMenu() {
        final Menu menu;

        int nrSurveys = 0;
        try {
            nrSurveys = surveysController.numberOfSurveysToAnswer();
            if(nrSurveys == -1) {
                System.out.println("Surveys are not available (Servers in maintenance)");
                return null;
            }
        } catch (IOException e) {
            System.out.println("Surveys are not available (Servers in maintenance)");
            return null;
        }
        if(nrSurveys != 0)
            menu = new Menu("Surveys "+ ANSI_RED + "*" + ANSI_RESET + " >");
        else
            menu = new Menu("Surveys >");

        menu.addItem(ANSWER_SURVEY, "Answer to a customer survey (" + nrSurveys + " unanswered)" , new AnswerSurveyUI()::show);
        menu.addItem(EXIT_OPTION, "Return", Actions.SUCCESS);

        return menu;
    }

    private Menu customerMenu() {
        final Menu menu = new Menu("Products >");

        menu.addItem(ADD_PRODUCT_TO_SHOPPING_CART,"Add product to shopping cart",new AddProductToShoppingcartUI()::show);
        menu.addItem(VIEW_OPEN_ORDERS,"Check my orders",new CheckOrdersUI()::show);
                new ShowMessageAction("Not implemented yet");
        menu.addItem(EXIT_OPTION,"EXIT", Actions.SUCCESS);

        return menu;
    }
}
