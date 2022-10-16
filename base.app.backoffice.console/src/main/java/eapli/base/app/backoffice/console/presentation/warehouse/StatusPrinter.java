package eapli.base.app.backoffice.console.presentation.warehouse;

import eapli.base.warehouse.domain.AgvStatus;
import eapli.framework.visitor.Visitor;

public class StatusPrinter implements Visitor<AgvStatus> {
    @Override
    public void visit(final AgvStatus visitee) {
        System.out.printf(visitee.toString());
    }
}
