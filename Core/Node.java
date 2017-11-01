package Core;

import javax.swing.tree.DefaultTreeModel;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

public interface Node {
    public String fileName();
    public String getHash();
    public long length();
    public String absolutePath();
    public ArrayList<Node> getChilds();
    public Node filter(ArrayList<FileFilter> filtres);
}
