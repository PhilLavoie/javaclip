package org.atk.javaclip.parsers;

import java.util.Comparator;

public interface ArgumentParser {

    static ArgumentParser integerParser() {
        return new ArgumentParser() {
            @Override
            public ArgumentParsingResult parse(final String[] tokens) {
                enforceEnoughArgs(tokens, 1);
                final String token = tokens[0];
                final Integer value;
                try {
                    value = Integer.parseInt(token);
                } catch (Exception e) {
                  throw new IllegalArgumentException("unable to parse integer value from " + token);
                }
                return new ArgumentParsingResult(value, 1);
            }
        };
    }

    static ArgumentParser doubleParser() {
        return new ArgumentParser() {
            @Override
            public ArgumentParsingResult parse(String[] tokens) {
                enforceEnoughArgs(tokens, 1);
                final String token = tokens[0];
                final Double value;
                try {
                    value = Double.parseDouble(token);
                } catch (Exception e) {
                    throw new IllegalArgumentException("unable to parse double value from " + token);
                }
                return new ArgumentParsingResult(value, 1);
            }
        };
    }

    static <T extends Comparable<T>> ArgumentParser bounded(final ArgumentParser parser, final T inclusiveMin, final T exclusiveMax) {
        return new ArgumentParser() {
            @Override
            public ArgumentParsingResult parse(String[] tokens) {
                ArgumentParsingResult result = parser.parse(tokens);
                T value = result.getValue();
                if (value.compareTo(inclusiveMin) < 0 ||
                        value.compareTo(exclusiveMax) >= 0) {
                    throw new IllegalArgumentException("value " + value + " is out of bounds ["
                            + inclusiveMin + ", " + exclusiveMax + " [");
                }
                return new ArgumentParsingResult(value, result.getNumberOfParsedArguments());
            }
        };
    }

    ArgumentParsingResult parse(final String[] tokens);

    static void enforceEnoughArgs(final String[] tokens, final int minNoArgs) {
        if (tokens.length < minNoArgs) {
            throw new IllegalArgumentException("expecting " + minNoArgs
                            + " argument" + (minNoArgs > 0 ? "s" : "")
                            + " but found " + tokens.length);
        }
    }
}
