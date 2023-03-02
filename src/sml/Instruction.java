package sml;

/**
 * Represents an abstract instruction in a Simple Machine Language (SML) program.
 *
 * @author alessioerosferri
 */
public abstract class Instruction {
    protected final String label;
    protected final String opcode;

    /**
     * Constructor: an instruction with a label and an opcode
     *
     * @param label  the label for the instruction (can be null)
     * @param opcode the opcode for the instruction (must be a valid operation of the language)
     */
    public Instruction(String label, String opcode) {
        this.label = label;
        this.opcode = opcode;
    }

    /**
     * @return the label for this instruction (can be null)
     */
    public String getLabel() {
        return label;
    }

    /**
     * @return the opcode for this instruction
     */
    public String getOpcode() {
        return opcode;
    }

    public static int NORMAL_PROGRAM_COUNTER_UPDATE = -1;

    /**
     * Executes the instruction on the given machine.
     *
     * @param machine the machine to execute the instruction on
     * @return the new program counter value (for jump instructions),
     * or NORMAL_PROGRAM_COUNTER_UPDATE to indicate that the next instruction should be executed
     */
    public abstract int execute(Machine machine);

    /**
     * @return the label string for this instruction (empty string if label is null)
     */
    protected String getLabelString() {
        return (getLabel() == null) ? "" : getLabel() + ": ";
    }

    // Abstract for the following methods means that they cannot be implemented at this level, but all concrete subclasses
    // must override these methods. The reason for this is that at this level of abstraction we need to leave to subclasses the
    // implementation, as they will know best how to implement each of these methods.

    /**
     * @return a string representation of this instruction (implementation left to concrete subclasses)
     */
    @Override
    public abstract String toString();

    /**
     * @param o the object to compare to this instruction
     * @return true if the object is an instruction with the same label and opcode, false otherwise
     */
    @Override
    public abstract boolean equals(Object o);

    /**
     * @return the hash code for this instruction (implementation left to concrete subclasses)
     */
    @Override
    public abstract int hashCode();
}
