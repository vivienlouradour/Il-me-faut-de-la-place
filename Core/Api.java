package Core;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.util.*;

/**
 * Point d'entré.
 * Permet de retourner un arbre représentant l'arborescence des fichiers à partir d'un répertoire
 */
public class Api {
    /**
     * Renvoi l'arborescence à partir d'un répertoire racine
     * @param racine répertoire de départ de l'arborescence
     * @return
     * @throws IllegalArgumentException
     */
    public CustomTreeNode getTree(String racine) throws IllegalArgumentException{
        //Le thread ne sert à rien pour l'instant
        File fileRoot = new File(racine);
        //Si le répertoire indiqué en argument n'existe pas, lance une exception
        if(!fileRoot.exists())
            throw new IllegalArgumentException("Le fichier spécifié n'existe pas");

        //Point de départ de l'arbre
        CustomTreeNode root = new CustomTreeNode(fileRoot);

        //Déclaration du thread chargé de construire l'arbre
        //En l'état, le thread est inutile. A voir par la suite faire autant de thread que le nombre de cores du CPU
        //int nbCpuCores = Runtime.getRuntime().availableProcessors();
        CreateTree createTree =
                new CreateTree(fileRoot, root);
        Thread thread = new Thread(createTree);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return root;
    }

    public CustomTreeNode filterTree(CustomTreeNode originalTree, String filterPatern){
        FileFiltering fileFiltering = new FileFiltering(filterPatern);
        CustomTreeNode newTree = (CustomTreeNode)originalTree.clone();
        if(originalTree.getCurrentFile().isFile()) {
            if(fileFiltering.isValid(originalTree.getCurrentFile()))
                return newTree;
            else
                return null;
        }
        else{
            Enumeration en = newTree.depthFirstEnumeration();
            while (en.hasMoreElements()) {
                CustomTreeNode node = (CustomTreeNode)en.nextElement();
                CustomTreeNode q = this.filterTree(node, filterPatern);
                if(q != null)
                    newTree.add(q);
            }
        }



        return newTree;
    }


    /**
     * Retourne une map (clé,valeurs).
     * La clé correspond au hash du fichier, la valeur correspond à une liste des fichiers identiques (ayant le même hash)
     * @param tree racine de l'arbre à analyser
     * @return HashMap des doublons
     */
    public HashMap<String, List<File>> getDoublons(CustomTreeNode tree){
        HashMap<String, List<File>> hashMap = new HashMap();

        Enumeration en = tree.depthFirstEnumeration();
        while (en.hasMoreElements()) {
            CustomTreeNode node = (CustomTreeNode)en.nextElement();
            File fileNode = node.getCurrentFile();
            String hash = hash(fileNode);
            if(hash == null)
                continue;
            if(!hashMap.containsKey(hash)) {
                ArrayList<File> array = new ArrayList<>();
                array.add(fileNode);
                hashMap.put(hash, array);
            }
            else
                hashMap.get(hash).add(fileNode);

        }
        return hashMap;
    }

    private String hash(File file){
        if(file.isDirectory())
            return null;
        FileInputStream inputStream = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            inputStream = new FileInputStream(file);
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
