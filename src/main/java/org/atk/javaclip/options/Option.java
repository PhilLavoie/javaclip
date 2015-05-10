package org.atk.javaclip.options;

import org.atk.javaclip.ArgumentParser;

public class Option {

    protected enum Presence {
        Optional,
        Mandatory;

    }
    protected Presence presence;
    protected String description;

    protected ArgumentParser parser;

    protected String argumentName;
    protected final String flag;
    protected final int index;

    protected enum Type {
        Flagged,
        IndexedLeft,
        IndexedRight
    }

    protected Type type;

    protected Option(String description, ArgumentParser parser, String argumentName, Presence presence,
                   Type type, String flag, int index) {
        this.description = description;
        this.parser = parser;
        this.argumentName =argumentName;
        this.presence = presence;
        this.type = type;
        this.flag = flag;
        this.index = index;
    }

    protected Presence getPresence() {
        return this.presence;
    }

    protected Type getType() {
        return this.type;
    }

    public String getDescription() {
        return description;
    }

    public ArgumentParser getParser() {
        return parser;
    }

    public String getArgumentName() {
        return argumentName;
    }

    public boolean isIndexedLeft() {
        return this.type == Type.IndexedLeft;
    }

    public boolean isIndexedRight() {
        return this.type == Type.IndexedRight;
    }

    public boolean isFlagged() {
        return this.type == Type.Flagged;
    }

    public String getFlag() {
        if (this.type != Type.Flagged) {
            throw new IllegalStateException(this.type + " options don't have flags");
        }
        return this.flag;
    }

    public int getIndex() {
        if (this.type != Type.IndexedLeft && this.type != Type.IndexedRight) {
            throw new IllegalStateException(this.type + " options don't have indexes");
        }
        return this.index;
    }

    public boolean isOptional() {
        return this.presence == Presence.Optional;
    }

    public boolean isMandatory() {
        return this.presence == Presence.Mandatory;
    }

}
