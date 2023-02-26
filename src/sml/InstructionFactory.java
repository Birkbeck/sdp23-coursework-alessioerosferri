package sml;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class InstructionFactory {
    private static final InstructionFactory instance = new InstructionFactory();
    private BeanFactory beanFactory;
    public static InstructionFactory getInstance() {
        return instance;
    }
    private InstructionFactory(){
        beanFactory = new ClassPathXmlApplicationContext("/beans.xml");
    }
    public Instruction createInstruction(String label, String opcode, Supplier<String> scan){
        try {
            Class<?> klass = beanFactory.getType(opcode);
            Constructor<?>[] constructors = klass.getConstructors();
            if (constructors.length == 0){
                throw new NoSuchMethodException("Class: "+klass.getName() + " cannot be instantiated.");
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
            return (Instruction) beanFactory.getBean(opcode, params.toArray());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
