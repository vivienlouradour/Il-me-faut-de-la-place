package IHM;

import core.CustomTreeNode;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import java.util.ArrayList;
import java.util.Map;

public class Test extends JFrame{

    public static void main(String[] args){
        core.Api api = new core.Api();
        JFrame frame = new JFrame("File Browser");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        CustomTreeNode root = api.getTree("D:\\test");
        ArrayList<CustomTreeNode> childs =  root.getChilds();
        Map map = root.doublons();
        System.out.println(root.getCurrentFileNode().length());
        System.out.println(root.getCurrentFileNode().absolutePath());
        System.out.println(root.length());
        System.out.println("test");
        for (CustomTreeNode child : childs) {
            System.out.println(child.getCurrentFileNode().getHash() + " ; file : " + child.getCurrentFileNode().absolutePath());
        }
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
