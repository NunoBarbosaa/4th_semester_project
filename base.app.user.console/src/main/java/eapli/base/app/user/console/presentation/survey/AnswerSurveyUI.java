package eapli.base.app.user.console.presentation.survey;

import eapli.base.surveys.application.CustomerSurveyController;
import eapli.base.surveys.domain.ObligatorinessType;
import eapli.base.surveys.domain.QuestionType;
import eapli.base.surveys.dto.QuestionDto;
import eapli.base.surveys.dto.SectionDto;
import eapli.base.surveys.dto.SurveyAnswerDto;
import eapli.base.surveys.dto.SurveyDto;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AnswerSurveyUI extends AbstractUI {

    public final String ANSI_RESET = "\u001B[0m";

    public final String ANSI_RED = "\u001B[31m";
    private final CustomerSurveyController controller = new CustomerSurveyController();

    @Override
    protected boolean doShow() {
        List<SurveyDto> unansweredSurveys;
        try {
            unansweredSurveys = controller.getListOfUnansweredSurveys();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("ERROR: " + e.getMessage());
            return true;
        }
        if (unansweredSurveys == null || unansweredSurveys.isEmpty()) {
            System.out.println("INFO: There are no surveys to answer");
            return true;
        }


        final SelectWidget<SurveyDto> surveyWidget = new SelectWidget<>("Select one of the surveys below", unansweredSurveys,
                new SurveySelectionPrinter());
        surveyWidget.show();

        if (surveyWidget.selectedElement() == null) {
            System.out.println("INFO: You did not selected any survey. Exiting...");
            return false;
        }

        SurveyDto chosenSurvey = surveyWidget.selectedElement();
        return createSurveyDynamicUI(chosenSurvey);
    }

    private boolean createSurveyDynamicUI(SurveyDto chosenSurvey) {
        List<SurveyAnswerDto> answers = new ArrayList<>();
        System.out.println("Survey: " + chosenSurvey.name());
        System.out.println();
        System.out.println(chosenSurvey.welcomeMessage());
        System.out.println("==============================================================================");

        Collections.sort(chosenSurvey.sections());
        for (SectionDto dto : chosenSurvey.sections()) {
            System.out.printf("Section %d: %s%n%n", dto.id(), dto.name());
            if (dto.description() != null && !dto.description().isEmpty() && !dto.description().isBlank())
                System.out.printf("(%s)%n", dto.description());
            Collections.sort(dto.questions());
            for (QuestionDto question : dto.questions()) {
                if (!question.obligatoriness().type().equals(ObligatorinessType.CONDITIONAL) || (question.obligatoriness().type().equals(ObligatorinessType.CONDITIONAL) && isToAnswerQuestion(question, answers))) {
                    System.out.println(printQuestionAndCollectAnswer(question));
                    SurveyAnswerDto answer;
                    if ((answer = collectAnswer(question, chosenSurvey, dto)) != null)
                        answers.add(answer);
                    else if (!question.obligatoriness().type().equals(ObligatorinessType.OPTIONAL))
                        return false;
                }
            }
            System.out.println("#########################");

        }
        System.out.println("End!");
        System.out.println("==============================================================================");
        System.out.println(chosenSurvey.endMessage());
        try {
            if (controller.saveAnswers(answers))
                System.out.println("INFO: Your answers were saved");
            else
                System.out.println("INFO: Something went wrong :(");
        } catch (IOException e) {
            System.out.println("INFO: Something went wrong :(");
        }
        return true;
    }

    private String printQuestionAndCollectAnswer(QuestionDto question) {
        StringBuilder prompt = new StringBuilder();
        prompt.append(String.format("Q%d: %s", question.id(), question.prompt()));
        if (question.obligatoriness().type().equals(ObligatorinessType.CONDITIONAL) || question.obligatoriness().type().equals(ObligatorinessType.MUST_ANSWER))
            prompt.append(ANSI_RED + "*" + ANSI_RESET);
        if (question.instructions() != null && !question.instructions().isEmpty() && !question.instructions().isBlank())
            prompt.append(String.format("%n(%s)", question.instructions()));
        return prompt.toString();
    }

    private SurveyAnswerDto collectAnswer(QuestionDto question, SurveyDto survey, SectionDto section) {
        String userName = controller.getUserName();
        if (question.type().equals(QuestionType.NUMERIC)) {
            int number;
            if (question.obligatoriness().type().equals(ObligatorinessType.OPTIONAL)) {
                number = Console.readInteger("Your Answer (-1 to skip):");
                if (number != -1)
                    return new SurveyAnswerDto(userName, survey.id(), section.id(), question.id(), question.type(), String.format("%d", number));
                else
                    return null;
            } else {
                number = Console.readInteger("Your Answer:");
                return new SurveyAnswerDto(userName, survey.id(), section.id(), question.id(), question.type(), String.format("%d", number));
            }
        }
        if (question.type().equals(QuestionType.YES_OR_NO)) {
            do {
                int option;
                if (question.obligatoriness().type().equals(ObligatorinessType.OPTIONAL)) {
                    option = Console.readInteger("\t1. Yes\n\t2. No\n\t0. Skip");
                    if (option == 0)
                        return null;
                } else {
                    option = Console.readInteger("\t1. Yes\n\t2. No");
                }
                if (option == 1)
                    return new SurveyAnswerDto(userName, survey.id(), section.id(), question.id(), question.type(), "Yes");
                if (option == 2)
                    return new SurveyAnswerDto(userName, survey.id(), section.id(), question.id(), question.type(), "No");
                System.out.println("INFO: Invalid choice");
            } while (true);
        }
        if (question.type().equals(QuestionType.SCALING)) {
            int nrOfOptions = question.possibleAnswers().size();
            int index = 1;
            for (String answer : question.possibleAnswers()) {
                System.out.printf("\t%d. %s%n", index, answer);
                index++;
            }
            do {
                int option;
                if (question.obligatoriness().type().equals(ObligatorinessType.OPTIONAL)) {
                    option = Console.readInteger(String.format("Choose a number from 1 to %d according to the scale above: (Enter 0 to skip)", nrOfOptions));
                    if (option == 0)
                        return null;
                } else {
                    option = Console.readInteger(String.format("Choose a number from 1 to %d according to the scale above:", nrOfOptions));
                }
                if (option > 0 && option <= nrOfOptions)
                    return new SurveyAnswerDto(userName, survey.id(), section.id(), question.id(), question.type(), question.possibleAnswers().get(option - 1));
                System.out.println("INFO: Invalid choice");
            } while (true);
        }
        if (question.type().equals(QuestionType.SORTING)) {
            int nrOfOptions = question.possibleAnswers().size();
            int index = 1;
            for (String answer : question.possibleAnswers()) {
                System.out.printf("\t%d. %s%n", index, answer);
                index++;
            }
            do {
                String options;
                if (question.obligatoriness().type().equals(ObligatorinessType.OPTIONAL)) {
                    options = Console.readLine("Sort the options above by writing all the numbers in order and separated with a comma: (Press enter when finished)");
                    if (options.isEmpty() || options.isBlank())
                        return null;
                } else {
                    options = Console.readLine("Sort the options above by writing the numbers in order and separated with a comma: (Press enter when finished)");
                }
                boolean invalid = false;
                if (options != null && !options.isEmpty() && !options.isBlank()) {
                    String[] individualOptions = options.split(",");
                    String answer = null;
                    if (individualOptions.length != nrOfOptions || (answer = createRawAnswer(question, nrOfOptions, individualOptions)) == null)
                        invalid = true;
                    if (!invalid) {
                        answer = answer.substring(0, answer.length() - 1);
                        return new SurveyAnswerDto(userName, survey.id(), section.id(), question.id(), question.type(), answer);
                    }
                }
                System.out.println("INFO: Invalid choice");
            } while (true);
        }
        if (question.type().equals(QuestionType.SINGLE_CHOICE)) {
            int nrOfOptions = question.possibleAnswers().size();
            int index = 1;
            for (String answer : question.possibleAnswers()) {
                System.out.printf("\t%d. %s%n", index, answer);
                index++;
            }
            do {
                int option;
                if (question.obligatoriness().type().equals(ObligatorinessType.OPTIONAL)) {
                    option = Console.readInteger("Choose one of the options above: (Enter 0 to skip)");
                    if (option == 0)
                        return null;
                } else {
                    option = Console.readInteger("Choose one of the options above:");
                }
                if (option > 0 && option <= nrOfOptions)
                    return new SurveyAnswerDto(userName, survey.id(), section.id(), question.id(), question.type(), question.possibleAnswers().get(option - 1));
                System.out.println("INFO: Invalid choice");
            } while (true);
        }
        if (question.type().equals(QuestionType.MULTIPLE_CHOICE)) {
            int nrOfOptions = question.possibleAnswers().size();
            int index = 1;
            for (String answer : question.possibleAnswers()) {
                System.out.printf("\t%d. %s%n", index, answer);
                index++;
            }
            do {
                String options;
                if (question.obligatoriness().type().equals(ObligatorinessType.OPTIONAL)) {
                    options = Console.readLine("You can select multiple options, in the following format -> \"1,5,3\": (Enter 0 to skip)");
                    if (options.trim().equals("0"))
                        return null;
                } else {
                    options = Console.readLine("You can select multiple options, in the following format -> \"1,5,3\":");
                }
                boolean invalid = false;
                if (options != null && !options.isEmpty() && !options.isBlank()) {
                    String[] individualOptions = options.split(",");
                    String answer;
                    if ((answer = createRawAnswer(question, nrOfOptions, individualOptions)) == null)
                        invalid = true;
                    if (!invalid) {
                        answer = answer.substring(0, answer.length() - 1);
                        return new SurveyAnswerDto(userName, survey.id(), section.id(), question.id(), question.type(), answer);
                    }
                }

            } while (true);
        }
        if (question.type().equals(QuestionType.FREE_TEXT)) {
            String answer;
            if (question.obligatoriness().type().equals(ObligatorinessType.OPTIONAL)) {
                answer = Console.readLine("Write your answer below: (Hit enter key when you finished your answer or leave field empty to skip)");
                if (answer.isEmpty() || answer.isBlank())
                    return null;
            } else {
                answer = Console.readLine("Write your answer below: (Hit enter key when you finished your answer)");
            }
            do {
                if (!answer.isEmpty() && !answer.isBlank())
                    return new SurveyAnswerDto(userName, survey.id(), section.id(), question.id(), question.type(), answer);
                System.out.println("INFO: You cannot leave the field empty");
                answer = Console.readLine("Write your answer below: (Hit enter key when you finished your answer)");
            } while (true);

        }
        System.out.println("ERROR: Something went wrong :(");
        return null;
    }

    private String createRawAnswer(QuestionDto question, int nrOfOptions, String[] individualOptions) {
        StringBuilder answer = new StringBuilder();
        try {
            for (String option : individualOptions) {
                int nrOption = Integer.parseInt(option.trim());
                if (nrOption > 0 && nrOption <= nrOfOptions) {
                    answer.append(question.possibleAnswers().get(nrOption - 1)).append(";");
                } else {
                    System.out.println("INFO: Invalid choices");
                    return null;
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("INFO: Invalid input, please follow the displayed rules");
            return null;
        }
        return answer.toString();
    }

    private boolean isToAnswerQuestion(QuestionDto question, List<SurveyAnswerDto> answers) {
        for (SurveyAnswerDto answer : answers) {
            if (answer.sectionId() == question.obligatoriness().sectionIdDependency() &&
                    answer.questionId() == question.obligatoriness().questionIdDependency() &&
                    answer.rawAnswer().equals(question.obligatoriness().answerDependency()))
                return true;
        }
        return false;
    }

    @Override
    public String headline() {
        return "Surveys";
    }
}
