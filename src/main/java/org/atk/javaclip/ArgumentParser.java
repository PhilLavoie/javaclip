package org.atk.javaclip;

/**
 * Created by root on 5/9/15.
 */
public interface ArgumentParser<T> {
    T parse(String[] tokens);
}
