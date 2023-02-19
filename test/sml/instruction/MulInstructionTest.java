package sml.instruction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.Instruction;
import sml.Machine;
import sml.Registers;

import static sml.Registers.Register.EAX;
import static sml.Registers.Register.EBX;

class MulInstructionTest {
    private Machine machine;
    private Registers registers;

    @BeforeEach
    void setUp() {
        machine = new Machine(new Registers());
        registers = machine.getRegisters();
    }

    @AfterEach
    void tearDown() {
        machine = null;
        registers = null;
    }

    @Test
    void executeValid() {
        registers.set(EAX, 5);
        registers.set(EBX, 6);
        Instruction instruction = new MulInstructionTest(null, EAX, EBX);
        instruction.execute(machine);
        Assertions.assertEquals(30, machine.getRegisters().get(EAX));
    }

    @Test
    void executeValidTwo() {
        registers.set(EAX, 7);
        registers.set(EBX, -6);
        Instruction instruction = new MulInstructionTest(null, EAX, EBX);
        instruction.execute(machine);
        Assertions.assertEquals(-42, machine.getRegisters().get(EAX));
    }

    @Test
    void testToString() {
        Instruction instruction = new MulInstructionTest(null, EAX, EBX);
        Assertions.assertEquals("mul EAX EBX", instruction.toString());
    }

    @Test
    void testToStringWithLabel() {
        Instruction instruction = new MulInstructionTest("alessio", EAX, EBX);
        Assertions.assertEquals("alessio: mul EAX EBX", instruction.toString());
    }

    @Test
    void testEquals() {
        Instruction instruction = new MulInstructionTest(null, EAX, EBX);
        Instruction instructionCopy = new MulInstructionTest(null, EAX, EBX);
        Assertions.assertEquals(instruction, instructionCopy);
    }

    @Test
    void testEqualsSameLabels() {
        Instruction instruction = new MulInstructionTest("same", EAX, EBX);
        Instruction instructionCopy = new MulInstructionTest("same", EAX, EBX);
        Assertions.assertEquals(instruction, instructionCopy);
    }

    @Test
    void testEqualsDifferentLabels() {
        Instruction instruction = new MulInstructionTest(null, EAX, EBX);
        Instruction instructionCopy = new MulInstructionTest("same", EAX, EBX);
        Assertions.assertNotEquals(instruction, instructionCopy);
    }

    @Test
    void testHashCode() {
        Instruction instruction = new MulInstructionTest(null, EAX, EBX);
        Instruction instructionCopy = new MulInstructionTest("label", EAX, EBX);
        Assertions.assertEquals(instruction.hashCode(), instruction.hashCode());
        Assertions.assertNotEquals(instructionCopy.hashCode(), instruction.hashCode());
    }
}