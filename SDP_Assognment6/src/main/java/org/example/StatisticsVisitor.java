package org.example;

public class StatisticsVisitor implements Visitor {
    private int files = 0;
    private int dirs = 0;
    private int maxDepth = 0;
    private int depth = -1;
    public int getFiles() {
        return files;
    }
    public int getDirs() {
        return dirs;
    }
    public int getMaxDepth() {
        return maxDepth;
    }
    public void reset() {
        files = dirs = maxDepth = 0;
        depth = -1;
    }
    public void visit(FileNode f) {
        files++;
        if (depth > maxDepth)
            maxDepth = depth;
    }
    public void visit(DirectoryNode d) {
        dirs++;
        depth++;
        if (depth > maxDepth)
            maxDepth = depth;
        for (Element e : d.getChildren())
            e.accept(this);
        depth--;
    }
}
