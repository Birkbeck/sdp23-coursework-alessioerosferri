package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * This class represents a jnz instruction that reads the content of the register passed, if it is different from 0 it will then change
 * the program counter to execute the instruction with the label that has been provided
 * The instruction has an opcode of "jnz".
 *
 * @see Instruction
 * @author alessioerosferri
 */
public class JnzInstruction extends Instruction {
	private final RegisterName source;

	private final String nextInstructionLabel;

	public static final String OP_CODE = "jnz";


	/**
	 * Constructor: creates a new JnzInstruction with a label, register to be checked, and label of instruction to jump to.
	 *
	 * @param label the label of the instruction (can be null)
	 * @param source the register containing the second value to be subtracted
	 * @param nextInstructionLabel the label for the next instruction to execute if content of source is not 0
	 */
	public JnzInstruction(String label, RegisterName source, String nextInstructionLabel) {
		super(label, OP_CODE);
		this.nextInstructionLabel = nextInstructionLabel;
		this.source = source;
	}

	/**
	 * Executes the jnz instruction on the given machine by evaluating content of the source register and if different from 0 it will
	 * return the program counter for the instruction with the label passed as nextInstructionLabel, otherwise it will return normal program counter update.
	 * If the label does not exist, we assume that the next instruction will be executed. This scenario is not reachable as another class takes care of ensuring that labels defined actually exist.
	 *
	 * @param m the machine on which the instruction will be executed
	 * @return the new program counter or the normal program counter update if register is equal to 0
	 */
	@Override
	public int execute(Machine m) {
		int value = m.getRegisters().get(source);
		if (value != 0){
			int address = m.getLabels().getAddress(this.nextInstructionLabel);
			return address;
		}
		return NORMAL_PROGRAM_COUNTER_UPDATE;
	}

	/**
	 * @return a string representation of the jnz instruction
	 */
	@Override
	public String toString() {
		return getLabelString() + getOpcode() + " " + source + " " + nextInstructionLabel;
	}

	/**
	 * @param o the object to compare to this instruction
	 * @return true if the object is an instruction with the same label and source register and label instruction to jump to, otherwise false
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof JnzInstruction that)) return false;
		return nextInstructionLabel.equals(that.nextInstructionLabel)
				&& source.equals(that.source)
				&& Objects.equals(label, that.getLabel());
	}

	/**
	 * @return the hash code for this instruction
	 */
	@Override
	public int hashCode() {
		return Objects.hash(nextInstructionLabel, source, getLabel());
	}
}