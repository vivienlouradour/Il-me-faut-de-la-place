package Core;

import javax.swing.tree.DefaultTreeModel;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

public interface INode {
    public ArrayList<File> doublons();
    public DefaultTreeModel modelTree();
    public String fileName();
    public Byte[] hash();
    public long size();
    public String absolutePath();
    public ArrayList<INode> childs();
    public INode filter(ArrayList<FileFilter> filtres);
}
