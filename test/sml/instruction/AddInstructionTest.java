package sml.instruction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sml.Instruction;
import sml.Machine;
import sml.Registers;

import static sml.Registers.Register.*;

class AddInstructionTest {
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
        Instruction instruction = new AddInstruction(null, EAX, EBX);
        instruction.execute(machine);
        Assertions.assertEquals(11, machine.getRegisters().get(EAX));
    }

    @Test
    void executeValidTwo() {
        registers.set(EAX, -5);
        registers.set(EBX, 6);
        Instruction instruction = new AddInstruction(null, EAX, EBX);
        instruction.execute(machine);
        Assertions.assertEquals(1, machine.getRegisters().get(EAX));
    }

    @Test
    void testToString() {
        Instruction instruction = new AddInstruction(null, EAX, EBX);
        Assertions.assertEquals("add EAX EBX", instruction.toString());
    }

    @Test
    void testToStringWithLabel() {
        Instruction instruction = new AddInstruction("alessio", EAX, EBX);
        Assertions.assertEquals("alessio: add EAX EBX", instruction.toString());
    }

    @Test
    void testEquals() {
        Instruction instruction = new AddInstruction(null, EAX, EBX);
        Instruction instructionCopy = new AddInstruction(null, EAX, EBX);
        Assertions.assertEquals(instruction, instructionCopy);
    }

    @Test
    void testEqualsSameLabels() {
        Instruction instruction = new AddInstruction("same", EAX, EBX);
        Instruction instructionCopy = new AddInstruction("same", EAX, EBX);
        Assertions.assertEquals(instruction, instructionCopy);
    }

    @Test
    void testEqualsDifferentLabels() {
        Instruction instruction = new AddInstruction(null, EAX, EBX);
        Instruction instructionCopy = new AddInstruction("same", EAX, EBX);
        Assertions.assertNotEquals(instruction, instructionCopy);
    }

    @Test
    void testHashCode() {
        Instruction instruction = new AddInstruction(null, EAX, EBX);
        Instruction instructionCopy = new AddInstruction("label", EAX, EBX);
        Assertions.assertEquals(instruction.hashCode(), instruction.hashCode());
        Assertions.assertNotEquals(instructionCopy.hashCode(), instruction.hashCode());
    }
}