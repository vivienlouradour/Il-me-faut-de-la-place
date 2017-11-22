package IHM.Test;

import Core.Api;
import Core.DirectoryNode;
import Core.Node;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.HashMap;

public class Test extends JFrame {
    private static String path = "D:\\test";

    public static void main(String[] args){
        mainTest();
    }

    public static void mainTest(){
        JFrame frame = new JFrame("Il me faut de la place - TEST");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Création de l'arbre perso
        long debut = System.currentTimeMillis();
        Api api = new Api(path);
        System.out.println("******************************************************");
        System.out.println("Temps de construction de l'arbre perso : " + (System.currentTimeMillis() - debut));
        System.out.println("******************************************************");

        //Récupération des doublons
        debut = System.currentTimeMillis();
        HashMap<String, ArrayList<File>> doublons = api.getDoublons();
        System.out.println("Temps de récupération des doublons : " + (System.currentTimeMillis() - debut));
        System.out.println("******************************************************");

        //Deuxieme récupération des doublons (pour tester l'efficacité du cache)
        debut = System.currentTimeMillis();
        doublons = api.getDoublons();
        System.out.println("Temps de récupération (2e fois) des doublons : " + (System.currentTimeMillis() - debut));
        System.out.println("******************************************************");

        //Création du DefaultTreeModel sans filtres
        debut = System.currentTimeMillis();
        DefaultTreeModel treeModel = api.getModelTree();
        System.out.println("Temps de construction du treemodel sans filtres: " + (System.currentTimeMillis() - debut));
        System.out.println("******************************************************");

        //Création du DefaultTreeModel avec filtre
        debut = System.currentTimeMillis();
        FileFilter filtre = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return !pathname.getName().endsWith(".txt");
            }
        };
        DefaultTreeModel treeModelFiltered = api.getModelTree(filtre);
        System.out.println("Temps de construction du treemodel avec filtres: " + (System.currentTimeMillis() - debut));
        System.out.println("******************************************************");

        //Nettoyage du cache (utile si le fichier cache devient trop volumineux)
        api.cleanCache();

        //Construction de l'IHM de test
        JTree tree = new JTree(treeModelFiltered);
        tree.setShowsRootHandles(true);
        tree.setCellRenderer(new MyTreeCellRenderer());
        JScrollPane scrollPane = new JScrollPane(tree);

        frame.add(scrollPane);
        frame.setLocationByPlatform(true);
        frame.setSize(640, 480);
        frame.setVisible(true);
    }

    private static class MyTreeCellRenderer extends DefaultTreeCellRenderer {

        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

            // decide what icons you want by examining the node
            if (value instanceof DefaultMutableTreeNode) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
                if(node.getUserObject() instanceof DirectoryNode){
                    setIcon(UIManager.getIcon("FileView.directoryIcon"));
                }
                else{
                    setIcon(UIManager.getIcon("FileView.fileIcon"));
                }
            }
            return this;
        }

    }
}



