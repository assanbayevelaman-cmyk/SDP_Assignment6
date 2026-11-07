package org.example;

public class Demo {
    public static void main(String[] args) {
        DirectoryNode root = new DirectoryNode("root");
        DirectoryNode docs = new DirectoryNode("docs");
        DirectoryNode src = new DirectoryNode("src");
        DirectoryNode util = new DirectoryNode("util");
        docs.add(new FileNode("report.txt", 1200));
        docs.add(new FileNode("notes.md", 300));
        util.add(new FileNode("Helper.java", 800));
        src.add(new FileNode("Main.java", 5000));
        src.add(util);
        root.add(docs);
        root.add(src);
        root.add(new DirectoryNode("empty"));
        root.add(new FileNode("README.md", 150));
        TotalSizeVisitor size = new TotalSizeVisitor();
        root.accept(size);
        System.out.println("Total size: " + size.getTotal());
        SearchVisitor search = new SearchVisitor(n -> n.endsWith(".java"), 800);
        root.accept(search);
        System.out.println("Search (.java, >=800): " + search.getResults());
        StatisticsVisitor stats = new StatisticsVisitor();
        root.accept(stats);
        System.out.println("Files=" + stats.getFiles() + " Dirs=" + stats.getDirs() + " MaxDepth=" + stats.getMaxDepth());
    }
}
