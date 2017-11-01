package Core;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Array;
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
        CustomTreeNode root = new CustomTreeNode(new FileNode(fileRoot));

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
            System.out.println("C'est ici !");
            e.printStackTrace();
        }
        return root;
    }

    public CustomTreeNode filterTree(CustomTreeNode originalTree, String filterPatern){
        return null;
    }

    public Map<String, List<FileNode>> getDoublons(CustomTreeNode tree){
        HashMap<String, List<FileNode>> hashMap = new HashMap<String, List<FileNode>>();

        Enumeration en = tree.depthFirstEnumeration();
        while (en.hasMoreElements()) {
            CustomTreeNode node = (CustomTreeNode)en.nextElement();
            FileNode fileNode = node.getCurrentFileNode();
            String hash = hash(fileNode.getFile());
            if(hash == null)
                continue;
            if(!hashMap.containsKey(hash)) {
                ArrayList<FileNode> array = new ArrayList<>();
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
