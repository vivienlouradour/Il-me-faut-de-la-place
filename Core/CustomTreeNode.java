package Core;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.io.File;
import java.io.FileFilter;
import java.util.*;

public class CustomTreeNode extends DefaultMutableTreeNode {

    public CustomTreeNode(File fileNode){
        super(fileNode);
    }

    public File getCurrentFile(){
        return (File)this.getUserObject();
    }

    /**
     * Renvois l'arborescence sous forme d'ArrayList
     * @return
     */
    public ArrayList<CustomTreeNode> getChilds(){
        ArrayList<CustomTreeNode> arrayList = new ArrayList<>();
        Enumeration<CustomTreeNode> enumeration = this.children();
        while(enumeration.hasMoreElements()){
            arrayList.add(enumeration.nextElement());
        }
        return arrayList;
    }

    private ArrayList<File> toFileArrayList() {
        ArrayList<File> arrayList = new ArrayList<>();
        arrayList.add(this.getCurrentFile());
        for (CustomTreeNode child : this.getChilds()) {
            arrayList.addAll(child.toFileArrayList());
        }
        return arrayList;
    }


    public DefaultTreeModel modelTree() {
        return null;
    }

    /**
     * Taille totale de l'arborescence de fichiers
     * @return
     */
    public long length(){
        long size = 0;
        for (CustomTreeNode child : this.getChilds()) {
            size += child.length();
        }
        long currentSize = this.getCurrentFile().isDirectory() ? 0 : this.getCurrentFile().length();
        return currentSize + size;
    }
}
