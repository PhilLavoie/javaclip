package org.atk.javaclip;


import org.atk.javaclip.options.Option;

public interface Parser {
    ParsingResult parse(String[] args, Option... options);
}
