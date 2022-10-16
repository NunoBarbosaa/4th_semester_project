package eapli.base.surveys.antlr;
// Generated from C:/Users/Andr�/Documents/Documents/ISEP/LAPR4/Docs/LPROG\Project.g4 by ANTLR 4.10.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ProjectParser}.
 */
public interface ProjectListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ProjectParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(ProjectParser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProjectParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(ProjectParser.ProgContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProjectParser#id}.
	 * @param ctx the parse tree
	 */
	void enterId(ProjectParser.IdContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProjectParser#id}.
	 * @param ctx the parse tree
	 */
	void exitId(ProjectParser.IdContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProjectParser#date}.
	 * @param ctx the parse tree
	 */
	void enterDate(ProjectParser.DateContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProjectParser#date}.
	 * @param ctx the parse tree
	 */
	void exitDate(ProjectParser.DateContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProjectParser#criteria}.
	 * @param ctx the parse tree
	 */
	void enterCriteria(ProjectParser.CriteriaContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProjectParser#criteria}.
	 * @param ctx the parse tree
	 */
	void exitCriteria(ProjectParser.CriteriaContext ctx);
	/**
	 * Enter a parse tree produced by the {@code simpleCriteria}
	 * labeled alternative in {@link ProjectParser#criterias}.
	 * @param ctx the parse tree
	 */
	void enterSimpleCriteria(ProjectParser.SimpleCriteriaContext ctx);
	/**
	 * Exit a parse tree produced by the {@code simpleCriteria}
	 * labeled alternative in {@link ProjectParser#criterias}.
	 * @param ctx the parse tree
	 */
	void exitSimpleCriteria(ProjectParser.SimpleCriteriaContext ctx);
	/**
	 * Enter a parse tree produced by the {@code combinedCriteria}
	 * labeled alternative in {@link ProjectParser#criterias}.
	 * @param ctx the parse tree
	 */
	void enterCombinedCriteria(ProjectParser.CombinedCriteriaContext ctx);
	/**
	 * Exit a parse tree produced by the {@code combinedCriteria}
	 * labeled alternative in {@link ProjectParser#criterias}.
	 * @param ctx the parse tree
	 */
	void exitCombinedCriteria(ProjectParser.CombinedCriteriaContext ctx);
	/**
	 * Enter a parse tree produced by the {@code criteriaProductBought}
	 * labeled alternative in {@link ProjectParser#criteriaType}.
	 * @param ctx the parse tree
	 */
	void enterCriteriaProductBought(ProjectParser.CriteriaProductBoughtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code criteriaProductBought}
	 * labeled alternative in {@link ProjectParser#criteriaType}.
	 * @param ctx the parse tree
	 */
	void exitCriteriaProductBought(ProjectParser.CriteriaProductBoughtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code criteriaBrandBought}
	 * labeled alternative in {@link ProjectParser#criteriaType}.
	 * @param ctx the parse tree
	 */
	void enterCriteriaBrandBought(ProjectParser.CriteriaBrandBoughtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code criteriaBrandBought}
	 * labeled alternative in {@link ProjectParser#criteriaType}.
	 * @param ctx the parse tree
	 */
	void exitCriteriaBrandBought(ProjectParser.CriteriaBrandBoughtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code criteriaAgeMoreThan}
	 * labeled alternative in {@link ProjectParser#criteriaType}.
	 * @param ctx the parse tree
	 */
	void enterCriteriaAgeMoreThan(ProjectParser.CriteriaAgeMoreThanContext ctx);
	/**
	 * Exit a parse tree produced by the {@code criteriaAgeMoreThan}
	 * labeled alternative in {@link ProjectParser#criteriaType}.
	 * @param ctx the parse tree
	 */
	void exitCriteriaAgeMoreThan(ProjectParser.CriteriaAgeMoreThanContext ctx);
	/**
	 * Enter a parse tree produced by the {@code criteriaAgeLessThan}
	 * labeled alternative in {@link ProjectParser#criteriaType}.
	 * @param ctx the parse tree
	 */
	void enterCriteriaAgeLessThan(ProjectParser.CriteriaAgeLessThanContext ctx);
	/**
	 * Exit a parse tree produced by the {@code criteriaAgeLessThan}
	 * labeled alternative in {@link ProjectParser#criteriaType}.
	 * @param ctx the parse tree
	 */
	void exitCriteriaAgeLessThan(ProjectParser.CriteriaAgeLessThanContext ctx);
	/**
	 * Enter a parse tree produced by the {@code criteriaAgeInterval}
	 * labeled alternative in {@link ProjectParser#criteriaType}.
	 * @param ctx the parse tree
	 */
	void enterCriteriaAgeInterval(ProjectParser.CriteriaAgeIntervalContext ctx);
	/**
	 * Exit a parse tree produced by the {@code criteriaAgeInterval}
	 * labeled alternative in {@link ProjectParser#criteriaType}.
	 * @param ctx the parse tree
	 */
	void exitCriteriaAgeInterval(ProjectParser.CriteriaAgeIntervalContext ctx);
	/**
	 * Enter a parse tree produced by the {@code criteriaGender}
	 * labeled alternative in {@link ProjectParser#criteriaType}.
	 * @param ctx the parse tree
	 */
	void enterCriteriaGender(ProjectParser.CriteriaGenderContext ctx);
	/**
	 * Exit a parse tree produced by the {@code criteriaGender}
	 * labeled alternative in {@link ProjectParser#criteriaType}.
	 * @param ctx the parse tree
	 */
	void exitCriteriaGender(ProjectParser.CriteriaGenderContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProjectParser#combinationType}.
	 * @param ctx the parse tree
	 */
	void enterCombinationType(ProjectParser.CombinationTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProjectParser#combinationType}.
	 * @param ctx the parse tree
	 */
	void exitCombinationType(ProjectParser.CombinationTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProjectParser#survey}.
	 * @param ctx the parse tree
	 */
	void enterSurvey(ProjectParser.SurveyContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProjectParser#survey}.
	 * @param ctx the parse tree
	 */
	void exitSurvey(ProjectParser.SurveyContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProjectParser#welcome}.
	 * @param ctx the parse tree
	 */
	void enterWelcome(ProjectParser.WelcomeContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProjectParser#welcome}.
	 * @param ctx the parse tree
	 */
	void exitWelcome(ProjectParser.WelcomeContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProjectParser#listOfSections}.
	 * @param ctx the parse tree
	 */
	void enterListOfSections(ProjectParser.ListOfSectionsContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProjectParser#listOfSections}.
	 * @param ctx the parse tree
	 */
	void exitListOfSections(ProjectParser.ListOfSectionsContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProjectParser#sections}.
	 * @param ctx the parse tree
	 */
	void enterSections(ProjectParser.SectionsContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProjectParser#sections}.
	 * @param ctx the parse tree
	 */
	void exitSections(ProjectParser.SectionsContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProjectParser#questions}.
	 * @param ctx the parse tree
	 */
	void enterQuestions(ProjectParser.QuestionsContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProjectParser#questions}.
	 * @param ctx the parse tree
	 */
	void exitQuestions(ProjectParser.QuestionsContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProjectParser#question}.
	 * @param ctx the parse tree
	 */
	void enterQuestion(ProjectParser.QuestionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProjectParser#question}.
	 * @param ctx the parse tree
	 */
	void exitQuestion(ProjectParser.QuestionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProjectParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterInstruction(ProjectParser.InstructionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProjectParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitInstruction(ProjectParser.InstructionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProjectParser#answers}.
	 * @param ctx the parse tree
	 */
	void enterAnswers(ProjectParser.AnswersContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProjectParser#answers}.
	 * @param ctx the parse tree
	 */
	void exitAnswers(ProjectParser.AnswersContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProjectParser#questionType}.
	 * @param ctx the parse tree
	 */
	void enterQuestionType(ProjectParser.QuestionTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProjectParser#questionType}.
	 * @param ctx the parse tree
	 */
	void exitQuestionType(ProjectParser.QuestionTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProjectParser#gender}.
	 * @param ctx the parse tree
	 */
	void enterGender(ProjectParser.GenderContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProjectParser#gender}.
	 * @param ctx the parse tree
	 */
	void exitGender(ProjectParser.GenderContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProjectParser#condition}.
	 * @param ctx the parse tree
	 */
	void enterCondition(ProjectParser.ConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProjectParser#condition}.
	 * @param ctx the parse tree
	 */
	void exitCondition(ProjectParser.ConditionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProjectParser#end}.
	 * @param ctx the parse tree
	 */
	void enterEnd(ProjectParser.EndContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProjectParser#end}.
	 * @param ctx the parse tree
	 */
	void exitEnd(ProjectParser.EndContext ctx);
}