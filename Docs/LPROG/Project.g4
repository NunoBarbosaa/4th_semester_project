grammar Project;
prog: id;
//Survey ID
id: 'ID:' ID NEWLINE date;

date: 'Date: ' DATE ' - ' DATE NEWLINE criteria;

criteria: 'Criteria: ' criterias ;

criterias: criteriaType survey #simpleCriteria
| criteriaType combinationType NEWLINE criteriaType survey #combinedCriteria;

criteriaType: 'Bought- ' ID + DATE ' - ' DATE NEWLINE #criteriaProductBought
 | 'Brand- ' FREE_TEXT NEWLINE DATE ' - ' DATE NEWLINE #criteriaBrandBought
 | 'Age > ' INT NEWLINE #criteriaAgeMoreThan
 | 'Age < ' INT NEWLINE #criteriaAgeLessThan
 | 'Age[' INT '-' INT ']' NEWLINE #criteriaAgeInterval
 | 'Gender- ' gender NEWLINE #criteriaGender;

combinationType: '[AND]'
| '[OR]';
//Survey Name
survey: 'Survey Questionnaire -' FREE_TEXT NEWLINE welcome;

//Welcome message
welcome: 'Welcome Message -' FREE_TEXT NEWLINE listOfSections;

//List of sections name
listOfSections: 'List of sections:' NEWLINE sections ;

//Sections name
sections: INT '-' FREE_TEXT NEWLINE sections |  '[Sections]' NEWLINE questions
;
//Questions and sections
//Obligatoriness:
//"m"->The customer must answer
//"o"->optional can be answered by the customer or not
//Conditional:
//"c"->conditional will always have the section number and question number that it depends
//It can either validate a question of a section or a section
//The question might have an instruction or extra info to answer
questions: 'Section- ' FREE_TEXT  (NEWLINE instruction)? NEWLINE question
;

question:
 'Question-' INT NEWLINE (instruction NEWLINE )? '*' FREE_TEXT questionType NEWLINE 'Obligatoriness:' OBLIGATORINESS (condition)? NEWLINE (answers)? questions
|'Question-' INT NEWLINE (instruction NEWLINE )? '*' FREE_TEXT questionType NEWLINE 'Obligatoriness:' OBLIGATORINESS (condition)? NEWLINE (answers)? question
|end
;
//Extra information
instruction:'('FREE_TEXT')';

answers: 'Answers:' NEWLINE answers
| '-' FREE_TEXT NEWLINE answers
| '-' FREE_TEXT NEWLINE
;

questionType: '-Type= Free-text'
| '-Type= Single-choice'
| '-Type= Multiple-choice'
| '-Type= Numeric'
| '-Type= Yes/No'
| '-Type= Scaling'
| '-Type= Sorting'
;

gender: 'Male'
| 'Female'
| 'Undefined';

//Condition required to answer the question
condition:'(Section ' INT '|Question ' INT '|Answer:'FREE_TEXT ')';

//Final message
end: 'End' NEWLINE FREE_TEXT;

INT:[0-9]+;
OBLIGATORINESS:('m'|'o'|'c');
DATE:([0][1-9]|[1][0-9]|[2][0-9]|[3][0-1]) '-' ([0][1-9]|[1][0-2]) '-' INT;
NEWLINE : [\r\n]+ ;
ID:'('([0-9]|[a-z]|[A-Z])+')';

FREE_TEXT :  ('a'..'z' | ' ' | 'A'..'Z'| '.'| ','| [0-9]|'?'|'!'|'’'|'"')+;



//Seccoes de seguida corrigir
//Tipo de pergunta por texto

