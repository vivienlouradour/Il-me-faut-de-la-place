package Core;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.io.FileFilter;
import java.util.ArrayList;

class ModelTreeFactory {

    protected static DefaultTreeModel createTreeModel(Node rootNode){
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(rootNode);

        createModelTreeChild(rootNode, root);

        return new DefaultTreeModel(root);
    }

    protected static DefaultTreeModel createTreeModelWithFilters(Node rootNode, ArrayList<FileFilter> filtres){
        DefaultMutableTreeNode root = null;

        if(acceptNode(rootNode, filtres)){
            root = new DefaultMutableTreeNode(rootNode);
            createModelTreeChild(rootNode, root, filtres);
        }
        return new DefaultTreeModel(root);
    }






    //Méthodes privées
    private static void createModelTreeChild(Node nodeRoot, DefaultMutableTreeNode treeNode){
        ArrayList<Node> childs = nodeRoot.getChilds();
        if(childs == null)
            return;

        for (Node child : childs) {
            DefaultMutableTreeNode subNode = new DefaultMutableTreeNode(child);
            treeNode.add(subNode);
            createModelTreeChild(child, subNode);
        }
    }

    private static void createModelTreeChild(Node nodeRoot, DefaultMutableTreeNode treeNode, ArrayList<FileFilter> filtres){
        ArrayList<Node> childs = nodeRoot.getChilds();
        if(childs == null)
            return;

        for (Node child : childs) {
            if(acceptNode(child, filtres)){
                DefaultMutableTreeNode subNode = new DefaultMutableTreeNode(child);
                treeNode.add(subNode);
                createModelTreeChild(child, subNode, filtres);
            }
        }
    }

    private static boolean acceptNode(Node node, ArrayList<FileFilter> filtres){
        //On accepte tous les répertoire
        if(node instanceof DirectoryNode)
            return true;

        boolean accept = true;
        int i = 0;
        while (accept && i < filtres.size()) {
            accept = filtres.get(i).accept(node.getFile());
            i++;
        }
        return accept;
    }

}
