package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

import java.util.Objects;

/**
 * This class represents an out instruction that writes to console the value from the source register.
 * The instruction has an opcode of "out".
 *
 * @author alessioerosferri
 * @see Instruction
 */
public class OutInstruction extends Instruction {
    private final RegisterName source;

    public static final String OP_CODE = "out";


    /**
     * Constructor: creates a new OutInstruction with a label and source register.
     *
     * @param label  the label of the instruction (can be null)
     * @param source the register containing the value to be written to console
     */
    public OutInstruction(String label, RegisterName source) {
        super(label, OP_CODE);
        this.source = source;
    }

    /**
     * Executes the out instruction on the given machine by writing to console the value from the source register.
     * Returns program counter value which indicates to execute the next instruction
     *
     * @param m the machine on which the instruction will be executed
     * @return the NORMAL_PROGRAM_COUNTER_UPDATE to indicate that the next instruction should be executed
     */
    @Override
    public int execute(Machine m) {
        int value = m.getRegisters().get(source);
        System.out.println(value);
        return NORMAL_PROGRAM_COUNTER_UPDATE;
    }

    /**
     * @return a string representation of the out instruction
     */
    @Override
    public String toString() {
        return getLabelString() + getOpcode() + " " + source;
    }

    /**
     * @param o the object to compare to this instruction
     * @return true if the object is an instruction with the same label and opcode, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OutInstruction that)) return false;
        return source.equals(that.source)
                && Objects.equals(label, that.getLabel());
    }

    /**
     * @return the hash code for this instruction
     */
    @Override
    public int hashCode() {
        return Objects.hash(source, getLabel());
    }
}