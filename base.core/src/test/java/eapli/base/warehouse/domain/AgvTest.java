package eapli.base.warehouse.domain;

import eapli.base.warehouse.dto.AgvDto;
import org.junit.Test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AgvTest {

    @Test(expected = IllegalArgumentException.class)
    public void testIdCannotHaveMoreThan8chars() {
        AgvDto dto = new AgvDto("This is a long id", "A description", "agvModel", 10, 0.7, 2, AgvStatus.AVAILABLE);
        Square square = new Square(1,1);
        dto.setDock(new AgvDock("D1", square, square, square, Accessibility.W_MINUS));
        new Agv(dto);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAutonomyCannotBeLessOrEqualToZero() {
        AgvDto dto = new AgvDto("AGV1", "A description", "agvModel", 10, 0.7, -1, AgvStatus.AVAILABLE);
        Square square = new Square(1,1);
        dto.setDock(new AgvDock("D1", square, square, square, Accessibility.W_MINUS));
        new Agv(dto);
    }

    @Test
    public void testStatusChange() {
        AgvDto dto = new AgvDto("AGV1", "A description", "agvModel", 10, 0.7, 2, AgvStatus.AVAILABLE);
        Square square = new Square(1,1);
        dto.setDock(new AgvDock("D1", square, square, square, Accessibility.W_MINUS));
        Agv agv = new Agv(dto);
        assertEquals(AgvStatus.AVAILABLE, agv.currentStatus());
        agv.changeStatusTo(AgvStatus.IN_MAINTENANCE);
        assertEquals(AgvStatus.IN_MAINTENANCE, agv.currentStatus());
    }

    @Test
    public void testAGVPositionIsSameAsDock() {
        AgvDto dto = new AgvDto("AGV1", "A description", "agvModel", 10, 0.7, 2, AgvStatus.AVAILABLE);
        Square square = new Square(1,1);
        dto.setDock(new AgvDock("D1", square, square, square, Accessibility.W_MINUS));
        Agv agv = new Agv(dto);
        assertEquals(agv.currentPosition(), dto.getDock().startsFrom());
    }

    @Test
    public void testTwoAGVsAreEqual() {
        AgvDto dto = new AgvDto("AGV1", "A description", "agvModel", 10, 0.7, 2, AgvStatus.AVAILABLE);
        Square square = new Square(1,1);
        dto.setDock(new AgvDock("D1", square, square, square, Accessibility.W_MINUS));
        Agv agv = new Agv(dto);

        AgvDto dto2 = new AgvDto("AGV1", "Another description", "agvModel", 20, 0.6, 1, AgvStatus.AVAILABLE);
        dto2.setDock(new AgvDock("D2", square, square, square, Accessibility.W_MINUS));
        Agv agv2 = new Agv(dto2);

        assertEquals(agv, agv2);
    }

    @Test
    public void testTwoAGVsAreNotEqual() {
        AgvDto dto = new AgvDto("AGV1", "A description", "agvModel", 10, 0.7, 2, AgvStatus.AVAILABLE);
        Square square = new Square(1,1);
        dto.setDock(new AgvDock("D1", square, square, square, Accessibility.W_MINUS));
        Agv agv = new Agv(dto);

        AgvDto dto2 = new AgvDto("AGV2", "Another description", "agvModel", 20, 0.6, 1, AgvStatus.AVAILABLE);
        dto2.setDock(new AgvDock("D2", square, square, square, Accessibility.W_MINUS));
        Agv agv2 = new Agv(dto2);

        assertNotEquals(agv, agv2);
    }

    

}