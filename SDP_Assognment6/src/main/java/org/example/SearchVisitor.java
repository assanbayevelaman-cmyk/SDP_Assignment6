package org.example;
import java.util.*;
import java.util.function.Predicate;

public class SearchVisitor implements Visitor {
    private final Predicate<String> nameOk;
    private final long minSize;
    private final List<String> results = new ArrayList<>();
    private final Deque<String> path = new ArrayDeque<>();
    public SearchVisitor(Predicate<String> nameOk, long minSize) {
        this.nameOk = nameOk;
        this.minSize = minSize;
    }
    public void reset() {
        results.clear();
        path.clear();
    }
    public List<String> getResults() {
        List<String> copy = new ArrayList<>(results);
        Collections.sort(copy);
        return copy;
    }
    public void visit(FileNode f) {
        if (nameOk.test(f.getName()) && f.getSize() >= minSize) {
            String p = path.isEmpty() ? f.getName() : String.join("/", path) + "/" + f.getName();
            results.add(p);
        }
    }
    public void visit(DirectoryNode d) {
        path.addLast(d.getName());
        for (Element e : d.getChildren())
            e.accept(this);
        path.removeLast();
    }
}
