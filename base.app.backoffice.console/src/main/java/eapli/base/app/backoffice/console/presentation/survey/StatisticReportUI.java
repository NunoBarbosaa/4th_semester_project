package eapli.base.app.backoffice.console.presentation.survey;

import eapli.base.surveys.application.StatisticReportController;
import eapli.base.surveys.domain.QuestionType;
import eapli.base.surveys.dto.QuestionDto;
import eapli.base.surveys.dto.SectionDto;
import eapli.base.surveys.dto.SurveyDto;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;

import java.util.Collections;
import java.util.List;

public class StatisticReportUI extends AbstractUI {

    private final StatisticReportController controller = new StatisticReportController();

    private static final String YES = "Yes";

    private static final String NO = "No";

    @Override
    protected boolean doShow() {
        int continuar;
        do {
            List<SurveyDto> surveyDtoList = controller.allSurveys();
            if (surveyDtoList == null) {
                System.out.println("No exists questionnaires created.");
            } else {
                String id;
                int i;
                List<String> combinations;
                boolean newLine;
                System.out.println();
                final SelectWidget<SurveyDto> surveyWidget = new SelectWidget<>("Select one of the surveys below:\n", surveyDtoList, new SurveySelectionPrinter());
                surveyWidget.show();
                if (surveyWidget.selectedElement() == null) {
                    System.out.println("\nINFO: You did not selected any survey. Exiting...");
                    return false;
                }
                SurveyDto surveyDto = surveyWidget.selectedElement();
                id = surveyDto.id();
                System.out.println("\n------------------------ " + surveyDto.name() + " ------------------------");
                int customersRequestedToAnswer = controller.numberCustomersRequestedToAnswer(id);
                System.out.println("\nNumber of customers requested to answer: " + customersRequestedToAnswer);
                int customersAnswered = controller.numberCustomersAnswered(id);
                System.out.println("Number of customers requested to answer: " + customersAnswered);
                double percentage = (double) customersAnswered / customersRequestedToAnswer * 100;
                System.out.printf("Percentage of responses obtained: %.0f%%.", percentage);
                Collections.sort(surveyDto.sections());
                for (SectionDto sectionDto : surveyDto.sections()) {
                    System.out.println("\n\nSection ID: " + sectionDto.id());
                    System.out.println("Section Name: " + sectionDto.name());
                    Collections.sort(sectionDto.questions());
                    for (QuestionDto questionDto : sectionDto.questions()) {
                        System.out.println("\nQuestion: " + questionDto.prompt());
                        System.out.println("Type: " + questionDto.type().toString().replace("_", " "));
                        if (questionDto.type().equals(QuestionType.SINGLE_CHOICE)) {
                            questionWithMultipleAnswers(id, sectionDto.id(), questionDto);
                        } else if (questionDto.type().equals(QuestionType.MULTIPLE_CHOICE)) {
                            questionWithMultipleAnswers(id, sectionDto.id(), questionDto);
                            combinations = controller.combinations(id, sectionDto.id(), questionDto.id());
                            i = 0;
                            newLine = false;
                            for (String s : combinations) {
                                if (s.split(";").length > 1) {
                                    if(i > 0) System.out.print(", ");
                                    newLine = true;
                                    System.out.printf(s.replace(";", " + ") + " = %.0f%%", controller.getPercentageAnswerQuestion(id, sectionDto.id(), questionDto.id(), s));
                                    i++;
                                }
                            }
                            if (newLine) System.out.println();
                        } else if (questionDto.type().equals(QuestionType.SCALING)) {
                            questionWithMultipleAnswers(id, sectionDto.id(), questionDto);
                        } else if (questionDto.type().equals(QuestionType.YES_OR_NO)) {
                            System.out.printf("YES = %.0f%%, NO = %.0f%%\n", controller.getPercentageAnswerQuestion(id, sectionDto.id(), questionDto.id(), YES),
                                    controller.getPercentageAnswerQuestion(id, sectionDto.id(), questionDto.id(), NO));
                        } else if (questionDto.type().equals(QuestionType.FREE_TEXT)) {
                            int num = controller.numberCustomersAnswerQuestion(id, sectionDto.id(), questionDto.id());
                            double per = (double) num / customersAnswered * 100;
                            System.out.printf("Percentage of customers that answer the question: %.0f%%.\n", per);
                        } else if (questionDto.type().equals(QuestionType.NUMERIC)) {
                            int avg = controller.averageAnswer(id, sectionDto.id(), questionDto.id());
                            if (avg != 0) System.out.println("Average: " + avg);
                            else System.out.println("No answers for this question.");
                        } else if(questionDto.type().equals(QuestionType.SORTING)) {
                            List<String> allAnswers = controller.allAnswers(id, sectionDto.id(), questionDto.id());
                            for (int j = 0; j < questionDto.possibleAnswers().size(); j++) {
                                i = 0;
                                System.out.print((j + 1) + " Place = ");
                                for (String s : questionDto.possibleAnswers()) {
                                    System.out.printf(s + " (%.0f%%)", percentage(j, s, allAnswers));
                                    i++;
                                    if(i < questionDto.possibleAnswers().size()) System.out.print(", ");
                                }
                                System.out.println();
                            }
                        }
                    }
                }
                System.out.println("\n------------------------ End of Report ------------------------");
            }
            System.out.println("\nPretend to get a new report of other survey?");
            System.out.println("1 - Yes\n2 - No\n");
            continuar = Console.readOption(1, 2, 2);
        } while(continuar == 1);
        return true;
    }

    private double percentage(int pos, String answer, List<String> answers){
        String[] newString;
        int count = 0;
        for (String s : answers){
            newString = s.split(";");
            if(answer.equals(newString[pos])) count++;
        }
        if (count == 0) return 0;
        else return (double) count / answers.size() * 100;
    }

    private void questionWithMultipleAnswers(String id, int sectionID, QuestionDto questionDto) {
        int i = 0;
        for (String s : questionDto.possibleAnswers()) {
            System.out.printf(s + " = %.0f%%", controller.getPercentageAnswerQuestion(id, sectionID, questionDto.id(), s));
            i++;
            if(i < questionDto.possibleAnswers().size()) System.out.print(", ");
        }
        System.out.println();
    }

    @Override
    public String headline() {
        return "Statistic Report";
    }
}
