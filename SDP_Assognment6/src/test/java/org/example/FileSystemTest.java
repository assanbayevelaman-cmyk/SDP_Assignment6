package org.example;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class FileSystemTest {
    @Test
    void t1_test() {
        class Counter implements Visitor {
            int f, d;
            public void visit(FileNode n) {
                f++;
            }
            public void visit(DirectoryNode n) {
                d++;
                n.getChildren().forEach(c -> c.accept(this));
            }
            public void reset() {
                f = d = 0;
            }
        }
        DirectoryNode root = new DirectoryNode("root");
        root.add(new DirectoryNode("docs"));
        root.add(new FileNode("a.txt", 1));
        Counter c = new Counter();
        root.accept(c);
        assertEquals(1, c.f);
        assertEquals(2, c.d);
    }
    @Test
    void t2_test() {
        DirectoryNode root = new DirectoryNode("root");
        DirectoryNode mid = new DirectoryNode("mid");
        DirectoryNode deep = new DirectoryNode("deep");
        root.add(new FileNode("a", 300));
        mid.add(new FileNode("b", 500));
        deep.add(new FileNode("c", 1000));
        mid.add(deep);
        root.add(mid);
        TotalSizeVisitor v = new TotalSizeVisitor();
        root.accept(v);
        assertEquals(1800L, v.getTotal());
    }
    @Test
    void t3_test() {
        DirectoryNode root = new DirectoryNode("root");
        root.add(new FileNode("big.java", 500));
        root.add(new FileNode("small.java", 100));
        root.add(new FileNode("big.txt", 500));
        SearchVisitor v = new SearchVisitor(n -> n.endsWith(".java"), 500);
        root.accept(v);
        assertEquals(List.of("root/big.java"), v.getResults());
    }
    @Test
    void t4_test() {
        DirectoryNode root = new DirectoryNode("root");
        DirectoryNode a = new DirectoryNode("a");
        root.add(new FileNode("z.txt", 1));
        a.add(new FileNode("m.txt", 1));
        root.add(a);
        SearchVisitor v = new SearchVisitor(n -> true, 0);
        root.accept(v);
        assertEquals(List.of("root/a/m.txt", "root/z.txt"), v.getResults());
    }
    @Test
    void t5_test() {
        DirectoryNode root = new DirectoryNode("root");
        DirectoryNode a = new DirectoryNode("a");
        a.add(new FileNode("f", 1));
        root.add(a);
        root.add(new FileNode("g", 1));
        StatisticsVisitor v = new StatisticsVisitor();
        root.accept(v);
        assertEquals(2, v.getFiles());
        assertEquals(2, v.getDirs());
        assertEquals(1, v.getMaxDepth());
    }
    @Test
    void t6_test() {
        DirectoryNode empty = new DirectoryNode("e");
        StatisticsVisitor s = new StatisticsVisitor();
        empty.accept(s);
        assertEquals(0, s.getFiles());
        assertEquals(1, s.getDirs());
        assertEquals(0, s.getMaxDepth());
        SearchVisitor q = new SearchVisitor(n -> false, 0);
        empty.accept(q);
        assertTrue(q.getResults().isEmpty());
    }
    @Test
    void t7_test() {
        DirectoryNode root = new DirectoryNode("root");
        root.add(new FileNode("a", 10));
        TotalSizeVisitor v = new TotalSizeVisitor();
        root.accept(v);
        long first = v.getTotal();
        v.reset();
        root.accept(v);
        assertEquals(first, v.getTotal());
    }
    @Test
    void t8_test() {
        assertThrows(IllegalArgumentException.class, () ->  new FileNode("x", -1));
        DirectoryNode d = new DirectoryNode("d");
        d.add(new FileNode("a", 1));
        assertThrows(IllegalStateException.class, () -> d.add(new FileNode("a", 2)));
        DirectoryNode p1 = new DirectoryNode("pt1");
        DirectoryNode p2 = new DirectoryNode("p2");
        DirectoryNode c = new DirectoryNode("c");
        p1.add(c);
        assertThrows(IllegalStateException.class, () -> p2.add(c));
        DirectoryNode a = new DirectoryNode("a");
        DirectoryNode b = new DirectoryNode("b");
        a.add(b);
        assertThrows(IllegalStateException.class, () -> b.add(a));
    }
}
