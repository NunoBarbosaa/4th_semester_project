package eapli.base.app.user.console.presentation.survey;

import eapli.base.surveys.dto.SurveyDto;

import eapli.framework.visitor.Visitor;

public class SurveySelectionPrinter implements Visitor<SurveyDto> {
    @Override
    public void visit(SurveyDto visitee) {
        System.out.printf("Survey: %s | Nr of questions: %d\n", visitee.name(), visitee.numberOfQuestions());
    }
}
