package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Event class
 */
public class EventTest {
    private Event e;
    private Event e1;
    private Event e2;
    private Date d;

    //NOTE: these tests might fail if time at which line (2) below is executed
    //is different from time that line (1) is executed.  Lines (1) and (2) must
    //run in same millisecond for this test to make sense and pass.

    @BeforeEach
    public void runBefore() {
        e = new Event("Sensor open at door");   // (1)
        d = Calendar.getInstance().getTime();   // (2)
        e1 = new Event("Sensor open at door");   // (1)
        e2 = new Event("Sensor open r door");   // (1)
    }

    @Test
    public void testEvent() {
        assertEquals("Sensor open at door", e.getDescription());
        assertTrue(d.equals(e.getDate()));
    }

    @Test
    public void testHashCode() {
        assertTrue(e.hashCode() == e1.hashCode());
        assertFalse(e.hashCode() == e2.hashCode());
    }

    @Test
    public void testEqualsObject() {
        assertTrue(e.equals(e));
        assertTrue(e.equals(e1));
        assertFalse(e.equals(e2));
        assertFalse(e.equals(null));
        assertFalse(e.equals(new Projectile()));
    }


    @Test
    public void testToString() {
        assertEquals(d.toString() + "\n" + "Sensor open at door", e.toString());
    }
}
