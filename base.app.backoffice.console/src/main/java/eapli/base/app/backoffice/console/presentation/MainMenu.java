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
package eapli.base.app.backoffice.console.presentation;

import eapli.base.app.backoffice.console.presentation.category.ListCategoryUI;
import eapli.base.app.backoffice.console.presentation.category.RegisterCategoryUI;
import eapli.base.app.backoffice.console.presentation.clientuser.GenerateCredentialsUI;
import eapli.base.app.backoffice.console.presentation.customer.RegisterCustomerUI;
import eapli.base.app.backoffice.console.presentation.order.AddOrderUI;
import eapli.base.app.backoffice.console.presentation.order.UpdateOrderToBeingDeliveredUI;
import eapli.base.app.backoffice.console.presentation.productmanagement.SpecifyNewProductUI;
import eapli.base.app.backoffice.console.presentation.productmanagement.ViewProductsCatalogUI;
import eapli.base.app.backoffice.console.presentation.survey.StatisticReportUI;
import eapli.base.app.backoffice.console.presentation.survey.SurveyCreationUI;
import eapli.base.app.backoffice.console.presentation.warehouse.AlreadyPreparedByAgvUI;
import eapli.base.app.backoffice.console.presentation.warehouse.ConfigureAgvUI;
import eapli.base.app.backoffice.console.presentation.warehouse.PrepareOrderByAGVUI;
import eapli.base.app.backoffice.console.presentation.warehouse.WarehouseDashboardUI;
import eapli.base.app.common.console.presentation.authz.MyUserMenu;
import eapli.base.Application;
import eapli.base.app.backoffice.console.presentation.authz.AddUserUI;
import eapli.base.app.backoffice.console.presentation.authz.DeactivateUserAction;
import eapli.base.app.backoffice.console.presentation.authz.ListUsersAction;
import eapli.base.app.backoffice.console.presentation.clientuser.AcceptRefuseSignupRequestAction;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.actions.Actions;
import eapli.framework.actions.menu.Menu;
import eapli.framework.actions.menu.MenuItem;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.ExitWithMessageAction;
import eapli.framework.presentation.console.menu.HorizontalMenuRenderer;
import eapli.framework.presentation.console.menu.MenuItemRenderer;
import eapli.framework.presentation.console.menu.MenuRenderer;
import eapli.framework.presentation.console.menu.VerticalMenuRenderer;

/**
 * TODO split this class in more specialized classes for each menu
 *
 * @author Paulo Gandra Sousa
 */
public class MainMenu extends AbstractUI {

    private static final String RETURN_LABEL = "Return ";

    private static final int EXIT_OPTION = 0;

    // USERS
    private static final int ADD_USER_OPTION = 1;
    private static final int LIST_USERS_OPTION = 2;
    private static final int DEACTIVATE_USER_OPTION = 3;
    private static final int ACCEPT_REFUSE_SIGNUP_REQUEST_OPTION = 4;

    // SETTINGS
    private static final int SET_KITCHEN_ALERT_LIMIT_OPTION = 1;

    // WAREHOUSE
    private static final int CONFIG_AGV = 1;
    private static final int PREPARE_ORDER_BY_AGV = 2;
    private static final int ALREADY_PREPARED_BY_AGV=3;
    private static final int DASHBOARD = 4;

    //PRODUCT_MANAGEMENT
    private static final int SPECIFY_PRODUCT = 1;
    private static final int SPECIFY_CATEGORY = 2;
    private static final int LIST_CATEGORIES = 3;
    private static final int VIEW_AND_SEARCH_CATALOG = 4;


    // CUSTOMERS
    private static final int REGISTER_CUSTOMER = 1;
    private static final int GENERATE_CREDENTIALS = 2;

    //ORDER
    private static final int ADD_ORDEM = 1;
    private static final int UPDATE_ORDER_TO_BEING_DELIVERED=2;


    // SURVEYS

    private static final int SURVEYS_OPTION = 2;
    private static final int CREATE_SURVEYS_OPTION = 1;
    private static final int STATISTICAL_REPORT_QUESTIONNAIRE = 2;
    // MAIN MENU
    private static final int MY_USER_OPTION = 1;
    private static final int USERS_OPTION = 2;
    private static final int SETTINGS_OPTION = 3;

    private static final int WAREHOUSE_OPTION = 2;

    private static final int CUSTOMERS_OPTION = 3;

    private static final int ORDER_OPTION = 4;

    private static final int PRODUCT_MANAGEMENT_OPTION = 2;


    private static final String SEPARATOR_LABEL = "--------------";

    private final AuthorizationService authz = AuthzRegistry.authorizationService();

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
        final MenuRenderer renderer;
        if (Application.settings().isMenuLayoutHorizontal()) {
            renderer = new HorizontalMenuRenderer(menu, MenuItemRenderer.DEFAULT);
        } else {
            renderer = new VerticalMenuRenderer(menu, MenuItemRenderer.DEFAULT);
        }
        return renderer.render();
    }

    @Override
    public String headline() {

        return authz.session().map(s -> "Base [ @" + s.authenticatedUser().identity() + " ]")
                .orElse("Base [ ==Anonymous== ]");
    }

    private Menu buildMainMenu() {
        final Menu mainMenu = new Menu();

        final Menu myUserMenu = new MyUserMenu();
        mainMenu.addSubMenu(MY_USER_OPTION, myUserMenu);

        if (!Application.settings().isMenuLayoutHorizontal()) {
            mainMenu.addItem(MenuItem.separator(SEPARATOR_LABEL));
        }

        if (authz.isAuthenticatedUserAuthorizedTo(BaseRoles.POWER_USER, BaseRoles.ADMIN)) {
            final Menu usersMenu = buildUsersMenu();
            mainMenu.addSubMenu(USERS_OPTION, usersMenu);
            final Menu settingsMenu = buildAdminSettingsMenu();
            mainMenu.addSubMenu(SETTINGS_OPTION, settingsMenu);
        }

        if (authz.isAuthenticatedUserAuthorizedTo(BaseRoles.WAREHOUSE_EMPLOYEE)) {
            final Menu warehouseEmployeeMenu = buildWarehouseEmployeeMenu();
            mainMenu.addSubMenu(WAREHOUSE_OPTION, warehouseEmployeeMenu);
        }

        if (authz.isAuthenticatedUserAuthorizedTo(BaseRoles.SALES_CLERK)) {
            final Menu salesClerkProductMenu = buildSalesClerkProductMenu();
            mainMenu.addSubMenu(PRODUCT_MANAGEMENT_OPTION, salesClerkProductMenu);
            final Menu salesClerkCustomerMenu = buildSalesClerkCustomerMenu();
            mainMenu.addSubMenu(CUSTOMERS_OPTION, salesClerkCustomerMenu);
            final Menu salesClerkOrderMenu = buildSalesClerkOrderMenu();
            mainMenu.addSubMenu(ORDER_OPTION, salesClerkOrderMenu);
        }

        if(authz.isAuthenticatedUserAuthorizedTo(BaseRoles.SALES_MANAGER)){
            final Menu surveysMenu = buildSalesManagerSurveyMenu();
            mainMenu.addSubMenu(SURVEYS_OPTION, surveysMenu);

        }

        if (!Application.settings().isMenuLayoutHorizontal()) {
            mainMenu.addItem(MenuItem.separator(SEPARATOR_LABEL));
        }

        mainMenu.addItem(EXIT_OPTION, "Exit", new ExitWithMessageAction("Bye, Bye"));

        return mainMenu;
    }

    private Menu buildSalesManagerSurveyMenu() {
        final Menu menu = new Menu("Surveys");

        menu.addItem(CREATE_SURVEYS_OPTION, "Create a new survey from file", new SurveyCreationUI()::show);
        menu.addItem(STATISTICAL_REPORT_QUESTIONNAIRE, "See statistical report of a questionnaire", new StatisticReportUI()::show);
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);
        return menu;
    }

    private Menu buildAdminSettingsMenu() {
        final Menu menu = new Menu("Settings >");

        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);

        return menu;
    }

    private Menu buildUsersMenu() {
        final Menu menu = new Menu("Users >");

        menu.addItem(ADD_USER_OPTION, "Add User", new AddUserUI()::show);
        menu.addItem(LIST_USERS_OPTION, "List all Users", new ListUsersAction());
        menu.addItem(DEACTIVATE_USER_OPTION, "Deactivate User", new DeactivateUserAction());
        menu.addItem(ACCEPT_REFUSE_SIGNUP_REQUEST_OPTION, "Accept/Refuse Signup Request",
                new AcceptRefuseSignupRequestAction());
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);

        return menu;
    }

    private Menu buildWarehouseEmployeeMenu() {
        final Menu menu = new Menu("Warehouse >");

        menu.addItem(CONFIG_AGV, "Configure new AGV", new ConfigureAgvUI()::show);
        menu.addItem(PREPARE_ORDER_BY_AGV, "Prepare an Order By an AGV", new PrepareOrderByAGVUI()::show);
        menu.addItem(ALREADY_PREPARED_BY_AGV, "Update Order already prepared by AGV's",new AlreadyPreparedByAgvUI()::show);
        menu.addItem(DASHBOARD, "View Warehouse dashboard", new WarehouseDashboardUI()::show);
        menu.addItem(EXIT_OPTION, "Return", new ExitWithMessageAction(""));

        return menu;
    }

    private Menu buildSalesClerkCustomerMenu() {
        final Menu menu = new Menu("Customers >");

        menu.addItem(REGISTER_CUSTOMER, "Register a new customer", new RegisterCustomerUI()::show);
        menu.addItem(GENERATE_CREDENTIALS, "Generate Credentials User", new GenerateCredentialsUI()::show);
        menu.addItem(DASHBOARD, "View Warehouse dashboard", new WarehouseDashboardUI()::show);
        menu.addItem(EXIT_OPTION, "Return", new ExitWithMessageAction(""));
        return menu;
    }

    private Menu buildSalesClerkProductMenu() {
        final Menu menu = new Menu("Products >");

        menu.addItem(SPECIFY_PRODUCT, "Specify new Product", new SpecifyNewProductUI()::show);
        menu.addItem(SPECIFY_CATEGORY, "Specify new Category", new RegisterCategoryUI()::show);
        menu.addItem(LIST_CATEGORIES,"List categories",new ListCategoryUI()::show);
        menu.addItem(VIEW_AND_SEARCH_CATALOG,"View and search catalog", new ViewProductsCatalogUI()::show);
        menu.addItem(EXIT_OPTION, "Return", new ExitWithMessageAction(""));

        return menu;
    }

    private Menu buildSalesClerkOrderMenu(){
        final Menu menu = new Menu("Order >");

        menu.addItem(SPECIFY_PRODUCT, "Add an Order to a Customer", new AddOrderUI()::show);
        menu.addItem(UPDATE_ORDER_TO_BEING_DELIVERED,"Update Orders",new UpdateOrderToBeingDeliveredUI()::show);
        menu.addItem(EXIT_OPTION, "Return", new ExitWithMessageAction(""));

        return menu;
    }


}