package org.example;
import java.util.*;

public class DirectoryNode implements Element {
    private final String name;
    private final List<Element> children = new ArrayList<>();
    private DirectoryNode parent;
    public DirectoryNode(String name) {
        this.name = name;
    }
    public void accept(Visitor v) {
        v.visit(this);
    }
    public String getName() {
        return name;
    }
    public List<Element> getChildren() {
        return Collections.unmodifiableList(children);
    }
    public void add(Element e) {
        for (Element c : children)
            if (c.getName().equals(e.getName()))
                throw new IllegalStateException("duplicate sibling: " + e.getName());
        if (e instanceof DirectoryNode d) {
            if (this.isAncestor(d))
                throw new IllegalStateException("cycle");
            if (d.parent != null)
                throw new IllegalStateException("already has parent");
            d.parent = this;
        }
        children.add(e);
    }
    private boolean isAncestor(DirectoryNode d) {
        for (DirectoryNode cur = this; cur != null; cur = cur.parent)
            if (cur == d)
                return true;
        return false;
    }
}
