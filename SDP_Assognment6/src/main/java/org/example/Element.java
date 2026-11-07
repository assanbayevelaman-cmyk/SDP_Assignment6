package org.example;

public interface Element {
    String getName();
    void accept(Visitor v);
}
