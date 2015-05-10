package org.atk.javaclip;

import javax.swing.text.html.Option;

/**
 * Created by root on 5/9/15.
 */
public interface Parser {
    ParsingResult parse(String[] args, Option... options);
}
