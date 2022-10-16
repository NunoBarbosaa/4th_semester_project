package eapli.base.surveys.dto;

import java.util.List;

public class SurveyDto {

    private final String id;

    private final String name;

    private final String welcomeMessage;

    private final String endMessage;

    private final List<SectionDto> sections;

    public SurveyDto(String id, String name, String welcomeMessage, String endMessage, List<SectionDto> sections) {
        this.id = id;
        this.name = name;
        this.welcomeMessage = welcomeMessage;
        this.endMessage = endMessage;
        this.sections = sections;
    }

    public String id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String welcomeMessage() {
        return welcomeMessage;
    }

    public String endMessage() {
        return endMessage;
    }

    public List<SectionDto> sections() {
        return sections;
    }

    public int numberOfQuestions(){
        int questionCount = 0;
        for (SectionDto section: sections){
            questionCount += section.questions().size();
        }
        return questionCount;
    }
}
