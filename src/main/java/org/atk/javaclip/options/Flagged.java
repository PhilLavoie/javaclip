package org.atk.javaclip.options;

import org.atk.javaclip.ArgumentParser;

/**
 * Created by root on 5/9/15.
 */
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
}
