package sml;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import static sml.Registers.Register;

/**
 * This class translates an SML program from a source file.
 * <p>
 * It reads in a file and processes each line, converting it into an SML instruction.
 * The resulting instructions are stored in a list, along with their labels.
 * <p>
 * The Translator uses reflection API to instantiate the appropriate Instruction subclass based on the opcode found
 * in the input file. The Instruction subclasses must follow a specific naming convention and be located in the
 * "sml.instruction" package.
 *
 * @author alessioerosferri
 */
public final class Translator {

    private final String fileName; // source file of SML code

    // line contains the characters in the current line that's not been processed yet
    private String line = "";

    public Translator(String fileName) {
        this.fileName =  fileName;
    }

    public void readAndTranslate(Labels labels, List<Instruction> program) throws IOException {
        try (var sc = new Scanner(new File(fileName), StandardCharsets.UTF_8)) {
            labels.reset();
            program.clear();

            // Each iteration processes line and reads the next input line into "line"
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                String label = getLabel();

                Instruction instruction = getInstruction(label);
                if (instruction != null) {
                    if (label != null)
                        labels.addLabel(label, program.size());
                    program.add(instruction);
                }
            }
        }
    }

    /**
     * Translates the current line into an instruction with the given label
     *
     * @param label the instruction label
     * @return the new instruction
     * <p>
     * The input line should consist of a single SML instruction,
     * with its label already removed.
     */
    private Instruction getInstruction(String label) {
        if (line.isEmpty())
            return null;

        String opcode = scan();
        String composedStringClass = "sml.instruction." + Character.toUpperCase(opcode.charAt(0)) + opcode.substring(1) + "Instruction";

        try {
            Class<?> klass = Class.forName(composedStringClass);
            Constructor<?>[] constructors = klass.getConstructors();
            if (constructors.length == 0){
                throw new NoSuchMethodException("Class: "+composedStringClass + " cannot be instantiated.");
            }

            Constructor<?> constructor = constructors[0];
            Class<?>[] paramTypesDeclared = constructor.getParameterTypes();

            // going through parameters in constructor signature and constructing through a stream the params to send upon instantiating the Instruction.
            List<Object> params = Arrays.stream(paramTypesDeclared)
                    .skip(1)
                    .map((e)->{
                        switch (e.getName()){
                            case "sml.RegisterName" -> {
                                return Register.valueOf(scan());
                            }
                            case "java.lang.Integer" -> {
                                return Integer.parseInt(scan());
                            }
                            default -> {
                                // unknown how to handle, defaulting to String
                                return scan();
                            }
                        }
                    })
                    .collect(Collectors.toList());

            params.add(0, label);
            return (Instruction) constructor.newInstance(params.toArray());
        } catch (ClassNotFoundException e) {
            System.out.println("Unknown instruction with opcode: " + opcode);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }


    private String getLabel() {
        String word = scan();
        if (word.endsWith(":"))
            return word.substring(0, word.length() - 1);

        // undo scanning the word
        line = word + " " + line;
        return null;
    }

    /*
     * Return the first word of line and remove it from line.
     * If there is no word, return "".
     */
    private String scan() {
        line = line.trim();

        for (int i = 0; i < line.length(); i++)
            if (Character.isWhitespace(line.charAt(i))) {
                String word = line.substring(0, i);
                line = line.substring(i);
                return word;
            }

        return line;
    }
}