package eapli.base.surveys.domain;

import eapli.framework.domain.model.DomainEntity;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Section implements DomainEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int pk;

    @Column(name = "sectionId")
    private int id;

    private String sectionName;

    @Column(length = 512)
    private String sectionDescription;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "sectionId")
    @Fetch(FetchMode.SELECT)
    private List<Question> questionList;

    protected Section(){}

    public Section(int id, String sectionName, String sectionDescription){
        this.id = id;
        if (sectionName == null || sectionName.isEmpty() || sectionName.isBlank())
            throw new IllegalArgumentException("Section name should not be null or empty");
        this.sectionName = sectionName;
        this.sectionDescription = sectionDescription;
        this.questionList = new ArrayList<>();
    }

    public boolean addQuestion(Question question){
        if(questionList.contains(question))
            return false;
        return questionList.add(question);
    }

    public Question findQuestion(int id){
        for(Question question : questionList)
            if(question.identity() == id) return question;
        return null;
    }

    public void changeInstructions(String sectionDescription) {
        if (sectionDescription == null || sectionDescription.isEmpty() || sectionDescription.isBlank())
            throw new IllegalArgumentException("New section instructions should not be null or empty");
        this.sectionDescription = sectionDescription;
    }

    public String name() {
        return sectionName;
    }

    public String description() {
        return sectionDescription;
    }

    public List<Question> questions() {
        return questionList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Section section = (Section) o;
        return id == section.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean sameAs(Object other) {
        if(!(other instanceof Section))
            return false;
        Section obj = (Section) other;
        return this.pk == obj.pk;
    }

    @Override
    public Integer identity() {
        return id;
    }
}
