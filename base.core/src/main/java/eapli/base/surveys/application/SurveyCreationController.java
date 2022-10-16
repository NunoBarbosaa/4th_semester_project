package eapli.base.surveys.application;

import eapli.base.customermanagement.domain.Gender;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.surveys.antlr.ProjectLexer;
import eapli.base.surveys.antlr.ProjectParser;
import eapli.base.surveys.domain.*;
import eapli.base.surveys.repositories.SurveyRepository;
import eapli.base.surveys.repositories.SurveyTargetRepository;
import eapli.base.surveys.visitors.CriteriaVisitor;
import eapli.base.surveys.visitors.SectionVisitor;
import eapli.base.surveys.visitors.SurveyVisitor;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class SurveyCreationController {

    private final SurveyRepository repo = PersistenceContext.repositories().surveys();
    private final SurveyTargetRepository repoTarget = PersistenceContext.repositories().surveyTargets();


    private Survey survey;


    public boolean createSurvey(String filePath) throws IOException {
        ProjectLexer lexer = new ProjectLexer(CharStreams.fromFileName(filePath));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ProjectParser parser = new ProjectParser(tokens);
        ParseTree tree = parser.prog(); // parse
        SurveyVisitor eval = new SurveyVisitor();
        survey = eval.visit(tree);
        SectionVisitor sectionVisitor = new SectionVisitor();
        sectionVisitor.visit(tree);
        Map<String, Section> sections = sectionVisitor.sections();
        CriteriaVisitor criteriaVisitor = new CriteriaVisitor();
        criteriaVisitor.visit(tree);
        Map<SurveyCriteria[], String> criteria = criteriaVisitor.getCriterias();
        if (criteria.size() == 1) {
            for (Map.Entry<SurveyCriteria[], String> entry : criteria.entrySet()) {
                if (entry.getKey().length == 1)
                    survey.addCriteria(entry.getKey()[0]);
                else {
                    survey.addCriterias(criteria);
                }
                break;
            }
        } else
            throw new IllegalStateException("Survey criteria was not provided");

        if (sections == null)
            return false;
        for (Map.Entry<String, Section> entry : sections.entrySet()) {
            survey.addSection(entry.getValue());
        }
        return survey != null;
    }

    public boolean isSurveyOnDataBase() {
        return repo.findBySurveyId(survey.identity()).isPresent();
    }

    public boolean saveSurvey() {
        return repo.save(survey) != null;

    }

    public void setTargetCustomers() {
        String query;
        if (this.survey.criteria() != null)
            query = this.survey.criteria().query();
        else
            query = this.survey.criteriaQuery();
        List<String> customersIDs = repo.findTargetCustomers(query);
        Survey newSurvey = repo.findBySurveyId(survey.identity()).get();
        for (String id : customersIDs) {
            SurveyTargetCustomer target = new SurveyTargetCustomer(id, newSurvey);
            repoTarget.save(target);
        }
    }


}
