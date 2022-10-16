package eapli.base.surveys.dto;

import java.util.List;

public class SectionDto implements Comparable<SectionDto>{

    private final int sectionId;

    private final String name;

    private final String description;

    private final List<QuestionDto> questionList;

    public SectionDto(int sectionId, String name, String description, List<QuestionDto> questionList) {
        this.sectionId = sectionId;
        this.name = name;
        this.description = description;
        this.questionList = questionList;
    }

    public int id() {
        return sectionId;
    }

    public String name() {
        return name;
    }

    public String description() {
        return description;
    }

    public List<QuestionDto> questions() {
        return questionList;
    }

    @Override
    public int compareTo(SectionDto e) {
        return this.id()-(e.id());
    }
}
