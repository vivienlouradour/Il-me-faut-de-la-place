package core;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.io.FileFilter;
import java.util.*;
import java.util.stream.Collectors;

public class CustomTreeNode extends DefaultMutableTreeNode {

    public CustomTreeNode(FileNode fileNode){
        super(fileNode);
    }

    public FileNode getCurrentFileNode(){
        return (FileNode)this.getUserObject();
    }

    public ArrayList<CustomTreeNode> getChilds(){
        ArrayList<CustomTreeNode> arrayList = new ArrayList<>();
        Enumeration<CustomTreeNode> enumeration = this.children();
        while(enumeration.hasMoreElements()){
            arrayList.add(enumeration.nextElement());
        }
        return arrayList;
    }

    private ArrayList<FileNode> toFileNodeArrayList() {
        ArrayList<FileNode> arrayList = new ArrayList<>();
        arrayList.add(this.getCurrentFileNode());
        for (CustomTreeNode child : this.getChilds()) {
            arrayList.addAll(child.toFileNodeArrayList());
        }
        return arrayList;
    }


    public Map<String, List<FileNode>> doublons() {
        List<FileNode> listFiles = this.toFileNodeArrayList();

        Map<String, List<FileNode>> doublons =
                listFiles.stream().collect(Collectors.groupingBy(w -> Arrays.toString(w.getHash())));

        //Enleve la clé "null" (= hash des répertoires)
        doublons.remove("null");
        return doublons;
    }


    public DefaultTreeModel modelTree() {
        return null;
    }

    public long length(){
        long size = 0;
        for (CustomTreeNode child : this.getChilds()) {
            size += child.length();
        }
        return this.getCurrentFileNode().length() + size;
    }

    public Node filter(ArrayList<FileFilter> filtres) {
        return null;
    }


}
