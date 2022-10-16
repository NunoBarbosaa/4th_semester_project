package base.app.agvmanager.server.protocol;

import eapli.base.infrastructure.spomsp.commonrequest.ProtocolV1Request;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AgvManagerProtocolParserTest {

    @Test
    public void testCommTestIsParsedCorrectly(){
        ProtocolV1Request request = AgvManagerProtocolParser.parseRequest("1,0,0,0");

        assertEquals(request.execute(), "1,2,0,0");
    }

    @Test
    public void testDisconTestIsParsedCorrectly(){
        ProtocolV1Request request = AgvManagerProtocolParser.parseRequest("1,1,0,0");

        assertEquals(request.execute(), "1,2,0,0");
    }


    @Test
    public void testUnknownRequestIsParsedCorrectly(){
        ProtocolV1Request request = AgvManagerProtocolParser.parseRequest("1,20,0,0");

        assertEquals(request.execute(), "1,11,0,0");
    }

    @Test
    public void testErrorMessagesAreRepliedWhenNeeded1(){
        //Test when receiving commTest request
        ProtocolV1Request request = AgvManagerProtocolParser.parseRequest("1,0,0,0,2");

        assertEquals(request.execute(), "1,10,46,0,\"Number of parameters different than expected\"");

        ProtocolV1Request request1 = AgvManagerProtocolParser.parseRequest("1,0,1,0");

        assertEquals(request1.execute(), "1,10,33,0,\"Message should have data size 0\"");
    }

    @Test
    public void testErrorMessagesAreRepliedWhenNeeded2(){
        //Test when receiving disconnect request
        ProtocolV1Request request = AgvManagerProtocolParser.parseRequest("1,1,0,0,2");

        assertEquals(request.execute(), "1,10,46,0,\"Number of parameters different than expected\"");

        ProtocolV1Request request1 = AgvManagerProtocolParser.parseRequest("1,1,1,0");

        assertEquals(request1.execute(), "1,10,33,0,\"Message should have data size 0\"");
    }

}