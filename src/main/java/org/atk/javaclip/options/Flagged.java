package org.atk.javaclip.options;

import org.atk.javaclip.ArgumentParser;

public class Flagged extends Option {
    protected String flag;

    protected Flagged(String flag, String description, ArgumentParser parser, String argumentName,
                      Presence presence) {
        super(description, parser, argumentName, presence);
        this.flag = flag;
    }

    protected String getFlag() {
        return this.flag;
    }


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
        return true;
    }
}
