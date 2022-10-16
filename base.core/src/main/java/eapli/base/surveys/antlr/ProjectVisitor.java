package eapli.base.surveys.antlr;
// Generated from C:/Users/Andr�/Documents/Documents/ISEP/LAPR4/Docs/LPROG\Project.g4 by ANTLR 4.10.1
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ProjectParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ProjectVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ProjectParser#prog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProg(ProjectParser.ProgContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProjectParser#id}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitId(ProjectParser.IdContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProjectParser#date}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDate(ProjectParser.DateContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProjectParser#criteria}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCriteria(ProjectParser.CriteriaContext ctx);
	/**
	 * Visit a parse tree produced by the {@code simpleCriteria}
	 * labeled alternative in {@link ProjectParser#criterias}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleCriteria(ProjectParser.SimpleCriteriaContext ctx);
	/**
	 * Visit a parse tree produced by the {@code combinedCriteria}
	 * labeled alternative in {@link ProjectParser#criterias}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCombinedCriteria(ProjectParser.CombinedCriteriaContext ctx);
	/**
	 * Visit a parse tree produced by the {@code criteriaProductBought}
	 * labeled alternative in {@link ProjectParser#criteriaType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCriteriaProductBought(ProjectParser.CriteriaProductBoughtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code criteriaBrandBought}
	 * labeled alternative in {@link ProjectParser#criteriaType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCriteriaBrandBought(ProjectParser.CriteriaBrandBoughtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code criteriaAgeMoreThan}
	 * labeled alternative in {@link ProjectParser#criteriaType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCriteriaAgeMoreThan(ProjectParser.CriteriaAgeMoreThanContext ctx);
	/**
	 * Visit a parse tree produced by the {@code criteriaAgeLessThan}
	 * labeled alternative in {@link ProjectParser#criteriaType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCriteriaAgeLessThan(ProjectParser.CriteriaAgeLessThanContext ctx);
	/**
	 * Visit a parse tree produced by the {@code criteriaAgeInterval}
	 * labeled alternative in {@link ProjectParser#criteriaType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCriteriaAgeInterval(ProjectParser.CriteriaAgeIntervalContext ctx);
	/**
	 * Visit a parse tree produced by the {@code criteriaGender}
	 * labeled alternative in {@link ProjectParser#criteriaType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCriteriaGender(ProjectParser.CriteriaGenderContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProjectParser#combinationType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCombinationType(ProjectParser.CombinationTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProjectParser#survey}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSurvey(ProjectParser.SurveyContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProjectParser#welcome}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWelcome(ProjectParser.WelcomeContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProjectParser#listOfSections}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListOfSections(ProjectParser.ListOfSectionsContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProjectParser#sections}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSections(ProjectParser.SectionsContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProjectParser#questions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuestions(ProjectParser.QuestionsContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProjectParser#question}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuestion(ProjectParser.QuestionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProjectParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstruction(ProjectParser.InstructionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProjectParser#answers}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnswers(ProjectParser.AnswersContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProjectParser#questionType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuestionType(ProjectParser.QuestionTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProjectParser#gender}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGender(ProjectParser.GenderContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProjectParser#condition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondition(ProjectParser.ConditionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProjectParser#end}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnd(ProjectParser.EndContext ctx);
}