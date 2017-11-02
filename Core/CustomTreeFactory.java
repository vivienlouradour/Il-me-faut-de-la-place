package Core;

import java.io.File;

class CustomTreeFactory {

    protected static INode create(String racine){
        INode rootNode;
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
    private static void createChilds(File file, INode node){
        File[] files = file.listFiles();
        if(files == null)
            return;
        for (File subFile: files) {
            INode subNode = createNode(subFile);
            ((DirectoryNode)node).addChild(subNode);
            createChilds(subFile, subNode);
        }

    }

    private static INode createNode(File file){
        if(file.isFile())
            return new FileNode(file);
        else
            return new DirectoryNode(file);
    }

}
