package org.atk.javaclip.options;

import org.atk.javaclip.ArgumentParser;

/**
 * Created by root on 5/9/15.
 */
public class Indexed extends Option {

    protected int index;

    protected enum Position {
        Left,
        Right
    }

    protected Position position;

    protected Indexed(Position position, int index, String description, ArgumentParser parser,
                      String argumentName, Presence presence) {
        super(description, parser, argumentName, presence);
        this.position = position;
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }

    protected Position getPosition() {
        return this.position;
    }
}
