package Core;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.io.File;

public class Api {

    public JTree getTree(String pathRacine){
        File racineFile = new File(pathRacine);
        DefaultMutableTreeNode racine = this.listFiles(racineFile);
        return new JTree(racine);
    }

    private DefaultMutableTreeNode listFiles(File file){
        DefaultMutableTreeNode racine = new DefaultMutableTreeNode(file);
        if(file.isFile())
            return racine;
        else{
            File[] files = file.listFiles();
            if(files == null)
                return racine;
            for(File f : files){
                DefaultMutableTreeNode subNode;
                if(f.isDirectory()){
                    subNode = this.listFiles(f);
                    //racine.add(this.listFiles(f));
                }
                else{
                    subNode = new DefaultMutableTreeNode(f);
                }
                racine.add(subNode);
            }
            return racine;
        }
    }
}
