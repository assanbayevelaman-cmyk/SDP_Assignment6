package org.example;

public class TotalSizeVisitor implements Visitor {
    private long total = 0;
    public long getTotal() {
        return total;
    }
    public void reset() {
        total = 0;
    }
    public void visit(FileNode f) {
        total += f.getSize();
    }
    public void visit(DirectoryNode d) {
        for (Element e : d.getChildren())
            e.accept(this);
    }
}
