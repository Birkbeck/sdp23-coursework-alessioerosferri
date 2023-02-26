package sml;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class InstructionFactory {
    private static final InstructionFactory instance = new InstructionFactory();
    public static InstructionFactory getInstance() {
        return instance;
    }
    private InstructionFactory(){

    }
    public Instruction createInstruction(String label, String opcode, Supplier<String> scan){
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
                                return Registers.Register.valueOf(scan.get());
                            }
                            case "java.lang.Integer" -> {
                                return Integer.parseInt(scan.get());
                            }
                            default -> {
                                // unknown how to handle, defaulting to String
                                return scan.get();
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
}
