package org.atk.javaclip;

import org.atk.javaclip.options.Option;

import java.util.ArrayList;
import java.util.HashMap;

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

        HashMap<String, Option> flaggedMap = new HashMap<>();

        for (Option opt: options) {
            if (opt.isFlagged()) {
                if (flaggedMap.containsKey(opt.getFlag())) {
                    throw new IllegalArgumentException("flag " + opt.getFlag() + " is not unique and is expected to be");
                }
                flaggedMap.put(opt.getFlag(), opt);
            }
        }

        return null;
    }
}
