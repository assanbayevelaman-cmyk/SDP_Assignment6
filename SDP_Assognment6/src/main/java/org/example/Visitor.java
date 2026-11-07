package org.example;

public interface Visitor {
    void visit(FileNode f);
    void visit(DirectoryNode d);
    void reset();
}
