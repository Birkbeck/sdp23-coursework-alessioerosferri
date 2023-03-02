package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

import java.util.Objects;

/**
 * This class represents a mov instruction that stores a given integer to the register provided.
 * The instruction has an opcode of "mov".
 *
 * @author alessioerosferri
 * @see Instruction
 */
public class MovInstruction extends Instruction {
    private final RegisterName result;
    private final Integer source;

    public static final String OP_CODE = "mov";


    /**
     * Constructor: creates a new MovInstruction with a label, source, and result register.
     *
     * @param label  the label of the instruction (can be null)
     * @param result the register where the store operation will be executed
     * @param source the value to be stored
     */
    public MovInstruction(String label, RegisterName result, Integer source) {
        super(label, OP_CODE);
        this.result = result;
        this.source = source;
    }

    /**
     * Executes the mov instruction on the given machine by storing the given integer to the register.
     * Returns program counter value which indicates to execute the next instruction
     *
     * @param m the machine on which the instruction will be executed
     * @return the NORMAL_PROGRAM_COUNTER_UPDATE to indicate that the next instruction should be executed
     */
    @Override
    public int execute(Machine m) {
        m.getRegisters().set(result, source);
        return NORMAL_PROGRAM_COUNTER_UPDATE;
    }

    /**
     * @return a string representation of the mov instruction
     */
    @Override
    public String toString() {
        return getLabelString() + getOpcode() + " " + result + " " + source;
    }

    /**
     * @param o the object to compare to this instruction
     * @return true if the object is an instruction with the same label and opcode, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MovInstruction that)) return false;
        return source.equals(that.source)
                && Objects.equals(label, that.getLabel())
                && Objects.equals(result, that.result);
    }

    /**
     * @return the hash code for this instruction
     */
    @Override
    public int hashCode() {
        return Objects.hash(source, getLabel());
    }
}