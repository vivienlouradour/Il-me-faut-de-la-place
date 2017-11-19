package Core;

import java.io.File;
import java.util.ArrayList;

public abstract class Node{
    private File file;
    private DirectoryNode parent;
    private long totalLength;

    protected Node(File file){
        this.file = file;
    }

    /**
     * Récupère l'instance de java.io.File associé au noeud courrant
     * @return
     */
    public File getFile(){
        return this.file;
    }

    /**
     * Renvoi la taille totale du noeud (taille simple si fichier, taille complète si répertoire)
     * @return taille en octets
     */
    public long getTotalLength(){
        return this.totalLength;
    }
    protected DirectoryNode getParent() {
        return parent;
    }


    protected void setParent(DirectoryNode parentNode){
        this.parent = parentNode;
    }

    protected void setTotalLength(long totalLength){
        this.totalLength = totalLength;
    }

    abstract protected ArrayList<Node> getChilds();

    @Override
    public String toString(){
        return this.file.getName();
    }
}