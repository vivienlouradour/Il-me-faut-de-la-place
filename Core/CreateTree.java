package Core;

import java.io.File;

class CreateTree implements Runnable{

    private CustomTreeNode root;
    //private DefaultTreeModel treeModel;  si on préfere retourner un treeModel au lieu de DefaultMutableTreeModel : à voir
    private File fileRoot;

    public CreateTree(File fileRoot, CustomTreeNode root) {
        this.fileRoot = fileRoot;
        this.root = root;
    }

    @Override
    public void run() {
        createChildren(fileRoot, root);
    }

    private void createChildren(File fileRoot, CustomTreeNode node) {
        File[] files = fileRoot.listFiles();
        if (files == null) return;

        for (File file : files) {
            CustomTreeNode childNode =
                    new CustomTreeNode(file);
            node.add(childNode);
            if (file.isDirectory()) {
                createChildren(file, childNode);
            }
        }
    }
}
