package core;

import javax.swing.tree.DefaultTreeModel;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

public interface Node {
    public ArrayList<File> doublons();
    public DefaultTreeModel modelTree();
    public String fileName();
    public byte[] getHash();
    public long length();
    public String absolutePath();
    public ArrayList<Node> getChilds();
    public Node filter(ArrayList<FileFilter> filtres);
}
