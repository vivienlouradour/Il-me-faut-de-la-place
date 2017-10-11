package Core;

import java.io.File;

public class FabriqueNode {
    public INode tree(String racine) throws IllegalArgumentException{
        return this.tree(racine, 0);
    }

    public INode tree(String racine, int profondeur){
        File file = new File(racine);
        if(!file.exists())
            throw new IllegalArgumentException("Le chemin spécifié n'existe pas");
    }
}
