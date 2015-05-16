package org.atk.javaclip.options;

import org.atk.javaclip.parsers.ArgumentParser;

import java.util.HashMap;

public class OptionBuilder {

    enum Type {
        Flagged("flagged"),
        IndexedLeft("indexed left"),
        IndexedRight("indexed right"),
        Floating("floating"),
        UndefinedYet("undefined");

        private String pretty;

        Type(String prettyString) {
            this.pretty = prettyString;
        }

        public String getPrettyString() {
            return this.pretty;
        }
    }

    private Type type = Type.UndefinedYet;

    private String flag;
    private int index;

    private boolean descriptionProvided = false;
    private String description = "";

    private boolean argumentParserProvided = false;
    private String argumentName;
    private ArgumentParser parser;

    enum Presence {
        Optional,
        Mandatory,
        UndefinedYet
    }
    private Presence presence = Presence.UndefinedYet;

    public OptionBuilder() {}

    static public OptionBuilder builder() {
        return new OptionBuilder();
    }

    public OptionBuilder flaggedWith(String flag) {
        checkFlag(flag);
        checkType(this.type, Type.Flagged);
        this.type = Type.Flagged;
        this.flag = flag;
        return this;
    }

    private void checkFlag(String flag) {
        if (flag == null || flag.trim().isEmpty()) {
            throw new IllegalArgumentException("invalid flag, the provided flag must contain at least one meaningful character");
        }
    }

    public OptionBuilder indexedLeftAt(int index) {
        checkIndex(index);
        checkType(this.type, Type.IndexedLeft);
        this.type = Type.IndexedLeft;
        this.index = index;
        return this;
    }

    public OptionBuilder indexedRightAt(int index) {
        checkIndex(index);
        checkType(this.type, Type.IndexedRight);
        this.type = Type.IndexedRight;
        this.index = index;
        return this;
    }

    private void checkIndex(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("invalid index: " + index + ", the provided value must be above 0");
        }
    }

    public OptionBuilder floating() {
        checkType(this.type, Type.Floating);
        this.type = Type.Floating;
        return this;
    }

    private static void checkType(Type current, Type incoming) {
        if (current != Type.UndefinedYet) {
            throw new RuntimeException(getInvalidTypeMessage(current, incoming));
        }
    }

    private static String getInvalidTypeMessage(Type current, Type incoming) {
        assert current != Type.UndefinedYet;

        return "type already set... trying to change from "
                + current.getPrettyString() + " to "
                + incoming.getPrettyString();
    }

    public OptionBuilder withDescription(String description) {
        if (this.descriptionProvided) {
            throw new IllegalStateException("description already provided");
        }
        this.descriptionProvided = true;

        this.description = description;

        return this;
    }

    public OptionBuilder withArgument(String argName, ArgumentParser parser) {
        checkArgumentName(argName);
        if (this.argumentParserProvided) {
            throw new IllegalStateException("already set the argument");
        }
        this.argumentParserProvided = true;

        this.argumentName = argName;
        this.parser = parser;

        return this;
    }

    private void checkArgumentName(String argName) {
        if (argName == null || argName.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "invalid argument name, provided argument name must have at least one meaningful character");
        }
    }

    public OptionBuilder isMandatory() {
        setPresence(Presence.Mandatory);
        return this;
    }

    public OptionBuilder isMandatory(boolean mandatory) {
        if (mandatory) {
            return isMandatory();
        }
        return isOptional();
    }

    public OptionBuilder isOptional() {
        setPresence(Presence.Optional);
        return this;
    }

    public OptionBuilder isOptional(boolean optional) {
        if (optional) {
            return isOptional();
        }
        return isMandatory();
    }


    private void setPresence(Presence presence) {
        switch (this.presence) {
            case Optional:
                throw new IllegalStateException("already set presence to optional");
            case Mandatory:
                throw new IllegalStateException("already set presence to mandatory");
            case UndefinedYet:
                this.presence = presence;
                break;
            default:
                assert false: "unexpected presence: " + presence;
        }
    }

    public Option build() {
        switch (type) {
            case Flagged:
                return new Option(description, parser, argumentName,
                        convertPresenceAndUseDefaultIfNotSet(presence, type), Option.Type.Flagged, flag, 0);
            case IndexedLeft:
            case IndexedRight:
            case Floating:
                if (!argumentParserProvided) {
                    throw new IllegalStateException(this.type.getPrettyString() + " option require an argument parser");
                }

                return new Option(description, parser, argumentName,
                        convertPresenceAndUseDefaultIfNotSet(presence, type), convertType(this.type), null, index);
            case UndefinedYet:
                throw new IllegalStateException("must set either a flag or an index");
            default:
                assert false : "unexpected type: " + type;
        }
        //Never make it here.
        return null;
    }

    private Option.Type convertType(Type type) {
        switch (type) {
            case Flagged:
                return Option.Type.Flagged;
            case IndexedLeft:
                return Option.Type.IndexedLeft;
            case IndexedRight:
                return Option.Type.IndexedRight;
            case Floating:
                return Option.Type.Floating;
            default:
                assert false;
        }
        return null;
    }

    private Option.Presence convertPresenceAndUseDefaultIfNotSet(Presence presence, Type type) {
       switch (presence) {
           case Mandatory:
               return Option.Presence.Mandatory;
           case Optional:
               return Option.Presence.Optional;
           case UndefinedYet:
               return defaultPresenceFor(type);
           default:
               assert false: "unexpected presence: " + presence;
        }
        //Not supposed to make it here.
        return null;
    }

    private Option.Presence defaultPresenceFor(Type type) {
        final Option.Presence DEFAULT_FLAGGED_PRESENCE = Option.Presence.Optional;
        final Option.Presence DEFAULT_INDEXED_PRESENCE = Option.Presence.Mandatory;
        switch (type) {
            case Flagged:
                return DEFAULT_FLAGGED_PRESENCE;
            case IndexedLeft:
            case IndexedRight:
                return DEFAULT_INDEXED_PRESENCE;
            case UndefinedYet:
                assert false: "should not call this method with and undefined type";
            default:
                assert false: "unexpected type: " + type;
        }
        //Don't make it here.
        return null;
    }

}
