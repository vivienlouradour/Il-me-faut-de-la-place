package Core;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.io.FileFilter;
import java.util.ArrayList;

class ModelTreeFactory {

    protected static DefaultTreeModel createTreeModelWithFilters(INode rootNode){
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(rootNode.getFile());

        createModelTreeChild(rootNode, root);

        return new DefaultTreeModel(root);
    }

    protected static DefaultTreeModel createTreeModelWithFilters(INode rootNode, ArrayList<FileFilter> filtres){
        DefaultMutableTreeNode root = null;

        if(acceptNode(rootNode, filtres)){
            root = new DefaultMutableTreeNode(rootNode.getFile());
            createModelTreeChild(rootNode, root, filtres);
        }
        return new DefaultTreeModel(root);
    }






    //Méthodes privées
    private static void createModelTreeChild(INode nodeRoot, DefaultMutableTreeNode treeNode){
        ArrayList<INode> childs = nodeRoot.getChilds();
        if(childs == null)
            return;

        for (INode child : childs) {
            DefaultMutableTreeNode subNode = new DefaultMutableTreeNode(child.getFile());
            treeNode.add(subNode);
            createModelTreeChild(child, subNode);
        }
    }

    private static void createModelTreeChild(INode nodeRoot, DefaultMutableTreeNode treeNode, ArrayList<FileFilter> filtres){
        ArrayList<INode> childs = nodeRoot.getChilds();
        if(childs == null)
            return;

        for (INode child : childs) {
            if(acceptNode(child, filtres)){
                DefaultMutableTreeNode subNode = new DefaultMutableTreeNode(child.getFile());
                treeNode.add(subNode);
                createModelTreeChild(child, subNode, filtres);
            }
        }
    }

    private static boolean acceptNode(INode node, ArrayList<FileFilter> filtres){
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
