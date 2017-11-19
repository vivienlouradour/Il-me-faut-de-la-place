package Core;

import com.sun.media.jfxmediaimpl.MediaDisposer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Classe qui s'occupe de la gestion du cache
 * Format de cache : XML
 */
class CacheManagerV2 implements MediaDisposer.Disposable{
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    private final String cacheFileName = "cache_v3.ser";
    private File cacheFile;
    private Document xDocument;
    private ArrayList<SerializedFile> serializedFiles;

    protected CacheManagerV2() throws IOException, ParserConfigurationException, TransformerException {
        String applicationDirectoryPath = ApplicationDirectoryUtilities.getProgramDirectory();
        this.cacheFile = new File(applicationDirectoryPath + File.separator + cacheFileName);

        //Si le fichier de cache est trouvé, on le parse
        if(cacheFile.exists()){
            try {
                FileInputStream in = new FileInputStream(this.cacheFile);
                ObjectInputStream oos = new ObjectInputStream(in);
                this.serializedFiles = (ArrayList<SerializedFile>)oos.readObject();
            }
            catch (Exception ex){
                ex.printStackTrace(System.err);
            }
        }
        //Sinon  on le créé
        else{
            this.serializedFiles = new ArrayList<>();
        }

    }

    /**
     * Recherche dans le cache le hash à jour d'un fichier
     * @param file fichier à rechercher
     * @return Hash MD5 du fichier, null si le fichier n'est pas trouvé dans le cache ou qu'il a été modifié depuis la derniere mise en cache
     */
    protected String getHashAndUpdateCache(File file){
        String hash;
        String absolutePathToSearch = file.getAbsolutePath();
        Date fileDate = new Date(file.lastModified());
        try {
            SerializedFile fileFound = this.serializedFiles.stream()
                    .filter(listFile -> listFile.absolutePath.equals(absolutePathToSearch))
                    .findFirst()
                    .get();
            if(fileFound.lastModification.equals(fileDate))
                hash = fileFound.hash;
            else {
                hash = HashManager.hashFile(file);
                fileFound.hash = hash;
            }
        }
        catch (NoSuchElementException e){
            hash = HashManager.hashFile(file);
            if(hash != null)
                this.serializedFiles.add(new SerializedFile(absolutePathToSearch, hash, new Date(file.lastModified())));
        }
        return hash;
    }




    /**
     * Retourne la date de derniere modification d'un fichier sous le format yyyyMMddHHmmss
     * @return
     */
    private String getLastModifiedFormat(File file){
        return simpleDateFormat.format(file.lastModified());
    }

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
class SerializedFile implements Serializable{
    public String absolutePath;
    public String hash;
    public Date lastModification;

    SerializedFile(String absolutePath, String hash, Date lastModification){
        this.absolutePath = absolutePath;
        this.hash = hash;
        this.lastModification = lastModification;
    }
}
