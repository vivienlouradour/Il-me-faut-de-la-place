package Test;


import Core.Api;
import Core.DirectoryNode;
import Core.Error;
import Core.Node;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class TestCommandLine {
    private static boolean printRootSize = false;
    private static boolean printDoublons = false;
    private static boolean printExecutionTime = false;
    private static boolean printArborescence = false;
    private static boolean printErrors = false;
    private static String regex = null;
    private static String racine = null;

    public static void main(String[] args) {

        if(args.length == 0){
            printHelp();
            return;
        }

        parseArguments(args);

        if(racine == null) {
            printHelp();
            return;
        }

        long debut = System.currentTimeMillis();
        //Création de l'arbre custom
        Api api;
        try {
            api = new Api(racine);
        }
        catch (IllegalArgumentException ex){
            System.out.println("Erreur : " + ex.getMessage());
            printHelp();
            return;
        }

        System.out.println("******** Racine de l'arbre ********");
        System.out.println(racine);
        System.out.println();

        //Récupération des doublons
        if(printDoublons){
            HashMap<String, ArrayList<File>> doublons = api.getDoublons();
            printDoublons(doublons);
        }

        //Récupération de l'arbre (filtré ou non)
        DefaultTreeModel treeModel;
        if(regex != null){
            Pattern pattern = Pattern.compile(regex);
            FileFilter filtre = new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    return pattern.matcher(pathname.getName()).find();
                }
            };
            treeModel = api.getModelTree(filtre);
        }
        else{
            treeModel = api.getModelTree();
        }


        //Affichage de la taille de l'arbre
        if(printRootSize){
            long size = ((Node)((DefaultMutableTreeNode)treeModel.getRoot()).getUserObject()).getTotalLength();
            System.out.println("****** Taille de l'arborescence ******");
            System.out.println(size/1000000.0 + " Mo");
            System.out.println();
        }

        //Affichage de l'arborescence
        if(printArborescence){
            System.out.println("****** Arborescence ******");
            printArborescence(treeModel);
        }

        //Affichage des erreurs
        if(printErrors){
            printErrors(api.getErrorHandler().getErrorsCollection());
        }

        //Affichage du temps d'exécution
        if(printExecutionTime){
            System.out.println("****** Temps d'éxecution *******");
            System.out.println((System.currentTimeMillis() - debut) + " ms");
            System.out.println();
        }

    }

    private static void printHelp(){
        System.out.println("******** HELP ********");
        System.out.println(" * -h ou -help : affiche l'aide");
        System.out.println(" * -r <root_path> : chemin vers le repertoire à analyser");
        System.out.println(" * -f <regex> : applique un filtre à l'arborescence qui valide l'expression régulière regex. (Non valable pour le calcul de doublons)");
        System.out.println(" * -d : affiche les doublons contenus dans l'arborescence de fichiers");
        System.out.println(" * -w : affiche le poids total de l'arborescence");
        System.out.println(" * -p : affiche la structure de l'arborescence");
        System.out.println(" * -t : affiche le temps d'exécution");
        System.out.println(" * -e : affiche les erreurs qui se sont produites au cours de l'exécution");
        System.out.println();
    }

    private static void printDoublons(HashMap<String, ArrayList<File>> doublons){
        System.out.println("**** Liste des doublons ****");
        doublons.forEach((String hash, ArrayList<File> files) -> {
            System.out.println("Ces fichiers sont des doublons :");
            files.forEach((File file) -> {
                System.out.println("  - " + file.getAbsolutePath());
            });
        });
        System.out.println();
    }

    private static void printArborescence(DefaultTreeModel treeModel){
        Enumeration enumeration = ((DefaultMutableTreeNode)treeModel.getRoot()).preorderEnumeration();
        String escape = "\\";
        while (enumeration.hasMoreElements()){
            Node node = (Node)((DefaultMutableTreeNode)enumeration.nextElement()).getUserObject();
            System.out.println(escape + node.getFile().getName() + " : " + node.getTotalLength() / 1000000.0 + "Mo");
            if(node instanceof DirectoryNode)
                escape += "--\\";
        }
        System.out.println();
    }

    private static void printErrors(List<Error> errors){
        System.out.println("****** Liste des erreurs ******");
        errors.forEach(error -> System.out.println(error.toString()));
        System.out.println();
    }

    private static void parseArguments(String[] args){
        for(int i = 0; i < args.length; i++){
            if(args[i].charAt(0) != '-') {
                printHelp();
                return;
            }
            switch (args[i]){
                case "-help" :
                case "-h" :
                    printHelp();
                    return;
                case "-d":
                    printDoublons = true;
                    break;
                case "-w":
                    printRootSize = true;
                    break;
                case "-t":
                    printExecutionTime = true;
                    break;
                case "-p":
                    printArborescence = true;
                    break;
                case "-e":
                    printErrors = true;
                    break;
                case "-r" :
                    try{
                        racine = args[i+1];
                        i++;
                    }
                    catch (ArrayIndexOutOfBoundsException ex){
                        System.out.println("/!\\ argument " + args[i] + " invalide.");
                        printHelp();
                        return;
                    }
                    break;
                case "-f":
                    try{
                        regex = args[i+1];
                        i++;
                    }
                    catch (ArrayIndexOutOfBoundsException ex){
                        System.out.println("/!\\ argument " + args[i] + " invalide.");
                        printHelp();
                        return;
                    }
                    break;
                default:
                    printHelp();
                    return;
            }
        }
    }
}
