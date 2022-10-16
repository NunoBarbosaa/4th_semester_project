package base.app.agv.server.protocol;

import eapli.base.infrastructure.spomsp.commonrequest.ProtocolV1Request;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DigitalTwinProtocolParserTest {

    @Test
    public void testCommTestIsParsedCorrectly(){
        ProtocolV1Request request = DigitalTwinProtocolParser.parseRequest("1,0,0,0");

        assertEquals(request.execute(), "1,2,0,0");
    }

    @Test
    public void testDisconTestIsParsedCorrectly(){
        ProtocolV1Request request = DigitalTwinProtocolParser.parseRequest("1,1,0,0");

        assertEquals(request.execute(), "1,2,0,0");
    }


    @Test
    public void testUnknownRequestIsParsedCorrectly(){
        ProtocolV1Request request = DigitalTwinProtocolParser.parseRequest("1,20,0,0");

        assertEquals(request.execute(), "1,11,0,0");
    }

    @Test
    public void testErrorMessagesAreRepliedWhenNeeded1(){
        //Test when receiving commTest request
        ProtocolV1Request request = DigitalTwinProtocolParser.parseRequest("1,0,0,0,2");

        assertEquals(request.execute(), "1,10,46,0,\"Number of parameters different than expected\"");

        ProtocolV1Request request1 = DigitalTwinProtocolParser.parseRequest("1,0,1,0");

        assertEquals(request1.execute(), "1,10,33,0,\"Message should have data size 0\"");
    }

    @Test
    public void testErrorMessagesAreRepliedWhenNeeded2(){
        //Test when receiving disconnect request
        ProtocolV1Request request = DigitalTwinProtocolParser.parseRequest("1,1,0,0,2");

        assertEquals(request.execute(), "1,10,46,0,\"Number of parameters different than expected\"");

        ProtocolV1Request request1 = DigitalTwinProtocolParser.parseRequest("1,1,1,0");

        assertEquals(request1.execute(), "1,10,33,0,\"Message should have data size 0\"");
    }


    @Test
    public void testErrorInAssignAgvRequestIsParsedCorrectly(){
        ProtocolV1Request request = DigitalTwinProtocolParser.parseRequest("1,5,9,0,88,AGV1");

        assertEquals(request.execute(), "1,10,39,0,\"Agv id should be an alphanumeric code\"");

        ProtocolV1Request request1 = DigitalTwinProtocolParser.parseRequest("1,5,9,0,\"91\",AGV1");

        assertEquals(request1.execute(), "1,10,29,0,\"Order id should be a number\"");

        ProtocolV1Request request2 = DigitalTwinProtocolParser.parseRequest("1,5,9,0,\"91\",AGV1,99");

        assertEquals(request2.execute(), "1,10,46,0,\"Number of parameters different than expected\"");
    }
}