package eapli.base.app.backoffice.console.presentation.warehouse;

import eapli.base.warehouse.domain.AgvDock;
import eapli.framework.visitor.Visitor;

public class AgvDockPrinter implements Visitor<AgvDock> {
    @Override
    public void visit(final AgvDock visitee) {
        System.out.printf("Dock: %s | Starts: %s | Ends: %s", visitee.identity(), visitee.startsFrom(), visitee.endsOn());
    }
}
