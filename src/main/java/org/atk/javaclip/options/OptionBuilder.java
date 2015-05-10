package org.atk.javaclip.options;

import org.atk.javaclip.ArgumentParser;

/**
 * Created by root on 5/9/15.
 */
public class OptionBuilder {

    enum Type {
        Flagged,
        IndexedLeft,
        IndexedRight,
        UndefinedYet;
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

        switch (type) {
            case Flagged:
                throw new IllegalStateException("already provided a flag: " + flag);
            case IndexedLeft:
            case IndexedRight:
                throw new IllegalStateException("cannot flag an indexed argument!");
            case UndefinedYet:
                type = Type.Flagged;
                this.flag = flag;
                break;
            default:
                assert false : "unexpected type: " + type;
        }
        return this;
    }

    private void checkFlag(String flag) {
        if (flag == null || flag.trim().isEmpty()) {
            throw new IllegalArgumentException("invalid flag, the provided flag must contain at least one meaningful character");
        }
    }

    public OptionBuilder indexedLeftAt(int index) {
        checkIndex(index);

        switch (type) {
            case Flagged:
                throw new IllegalStateException("cannot index a flagged argument!");
            case IndexedLeft:
            case IndexedRight:
                throw new IllegalStateException("already provided an index: " + index);
            case UndefinedYet:
                type = Type.IndexedLeft;
                this.index = index;
                break;
            default:
                assert false : "unexpected type: " + type;
        }
        return this;
    }

    public OptionBuilder indexedRightAt(int index) {
        checkIndex(index);

        switch (type) {
            case Flagged:
                throw new IllegalStateException("cannot index a flagged argument!");
            case IndexedLeft:
            case IndexedRight:
                throw new IllegalStateException("already provided an index: " + index);
            case UndefinedYet:
                type = Type.IndexedRight;
                this.index = index;
                break;
            default:
                assert false : "unexpected type: " + type;
        }
        return this;
    }

    private void checkIndex(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("invalid index: " + index + ", the provided value must be above 0");
        }
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
                return new Flagged(flag, description, parser, argumentName, convertPresenceFor(presence, type));
            case IndexedLeft:
                if (!argumentParserProvided) {
                    throw new IllegalStateException("indexed argument require a parser and none was provided");
                }
                return new Indexed(Indexed.Position.Left, index, description, parser,
                        argumentName, convertPresenceFor(presence, type));
            case IndexedRight:
                if (!argumentParserProvided) {
                    throw new IllegalStateException("indexed argument require a parser and none was provided");
                }
                return new Indexed(Indexed.Position.Right, index, description, parser,
                        argumentName, convertPresenceFor(presence, type));
            case UndefinedYet:
                throw new IllegalStateException("must set either a flag or an index");
            default:
                assert false : "unexpected type: " + type;
        }
        //Never make it here.
        return null;
    }

    private Option.Presence convertPresenceFor(Presence presence, Type type) {
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
