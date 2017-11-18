package Core;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.util.Locale;

/**
 * Cette classe s'occupe de la génération des hash de fichiers et dy système de mise en cache
 */
class HashManager{


    private CacheManager cacheManager;

    /**
     * Constructeur vide
     */
    protected HashManager() throws IOException, ParserConfigurationException, TransformerException {
        this.cacheManager = new CacheManager();
    }


    /**
     * Retourne le hash du fichier passé en paramètre
     * Récupère la valeur dans le cache si elle existe et que le fichier n'a pas été modifié, sinon calcule le hash + mise à jour du fichier cache
     * @param file fichier à hasher
     * @return le Hash MD5 du fichier "file"
     */
    protected String getHash(File file){
        //Récupère le hash du fichier dans le cache s'il existe
        String hash = this.cacheManager.getHash(file);

        //Si le hash du fichier n'existe pas dans le cash, on le calcule et on update le cache
        if(hash == null){
            hash = hashFile(file);
            this.cacheManager.setHash(file, hash);
        }
        return hash;
    }

    /**
     * Hash un fichier et renvoit sa valeur (Hash MD5)
     * @param file fichier à hasher
     * @return hash MD5 du fichier
     */
    private String hashFile(File file){
        FileInputStream inputStream = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            inputStream = new FileInputStream(file);
            FileChannel channel = inputStream.getChannel();
            ByteBuffer buff = ByteBuffer.allocate(2048);
            while (channel.read(buff) != -1){
                buff.flip();
                md.update(buff);
                buff.clear();
            }
            byte[] hashValue = md.digest();
            return String.format(Locale.ROOT, "%032x", new BigInteger(1, hashValue));
            //return new String(hashValue, Charset.forName("UTF-8"));
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            return null;
        }
        finally {
            try {
                if (inputStream != null)
                    inputStream.close();
            }
            catch (Exception ex){
                ex.printStackTrace(System.out);
            }
        }
    }



}
