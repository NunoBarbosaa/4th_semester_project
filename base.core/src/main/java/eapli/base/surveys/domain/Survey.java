package eapli.base.surveys.domain;

import eapli.base.surveys.dto.QuestionDto;
import eapli.base.surveys.dto.SectionDto;
import eapli.base.surveys.dto.SurveyDto;
import eapli.framework.domain.model.AggregateRoot;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Entity
public class Survey implements AggregateRoot<String>{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int pk;

    @Column(name = "surveyId",unique = true)
    private String id;

    private Date startsOn;

    private Date endsOn;

    private String surveyName;

    @Column(length = 1024)
    private String welcomeMessage;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "surveyId")
    @Fetch(FetchMode.SELECT)
    private List<Section> sectionList;

    private String endMessage;

    @Transient
    private SurveyCriteria criteria;

    @Transient
    private Map<SurveyCriteria[], String> criteriaList;

    protected Survey() {
    }

    public Survey(String id, String startsOn, String endsOn, String surveyName, String welcomeMessage,
                  String endMessage) throws ParseException {
        if (id == null || id.isEmpty() || id.isBlank())
            throw new IllegalArgumentException("Survey ID should not be null or empty");
        this.id = id;
        this.startsOn = parseDate(startsOn);
        this.endsOn = parseDate(endsOn);
        if(this.startsOn.after(this.endsOn))
            throw new IllegalArgumentException("Begin date cannot be after end date");
        if (surveyName == null || surveyName.isEmpty() || surveyName.isBlank())
            throw new IllegalArgumentException("Survey name should not be null or empty");
        this.surveyName = surveyName;
        if (welcomeMessage == null || welcomeMessage.isEmpty() || welcomeMessage.isBlank())
            throw new IllegalArgumentException("Welcome Message should not be null or empty");
        this.welcomeMessage = welcomeMessage;
        if (endMessage == null || endMessage.isEmpty() || endMessage.isBlank())
            throw new IllegalArgumentException("End Message should not be null or empty");
        this.endMessage = endMessage;
        this.sectionList = new ArrayList<>();
    }

    private Date parseDate(String dateString) throws ParseException {
        if (dateString == null || dateString.isEmpty() || dateString.isBlank())
            throw new IllegalArgumentException("Date should not be null or empty");
        return new SimpleDateFormat("dd-MM-yyyy").parse(dateString);
    }

    public boolean addSection(Section section){
        if(sectionList.contains(section))
            return false;
        return sectionList.add(section);
    }

    public Section findSection(int id){
        for(Section section : sectionList)
            if(section.identity() == id) return section;
        return null;
    }

    public void addCriteria(SurveyCriteria criteria) {
        if(criteria != null)
            this.criteria = criteria;
        else
            throw new IllegalArgumentException("Criteria cannot be null");
    }

    public void addCriterias(Map<SurveyCriteria[], String> criteria){
        this.criteriaList = criteria;
    }

    public SurveyCriteria criteria() {
        return criteria;
    }

    public String name() {
        return surveyName;
    }

    public String welcomeMessage() {
        return welcomeMessage;
    }

    public List<Section> sections() {
        return sectionList;
    }

    public String endMessage() {
        return endMessage;
    }

    public int pk() {
        return pk;
    }

    public String criteriaQuery() {
        StringBuilder query = new StringBuilder();
        if(criteriaList == null)
            return null;
        if(criteriaList.size() > 1)
            throw new IllegalStateException("Too many criteria for the survey");
        for(Map.Entry<SurveyCriteria[], String> entry : criteriaList.entrySet()){
            query.append(entry.getKey()[0].query()).append("\n").append(entry.getKey()[1].convertCombination(entry.getValue())).append("\n").append(entry.getKey()[1].query());
        }
        return query.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Survey survey = (Survey) o;
        return id.equals(survey.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean sameAs(Object other) {
        if (!(other instanceof Survey))
            return false;
        Survey obj = (Survey) other;
        return this.pk == obj.pk;
    }

    @Override
    public String identity() {
        return id;
    }

    public SurveyDto toDTO(){
        List<SectionDto> sectionDtoList = new ArrayList<>();
        List<QuestionDto> questionDtoList;
        for (Section section : sections()){
            questionDtoList = new ArrayList<>();
            for (Question question : section.questions()){
                questionDtoList.add(new QuestionDto(question.identity(), question.prompt(), question.instructions(), question.obligatoriness(),
                        question.type(), question.possibleAnswers()));
            }
            sectionDtoList.add(new SectionDto(section.identity(), section.name(), section.description(), questionDtoList));
        }
        return new SurveyDto(identity(), name(), welcomeMessage(), endMessage(), sectionDtoList);
    }
}
