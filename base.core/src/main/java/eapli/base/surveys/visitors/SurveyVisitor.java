package eapli.base.surveys.visitors;


import eapli.base.surveys.antlr.ProjectBaseVisitor;
import eapli.base.surveys.antlr.ProjectParser;
import eapli.base.surveys.domain.Survey;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class SurveyVisitor extends ProjectBaseVisitor<Survey> {

    Map<String, String> surveyData = new HashMap<>();

    @Override
    public Survey visitProg(ProjectParser.ProgContext ctx) {
        visitChildren(ctx);
        try {
            return createSurvey();
        }catch (ParseException e){
            System.out.println("ERROR: Dates in the survey have wrong format");
            return null;
        }
    }

    private Survey createSurvey() throws ParseException {
        String id = surveyData.get("ID");
        String startsOn = surveyData.get("DATE_BEGIN");
        String endsOn = surveyData.get("DATE_END");
        String surveyName = surveyData.get("SURVEY_NAME");
        String welcomeMessage = surveyData.get("WELCOME_MESSAGE");
        String endMessage = surveyData.get("END_MESSAGE");
        return new Survey(id, startsOn, endsOn, surveyName, welcomeMessage, endMessage);
    }

    @Override
    public Survey visitId(ProjectParser.IdContext ctx) {
        String id = ctx.ID().getText();
        surveyData.put("ID", id.substring(1, id.length()-1));
        return visitChildren(ctx);
    }

    @Override
    public Survey visitDate(ProjectParser.DateContext ctx) {
        surveyData.put("DATE_BEGIN", ctx.DATE(0).getText());
        surveyData.put("DATE_END", ctx.DATE(1).getText());
        return visitChildren(ctx);
    }

    @Override
    public Survey visitSurvey(ProjectParser.SurveyContext ctx) {
        surveyData.put("SURVEY_NAME", ctx.FREE_TEXT().getText().trim());
        return visitChildren(ctx);
    }

    @Override
    public Survey visitWelcome(ProjectParser.WelcomeContext ctx) {
        surveyData.put("WELCOME_MESSAGE", ctx.FREE_TEXT().getText().trim());
        return visitChildren(ctx);
    }

    @Override
    public Survey visitEnd(ProjectParser.EndContext ctx) {
        surveyData.put("END_MESSAGE", ctx.FREE_TEXT().getText());
        return visitChildren(ctx);
    }
}
