package sml;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The Labels class contains the labels used by the program. It allows the machine to reference instruction addresses by label (if a label is used).
 * It ensures uniqueness of labels in the SML program.
 *
 * @author alessioerosferri
 */
public final class Labels {

	/** The map containing the labels and their associated addresses. */
	private final Map<String, Integer> labels = new HashMap<>();

	/**
	 * Adds a label with the associated address to the map.
	 *
	 * @param label the label
	 * @param address the address the label refers to
	 * @throws IllegalArgumentException if label already exists.
	 */
	public void addLabel(String label, int address) {
		Objects.requireNonNull(label);
		if (labels.containsKey(label)){
			throw new IllegalArgumentException("Label '" + label + "' is already defined in the program");
		}

		labels.put(label, address);
	}

	/**
	 * Returns the address associated with the label. If the label passed does not exist, it will throw a NullPointerException.
	 *
	 * @param label the label
	 * @return the address the label refers to
	 * @throws NullPointerException if label does not have an associated address.
	 */
	public int getAddress(String label) {
		if (!labels.containsKey(label)){
			throw new NullPointerException("Label '" + label + "' does not have an associated address");
		}

		return labels.get(label);
	}

	/**
	 * Returns a string representation of this instance in the form "[label -> address, label -> address, ..., label -> address]".
	 *
	 * @return the string representation of the labels map
	 */
	@Override
	public String toString() {
		return labels.entrySet()
				.stream()
				.map(entry -> entry.getKey() + " -> " + entry.getValue())
				.collect(Collectors.joining(", ", "[", "]"));
	}

	/**
	 * Indicates whether some other object is "equal to" this one.
	 *
	 * @param o the object to compare
	 * @return true if this object is the same as the o argument; false otherwise
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Labels that)) return false;
		return labels.equals(that.labels);
	}

	/**
	 * Returns a hash code value for the object.
	 *
	 * @return a hash code value for this object
	 */
	@Override
	public int hashCode() {
		return Objects.hash(labels);
	}

	/**
	 * Removes the labels
	 */
	public void reset() {
		labels.clear();
	}
}
