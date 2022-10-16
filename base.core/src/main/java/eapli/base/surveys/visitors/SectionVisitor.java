package eapli.base.surveys.visitors;


import eapli.base.surveys.antlr.ProjectBaseVisitor;
import eapli.base.surveys.antlr.ProjectParser;
import eapli.base.surveys.domain.Obligatoriness;
import eapli.base.surveys.domain.Question;
import eapli.base.surveys.domain.Section;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SectionVisitor extends ProjectBaseVisitor<Map<String, Section>> {

    private Map<String, Section> sections = new HashMap<>();
    List<String> possibleAnswers;
    private String currentSection;

    @Override
    public Map<String, Section> visitSections(ProjectParser.SectionsContext ctx) {
        if (ctx.FREE_TEXT() != null)
            sections.put(ctx.FREE_TEXT().getText().trim(), new Section(Integer.parseInt(ctx.INT().getText().trim()), ctx.FREE_TEXT().getText().trim(), ""));
        return visitChildren(ctx);

    }

    @Override
    public Map<String, Section> visitQuestions(ProjectParser.QuestionsContext ctx) {
        currentSection = ctx.FREE_TEXT().getText().trim();

        if (sections.containsKey(currentSection)) {
            if (ctx.instruction() != null)
                sections.get(currentSection).changeInstructions(ctx.instruction().FREE_TEXT().getText());
        } else {
            System.out.println("The section '" + ctx.FREE_TEXT().getText() + "' does not exist");
            return null;
        }
        return visitChildren(ctx);
    }

    @Override
    public Map<String, Section> visitQuestion(ProjectParser.QuestionContext ctx) {
        if(ctx.end()!=null)
            return visitChildren(ctx);
        possibleAnswers = new ArrayList<>();
        int questionId = Integer.parseInt(ctx.INT().getText().trim());
        String instruction = null;
        if (ctx.instruction() != null)
            instruction = ctx.instruction().FREE_TEXT().toString().trim();
        String question = ctx.FREE_TEXT().getText().trim();
        String questionType = ctx.questionType().getText().trim().substring(6).trim();
        String obligatorinessCode = ctx.OBLIGATORINESS().getText().trim();
        Obligatoriness obligatoriness;
        if (obligatorinessCode.equals("c") && ctx.condition() != null) {
            int sectionDep = Integer.parseInt(ctx.condition().INT(0).getText().trim());
            int questionDep = Integer.parseInt(ctx.condition().INT(1).getText().trim());
            String answerDep = ctx.condition().FREE_TEXT().getText();
            obligatoriness = new Obligatoriness("c", sectionDep, questionDep, answerDep);
        } else if (obligatorinessCode.equals("c") && ctx.condition() == null) {
            System.out.println("ERROR: Question has obligatoriness conditional but does not provide the conditions");
            return null;
        } else {
            obligatoriness = new Obligatoriness(obligatorinessCode);
        }
        if (ctx.answers() != null)
            visitChildren(ctx.answers());
        Question questionObj;
        if (!possibleAnswers.isEmpty())
            questionObj = new Question(questionId, question, instruction, obligatoriness, questionType, possibleAnswers);
        else
            questionObj = new Question(questionId, question, instruction, obligatoriness, questionType);
        sections.get(currentSection).addQuestion(questionObj);
        return visitChildren(ctx);
    }

    @Override
    public Map<String, Section> visitAnswers(ProjectParser.AnswersContext ctx) {
        if(ctx.FREE_TEXT() != null)
            possibleAnswers.add(ctx.FREE_TEXT().getText().trim());
        return visitChildren(ctx);
    }

    public Map<String, Section> sections() {
        return new HashMap<>(sections);
    }
}
