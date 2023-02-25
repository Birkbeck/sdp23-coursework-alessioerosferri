package sml;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * This class contains the labels used by the program. It allows the machine to reference instruction addresses by label (if a label is used).
 * It ensures uniqueness of labels in the SML program.
 *
 * @author alessioerosferri
 */
public final class Labels {
	private final Map<String, Integer> labels = new HashMap<>();

	/**
	 * Adds a label with the associated address to the map.
	 *
	 * @param label the label
	 * @param address the address the label refers to
	 * @throws IllegalArgumentException if label already existing.
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
	 * @throws NullPointerException if label already existing.
	 */
	public int getAddress(String label) {
		// Where can NullPointerException be thrown here?
		// Checking whether a label exists is something that could happen either in here or in the Translator class. Because both Translator and Labels class are public, and therefore could be used by any user (engineer)
		// I am making a design decision to check non-existent labels in both places. Translator ensures the program text passed in can be successfully executed, Labels class can handle also when a key in the hashmap is not present.
		// Whilst Translator verifies the user program is correct, Labels class could be used wrongly by the engineer (e.g. the wrong string variable passed to the method possibly?).
		if (!labels.containsKey(label)){
			throw new NullPointerException("Label '" + label + "' does not have an associated address");
		}

		return labels.get(label);
	}

	/**
	 * representation of this instance,
	 * in the form "[label -> address, label -> address, ..., label -> address]"
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Labels labels1)) return false;
		return labels.equals(labels1.labels);
	}

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
