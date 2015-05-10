package org.atk.javaclip.options;

import org.atk.javaclip.ArgumentParser;
import org.atk.javaclip.options.Option.*;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class OptionTest {

    @Test
    public void testDescription() {
        final String description = "you got balls, I like balls";
        Option opt = new Option(description, null, null, null, null, null, 0);
        assertEquals(description, opt.getDescription());
    }

    @Test
    public void testArgumentName() {
        final String arg = "<myArg>";
        Option opt = new Option(null, null, arg, null, null, null, 0);
        assertEquals(arg, opt.getArgumentName());
    }

    @Test
    public void testParser() {
        final ArgumentParser parser = mock(ArgumentParser.class);
        Option opt = new Option(null, parser, null, null, null, null, 0);
        assertEquals(parser, opt.getParser());
    }

    @Test
    public void testOptional() {
        final Presence presence = Presence.Optional;
        Option opt = new Option(null, null, null, presence, null, null, 0);
        assertEquals(presence, opt.getPresence());
        assertTrue(opt.isOptional());
        assertFalse(opt.isMandatory());
    }

    @Test
    public void testMandatory() {
        final Presence presence = Presence.Mandatory;
        Option opt = new Option(null, null, null, presence, null, null, 0);
        assertEquals(presence, opt.getPresence());
        assertTrue(opt.isMandatory());
        assertFalse(opt.isOptional());
    }

    @Test
    public void testFlagged() {
        final Type type = Type.Flagged;
        String flag = "--fake-flag";
        Option opt = new Option(null, null, null, null, type, flag, 0);
        assertEquals(type, opt.getType());
        assertEquals(flag, opt.getFlag());
    }

    @Test
    public void testIndexedLeft() {
        final Type type = Type.IndexedLeft;
        int index = new Random().nextInt(Integer.MAX_VALUE);
        Option opt = new Option(null, null, null, null, type, null, index);
        assertEquals(type, opt.getType());
        assertEquals(index, opt.getIndex());
    }

    @Test
    public void testIndexedRight() {
        final Type type = Type.IndexedRight;
        int index = new Random().nextInt(Integer.MAX_VALUE);
        Option opt = new Option(null, null, null, null, type, null, index);
        assertEquals(type, opt.getType());
        assertEquals(index, opt.getIndex());
    }

}