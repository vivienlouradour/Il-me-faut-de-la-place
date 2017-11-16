package Core;

import java.io.File;

/**
 * Cette classe s'occupe de la génération des hash de fichiers et de leurs mis en cache
 */
public class HashManager {
    /**
     * Nom du fichier de cache
     */
    private final String hashHistoryFileName = "";
    private final String applicationDirectoryPath = ApplicationDirectoryUtilities.getProgramDirectory();
    private File hashHistoryFile;

    /**
     * Constructeur vide
     */
    public HashManager(){
        this.hashHistoryFile = new File(applicationDirectoryPath + File.separator + hashHistoryFileName);

        //Si le fichier de cache n'est pas trouvé, on le créé
        if(!this.hashHistoryFile.exists()){
            initCache();
        }
    }

    /**
     * Retourne le hash du fichier passé en paramètre
     * Récupère la valeur dans le cache si elle existe et que le fichier n'a pas été modifié, sinon calcule le hash + mise à jour du fichier cache
     * @param file fichier à hasher
     * @return le Hash MD5 du fichier "file"
     */
    public String getHash(File file){
        //Récupère le hash du fichier dans le cache s'il existe
        String hash = getCacheHash(file);

        //Si le hash du fichier n'existe pas dans le cash, on le calcule et on update le cache
        if(hash == null){
            hash = hashFile(file);
            setCacheHash(file, hash);
        }
        return hash;
    }


    /**
     * Ajoute ou met à jour le hash d'un fichier dans le cache
     * @param file
     * @param hash
     */
    private void setCacheHash(File file, String hash){
        //si le hash est déjà dans le cache : update
        //sinon : insert
    }

    /**
     * Recherche dans le cache le hash à jour d'un fichier
     * @param file fichier à rechercher
     * @return Hash MD5 du fichier, null si le fichier n'est pas trouvé dans le cache ou qu'il a été modifié depuis la derniere mise en cache
     */
    private String getCacheHash(File file){
        //Retourne le hash du fichier s'il est présent dans le cache et que dateModif OK
        return null;
    }

    /**
     * Hash un fichier et renvoit sa valeur (Hash MD5)
     * @param file fichier à hasher
     * @return hash MD5 du fichier
     */
    private String hashFile(File file){
        //TODO
        return null;
    }

    /**
     * Créer et initialise un fichier de cache
     */
    private void initCache(){

    }


}
