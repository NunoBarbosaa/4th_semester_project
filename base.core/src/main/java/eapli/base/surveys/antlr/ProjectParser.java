package eapli.base.surveys.antlr;
// Generated from C:/Users/Andr�/Documents/Documents/ISEP/LAPR4/Docs/LPROG\Project.g4 by ANTLR 4.10.1
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ProjectParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.10.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, T__33=34, T__34=35, T__35=36, T__36=37, T__37=38, 
		T__38=39, INT=40, OBLIGATORINESS=41, DATE=42, NEWLINE=43, ID=44, FREE_TEXT=45;
	public static final int
		RULE_prog = 0, RULE_id = 1, RULE_date = 2, RULE_criteria = 3, RULE_criterias = 4, 
		RULE_criteriaType = 5, RULE_combinationType = 6, RULE_survey = 7, RULE_welcome = 8, 
		RULE_listOfSections = 9, RULE_sections = 10, RULE_questions = 11, RULE_question = 12, 
		RULE_instruction = 13, RULE_answers = 14, RULE_questionType = 15, RULE_gender = 16, 
		RULE_condition = 17, RULE_end = 18;
	private static String[] makeRuleNames() {
		return new String[] {
			"prog", "id", "date", "criteria", "criterias", "criteriaType", "combinationType", 
			"survey", "welcome", "listOfSections", "sections", "questions", "question", 
			"instruction", "answers", "questionType", "gender", "condition", "end"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'ID:'", "'Date: '", "' - '", "'Criteria: '", "'Bought- '", "'Brand- '", 
			"'Age > '", "'Age < '", "'Age['", "'-'", "']'", "'Gender- '", "'[AND]'", 
			"'[OR]'", "'Survey Questionnaire -'", "'Welcome Message -'", "'List of sections:'", 
			"'[Sections]'", "'Section- '", "'Question-'", "'*'", "'Obligatoriness:'", 
			"'('", "')'", "'Answers:'", "'-Type= Free-text'", "'-Type= Single-choice'", 
			"'-Type= Multiple-choice'", "'-Type= Numeric'", "'-Type= Yes/No'", "'-Type= Scaling'", 
			"'-Type= Sorting'", "'Male'", "'Female'", "'Undefined'", "'(Section '", 
			"'|Question '", "'|Answer:'", "'End'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, "INT", "OBLIGATORINESS", "DATE", "NEWLINE", "ID", 
			"FREE_TEXT"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Project.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public ProjectParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class ProgContext extends ParserRuleContext {
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public ProgContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prog; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProjectListener ) ((ProjectListener)listener).enterProg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProjectListener ) ((ProjectListener)listener).exitProg(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProjectVisitor ) return ((ProjectVisitor<? extends T>)visitor).visitProg(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgContext prog() throws RecognitionException {
		ProgContext _localctx = new ProgContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_prog);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(38);
			id();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IdContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(ProjectParser.ID, 0); }
		public TerminalNode NEWLINE() { return getToken(ProjectParser.NEWLINE, 0); }
		public DateContext date() {
			return getRuleContext(DateContext.class,0);
		}
		public IdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_id; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProjectListener ) ((ProjectListener)listener).enterId(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProjectListener ) ((ProjectListener)listener).exitId(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProjectVisitor ) return ((ProjectVisitor<? extends T>)visitor).visitId(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IdContext id() throws RecognitionException {
		IdContext _localctx = new IdContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_id);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(40);
			match(T__0);
			setState(41);
			match(ID);
			setState(42);
			match(NEWLINE);
			setState(43);
			date();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DateContext extends ParserRuleContext {
		public List<TerminalNode> DATE() { return getTokens(ProjectParser.DATE); }
		public TerminalNode DATE(int i) {
			return getToken(ProjectParser.DATE, i);
		}
		public TerminalNode NEWLINE() { return getToken(ProjectParser.NEWLINE, 0); }
		public CriteriaContext criteria() {
			return getRuleContext(CriteriaContext.class,0);
		}
		public DateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_date; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProjectListener ) ((ProjectListener)listener).enterDate(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProjectListener ) ((ProjectListener)listener).exitDate(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProjectVisitor ) return ((ProjectVisitor<? extends T>)visitor).visitDate(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DateContext date() throws RecognitionException {
		DateContext _localctx = new DateContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_date);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(45);
			match(T__1);
			setState(46);
			match(DATE);
			setState(47);
			match(T__2);
			setState(48);
			match(DATE);
			setState(49);
			match(NEWLINE);
			setState(50);
			criteria();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CriteriaContext extends ParserRuleContext {
		public CriteriasContext criterias() {
			return getRuleContext(CriteriasContext.class,0);
		}
		public CriteriaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_criteria; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProjectListener ) ((ProjectListener)listener).enterCriteria(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProjectListener ) ((ProjectListener)listener).exitCriteria(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProjectVisitor ) return ((ProjectVisitor<? extends T>)visitor).visitCriteria(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CriteriaContext criteria() throws RecognitionException {
		CriteriaContext _localctx = new CriteriaContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_criteria);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(52);
			match(T__3);
			setState(53);
			criterias();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CriteriasContext extends ParserRuleContext {
		public CriteriasContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_criterias; }
	 
		public CriteriasContext() { }
		public void copyFrom(CriteriasContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class SimpleCriteriaContext extends CriteriasContext {
		public CriteriaTypeContext criteriaType() {
			return getRuleContext(CriteriaTypeContext.class,0);
		}
		public SurveyContext survey() {
			return getRuleContext(SurveyContext.class,0);
		}
		public SimpleCriteriaContext(CriteriasContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProjectListener ) ((ProjectListener)listener).enterSimpleCriteria(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProjectListener ) ((ProjectListener)listener).exitSimpleCriteria(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProjectVisitor ) return ((ProjectVisitor<? extends T>)visitor).visitSimpleCriteria(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CombinedCriteriaContext extends CriteriasContext {
		public List<CriteriaTypeContext> criteriaType() {
			return getRuleContexts(CriteriaTypeContext.class);
		}
		public CriteriaTypeContext criteriaType(int i) {
			return getRuleContext(CriteriaTypeContext.class,i);
		}
		public CombinationTypeContext combinationType() {
			return getRuleContext(CombinationTypeContext.class,0);
		}
		public TerminalNode NEWLINE() { return getToken(ProjectParser.NEWLINE, 0); }
		public SurveyContext survey() {
			return getRuleContext(SurveyContext.class,0);
		}
		public CombinedCriteriaContext(CriteriasContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProjectListener ) ((ProjectListener)listener).enterCombinedCriteria(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProjectListener ) ((ProjectListener)listener).exitCombinedCriteria(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProjectVisitor ) return ((ProjectVisitor<? extends T>)visitor).visitCombinedCriteria(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CriteriasContext criterias() throws RecognitionException {
		CriteriasContext _localctx = new CriteriasContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_criterias);
		try {
			setState(64);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				_localctx = new SimpleCriteriaContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(55);
				criteriaType();
				setState(56);
				survey();
				}
				break;
			case 2:
				_localctx = new CombinedCriteriaContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(58);
				criteriaType();
				setState(59);
				combinationType();
				setState(60);
				match(NEWLINE);
				setState(61);
				criteriaType();
				setState(62);
				survey();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CriteriaTypeContext extends ParserRuleContext {
		public CriteriaTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_criteriaType; }
	 
		public CriteriaTypeContext() { }
		public void copyFrom(CriteriaTypeContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class CriteriaGenderContext extends CriteriaTypeContext {
		public GenderContext gender() {
			return getRuleContext(GenderContext.class,0);
		}
		public TerminalNode NEWLINE() { return getToken(ProjectParser.NEWLINE, 0); }
		public CriteriaGenderContext(CriteriaTypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProjectListener ) ((ProjectListener)listener).enterCriteriaGender(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProjectListener ) ((ProjectListener)listener).exitCriteriaGender(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProjectVisitor ) return ((ProjectVisitor<? extends T>)visitor).visitCriteriaGender(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CriteriaProductBoughtContext extends CriteriaTypeContext {
		public List<TerminalNode> DATE() { return getTokens(ProjectParser.DATE); }
		public TerminalNode DATE(int i) {
			return getToken(ProjectParser.DATE, i);
		}
		public TerminalNode NEWLINE() { return getToken(ProjectParser.NEWLINE, 0); }
		public List<TerminalNode> ID() { return getTokens(ProjectParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(ProjectParser.ID, i);
		}
		public CriteriaProductBoughtContext(CriteriaTypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProjectListener ) ((ProjectListener)listener).enterCriteriaProductBought(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProjectListener ) ((ProjectListener)listener).exitCriteriaProductBought(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProjectVisitor ) return ((ProjectVisitor<? extends T>)visitor).visitCriteriaProductBought(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CriteriaAgeMoreThanContext extends CriteriaTypeContext {
		public TerminalNode INT() { return getToken(ProjectParser.INT, 0); }
		public TerminalNode NEWLINE() { return getToken(ProjectParser.NEWLINE, 0); }
		public CriteriaAgeMoreThanContext(CriteriaTypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProjectListener ) ((ProjectListener)listener).enterCriteriaAgeMoreThan(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProjectListener ) ((ProjectListener)listener).exitCriteriaAgeMoreThan(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProjectVisitor ) return ((ProjectVisitor<? extends T>)visitor).visitCriteriaAgeMoreThan(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CriteriaAgeIntervalContext extends CriteriaTypeContext {
		public List<TerminalNode> INT() { return getTokens(ProjectParser.INT); }
		public TerminalNode INT(int i) {
			return getToken(ProjectParser.INT, i);
		}
		public TerminalNode NEWLINE() { return getToken(ProjectParser.NEWLINE, 0); }
		public CriteriaAgeIntervalContext(CriteriaTypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProjectListener ) ((ProjectListener)listener).enterCriteriaAgeInterval(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProjectListener ) ((ProjectListener)listener).exitCriteriaAgeInterval(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProjectVisitor ) return ((ProjectVisitor<? extends T>)visitor).visitCriteriaAgeInterval(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CriteriaAgeLessThanContext extends CriteriaTypeContext {
		public TerminalNode INT() { return getToken(ProjectParser.INT, 0); }
		public TerminalNode NEWLINE() { return getToken(ProjectParser.NEWLINE, 0); }
		public CriteriaAgeLessThanContext(CriteriaTypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProjectListener ) ((ProjectListener)listener).enterCriteriaAgeLessThan(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProjectListener ) ((ProjectListener)listener).exitCriteriaAgeLessThan(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProjectVisitor ) return ((ProjectVisitor<? extends T>)visitor).visitCriteriaAgeLessThan(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CriteriaBrandBoughtContext extends CriteriaTypeContext {
		public TerminalNode FREE_TEXT() { return getToken(ProjectParser.FREE_TEXT, 0); }
		public List<TerminalNode> NEWLINE() { return getTokens(ProjectParser.NEWLINE); }
		public TerminalNode NEWLINE(int i) {
			return getToken(ProjectParser.NEWLINE, i);
		}
		public List<TerminalNode> DATE() { return getTokens(ProjectParser.DATE); }
		public TerminalNode DATE(int i) {
			return getToken(ProjectParser.DATE, i);
		}
		public CriteriaBrandBoughtContext(CriteriaTypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProjectListener ) ((ProjectListener)listener).enterCriteriaBrandBought(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProjectListener ) ((ProjectListener)listener).exitCriteriaBrandBought(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProjectVisitor ) return ((ProjectVisitor<? extends T>)visitor).visitCriteriaBrandBought(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CriteriaTypeContext criteriaType() throws RecognitionException {
		CriteriaTypeContext _localctx = new CriteriaTypeContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_criteriaType);
		int _la;
		try {
			setState(99);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__4:
				_localctx = new CriteriaProductBoughtContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(66);
				match(T__4);
				setState(68); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(67);
					match(ID);
					}
					}
					setState(70); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==ID );
				setState(72);
				match(DATE);
				setState(73);
				match(T__2);
				setState(74);
				match(DATE);
				setState(75);
				match(NEWLINE);
				}
				break;
			case T__5:
				_localctx = new CriteriaBrandBoughtContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(76);
				match(T__5);
				setState(77);
				match(FREE_TEXT);
				setState(78);
				match(NEWLINE);
				setState(79);
				match(DATE);
				setState(80);
				match(T__2);
				setState(81);
				match(DATE);
				setState(82);
				match(NEWLINE);
				}
				break;
			case T__6:
				_localctx = new CriteriaAgeMoreThanContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(83);
				match(T__6);
				setState(84);
				match(INT);
				setState(85);
				match(NEWLINE);
				}
				break;
			case T__7:
				_localctx = new CriteriaAgeLessThanContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(86);
				match(T__7);
				setState(87);
				match(INT);
				setState(88);
				match(NEWLINE);
				}
				break;
			case T__8:
				_localctx = new CriteriaAgeIntervalContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(89);
				match(T__8);
				setState(90);
				match(INT);
				setState(91);
				match(T__9);
				setState(92);
				match(INT);
				setState(93);
				match(T__10);
				setState(94);
				match(NEWLINE);
				}
				break;
			case T__11:
				_localctx = new CriteriaGenderContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(95);
				match(T__11);
				setState(96);
				gender();
				setState(97);
				match(NEWLINE);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CombinationTypeContext extends ParserRuleContext {
		public CombinationTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_combinationType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProjectListener ) ((ProjectListener)listener).enterCombinationType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProjectListener ) ((ProjectListener)listener).exitCombinationType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProjectVisitor ) return ((ProjectVisitor<? extends T>)visitor).visitCombinationType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CombinationTypeContext combinationType() throws RecognitionException {
		CombinationTypeContext _localctx = new CombinationTypeContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_combinationType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(101);
			_la = _input.LA(1);
			if ( !(_la==T__12 || _la==T__13) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SurveyContext extends ParserRuleContext {
		public TerminalNode FREE_TEXT() { return getToken(ProjectParser.FREE_TEXT, 0); }
		public TerminalNode NEWLINE() { return getToken(ProjectParser.NEWLINE, 0); }
		public WelcomeContext welcome() {
			return getRuleContext(WelcomeContext.class,0);
		}
		public SurveyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_survey; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProjectListener ) ((ProjectListener)listener).enterSurvey(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProjectListener ) ((ProjectListener)listener).exitSurvey(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProjectVisitor ) return ((ProjectVisitor<? extends T>)visitor).visitSurvey(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SurveyContext survey() throws RecognitionException {
		SurveyContext _localctx = new SurveyContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_survey);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(103);
			match(T__14);
			setState(104);
			match(FREE_TEXT);
			setState(105);
			match(NEWLINE);
			setState(106);
			welcome();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WelcomeContext extends ParserRuleContext {
		public TerminalNode FREE_TEXT() { return getToken(ProjectParser.FREE_TEXT, 0); }
		public TerminalNode NEWLINE() { return getToken(ProjectParser.NEWLINE, 0); }
		public ListOfSectionsContext listOfSections() {
			return getRuleContext(ListOfSectionsContext.class,0);
		}
		public WelcomeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_welcome; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProjectListener ) ((ProjectListener)listener).enterWelcome(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProjectListener ) ((ProjectListener)listener).exitWelcome(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProjectVisitor ) return ((ProjectVisitor<? extends T>)visitor).visitWelcome(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WelcomeContext welcome() throws RecognitionException {
		WelcomeContext _localctx = new WelcomeContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_welcome);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(108);
			match(T__15);
			setState(109);
			match(FREE_TEXT);
			setState(110);
			match(NEWLINE);
			setState(111);
			listOfSections();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ListOfSectionsContext extends ParserRuleContext {
		public TerminalNode NEWLINE() { return getToken(ProjectParser.NEWLINE, 0); }
		public SectionsContext sections() {
			return getRuleContext(SectionsContext.class,0);
		}
		public ListOfSectionsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_listOfSections; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProjectListener ) ((ProjectListener)listener).enterListOfSections(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProjectListener ) ((ProjectListener)listener).exitListOfSections(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProjectVisitor ) return ((ProjectVisitor<? extends T>)visitor).visitListOfSections(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ListOfSectionsContext listOfSections() throws RecognitionException {
		ListOfSectionsContext _localctx = new ListOfSectionsContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_listOfSections);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(113);
			match(T__16);
			setState(114);
			match(NEWLINE);
			setState(115);
			sections();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SectionsContext extends ParserRuleContext {
		public TerminalNode INT() { return getToken(ProjectParser.INT, 0); }
		public TerminalNode FREE_TEXT() { return getToken(ProjectParser.FREE_TEXT, 0); }
		public TerminalNode NEWLINE() { return getToken(ProjectParser.NEWLINE, 0); }
		public SectionsContext sections() {
			return getRuleContext(SectionsContext.class,0);
		}
		public QuestionsContext questions() {
			return getRuleContext(QuestionsContext.class,0);
		}
		public SectionsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sections; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProjectListener ) ((ProjectListener)listener).enterSections(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProjectListener ) ((ProjectListener)listener).exitSections(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProjectVisitor ) return ((ProjectVisitor<? extends T>)visitor).visitSections(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SectionsContext sections() throws RecognitionException {
		SectionsContext _localctx = new SectionsContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_sections);
		try {
			setState(125);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INT:
				enterOuterAlt(_localctx, 1);
				{
				setState(117);
				match(INT);
				setState(118);
				match(T__9);
				setState(119);
				match(FREE_TEXT);
				setState(120);
				match(NEWLINE);
				setState(121);
				sections();
				}
				break;
			case T__17:
				enterOuterAlt(_localctx, 2);
				{
				setState(122);
				match(T__17);
				setState(123);
				match(NEWLINE);
				setState(124);
				questions();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class QuestionsContext extends ParserRuleContext {
		public TerminalNode FREE_TEXT() { return getToken(ProjectParser.FREE_TEXT, 0); }
		public List<TerminalNode> NEWLINE() { return getTokens(ProjectParser.NEWLINE); }
		public TerminalNode NEWLINE(int i) {
			return getToken(ProjectParser.NEWLINE, i);
		}
		public QuestionContext question() {
			return getRuleContext(QuestionContext.class,0);
		}
		public InstructionContext instruction() {
			return getRuleContext(InstructionContext.class,0);
		}
		public QuestionsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_questions; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProjectListener ) ((ProjectListener)listener).enterQuestions(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProjectListener ) ((ProjectListener)listener).exitQuestions(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProjectVisitor ) return ((ProjectVisitor<? extends T>)visitor).visitQuestions(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QuestionsContext questions() throws RecognitionException {
		QuestionsContext _localctx = new QuestionsContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_questions);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(127);
			match(T__18);
			setState(128);
			match(FREE_TEXT);
			setState(131);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				{
				setState(129);
				match(NEWLINE);
				setState(130);
				instruction();
				}
				break;
			}
			setState(133);
			match(NEWLINE);
			setState(134);
			question();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class QuestionContext extends ParserRuleContext {
		public TerminalNode INT() { return getToken(ProjectParser.INT, 0); }
		public List<TerminalNode> NEWLINE() { return getTokens(ProjectParser.NEWLINE); }
		public TerminalNode NEWLINE(int i) {
			return getToken(ProjectParser.NEWLINE, i);
		}
		public TerminalNode FREE_TEXT() { return getToken(ProjectParser.FREE_TEXT, 0); }
		public QuestionTypeContext questionType() {
			return getRuleContext(QuestionTypeContext.class,0);
		}
		public TerminalNode OBLIGATORINESS() { return getToken(ProjectParser.OBLIGATORINESS, 0); }
		public QuestionsContext questions() {
			return getRuleContext(QuestionsContext.class,0);
		}
		public InstructionContext instruction() {
			return getRuleContext(InstructionContext.class,0);
		}
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public AnswersContext answers() {
			return getRuleContext(AnswersContext.class,0);
		}
		public QuestionContext question() {
			return getRuleContext(QuestionContext.class,0);
		}
		public EndContext end() {
			return getRuleContext(EndContext.class,0);
		}
		public QuestionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_question; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProjectListener ) ((ProjectListener)listener).enterQuestion(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProjectListener ) ((ProjectListener)listener).exitQuestion(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProjectVisitor ) return ((ProjectVisitor<? extends T>)visitor).visitQuestion(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QuestionContext question() throws RecognitionException {
		QuestionContext _localctx = new QuestionContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_question);
		int _la;
		try {
			setState(183);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(136);
				match(T__19);
				setState(137);
				match(INT);
				setState(138);
				match(NEWLINE);
				setState(142);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__22) {
					{
					setState(139);
					instruction();
					setState(140);
					match(NEWLINE);
					}
				}

				setState(144);
				match(T__20);
				setState(145);
				match(FREE_TEXT);
				setState(146);
				questionType();
				setState(147);
				match(NEWLINE);
				setState(148);
				match(T__21);
				setState(149);
				match(OBLIGATORINESS);
				setState(151);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__35) {
					{
					setState(150);
					condition();
					}
				}

				setState(153);
				match(NEWLINE);
				setState(155);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__9 || _la==T__24) {
					{
					setState(154);
					answers();
					}
				}

				setState(157);
				questions();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(159);
				match(T__19);
				setState(160);
				match(INT);
				setState(161);
				match(NEWLINE);
				setState(165);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__22) {
					{
					setState(162);
					instruction();
					setState(163);
					match(NEWLINE);
					}
				}

				setState(167);
				match(T__20);
				setState(168);
				match(FREE_TEXT);
				setState(169);
				questionType();
				setState(170);
				match(NEWLINE);
				setState(171);
				match(T__21);
				setState(172);
				match(OBLIGATORINESS);
				setState(174);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__35) {
					{
					setState(173);
					condition();
					}
				}

				setState(176);
				match(NEWLINE);
				setState(178);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__9 || _la==T__24) {
					{
					setState(177);
					answers();
					}
				}

				setState(180);
				question();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(182);
				end();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InstructionContext extends ParserRuleContext {
		public TerminalNode FREE_TEXT() { return getToken(ProjectParser.FREE_TEXT, 0); }
		public InstructionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_instruction; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProjectListener ) ((ProjectListener)listener).enterInstruction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProjectListener ) ((ProjectListener)listener).exitInstruction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProjectVisitor ) return ((ProjectVisitor<? extends T>)visitor).visitInstruction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InstructionContext instruction() throws RecognitionException {
		InstructionContext _localctx = new InstructionContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_instruction);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(185);
			match(T__22);
			setState(186);
			match(FREE_TEXT);
			setState(187);
			match(T__23);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AnswersContext extends ParserRuleContext {
		public TerminalNode NEWLINE() { return getToken(ProjectParser.NEWLINE, 0); }
		public AnswersContext answers() {
			return getRuleContext(AnswersContext.class,0);
		}
		public TerminalNode FREE_TEXT() { return getToken(ProjectParser.FREE_TEXT, 0); }
		public AnswersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_answers; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProjectListener ) ((ProjectListener)listener).enterAnswers(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProjectListener ) ((ProjectListener)listener).exitAnswers(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProjectVisitor ) return ((ProjectVisitor<? extends T>)visitor).visitAnswers(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AnswersContext answers() throws RecognitionException {
		AnswersContext _localctx = new AnswersContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_answers);
		try {
			setState(199);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(189);
				match(T__24);
				setState(190);
				match(NEWLINE);
				setState(191);
				answers();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(192);
				match(T__9);
				setState(193);
				match(FREE_TEXT);
				setState(194);
				match(NEWLINE);
				setState(195);
				answers();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(196);
				match(T__9);
				setState(197);
				match(FREE_TEXT);
				setState(198);
				match(NEWLINE);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class QuestionTypeContext extends ParserRuleContext {
		public QuestionTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_questionType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProjectListener ) ((ProjectListener)listener).enterQuestionType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProjectListener ) ((ProjectListener)listener).exitQuestionType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProjectVisitor ) return ((ProjectVisitor<? extends T>)visitor).visitQuestionType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QuestionTypeContext questionType() throws RecognitionException {
		QuestionTypeContext _localctx = new QuestionTypeContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_questionType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(201);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__25) | (1L << T__26) | (1L << T__27) | (1L << T__28) | (1L << T__29) | (1L << T__30) | (1L << T__31))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GenderContext extends ParserRuleContext {
		public GenderContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_gender; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProjectListener ) ((ProjectListener)listener).enterGender(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProjectListener ) ((ProjectListener)listener).exitGender(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProjectVisitor ) return ((ProjectVisitor<? extends T>)visitor).visitGender(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GenderContext gender() throws RecognitionException {
		GenderContext _localctx = new GenderContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_gender);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(203);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__32) | (1L << T__33) | (1L << T__34))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConditionContext extends ParserRuleContext {
		public List<TerminalNode> INT() { return getTokens(ProjectParser.INT); }
		public TerminalNode INT(int i) {
			return getToken(ProjectParser.INT, i);
		}
		public TerminalNode FREE_TEXT() { return getToken(ProjectParser.FREE_TEXT, 0); }
		public ConditionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_condition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProjectListener ) ((ProjectListener)listener).enterCondition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProjectListener ) ((ProjectListener)listener).exitCondition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProjectVisitor ) return ((ProjectVisitor<? extends T>)visitor).visitCondition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConditionContext condition() throws RecognitionException {
		ConditionContext _localctx = new ConditionContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_condition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(205);
			match(T__35);
			setState(206);
			match(INT);
			setState(207);
			match(T__36);
			setState(208);
			match(INT);
			setState(209);
			match(T__37);
			setState(210);
			match(FREE_TEXT);
			setState(211);
			match(T__23);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EndContext extends ParserRuleContext {
		public TerminalNode NEWLINE() { return getToken(ProjectParser.NEWLINE, 0); }
		public TerminalNode FREE_TEXT() { return getToken(ProjectParser.FREE_TEXT, 0); }
		public EndContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_end; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProjectListener ) ((ProjectListener)listener).enterEnd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProjectListener ) ((ProjectListener)listener).exitEnd(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProjectVisitor ) return ((ProjectVisitor<? extends T>)visitor).visitEnd(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EndContext end() throws RecognitionException {
		EndContext _localctx = new EndContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_end);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(213);
			match(T__38);
			setState(214);
			match(NEWLINE);
			setState(215);
			match(FREE_TEXT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\u0004\u0001-\u00da\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0003\u0004A\b\u0004\u0001\u0005\u0001\u0005"+
		"\u0004\u0005E\b\u0005\u000b\u0005\f\u0005F\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0003\u0005d\b\u0005\u0001\u0006\u0001\u0006\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0001\t\u0001\t\u0001\t\u0001\t\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0001\n\u0001\n\u0003\n~\b\n\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0003\u000b\u0084\b\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0003\f\u008f"+
		"\b\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0003\f\u0098"+
		"\b\f\u0001\f\u0001\f\u0003\f\u009c\b\f\u0001\f\u0001\f\u0001\f\u0001\f"+
		"\u0001\f\u0001\f\u0001\f\u0001\f\u0003\f\u00a6\b\f\u0001\f\u0001\f\u0001"+
		"\f\u0001\f\u0001\f\u0001\f\u0001\f\u0003\f\u00af\b\f\u0001\f\u0001\f\u0003"+
		"\f\u00b3\b\f\u0001\f\u0001\f\u0001\f\u0003\f\u00b8\b\f\u0001\r\u0001\r"+
		"\u0001\r\u0001\r\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001"+
		"\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0003"+
		"\u000e\u00c8\b\u000e\u0001\u000f\u0001\u000f\u0001\u0010\u0001\u0010\u0001"+
		"\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001"+
		"\u0011\u0001\u0011\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001"+
		"\u0012\u0000\u0000\u0013\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012"+
		"\u0014\u0016\u0018\u001a\u001c\u001e \"$\u0000\u0003\u0001\u0000\r\u000e"+
		"\u0001\u0000\u001a \u0001\u0000!#\u00d9\u0000&\u0001\u0000\u0000\u0000"+
		"\u0002(\u0001\u0000\u0000\u0000\u0004-\u0001\u0000\u0000\u0000\u00064"+
		"\u0001\u0000\u0000\u0000\b@\u0001\u0000\u0000\u0000\nc\u0001\u0000\u0000"+
		"\u0000\fe\u0001\u0000\u0000\u0000\u000eg\u0001\u0000\u0000\u0000\u0010"+
		"l\u0001\u0000\u0000\u0000\u0012q\u0001\u0000\u0000\u0000\u0014}\u0001"+
		"\u0000\u0000\u0000\u0016\u007f\u0001\u0000\u0000\u0000\u0018\u00b7\u0001"+
		"\u0000\u0000\u0000\u001a\u00b9\u0001\u0000\u0000\u0000\u001c\u00c7\u0001"+
		"\u0000\u0000\u0000\u001e\u00c9\u0001\u0000\u0000\u0000 \u00cb\u0001\u0000"+
		"\u0000\u0000\"\u00cd\u0001\u0000\u0000\u0000$\u00d5\u0001\u0000\u0000"+
		"\u0000&\'\u0003\u0002\u0001\u0000\'\u0001\u0001\u0000\u0000\u0000()\u0005"+
		"\u0001\u0000\u0000)*\u0005,\u0000\u0000*+\u0005+\u0000\u0000+,\u0003\u0004"+
		"\u0002\u0000,\u0003\u0001\u0000\u0000\u0000-.\u0005\u0002\u0000\u0000"+
		"./\u0005*\u0000\u0000/0\u0005\u0003\u0000\u000001\u0005*\u0000\u00001"+
		"2\u0005+\u0000\u000023\u0003\u0006\u0003\u00003\u0005\u0001\u0000\u0000"+
		"\u000045\u0005\u0004\u0000\u000056\u0003\b\u0004\u00006\u0007\u0001\u0000"+
		"\u0000\u000078\u0003\n\u0005\u000089\u0003\u000e\u0007\u00009A\u0001\u0000"+
		"\u0000\u0000:;\u0003\n\u0005\u0000;<\u0003\f\u0006\u0000<=\u0005+\u0000"+
		"\u0000=>\u0003\n\u0005\u0000>?\u0003\u000e\u0007\u0000?A\u0001\u0000\u0000"+
		"\u0000@7\u0001\u0000\u0000\u0000@:\u0001\u0000\u0000\u0000A\t\u0001\u0000"+
		"\u0000\u0000BD\u0005\u0005\u0000\u0000CE\u0005,\u0000\u0000DC\u0001\u0000"+
		"\u0000\u0000EF\u0001\u0000\u0000\u0000FD\u0001\u0000\u0000\u0000FG\u0001"+
		"\u0000\u0000\u0000GH\u0001\u0000\u0000\u0000HI\u0005*\u0000\u0000IJ\u0005"+
		"\u0003\u0000\u0000JK\u0005*\u0000\u0000Kd\u0005+\u0000\u0000LM\u0005\u0006"+
		"\u0000\u0000MN\u0005-\u0000\u0000NO\u0005+\u0000\u0000OP\u0005*\u0000"+
		"\u0000PQ\u0005\u0003\u0000\u0000QR\u0005*\u0000\u0000Rd\u0005+\u0000\u0000"+
		"ST\u0005\u0007\u0000\u0000TU\u0005(\u0000\u0000Ud\u0005+\u0000\u0000V"+
		"W\u0005\b\u0000\u0000WX\u0005(\u0000\u0000Xd\u0005+\u0000\u0000YZ\u0005"+
		"\t\u0000\u0000Z[\u0005(\u0000\u0000[\\\u0005\n\u0000\u0000\\]\u0005(\u0000"+
		"\u0000]^\u0005\u000b\u0000\u0000^d\u0005+\u0000\u0000_`\u0005\f\u0000"+
		"\u0000`a\u0003 \u0010\u0000ab\u0005+\u0000\u0000bd\u0001\u0000\u0000\u0000"+
		"cB\u0001\u0000\u0000\u0000cL\u0001\u0000\u0000\u0000cS\u0001\u0000\u0000"+
		"\u0000cV\u0001\u0000\u0000\u0000cY\u0001\u0000\u0000\u0000c_\u0001\u0000"+
		"\u0000\u0000d\u000b\u0001\u0000\u0000\u0000ef\u0007\u0000\u0000\u0000"+
		"f\r\u0001\u0000\u0000\u0000gh\u0005\u000f\u0000\u0000hi\u0005-\u0000\u0000"+
		"ij\u0005+\u0000\u0000jk\u0003\u0010\b\u0000k\u000f\u0001\u0000\u0000\u0000"+
		"lm\u0005\u0010\u0000\u0000mn\u0005-\u0000\u0000no\u0005+\u0000\u0000o"+
		"p\u0003\u0012\t\u0000p\u0011\u0001\u0000\u0000\u0000qr\u0005\u0011\u0000"+
		"\u0000rs\u0005+\u0000\u0000st\u0003\u0014\n\u0000t\u0013\u0001\u0000\u0000"+
		"\u0000uv\u0005(\u0000\u0000vw\u0005\n\u0000\u0000wx\u0005-\u0000\u0000"+
		"xy\u0005+\u0000\u0000y~\u0003\u0014\n\u0000z{\u0005\u0012\u0000\u0000"+
		"{|\u0005+\u0000\u0000|~\u0003\u0016\u000b\u0000}u\u0001\u0000\u0000\u0000"+
		"}z\u0001\u0000\u0000\u0000~\u0015\u0001\u0000\u0000\u0000\u007f\u0080"+
		"\u0005\u0013\u0000\u0000\u0080\u0083\u0005-\u0000\u0000\u0081\u0082\u0005"+
		"+\u0000\u0000\u0082\u0084\u0003\u001a\r\u0000\u0083\u0081\u0001\u0000"+
		"\u0000\u0000\u0083\u0084\u0001\u0000\u0000\u0000\u0084\u0085\u0001\u0000"+
		"\u0000\u0000\u0085\u0086\u0005+\u0000\u0000\u0086\u0087\u0003\u0018\f"+
		"\u0000\u0087\u0017\u0001\u0000\u0000\u0000\u0088\u0089\u0005\u0014\u0000"+
		"\u0000\u0089\u008a\u0005(\u0000\u0000\u008a\u008e\u0005+\u0000\u0000\u008b"+
		"\u008c\u0003\u001a\r\u0000\u008c\u008d\u0005+\u0000\u0000\u008d\u008f"+
		"\u0001\u0000\u0000\u0000\u008e\u008b\u0001\u0000\u0000\u0000\u008e\u008f"+
		"\u0001\u0000\u0000\u0000\u008f\u0090\u0001\u0000\u0000\u0000\u0090\u0091"+
		"\u0005\u0015\u0000\u0000\u0091\u0092\u0005-\u0000\u0000\u0092\u0093\u0003"+
		"\u001e\u000f\u0000\u0093\u0094\u0005+\u0000\u0000\u0094\u0095\u0005\u0016"+
		"\u0000\u0000\u0095\u0097\u0005)\u0000\u0000\u0096\u0098\u0003\"\u0011"+
		"\u0000\u0097\u0096\u0001\u0000\u0000\u0000\u0097\u0098\u0001\u0000\u0000"+
		"\u0000\u0098\u0099\u0001\u0000\u0000\u0000\u0099\u009b\u0005+\u0000\u0000"+
		"\u009a\u009c\u0003\u001c\u000e\u0000\u009b\u009a\u0001\u0000\u0000\u0000"+
		"\u009b\u009c\u0001\u0000\u0000\u0000\u009c\u009d\u0001\u0000\u0000\u0000"+
		"\u009d\u009e\u0003\u0016\u000b\u0000\u009e\u00b8\u0001\u0000\u0000\u0000"+
		"\u009f\u00a0\u0005\u0014\u0000\u0000\u00a0\u00a1\u0005(\u0000\u0000\u00a1"+
		"\u00a5\u0005+\u0000\u0000\u00a2\u00a3\u0003\u001a\r\u0000\u00a3\u00a4"+
		"\u0005+\u0000\u0000\u00a4\u00a6\u0001\u0000\u0000\u0000\u00a5\u00a2\u0001"+
		"\u0000\u0000\u0000\u00a5\u00a6\u0001\u0000\u0000\u0000\u00a6\u00a7\u0001"+
		"\u0000\u0000\u0000\u00a7\u00a8\u0005\u0015\u0000\u0000\u00a8\u00a9\u0005"+
		"-\u0000\u0000\u00a9\u00aa\u0003\u001e\u000f\u0000\u00aa\u00ab\u0005+\u0000"+
		"\u0000\u00ab\u00ac\u0005\u0016\u0000\u0000\u00ac\u00ae\u0005)\u0000\u0000"+
		"\u00ad\u00af\u0003\"\u0011\u0000\u00ae\u00ad\u0001\u0000\u0000\u0000\u00ae"+
		"\u00af\u0001\u0000\u0000\u0000\u00af\u00b0\u0001\u0000\u0000\u0000\u00b0"+
		"\u00b2\u0005+\u0000\u0000\u00b1\u00b3\u0003\u001c\u000e\u0000\u00b2\u00b1"+
		"\u0001\u0000\u0000\u0000\u00b2\u00b3\u0001\u0000\u0000\u0000\u00b3\u00b4"+
		"\u0001\u0000\u0000\u0000\u00b4\u00b5\u0003\u0018\f\u0000\u00b5\u00b8\u0001"+
		"\u0000\u0000\u0000\u00b6\u00b8\u0003$\u0012\u0000\u00b7\u0088\u0001\u0000"+
		"\u0000\u0000\u00b7\u009f\u0001\u0000\u0000\u0000\u00b7\u00b6\u0001\u0000"+
		"\u0000\u0000\u00b8\u0019\u0001\u0000\u0000\u0000\u00b9\u00ba\u0005\u0017"+
		"\u0000\u0000\u00ba\u00bb\u0005-\u0000\u0000\u00bb\u00bc\u0005\u0018\u0000"+
		"\u0000\u00bc\u001b\u0001\u0000\u0000\u0000\u00bd\u00be\u0005\u0019\u0000"+
		"\u0000\u00be\u00bf\u0005+\u0000\u0000\u00bf\u00c8\u0003\u001c\u000e\u0000"+
		"\u00c0\u00c1\u0005\n\u0000\u0000\u00c1\u00c2\u0005-\u0000\u0000\u00c2"+
		"\u00c3\u0005+\u0000\u0000\u00c3\u00c8\u0003\u001c\u000e\u0000\u00c4\u00c5"+
		"\u0005\n\u0000\u0000\u00c5\u00c6\u0005-\u0000\u0000\u00c6\u00c8\u0005"+
		"+\u0000\u0000\u00c7\u00bd\u0001\u0000\u0000\u0000\u00c7\u00c0\u0001\u0000"+
		"\u0000\u0000\u00c7\u00c4\u0001\u0000\u0000\u0000\u00c8\u001d\u0001\u0000"+
		"\u0000\u0000\u00c9\u00ca\u0007\u0001\u0000\u0000\u00ca\u001f\u0001\u0000"+
		"\u0000\u0000\u00cb\u00cc\u0007\u0002\u0000\u0000\u00cc!\u0001\u0000\u0000"+
		"\u0000\u00cd\u00ce\u0005$\u0000\u0000\u00ce\u00cf\u0005(\u0000\u0000\u00cf"+
		"\u00d0\u0005%\u0000\u0000\u00d0\u00d1\u0005(\u0000\u0000\u00d1\u00d2\u0005"+
		"&\u0000\u0000\u00d2\u00d3\u0005-\u0000\u0000\u00d3\u00d4\u0005\u0018\u0000"+
		"\u0000\u00d4#\u0001\u0000\u0000\u0000\u00d5\u00d6\u0005\'\u0000\u0000"+
		"\u00d6\u00d7\u0005+\u0000\u0000\u00d7\u00d8\u0005-\u0000\u0000\u00d8%"+
		"\u0001\u0000\u0000\u0000\r@Fc}\u0083\u008e\u0097\u009b\u00a5\u00ae\u00b2"+
		"\u00b7\u00c7";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}