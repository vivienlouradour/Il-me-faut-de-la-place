package Core;

import com.sun.media.jfxmediaimpl.MediaDisposer;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Classe qui s'occupe de la gestion du cache
 * Format de cache : sérialisation d'une liste d'objets
 */
class CacheManager implements MediaDisposer.Disposable{
    private final String cacheFileName = "hash_cache.ser";
    private File cacheFile;
    /**
     * Contenu du cache (initialisé dans le constructeur et vide si cache absent ou corrompu)
     */
    private ArrayList<SerializedFile> serializedFiles;

    /**
     * Constructeur vide
     * Désérialise le cache s'il existe
     */
    protected CacheManager(){
        String applicationDirectoryPath = ApplicationDirectoryUtilities.getProgramDirectory();
        this.cacheFile = new File(applicationDirectoryPath + File.separator + cacheFileName);

        //Si le fichier de cache est trouvé, on le parse
        if(cacheFile.exists()){
            try {
                FileInputStream in = new FileInputStream(this.cacheFile);
                ObjectInputStream oos = new ObjectInputStream(in);
                this.serializedFiles = (ArrayList<SerializedFile>) oos.readObject();
            }
            catch (Exception e){
                this.serializedFiles = new ArrayList<>();
            }
        }
        //Sinon  on le créé
        else{
            this.serializedFiles = new ArrayList<>();
        }

    }

    /**
     * Renvoi le hash du fichier passé en paramètre
     * Si le fichier est présent dans le cache et que le hash est à jour : lecture du hash dans le cache
     * Sinon : génération d'un nouveau hash + insert ou update dans le cache
     * @param file fichier à hasher
     * @return Hash MD5 du fichier, null si erreur de lecture
     */
    protected String getHashAndUpdateCache(File file){
        String hash;
        String absolutePathToSearch = file.getAbsolutePath();
        Date currentFileDate = new Date(file.lastModified());
        //Recherche du hash dans le cache
        try {
            //Lance une NoSuchElementException si lrien n'est trouvé dans le cache
            SerializedFile cachedFile = this.serializedFiles.stream()
                    .filter(listFile -> listFile.absolutePath.equals(absolutePathToSearch))
                    .findFirst()
                    .get();
            if(cachedFile.lastModification.equals(currentFileDate))
                hash = cachedFile.hash;
            else {
                hash = HashManager.hashFile(file);
                cachedFile.hash = hash;
            }
        }
        //Génération d'un nouveau hash et insertion dans le cache
        catch (NoSuchElementException e){
            hash = HashManager.hashFile(file);
            //hash == null en cas d'erreur de lecture du fichier
            if(hash != null)
                this.serializedFiles.add(new SerializedFile(absolutePathToSearch, hash, new Date(file.lastModified())));
        }
        return hash;
    }

    /**
     * Nettoie le cache pour libérer de l'espace
     * Lit le cache et enlève tous les fichiers qui ne sont pas trouvés sur la machine
     * @return faux si aucun fichier n'a été nettoyé (cache n'existe pas ou aucun fichier à nettoyer)
     */
    protected boolean cleanCache(){
        int nbFilesClear = 0;
        Iterator<SerializedFile> iterator = this.serializedFiles.iterator();
        boolean hasRemoved = this.serializedFiles.removeIf(serializedFile -> new File(serializedFile.absolutePath).exists());
        return hasRemoved;
    }

    /**
     * Enregistre les changements dans le cache
     */
    @Override
    public void dispose() {
        //Enregistrement des changements
        try {
            FileOutputStream out = new FileOutputStream(this.cacheFile);
            ObjectOutputStream oos = new ObjectOutputStream(out);
            oos.writeObject(this.serializedFiles);
            oos.flush();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }


}

/**
 * Cette classe est utilisée pour représenter un fichier dans le cache
 */
class SerializedFile implements Serializable{
    String absolutePath;
    String hash;
    Date lastModification;

    SerializedFile(String absolutePath, String hash, Date lastModification){
        this.absolutePath = absolutePath;
        this.hash = hash;
        this.lastModification = lastModification;
    }
}
