package eapli.base.usermanagement.domain;

import eapli.framework.infrastructure.authz.domain.model.Role;

public class SalesRoles {

    public static final Role POWER_USER = Role.valueOf("POWER_USER");
    public static final Role MENU_MANAGER = Role.valueOf("MENU_MANAGER");

    public static final Role ADMIN = Role.valueOf("Admin");
}
