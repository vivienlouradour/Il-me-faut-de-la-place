package IHM;

import Core.CustomTreeNode;
import Core.FileNode;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test extends JFrame{

    public static void main(String[] args){
        Core.Api api = new Core.Api();
        JFrame frame = new JFrame("File Browser");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Construit l'arbre
        long debut = System.currentTimeMillis();
        CustomTreeNode root = api.getTree("D:\\test");
        System.out.println("Temps de construction : " + (System.currentTimeMillis() - debut));
        System.out.println("Taille totale : " + root.length());

        //Récupère les doublons
        debut = System.currentTimeMillis();
        Map doublons = api.getDoublons(root);
        System.out.println("Temps de construction : " + (System.currentTimeMillis() - debut));
        System.out.println("Racine : " + root.getCurrentFileNode().absolutePath());
        System.out.println(root.length());


        //Construction de l'IHM de test
        DefaultTreeModel treeModel = new DefaultTreeModel(root);
        JTree tree = new JTree(treeModel);
        tree.setShowsRootHandles(true);
        JScrollPane scrollPane = new JScrollPane(tree);

        frame.add(scrollPane);
        frame.setLocationByPlatform(true);
        frame.setSize(640, 480);
        frame.setVisible(true);
    }


}
