package sml;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LabelsTest {
    private Labels labels;

    @BeforeEach
    void setUp() {
        labels = new Labels();
    }

    @AfterEach
    void tearDown() {
        labels = null;
    }

    @Test
    void addLabelSuccess() {
        labels.addLabel("L", 1);
        Assertions.assertEquals("[L -> 1]", labels.toString());
    }

    @Test
    void addLabelDuplicate() {
        labels.addLabel("L", 1);
        Assertions.assertThrows(IllegalArgumentException.class, () -> labels.addLabel("L", 2));
    }

    @Test
    void getAddressSuccess() {
        labels.addLabel("L", 1);
        Assertions.assertEquals(1, labels.getAddress("L"));
    }

    @Test
    void getAddressNonExistent() {
        Assertions.assertThrows(NullPointerException.class, () -> labels.getAddress("L"));
    }

    @Test
    void testToString() {
        labels.addLabel("L", 1);
        labels.addLabel("K", 1);
        Assertions.assertEquals("[K -> 1, L -> 1]", labels.toString());
    }

    @Test
    void testEquals() {
        Assertions.assertEquals(labels, new Labels());
        labels.addLabel("k", 1);
        Assertions.assertNotEquals(labels, new Labels());
    }

    @Test
    void testHashCode() {
        Assertions.assertEquals(labels.hashCode(), new Labels().hashCode());
        labels.addLabel("k", 1);
        Assertions.assertNotEquals(labels.hashCode(), new Labels().hashCode());
    }

    @Test
    void reset() {
        labels.addLabel("L", 1);
        labels.reset();
        Assertions.assertEquals("[]", labels.toString());
    }
}