package eapli.base.app.backoffice.console.presentation.survey;

import eapli.base.surveys.dto.SurveyDto;
import eapli.framework.visitor.Visitor;

public class SurveySelectionPrinter implements Visitor<SurveyDto> {

    @Override
    public void visit(SurveyDto visitee) {
        System.out.printf("Survey: %s | ID: %s | Nr. of questions: %d\n", visitee.name(), visitee.id() ,visitee.numberOfQuestions());
    }
}

