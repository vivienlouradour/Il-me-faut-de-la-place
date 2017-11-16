package Core;

import javax.swing.tree.DefaultTreeModel;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Vivien Louradour
 */
public class Api {
    private INode customTree;

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
        return ModelTreeFactory.createTreeModel(this.customTree, filtres);
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
        HashMap<String, ArrayList<File>> hashMap = new HashMap();
        String hash = hash(this.customTree);
        if(hash != null){
            ArrayList<File> listFile = new ArrayList<>();
            listFile.add(this.customTree.getFile());
            hashMap.put(hash, listFile);
        }
        hashChilds(this.customTree, hashMap);

        //On enlève tous les "non-doublons"
        hashMap.entrySet().removeIf(e ->  e.getValue().size() <= 1);
        return hashMap;
    }

    //Méthodes privées
    private void hashChilds(INode node, HashMap<String, ArrayList<File>> hashMap){
        ArrayList<INode> childNodes = node.getChilds();
        if(childNodes == null)
            return;
        for (INode childNode : childNodes) {
            String hash = hash(childNode);
            if(hash != null){
                if(hashMap.containsKey(hash))
                    hashMap.get(hash).add(childNode.getFile());
                else{
                    ArrayList<File> listFile = new ArrayList<>();
                    listFile.add(childNode.getFile());
                    hashMap.put(hash, listFile);
                }
            }
        }
    }

    private String hash(INode node){
        if(node instanceof DirectoryNode)
            return null;
        FileInputStream inputStream = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            inputStream = new FileInputStream(node.getFile());
            FileChannel channel = inputStream.getChannel();
            ByteBuffer buff = ByteBuffer.allocate(2048);
            while (channel.read(buff) != -1){
                buff.flip();
                md.update(buff);
                buff.clear();
            }
            byte[] hashValue = md.digest();
            return new String(hashValue);
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            return null;
        }
        finally {
            try {
                if (inputStream != null)
                    inputStream.close();
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }
}