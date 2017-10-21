package core;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.File;

public class Api {

    private DefaultMutableTreeNode tree;

    public CustomTreeNode getTree(String racine) throws IllegalArgumentException{
        //Le thread ne sert à rien pour l'instant
        File fileRoot = new File(racine);
        if(!fileRoot.exists())
            throw new IllegalArgumentException("Le fichier spécifié n'existe pas");
        CustomTreeNode root = new CustomTreeNode(new FileNode(fileRoot));
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
        return null;
    }
}
