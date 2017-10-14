package Core;

import javax.swing.tree.DefaultTreeModel;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

public class DirectoryNode extends Node {
    private ArrayList<INode> childs;

    protected DirectoryNode(File file){
        super(file);
        childs = new ArrayList<INode>();
        for (File fileToAdd : file.listFiles()) {
            this.addChild(fileToAdd);
        }
    }

    protected void addChild(File file){
        if(file.isDirectory())
            this.childs.add(new DirectoryNode(file));
        else
            this.childs.add(new FileNode(file));
    }

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
