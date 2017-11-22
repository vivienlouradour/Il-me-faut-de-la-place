package Core;

import java.io.File;

/**
 * Classe permetant de créer l'arborescence de fichier (Node) à partir d'un chemin
 */
class CustomTreeFactory {
    protected static Node create(String racine){
        Node rootNode;
        File fileRoot = new File(racine);
        //Si le répertoire indiqué en argument n'existe pas, lance une exception
        if(!fileRoot.exists())
            throw new IllegalArgumentException("Le fichier spécifié n'existe pas");
        //Si la racine est un fichier, on se contente de créer le FileNode.
        if(fileRoot.isFile())
            rootNode = new FileNode(fileRoot);
        else {
            rootNode = new DirectoryNode(fileRoot);
            createChilds(fileRoot, (DirectoryNode)rootNode);
        }
        return rootNode;
    }

    /**
     * Fonction récursive qui créé les enfants des noeuds
     * @param file
     * @param node
     */
    private static void createChilds(File file, Node node){
        File[] files = file.listFiles();
        if(files == null)
            return;
        for (File subFile: files) {
            Node subNode = createNode(subFile);
            subNode.setTotalLength(subFile.length());
            createChilds(subFile, subNode);
            node.setTotalLength(node.getTotalLength() + subNode.getTotalLength());
            ((DirectoryNode)node).addChild(subNode);
        }

    }

    private static Node createNode(File file){
        if(file.isFile())
            return new FileNode(file);
        else
            return new DirectoryNode(file);
    }
}
