package org.example;

public class FileNode implements Element {
    private final String name;
    private final long size;
    public FileNode(String name, long size) {
        if (size < 0)
            throw new IllegalArgumentException("negative size " + size);
        this.name = name;
        this.size = size;
    }
    public String getName() {
        return name;
    }
    public long getSize() {
        return size;
    }
    public void accept(Visitor v) {
        v.visit(this);
    }
}
