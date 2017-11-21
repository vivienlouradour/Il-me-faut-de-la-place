package Core;

import javax.swing.tree.DefaultTreeModel;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Vivien Louradour
 */
public class Api {
    private Node customTree;

    /**
     * Initialise l'instance à partir de la racine donnée en paramètre
     * @param racine racine de l'arborescence
     */
    public Api(String racine){
        this.customTree = CustomTreeFactory.create(racine);
    }

    /**
     * Retourne un treeModel de l'arborescence inchangée
     * @return
     */
    public DefaultTreeModel getModelTree(){
        return ModelTreeFactory.createTreeModel(this.customTree);
    }

    /**
     * Retourne un treeModel de l'arborescence filtré
     * @param filtres liste de filtres à appliquer
     * @return
     */
    public DefaultTreeModel getModelTree(ArrayList<FileFilter> filtres) {
        return ModelTreeFactory.createTreeModelWithFilters(this.customTree, filtres);
    }

    /**
     * Retourne un treeModel de l'arborescence filtré
     * @param filtre filtre à appliquer
     * @return
     */
    public DefaultTreeModel getModelTree(FileFilter filtre){
        ArrayList<FileFilter> filtres = new ArrayList<>();
        filtres.add(filtre);
        return  getModelTree(filtres);
    }

    public HashMap<String, ArrayList<File>> getDoublons(){
        HashManager hashManager = null;
        try {
            hashManager = new HashManager();
            HashMap<String, ArrayList<File>> hashMap = new HashMap<String, ArrayList<File>>();
            String hash = hash(this.customTree, hashManager);
            if(hash != null){
                ArrayList<File> listFile = new ArrayList<>();
                listFile.add(this.customTree.getFile());
                hashMap.put(hash, listFile);
            }
            hashChilds(this.customTree, hashMap, hashManager);

            //On enlève tous les "non-doublons"
            hashMap.entrySet().removeIf(e ->  e.getValue().size() <= 1);
            return hashMap;
        }
        catch (Exception ex){
            ex.printStackTrace(System.err);
            return null;
        }
        finally {
            if(hashManager != null)
                hashManager.dispose();
        }
    }


    /**
     * Nettoie le cache pour libérer de l'espace
     * Lit le cache et enlève tous les fichiers qui ne sont pas trouvés sur la machine
     * @return nombre de fichiers enlevés du cache
     * @throws FileNotFoundException
     */
    public int cleanCache(){
        //TODO meilleur gestion des exceptions
        CacheManager cacheManager = null;
        cacheManager = new CacheManager();
        cacheManager.dispose();
        return cacheManager.cleanCache();

    }

    //Méthodes privées
    private void hashChilds(Node node, HashMap<String, ArrayList<File>> hashMap, HashManager hashManager){
        ArrayList<Node> childNodes = node.getChilds();
        if(childNodes == null)
            return;
        for (Node childNode : childNodes) {
            String hash = hash(childNode, hashManager);
            if(hash != null){
                if(hashMap.containsKey(hash))
                    hashMap.get(hash).add(childNode.getFile());
                else{
                    ArrayList<File> listFile = new ArrayList<>();
                    listFile.add(childNode.getFile());
                    hashMap.put(hash, listFile);
                }
            }
            hashChilds(childNode ,hashMap, hashManager);
        }
    }

    private String hash(Node node, HashManager hashManager){
        if(node instanceof DirectoryNode)
            return null;
        return hashManager.getHash(node.getFile());
    }
}