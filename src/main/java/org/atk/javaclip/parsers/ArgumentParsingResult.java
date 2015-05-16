package org.atk.javaclip.parsers;

public class ArgumentParsingResult {

    int noArgParsed;
    Object value;

    public ArgumentParsingResult(Object value, int numberOfArgumentsParsed) {
        this.noArgParsed = numberOfArgumentsParsed;
        this.value = value;
    }

    public int getNumberOfParsedArguments() {
        return this.noArgParsed;
    }

    public <T> T getValue() {
        return (T) this.value;
    }
}
