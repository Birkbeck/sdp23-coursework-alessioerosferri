package sml;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The Registers class represents a set of named registers.
 * Each register has a name and a value, which is an integer.
 * The class provides methods for setting and getting register values,
 * as well as for clearing all register values.
 * @author alessioerosferri
 */
public final class Registers {
    private final Map<Register, Integer> registers = new HashMap<>();

    /**
     * Enumeration of register names.
     */
    public enum Register implements RegisterName {
        EAX, EBX, ECX, EDX, ESP, EBP, ESI, EDI;
    }

    /**
     * Constructs a new Registers object and clears all register values.
     */
    public Registers() {
        clear(); // the class is final
    }

    /**
     * Clears all register values, setting them to 0.
     */
    public void clear() {
        for (Register register : Register.values())
            registers.put(register, 0);
    }

    /**
     * Sets the given register to the value.
     *
     * @param register register name
     * @param value new value
     */
    public void set(RegisterName register, int value) {
        registers.put((Register)register, value);
    }

    /**
     * Returns the value stored in the register.
     *
     * @param register register name
     * @return value
     */
    public int get(RegisterName register) {
        return registers.get((Register)register);
    }

    /**
     * Returns a string representation of the Registers object,
     * in the form "[register1 = value1, register2 = value2, ...]".
     *
     * @return a string representation of the Registers object
     */
    @Override
    public String toString() {
        return registers.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> e.getKey() + " = " + e.getValue())
                .collect(Collectors.joining(", ", "[", "]")) ;
    }

    /**
     * Compares this Registers object to another object for equality.
     *
     * @param o the object to compare to
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Registers that) {
            return registers.equals(that.registers);
        }
        return false;
    }

    /**
     * Returns a hash code for the Registers object.
     *
     * @return a hash code for the Registers object
     */
    @Override
    public int hashCode() {
        return registers.hashCode();
    }
}
