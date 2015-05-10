package org.atk.javaclip.options;

import org.atk.javaclip.ArgumentParser;
import org.junit.Test;

import static org.junit.Assert.*;

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

        Option opt = new Option(description, parser, argumentName, presence) {
            @Override
            public boolean isIndexedLeft() {
                return false;
            }

            @Override
            public boolean isIndexedRight() {
                return false;
            }

            @Override
            public boolean isFlagged() {
                return false;
            }
        };

        assertSame(description, opt.getDescription());
        assertSame(parser, opt.getParser());
        assertSame(argumentName, opt.getArgumentName());
        assertSame(presence, opt.getPresence());
    }

}