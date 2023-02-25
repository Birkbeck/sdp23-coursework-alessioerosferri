package sml.instruction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.Instruction;
import sml.Machine;
import sml.Registers;

import static sml.Registers.Register.EAX;

class MovInstructionTest {
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
        registers.set(EAX, 12);
        Assertions.assertEquals(12, machine.getRegisters().get(EAX));
        Instruction instruction = new MovInstruction(null, EAX, 1);
        instruction.execute(machine);
        Assertions.assertEquals(1, machine.getRegisters().get(EAX));
    }

    @Test
    void executeValidTwo() {
        registers.set(EAX, -13);
        Assertions.assertEquals(-13, machine.getRegisters().get(EAX));
        Instruction instruction = new MovInstruction(null, EAX, -1);
        instruction.execute(machine);
        Assertions.assertEquals(-1, machine.getRegisters().get(EAX));
    }

    @Test
    void testToString() {
        Instruction instruction = new MovInstruction(null, EAX, 1);
        Assertions.assertEquals("mov EAX 1", instruction.toString());
    }

    @Test
    void testToStringWithLabel() {
        Instruction instruction = new MovInstruction("alessio", EAX, 2);
        Assertions.assertEquals("alessio: mov EAX 2", instruction.toString());
    }

    @Test
    void testEquals() {
        Instruction instruction = new MovInstruction(null, EAX, 1);
        Instruction instructionCopy = new MovInstruction(null, EAX, 1);
        Assertions.assertEquals(instruction, instructionCopy);
    }

    @Test
    void testEqualsSameLabels() {
        Instruction instruction = new MovInstruction("same", EAX, 2);
        Instruction instructionCopy = new MovInstruction("same", EAX, 2);
        Assertions.assertEquals(instruction, instructionCopy);
    }

    @Test
    void testEqualsDifferentLabels() {
        Instruction instruction = new MovInstruction(null, EAX, 3);
        Instruction instructionCopy = new MovInstruction("same", EAX, 3);
        Assertions.assertNotEquals(instruction, instructionCopy);
    }

    @Test
    void testHashCode() {
        Instruction instruction = new MovInstruction(null, EAX, 10);
        Instruction instructionCopy = new MovInstruction("label", EAX, 11);
        Assertions.assertEquals(instruction.hashCode(), instruction.hashCode());
        Assertions.assertNotEquals(instructionCopy.hashCode(), instruction.hashCode());
    }
}