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

class DivInstructionTest {
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
        registers.set(EBX, 6);
        Instruction instruction = new DivInstruction(null, EAX, EBX);
        instruction.execute(machine);
        Assertions.assertEquals(2, machine.getRegisters().get(EAX));
    }

    @Test
    void executeValidTwo() {
        registers.set(EAX, 7);
        registers.set(EBX, 6);
        Instruction instruction = new DivInstruction(null, EAX, EBX);
        instruction.execute(machine);
        Assertions.assertEquals(1, machine.getRegisters().get(EAX));
    }

    @Test
    void executeValidThird() {
        registers.set(EAX, 3);
        registers.set(EBX, 4);
        Instruction instruction = new DivInstruction(null, EAX, EBX);
        instruction.execute(machine);
        Assertions.assertEquals(0, machine.getRegisters().get(EAX));
    }

    @Test
    void executeValidFourth() {
        registers.set(EAX, 6);
        registers.set(EBX, -2);
        Instruction instruction = new DivInstruction(null, EAX, EBX);
        instruction.execute(machine);
        Assertions.assertEquals(-3, machine.getRegisters().get(EAX));
    }

    @Test
    void testToString() {
        Instruction instruction = new DivInstruction(null, EAX, EBX);
        Assertions.assertEquals("div EAX EBX", instruction.toString());
    }

    @Test
    void testToStringWithLabel() {
        Instruction instruction = new DivInstruction("alessio", EAX, EBX);
        Assertions.assertEquals("alessio: div EAX EBX", instruction.toString());
    }

    @Test
    void testEquals() {
        Instruction instruction = new DivInstruction(null, EAX, EBX);
        Instruction instructionCopy = new DivInstruction(null, EAX, EBX);
        Assertions.assertEquals(instruction, instructionCopy);
    }

    @Test
    void testEqualsSameLabels() {
        Instruction instruction = new DivInstruction("same", EAX, EBX);
        Instruction instructionCopy = new DivInstruction("same", EAX, EBX);
        Assertions.assertEquals(instruction, instructionCopy);
    }

    @Test
    void testEqualsDifferentLabels() {
        Instruction instruction = new DivInstruction(null, EAX, EBX);
        Instruction instructionCopy = new DivInstruction("same", EAX, EBX);
        Assertions.assertNotEquals(instruction, instructionCopy);
    }

    @Test
    void testHashCode() {
        Instruction instruction = new DivInstruction(null, EAX, EBX);
        Instruction instructionCopy = new DivInstruction("label", EAX, EBX);
        Assertions.assertEquals(instruction.hashCode(), instruction.hashCode());
        Assertions.assertNotEquals(instructionCopy.hashCode(), instruction.hashCode());
    }
}