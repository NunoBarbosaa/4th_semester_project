US3501 - Get the list of questionnaires that the system is asking me to answer and be able to answer any of those questionnaires
=======================================


# 1. Requirements

The development of this user story will be split in two parts. The first one being the parsing of the questionnaire using ANTLR and the 
grammar developed in the previous sprint. The second part will be about creating the dynamic interface to allow the user to answer to the 
surveys and storing the answers in the database.

The following requirements will be met:
* The questionnaire is created based on a text file that complies with the grammar defined previously
* The customer only answers surveys if he is part of the target audience
* The UI needs to be dynamically created taking in consideration the different types of questions
* Some questions are optional, others are only shown if a certain criteria is met
* The answers provided by the customer are stored in raw text in the database


# 2. Analysis

The following diagram is extracted from the Domain Model and is an overview of some business concepts that are going to be 
important in the development of the User Story. 

![SVG](US3501%20-%20DM.svg)

# 3. Design

As said in the beginning, the User Story will be split in two main functionalities, as such, following is a separate design for each one of the two, since they do not
have any point of crossover.

## 3.1. Sequence Diagram

### 3.1.1 Survey creation

This functionality will ask the user for a text file that has a questionnaire data in it. The questionnaire should always comply with the grammar defined in the previous
sprint, otherwise the survey will not be accepted and an error message will be thrown. If the file is compliant both grammarly and lexically, it will be imported to the system.
Then the user is asked if he wants to persist the survey and make it available for all users or not. In the first case, the system will then check if there is no other
questionnaire with the same ID as the new one, because, in case it happens, the system won't try to persist the new survey.
Below is the sequence diagram that represents the flow of the functionality.

![SVG](US3501%20-%20SD1.svg)

### 3.1.2 Survey Presentation to Customers

The second part of th User Story will be the presentation of the surveys to customers so that they can answer them. The customer app will communicate with the 
orders server and ask for the Surveys that the logged in customer can answer. The customer than can choose which one it will ask. Once the survey is chosen, the UI
will present the questions by order.
The following diagram illustrates the communications with the server and also the customer's interactions.

![SVG](US3501%20-%20SD2.svg)

The final part of the functionality is storing the answers so that later on, reports about surveys can be done. The process is represented in the sequence diagram below.

![SVG](US3501%20-%20SD3.svg)

## 3.2. Class Diagram

### 3.2.1 Survey creation

![SVG](US3501%20-%20CD1.svg)

## 3.3 Testing

### 3.3.1 Survey Creation

In order to assure that the created surveys comply with the structure specified by the team, some rules are imposed when creating those surveys, as
such, to guarantee that these rules are always applied and that there is no regression in the code, the following tests are going to be implemented.

#### Guaranteeing that all non-optional text in the survey is there and is not null/empty

        @Test(expected = IllegalArgumentException.class)
        public void testNullOrEmptyNameIsNotAccepted(){
            new Survey("34123", "22-08-2022", "30-09-2022", null, "Welcome", "End");
        }
* Similar tests will be performed to the ID, welcome and end messages and also date strings. 
* These tests will also be performed in the Section class and Question class when relevant.

#### Making sure that the endDate cannot be a date that happens before the beginDate
        
        @Test(expected = IllegalArgumentException.class)
        public void testDateIntervalIsValid(){
            new Survey("34123", "30-09-2022", "22-08-2022", "Name", "Welcome", "End");
        }

#### Test to ensure that invalid obligatoriness types are identified, even though the grammar should also prevent that invalid types are accepted

        @Test(expected = IllegalArgumentException.class)
        public void testInvalidObligatorinessType(){
            new Obligatoriness("unknown");
        }

#### Test to ensure that when the obligatoriness type is conditional, the conditions are provided

        @Test(expected = IllegalStateException.class)
        public void testInvalidObligatorinessType(){
            new Obligatoriness("c");
        }

#### Test to ensure that when a question has dependency, their dependent question id is valid or not 0

        @Test(expected = IllegalArgumentException.class)
        public void testInvalidObligatorinessCondition(){
            new Obligatoriness("c", 0, 2, "yes");
        }

#### Tests to ensure that when a question is of type 'Free Text', 'Numeric' or 'Yes/No' it cannot include possible answers

        @Test(expected = IllegalArgumentException.class)
        public void testSomeQuestionCannotAcceptDifferentPossibleAnswers(){
            new Question(1, "Question?", "No instructions", new Obligatoriness("m"),
                    "Free Text", possibleAnswers)
        }

#### Test to ensure that invalid question types are identified, even though the grammar should also prevent that invalid types are accepted

        @Test(expected = IllegalArgumentException.class)
        public void testInvalidQuestionTypeIsNotAccepted(){
            new Question(1, "Question?", "No instructions", new Obligatoriness("m"),
                    "Whatever", possibleAnswers)
        }






