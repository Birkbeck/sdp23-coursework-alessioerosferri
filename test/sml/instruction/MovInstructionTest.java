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
        Instruction instruction = new MovInstruction(null, 1, EAX);
        instruction.execute(machine);
        Assertions.assertEquals(1, machine.getRegisters().get(EAX));
    }

    @Test
    void executeValidTwo() {
        registers.set(EAX, -13);
        Assertions.assertEquals(-13, machine.getRegisters().get(EAX));
        Instruction instruction = new MovInstruction(null, -1, EAX);
        instruction.execute(machine);
        Assertions.assertEquals(-1, machine.getRegisters().get(EAX));
    }

    @Test
    void testToString() {
        Instruction instruction = new MovInstruction(null, 1 ,EAX);
        Assertions.assertEquals("mov 1 EAX", instruction.toString());
    }

    @Test
    void testToStringWithLabel() {
        Instruction instruction = new MovInstruction("alessio", 2, EAX);
        Assertions.assertEquals("alessio: mov 2 EAX", instruction.toString());
    }

    @Test
    void testEquals() {
        Instruction instruction = new MovInstruction(null, 1, EAX);
        Instruction instructionCopy = new MovInstruction(null, 1, EAX);
        Assertions.assertEquals(instruction, instructionCopy);
    }

    @Test
    void testEqualsSameLabels() {
        Instruction instruction = new MovInstruction("same", 2, EAX);
        Instruction instructionCopy = new MovInstruction("same", 2, EAX);
        Assertions.assertEquals(instruction, instructionCopy);
    }

    @Test
    void testEqualsDifferentLabels() {
        Instruction instruction = new MovInstruction(null, 3, EAX);
        Instruction instructionCopy = new MovInstruction("same", 3, EAX);
        Assertions.assertNotEquals(instruction, instructionCopy);
    }

    @Test
    void testHashCode() {
        Instruction instruction = new MovInstruction(null, 10, EAX);
        Instruction instructionCopy = new MovInstruction("label", 11, EAX);
        Assertions.assertEquals(instruction.hashCode(), instruction.hashCode());
        Assertions.assertNotEquals(instructionCopy.hashCode(), instruction.hashCode());
    }
}