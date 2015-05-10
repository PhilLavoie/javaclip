package org.atk.javaclip;

import org.atk.javaclip.options.Option;

import java.util.ArrayList;

public class DefaultParser implements Parser {

    public ParsingResult parse(String[] args, Option... options) {
        ArrayList<Option> indexedLeft = new ArrayList<>();

        for (Option opt: options) {
            if (opt.isIndexedLeft()) {
                indexedLeft.add(opt);
            }
        }

        ArrayList<Option> indexedRight = new ArrayList<>();

        for (Option opt: options) {
            if (opt.isIndexedRight()) {
                indexedRight.add(opt);
            }
        }

        return null;
    }
}
