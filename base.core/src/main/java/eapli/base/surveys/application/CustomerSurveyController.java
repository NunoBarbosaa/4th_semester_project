package eapli.base.surveys.application;

import eapli.base.Application;
import eapli.base.infrastructure.spomsp.CommProtocol;
import eapli.base.surveys.domain.Question;
import eapli.base.surveys.domain.Section;
import eapli.base.surveys.domain.Survey;
import eapli.base.surveys.domain.SurveyAnswer;
import eapli.base.surveys.dto.QuestionDto;
import eapli.base.surveys.dto.SectionDto;
import eapli.base.surveys.dto.SurveyAnswerDto;
import eapli.base.surveys.dto.SurveyDto;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CustomerSurveyController {

    private final AuthorizationService authz =
            AuthzRegistry.authorizationService();

    BufferedOutputStream sOut;
    BufferedInputStream sIn;

    private SSLSocket orderServerSock;

    private static final String TRUSTED_STORE = "certs/client_customerapp.jks";
    private static final String KEYSTORE_PASS = "12345678";

    public int numberOfSurveysToAnswer() throws IOException {
        String username = authz.session().get().authenticatedUser().username().toString();
        byte[] reply = testComms();
        if (reply[1] == CommProtocol.ACK_CODE) {
            sOut.write(new byte[]{CommProtocol.PROTOCOL_V1, CommProtocol.NR_SURVEYS_REQUEST});
            int[] dataSize = CommProtocol.dataSizeCalculator(username);
            sOut.write(new byte[]{(byte) dataSize[0], (byte) dataSize[1]});
            sOut.write(username.getBytes());
            sOut.flush();

            reply = sIn.readNBytes(4);

            if (reply[1] == CommProtocol.NR_SURVEYS_REPLY) {
                reply = sIn.readNBytes((reply[2] & 0xff) + 256 * (reply[3] & 0xff));
                if (reply.length == 1)
                    return (int) reply[0] & 0xff;
                else return -1;
            } else return -1;
        } else return -1;
    }

    private byte[] testComms() throws IOException {
        // Trust these certificates provided by servers
        System.setProperty("javax.net.ssl.trustStore", TRUSTED_STORE);
        System.setProperty("javax.net.ssl.trustStorePassword", KEYSTORE_PASS);

        // Use this certificate and private key for client certificate when requested by
        // the server
        System.setProperty("javax.net.ssl.keyStore", TRUSTED_STORE);
        System.setProperty("javax.net.ssl.keyStorePassword", KEYSTORE_PASS);

        SSLSocketFactory sf = (SSLSocketFactory) SSLSocketFactory.getDefault();

        orderServerSock = (SSLSocket) sf.createSocket(Application.settings().getProperty("ordersserver.address"),
                Integer.parseInt(Application.settings().getProperty("ordersserver.port")));
        orderServerSock.startHandshake();
        sOut = new BufferedOutputStream(orderServerSock.getOutputStream());
        sIn = new BufferedInputStream(orderServerSock.getInputStream());

        sOut.write(CommProtocol.COMM_TEST_V1);
        sOut.flush();

        return sIn.readNBytes(4);
    }

    public List<SurveyDto> getListOfUnansweredSurveys() throws IOException, ClassNotFoundException {
        String username = authz.session().get().authenticatedUser().username().toString();
        if(testComms()[1] == CommProtocol.ACK_CODE){
            sOut.write(new byte[]{CommProtocol.PROTOCOL_V1, CommProtocol.LIST_SURVEYS_REQUEST});
            int[] dataSize = CommProtocol.dataSizeCalculator(username);
            sOut.write(new byte[]{(byte) dataSize[0], (byte) dataSize[1]});
            sOut.write(username.getBytes());
            sOut.flush();

            ObjectInputStream oIn = new ObjectInputStream(orderServerSock.getInputStream());

            List<Survey> surveys = (List<Survey>) oIn.readObject();
            List<SurveyDto> surveyDtoList = new ArrayList<>();
            for(Survey survey : surveys){
                List<SectionDto> sections = new ArrayList<>();
                for(Section section: survey.sections()) {
                    List<QuestionDto> questions = new ArrayList<>();
                    for(Question question : section.questions())
                        questions.add(new QuestionDto(question.identity(), question.prompt(), question.instructions(),
                                question.obligatoriness(), question.type(), question.possibleAnswers()));
                    sections.add(new SectionDto(section.identity(), section.name(), section.description(), questions));
                }
                SurveyDto dto = new SurveyDto(survey.identity(), survey.name(), survey.welcomeMessage(),
                        survey.endMessage(), sections);
                surveyDtoList.add(dto);
            }
            return surveyDtoList;
        }else return Collections.emptyList();
    }

    public String getUserName(){
        return authz.session().get().authenticatedUser().username().toString();
    }

    public boolean saveAnswers(List<SurveyAnswerDto> answers) throws IOException {
        List<SurveyAnswer> finalAnswers = new ArrayList<>();
        for(SurveyAnswerDto answerDto : answers){
            finalAnswers.add(new SurveyAnswer(answerDto));
        }
        return saveAnswersToOrdersServer(finalAnswers);
    }

    private boolean saveAnswersToOrdersServer(List<SurveyAnswer> finalAnswers) throws IOException {


        sOut.write(new byte[]{1,CommProtocol.SAVE_ANSWERS,0,0});
        sOut.flush();

        ObjectOutputStream oOut = new ObjectOutputStream(orderServerSock.getOutputStream());

        oOut.writeObject(finalAnswers);
        oOut.flush();

        return Arrays.equals(sIn.readNBytes(4), CommProtocol.ACK_MESSAGE_V1);
    }
}
