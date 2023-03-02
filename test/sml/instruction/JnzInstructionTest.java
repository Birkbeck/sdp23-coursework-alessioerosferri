package sml.instruction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.Instruction;
import sml.Labels;
import sml.Machine;
import sml.Registers;

import java.util.Arrays;
import java.util.List;

import static sml.Registers.Register.EAX;

class JnzInstructionTest {
    private Machine machine;
    private Registers registers;
    private List<Instruction> instructions;
    private Labels labels;

    @BeforeEach
    void setUp() {
        machine = new Machine(new Registers());
        labels = machine.getLabels();
        registers = machine.getRegisters();
        instructions = machine.getProgram();
        Instruction firstInstruction = new JnzInstruction("l", EAX, "l");
        Instruction secondInstruction = new JnzInstruction("k", EAX, "k");
        instructions.addAll(Arrays.asList(firstInstruction, secondInstruction));
        labels.addLabel("l", 0);
        labels.addLabel("k", 1);
    }

    @AfterEach
    void tearDown() {
        machine = null;
        registers = null;
        instructions = null;
    }

    @Test
    void executeValid() {
        registers.set(EAX, 1);
        Instruction instruction = new JnzInstruction(null, EAX, "l");
        int programCounter = instruction.execute(machine);
        Assertions.assertEquals(0, programCounter);
    }

    @Test
    void executeValidTwo() {
        registers.set(EAX, 0);
        Instruction instruction = new JnzInstruction(null, EAX, "l");
        int programCounter = instruction.execute(machine);
        Assertions.assertEquals(Instruction.NORMAL_PROGRAM_COUNTER_UPDATE, programCounter);
    }

    @Test
    void executeValidThird() {
        registers.set(EAX, -10);
        Instruction instruction = new JnzInstruction(null, EAX, "k");
        int programCounter = instruction.execute(machine);
        Assertions.assertEquals(1, programCounter);
    }

    @Test
    void executeValidFourth() {
        registers.set(EAX, -10);
        Instruction instruction = new JnzInstruction(null, EAX, "doesnotexist");
        Assertions.assertThrows(NullPointerException.class, () -> instruction.execute(machine));
    }

    @Test
    void testToString() {
        Instruction instruction = new JnzInstruction(null, EAX, "l");
        Assertions.assertEquals("jnz EAX l", instruction.toString());
    }

    @Test
    void testToStringWithLabel() {
        Instruction instruction = new JnzInstruction("alessio", EAX, "k");
        Assertions.assertEquals("alessio: jnz EAX k", instruction.toString());
    }

    @Test
    void testEquals() {
        Instruction instruction = new JnzInstruction(null, EAX, "l");
        Instruction instructionCopy = new JnzInstruction(null, EAX, "l");
        Assertions.assertEquals(instruction, instructionCopy);
    }

    @Test
    void testEqualsSameLabels() {
        Instruction instruction = new JnzInstruction("same", EAX, "l");
        Instruction instructionCopy = new JnzInstruction("same", EAX, "l");
        Assertions.assertEquals(instruction, instructionCopy);
    }

    @Test
    void testEqualsDifferentLabels() {
        Instruction instruction = new JnzInstruction("same", EAX, "l");
        Instruction instructionCopy = new JnzInstruction(null, EAX, "l");
        Assertions.assertNotEquals(instruction, instructionCopy);
    }

    @Test
    void testHashCode() {
        Instruction instruction = new JnzInstruction(null, EAX, "l");
        Instruction instructionCopy = new JnzInstruction("label", EAX, "l");
        Instruction instructionCopyThird = new JnzInstruction("label", EAX, "l");
        Assertions.assertEquals(instructionCopyThird.hashCode(), instructionCopy.hashCode());
        Assertions.assertNotEquals(instructionCopy.hashCode(), instruction.hashCode());
    }
}