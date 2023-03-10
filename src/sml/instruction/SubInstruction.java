package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

import java.util.Objects;

/**
 * This class represents a sub instruction that subtracts two values from registers and stores the result in a register.
 * The instruction has an opcode of "sub".
 *
 * @author alessioerosferri
 * @see Instruction
 */
public class SubInstruction extends Instruction {
    private final RegisterName result;
    private final RegisterName source;

    public static final String OP_CODE = "sub";


    /**
     * Constructor: creates a new SubInstruction with a label, result register and source register.
     *
     * @param label  the label of the instruction (can be null)
     * @param result the register where the result will be stored
     * @param source the register containing the second value to be subtracted
     */
    public SubInstruction(String label, RegisterName result, RegisterName source) {
        super(label, OP_CODE);
        this.result = result;
        this.source = source;
    }

    /**
     * Executes the sub instruction on the given machine by subtracting two values from registers and storing the result
     * in the result register. Returns program counter value which indicates to execute the next instruction
     *
     * @param m the machine on which the instruction will be executed
     * @return the NORMAL_PROGRAM_COUNTER_UPDATE to indicate that the next instruction should be executed
     */
    @Override
    public int execute(Machine m) {
        int value1 = m.getRegisters().get(result);
        int value2 = m.getRegisters().get(source);
        m.getRegisters().set(result, value1 - value2);
        return NORMAL_PROGRAM_COUNTER_UPDATE;
    }

    /**
     * @return a string representation of the sub instruction
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
        if (!(o instanceof SubInstruction that)) return false;
        return result.equals(that.result)
                && source.equals(that.source)
                && Objects.equals(label, that.getLabel());
    }

    /**
     * @return the hash code for this instruction
     */
    @Override
    public int hashCode() {
        return Objects.hash(result, source, getLabel());
    }
}