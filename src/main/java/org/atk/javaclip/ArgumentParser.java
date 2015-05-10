package org.atk.javaclip;

public interface ArgumentParser<T> {
    T parse(String[] tokens);
}
