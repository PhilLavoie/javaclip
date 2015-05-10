package org.atk.javaclip.options;

import org.atk.javaclip.ArgumentParser;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;
import static org.atk.javaclip.options.OptionBuilder.*;
import static org.mockito.Mockito.mock;

public class OptionBuilderTest {

    private OptionBuilder dummyFlagged() {
        return dummyFlagged("--jesus-is-my-shephard");
    }

    private OptionBuilder dummyFlagged(String flag) {
        return builder().flaggedWith(flag);
    }

    private OptionBuilder dummyIndexedLeft() {
        return dummyIndexedLeft(0);
    }

    private OptionBuilder dummyIndexedLeft(int index) {
        return dummyIndexed(index, Position.Left);
    }

    private OptionBuilder dummyIndexedRight() {
        return dummyIndexedRight(0);
    }

    private OptionBuilder dummyIndexedRight(int index) {
        return dummyIndexed(index, Position.Right);
    }

    enum Position {
        Left,
        Right
    }

    private OptionBuilder dummyIndexed(final int index, final Position position) {
        return position == Position.Left ?
                builder().indexedLeftAt(index).withArgument("<gsp_4_prez>", mock(ArgumentParser.class)) :
                builder().indexedRightAt(index).withArgument("<gsp_4_prez>", mock(ArgumentParser.class));
    }

    private OptionBuilder dummyFor(Type type) {
        switch (type) {
            case Flagged:
                return dummyFlagged();
            case IndexedLeft:
                return dummyIndexedLeft();
            case IndexedRight:
                return dummyIndexedRight();
            case UndefinedYet:
                assert false;
            default:
                assert false;
        }
        return null;
    }

    @Test
    public void testFlagged() {
        Option opt = dummyFlagged().build();
        assertTrue(opt.isFlagged());
        assertFalse(opt.isIndexedLeft());
        assertFalse(opt.isIndexedRight());
    }

    @Test
    public void testIndexedLeft() {
        Option opt = dummyIndexedLeft().build();
        assertTrue(opt.isIndexedLeft());
        assertFalse(opt.isFlagged());
        assertFalse(opt.isIndexedRight());
    }

    @Test
    public void testIndexedRight() {
        Option opt = dummyIndexedRight().build();
        assertTrue(opt.isIndexedRight());
        assertFalse(opt.isIndexedLeft());
        assertFalse(opt.isFlagged());
    }

    @Test
    public void testFlaggedDescription() {
        final String myDesc = "GSP: Are you crazy in de head man?";
        Option flagged = dummyFlagged().withDescription(myDesc).build();
        assertEquals(myDesc, flagged.getDescription());
    }

    @Test
    public void testIndexedLeftDescription() {
        final String myDesc = "GSP: You think I'm afréd of you man?";
        Option indexedLeft = dummyIndexedLeft().withDescription(myDesc).build();
        assertEquals(myDesc, indexedLeft.getDescription());
    }

    @Test
    public void testFlagProperlySet() {
        final String flag = "--bj-is-da-bess";
        Option flagged = dummyFlagged(flag).build();
        assertEquals(flag, flagged.getFlag());
    }

    @Test
    public void testLeftIndexProperlySet() {
        final int index = new Random().nextInt(Integer.MAX_VALUE);
        Option indexed = dummyIndexedLeft(index).build();
        assertEquals(index, indexed.getIndex());
    }

    @Test
    public void testRightIndexProperlySet() {
        final int index = new Random().nextInt(Integer.MAX_VALUE);
        Option indexed = dummyIndexedRight(index).build();
        assertEquals(index, indexed.getIndex());
    }

    @Test
    public void testIndexedRightDescription() {
        final String myDesc = "GSP: You think I'm afréd of you man?";
        Option indexedRight = dummyIndexedRight().withDescription(myDesc).build();
        assertEquals(myDesc, indexedRight.getDescription());
    }

    @Test
    public void testOptionalFlagged() {
        testPresenceFor(Option.Presence.Optional, Type.Flagged);
    }

    @Test
    public void testMandatoryFlagged() {
        testPresenceFor(Option.Presence.Mandatory, Type.Flagged);
    }
    
    @Test
    public void testOptionalIndexedLeft() {
        testPresenceFor(Option.Presence.Optional, Type.IndexedLeft);
    }

    @Test
    public void testMandatoryIndexedLeft() {
        testPresenceFor(Option.Presence.Mandatory, Type.IndexedLeft);
    }

    @Test
    public void testOptionalIndexedRight() {
        testPresenceFor(Option.Presence.Optional, Type.IndexedRight);
    }

    @Test
    public void testMandatoryIndexedRight() {
        testPresenceFor(Option.Presence.Mandatory, Type.IndexedRight);
    }

    private void testPresenceFor(final Option.Presence presence, final Type type) {
        switch (presence) {
            case Optional:
                testOptionalFor(type);
                break;
            case Mandatory:
                testMandatoryFor(type);
                break;
            default:
                assert false;
        }
    }

    private void testOptionalFor(final Type type) {
        final Option.Presence presence = Option.Presence.Optional;

        {
            Option opt = dummyFor(type).isOptional().build();
            assertEquals(presence, opt.getPresence());
        }

        {
            Option opt = dummyFor(type).isOptional(true).build();
            assertEquals(presence, opt.getPresence());
        }

        {
            Option opt = dummyFor(type).isMandatory(false).build();
            assertEquals(presence, opt.getPresence());
        }
    }

    private void testMandatoryFor(final Type type) {
        final Option.Presence presence = Option.Presence.Mandatory;

        {
            Option opt = dummyFor(type).isMandatory().build();
            assertEquals(presence, opt.getPresence());
        }

        {
            Option opt = dummyFor(type).isMandatory(true).build();
            assertEquals(presence, opt.getPresence());
        }

        {
            Option opt = dummyFor(type).isOptional(false).build();
            assertEquals(presence, opt.getPresence());
        }
    }

}