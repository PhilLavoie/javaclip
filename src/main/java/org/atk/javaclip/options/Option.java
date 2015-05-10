package org.atk.javaclip.options;

import org.atk.javaclip.ArgumentParser;

/**
 * Created by root on 5/9/15.
 */
public abstract class Option {

    protected String description;
    protected ArgumentParser parser;
    protected String argumentName;
    protected enum Presence {
        Optional,
        Mandatory
    }
    protected Presence presence;

    protected Option(String description, ArgumentParser parser, String argumentName, Presence presence) {
        this.description = description;
        this.parser = parser;
        this.argumentName =argumentName;
        this.presence = presence;
    }

    protected String getDescription() {
        return description;
    }

    protected ArgumentParser getParser() {
        return parser;
    }

    protected String getArgumentName() {
        return argumentName;
    }

    protected Presence getPresence() {
        return this.presence;
    }
}
