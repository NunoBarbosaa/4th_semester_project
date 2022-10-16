package eapli.base.surveys.visitors;


import eapli.base.surveys.antlr.ProjectBaseVisitor;
import eapli.base.surveys.antlr.ProjectParser;
import eapli.base.surveys.domain.CombinationType;
import eapli.base.surveys.domain.SurveyCriteria;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CriteriaVisitor extends ProjectBaseVisitor<SurveyCriteria> {

    Map<SurveyCriteria[], String> criterias = new HashMap<>();

    List<SurveyCriteria> list = new ArrayList<>();
    String logicalOperator;

    @Override
    public SurveyCriteria visitCriteriaProductBought(ProjectParser.CriteriaProductBoughtContext ctx) {
        List<String> pIDs = new ArrayList<>();
        for (TerminalNode node : ctx.ID()) {
            String id = node.getText().trim();
            pIDs.add(id.substring(1, id.length() - 1));
        }
        try {
            list.add(new SurveyCriteria(pIDs, ctx.DATE(0).getText().trim(), ctx.DATE(1).getText().trim()));
            return visitChildren(ctx);
        } catch (ParseException e) {
            System.out.println("ERROR: Dates in the survey have wrong format");
            return null;
        }
    }

    @Override
    public SurveyCriteria visitCombinedCriteria(ProjectParser.CombinedCriteriaContext ctx) {
        logicalOperator = ctx.combinationType().getText();
        return visitChildren(ctx);
    }

    @Override
    public SurveyCriteria visitCriteriaBrandBought(ProjectParser.CriteriaBrandBoughtContext ctx) {
        try {
            return new SurveyCriteria(ctx.FREE_TEXT().getText().trim(), ctx.DATE(0).getText().trim(), ctx.DATE(1).getText().trim());
        } catch (ParseException e) {
            System.out.println("ERROR: Dates in the survey have wrong format");
            return null;
        }
    }

    @Override
    public SurveyCriteria visitCriteriaAgeInterval(ProjectParser.CriteriaAgeIntervalContext ctx) {
        list.add(new SurveyCriteria(Integer.parseInt(ctx.INT(0).getText()), Integer.parseInt(ctx.INT(1).getText())));
        return visitChildren(ctx);
    }

    @Override
    public SurveyCriteria visitCriteriaAgeLessThan(ProjectParser.CriteriaAgeLessThanContext ctx) {
        list.add(new SurveyCriteria(Integer.parseInt(ctx.INT().getText()), "Less"));
        return visitChildren(ctx);
    }

    @Override
    public SurveyCriteria visitCriteriaAgeMoreThan(ProjectParser.CriteriaAgeMoreThanContext ctx) {
        list.add(new SurveyCriteria(Integer.parseInt(ctx.INT().getText()), "Higher"));
        return visitChildren(ctx);
    }

    @Override
    public SurveyCriteria visitCriteriaGender(ProjectParser.CriteriaGenderContext ctx) {
        list.add(new SurveyCriteria(ctx.gender().getText().trim()));
        return visitChildren(ctx);
    }

    public Map<SurveyCriteria[], String> getCriterias() {
        SurveyCriteria[] crit;
        if(list.size() == 1){
            crit = new SurveyCriteria[1];
            crit[0] = list.get(0);
            criterias.put(crit, null);
            return criterias;
        }
        if(list.size() != 2)
            throw new IllegalStateException("Survey has a different number of criteria then expected");
        if(logicalOperator == null)
            throw new IllegalStateException("Logical operator is missing, cannot combine criteria");
        crit = new SurveyCriteria[2];
        crit[0] = list.get(0);
        crit[1] = list.get(1);
        criterias.put(crit, logicalOperator);
        return criterias;
    }
}
