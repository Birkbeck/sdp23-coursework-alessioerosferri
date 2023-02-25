package sml;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.instruction.*;

import java.io.IOException;

import static sml.Registers.Register.EAX;
import static sml.Registers.Register.EBX;

class TranslatorTest {
    private Translator translator;
    private Machine machine;
    private Registers registers;

    @BeforeEach
    void setUp() {
        machine = new Machine(new Registers());
        registers = machine.getRegisters();
        registers.set(EAX, 0);
        registers.set(EBX, 0);
    }

    @AfterEach
    void tearDown() {
        machine = null;
        registers = null;
    }

    @Test
    void readAndTranslateAdd() throws IOException {
        translator = new Translator("test/resources/addInstruction.txt");
        translator.readAndTranslate(machine.getLabels(), machine.getProgram());
        Assertions.assertEquals(machine.getProgram().get(0), new AddInstruction(null, EAX, EBX));
        Assertions.assertEquals(machine.getProgram().get(1), new AddInstruction("label", EAX, EBX));
    }

    @Test
    void readAndTranslateDiv() throws IOException {
        translator = new Translator("test/resources/divInstruction.txt");
        translator.readAndTranslate(machine.getLabels(), machine.getProgram());
        Assertions.assertEquals(machine.getProgram().get(0), new DivInstruction(null, EAX, EBX));
        Assertions.assertEquals(machine.getProgram().get(1), new DivInstruction("label", EAX, EBX));
    }

    @Test
    void readAndTranslateJnz() throws IOException {
        translator = new Translator("test/resources/jnzInstruction.txt");
        translator.readAndTranslate(machine.getLabels(), machine.getProgram());
        Assertions.assertEquals(machine.getProgram().get(0), new JnzInstruction(null, EAX, "0"));
        Assertions.assertEquals(machine.getProgram().get(1), new JnzInstruction("label", EAX, "0"));
    }

    @Test
    void readAndTranslateMov() throws IOException {
        translator = new Translator("test/resources/movInstruction.txt");
        translator.readAndTranslate(machine.getLabels(), machine.getProgram());
        Assertions.assertEquals(machine.getProgram().get(0), new MovInstruction(null, EAX, 1));
        Assertions.assertEquals(machine.getProgram().get(1), new MovInstruction("label", EAX, 1));
    }

    @Test
    void readAndTranslateMul() throws IOException {
        translator = new Translator("test/resources/mulInstruction.txt");
        translator.readAndTranslate(machine.getLabels(), machine.getProgram());
        Assertions.assertEquals(machine.getProgram().get(0), new MulInstruction(null, EAX, EBX));
        Assertions.assertEquals(machine.getProgram().get(1), new MulInstruction("label", EAX, EBX));
    }

    @Test
    void readAndTranslateSub() throws IOException {
        translator = new Translator("test/resources/subInstruction.txt");
        translator.readAndTranslate(machine.getLabels(), machine.getProgram());
        Assertions.assertEquals(machine.getProgram().get(0), new SubInstruction(null, EAX, EBX));
        Assertions.assertEquals(machine.getProgram().get(1), new SubInstruction("label", EAX, EBX));
    }

    @Test
    void readAndTranslateOut() throws IOException {
        translator = new Translator("test/resources/outInstruction.txt");
        translator.readAndTranslate(machine.getLabels(), machine.getProgram());
        Assertions.assertEquals(machine.getProgram().get(0), new OutInstruction(null, EAX));
        Assertions.assertEquals(machine.getProgram().get(1), new OutInstruction("label", EAX));
    }
}