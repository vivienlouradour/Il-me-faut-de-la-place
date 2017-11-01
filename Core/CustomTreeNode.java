package Core;

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

    private ArrayList<FileNode> toFileNodeArrayList() {
        ArrayList<FileNode> arrayList = new ArrayList<>();
        arrayList.add(this.getCurrentFileNode());
        for (CustomTreeNode child : this.getChilds()) {
            arrayList.addAll(child.toFileNodeArrayList());
        }
        return arrayList;
    }


    /**
     * Retourne une map (clé,valeurs).
     * La clé correspond au hash du fichier, la valeur correspond à une liste des fichiers identiques (ayant le même hash)
     * @return Map des doublons
     */
    public Map<String, List<FileNode>> doublons() {
        List<FileNode> listFiles = this.toFileNodeArrayList();

        Map<String, List<FileNode>> doublons =
                listFiles.stream().collect(Collectors.groupingBy(file -> file.getHash() == null ? "null" : file.getHash()));

        //Enleve la clé "null" (= hash des répertoires)
        doublons.remove("null");
        return doublons;
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
        return this.getCurrentFileNode().length() + size;
    }

    public Node filter(ArrayList<FileFilter> filtres) {
        return null;
    }


}
