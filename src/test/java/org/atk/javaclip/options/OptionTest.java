package org.atk.javaclip.options;

import org.atk.javaclip.ArgumentParser;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by root on 5/9/15.
 */
public class OptionTest {

    @Test
    public void testConstructor() {
        final String description = "this is a dummy argument";
        final ArgumentParser parser = new ArgumentParser() {
            public Object parse(String[] tokens) {
                return null;
            }
        };
        final String argumentName = "<TOTO>";
        final Option.Presence presence = Option.Presence.Mandatory;

        Option mock = new Option(description, parser, argumentName, presence) {};

        assertSame(description, mock.getDescription());
        assertSame(parser, mock.getParser());
        assertSame(argumentName, mock.getArgumentName());
        assertSame(presence, mock.getPresence());
    }

}