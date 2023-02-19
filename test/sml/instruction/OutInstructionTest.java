package sml.instruction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.Instruction;
import sml.Machine;
import sml.Registers;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static sml.Registers.Register.EAX;
import static sml.Registers.Register.EBX;

class OutInstructionTest {
    private Machine machine;
    private Registers registers;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        machine = new Machine(new Registers());
        registers = machine.getRegisters();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        machine = null;
        registers = null;
        System.setOut(originalOut);
    }

    @Test
    void executeValid() {
        registers.set(EAX, 12);
        Instruction instruction = new OutInstruction(null, EAX);
        instruction.execute(machine);
        Assertions.assertEquals(outContent.toString(), "12");
    }

    @Test
    void executeValidTwo() {
        registers.set(EAX, 7);
        Instruction instruction = new OutInstruction(null, EAX);
        instruction.execute(machine);
        Assertions.assertEquals(outContent.toString(), "7");
    }

    @Test
    void testToString() {
        Instruction instruction = new OutInstruction(null, EAX);
        Assertions.assertEquals("out EAX", instruction.toString());
    }

    @Test
    void testToStringWithLabel() {
        Instruction instruction = new OutInstruction("alessio", EAX);
        Assertions.assertEquals("alessio: out EAX", instruction.toString());
    }

    @Test
    void testEquals() {
        Instruction instruction = new OutInstruction(null, EAX);
        Instruction instructionCopy = new OutInstruction(null, EAX);
        Assertions.assertEquals(instruction, instructionCopy);
    }

    @Test
    void testEqualsSameLabels() {
        Instruction instruction = new OutInstruction("same", EAX);
        Instruction instructionCopy = new OutInstruction("same", EAX);
        Assertions.assertEquals(instruction, instructionCopy);
    }

    @Test
    void testEqualsDifferentLabels() {
        Instruction instruction = new OutInstruction(null, EAX);
        Instruction instructionCopy = new OutInstruction("same", EAX);
        Assertions.assertNotEquals(instruction, instructionCopy);
    }

    @Test
    void testHashCode() {
        Instruction instruction = new OutInstruction(null, EAX);
        Instruction instructionCopy = new OutInstruction("label", EAX);
        Assertions.assertEquals(instruction.hashCode(), instruction.hashCode());
        Assertions.assertNotEquals(instructionCopy.hashCode(), instruction.hashCode());
    }
}