package Core;

import javax.swing.tree.DefaultTreeModel;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

public class DirectoryNode extends Node{
    private ArrayList<INode> childs;

    @Override
    public ArrayList<File> doublons() {
        return null;
    }

    @Override
    public DefaultTreeModel modelTree() {
        return null;
    }

    @Override
    public Byte[] hash() {
        return new Byte[0];
    }

    @Override
    public ArrayList<INode> childs() {
        return this.childs;
    }

    @Override
    public INode filter(ArrayList<FileFilter> filtres) {
        return null;
    }
}
