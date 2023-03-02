package sml;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static sml.Instruction.NORMAL_PROGRAM_COUNTER_UPDATE;

/**
 * Represents the machine, the context in which programs run.
 * An instance contains 8 registers and methods to access and change them.
 * <p>
 * The machine executes a program represented by a list of instructions. The instructions are executed sequentially,
 * starting from the first instruction (index 0), until the program counter reaches the end of the list.
 * <p>
 * The machine has 8 registers, each of which can store an integer value.
 * The machine also has a program counter, which keeps track of the index of the next instruction to be
 * executed. The program counter is initially set to 0 when a new machine instance is created.
 * <p>
 * The machine also has a set of labels, represented by an instance of the {@link Labels} class. The labels can be used
 * to refer to instruction addresses symbolically.
 * <p>
 * The machine is designed to execute a program that is represented by a list of {@link Instruction} objects. Each
 * instruction contains an opcode, and operands (how many depend on the instruction used). Refer to README for the SML syntax.
 */
public final class Machine {

    private final Labels labels = new Labels();

    private final List<Instruction> program = new ArrayList<>();

    private final Registers registers;

    // The program counter; it contains the index (in program)
    // of the next instruction to be executed.
    private int programCounter = 0;

    public Machine(Registers registers) {
        this.registers = registers;
    }

    /**
     * Execute the program in program, beginning at instruction 0.
     * Precondition: the program and its labels have been stored properly.
     */
    public void execute() {
        programCounter = 0;
        registers.clear();
        while (programCounter < program.size()) {
            Instruction ins = program.get(programCounter);
            int programCounterUpdate = ins.execute(this);
            programCounter = (programCounterUpdate == NORMAL_PROGRAM_COUNTER_UPDATE)
                    ? programCounter + 1
                    : programCounterUpdate;
        }
    }

    /**
     * @return labels used by the program
     */
    public Labels getLabels() {
        return this.labels;
    }

    /**
     * @return list of instructions contained in the program
     */
    public List<Instruction> getProgram() {
        return this.program;
    }

    /**
     * @return registers available in the Machine
     */
    public Registers getRegisters() {
        return this.registers;
    }


    /**
     * String representation of the program under execution.
     *
     * @return pretty formatted version of the code.
     */
    @Override
    public String toString() {
        return program.stream()
                .map(Instruction::toString)
                .collect(Collectors.joining("\n"));
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Machine that) {
            return Objects.equals(this.labels, that.labels)
                    && Objects.equals(this.program, that.program)
                    && Objects.equals(this.registers, that.registers)
                    && this.programCounter == that.programCounter;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(labels, program, registers, programCounter);
    }
}
