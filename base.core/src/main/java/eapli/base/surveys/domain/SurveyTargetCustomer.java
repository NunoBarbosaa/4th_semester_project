package eapli.base.surveys.domain;

import eapli.framework.domain.model.AggregateRoot;

import javax.persistence.*;

@Entity
public class SurveyTargetCustomer implements AggregateRoot<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long pk;

    private String idSystemUser;

    @ManyToOne
    private Survey survey;

    private int answered;

    public SurveyTargetCustomer(String idSystemUser, Survey survey){
        if(idSystemUser == null || idSystemUser.isEmpty() || idSystemUser.isBlank())
            throw new IllegalArgumentException("System User Id cannot be null or empty");
        if(survey == null)
            throw new IllegalArgumentException("Survey cannot be null");
        this.idSystemUser = idSystemUser;
        this.survey = survey;
        this.answered = 0;

    }

    protected SurveyTargetCustomer() {
    }

    public Survey survey() {
        return survey;
    }

    public String idSystemUser() {
        return idSystemUser;
    }

    public boolean isAnswered(){
        return answered == 1;
    }

    public void updateAnsweredStatusToTrue() {
        this.answered = 1;
    }

    @Override
    public boolean sameAs(Object other) {
        return false;
    }

    @Override
    public Long identity() {
        return pk;
    }
}
