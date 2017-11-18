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
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Classe qui s'occupe de la gestion du cache
 * Format de cache : XML
 */
class CacheManager implements MediaDisposer.Disposable{
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    private final String cacheFileName = "cache.xml";
    private File cacheFile;
    private Document xDocument;

    protected CacheManager() throws IOException, ParserConfigurationException, TransformerException {
        String applicationDirectoryPath = ApplicationDirectoryUtilities.getProgramDirectory();
        this.cacheFile = new File(applicationDirectoryPath + File.separator + cacheFileName);

        //Si le fichier de cache est trouvé, on le parse
        if(cacheFile.exists()){
            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                this.xDocument = builder.parse(this.cacheFile);
            }
            catch (Exception ex){
                ex.printStackTrace(System.out);
            }
        }
        //Sinon  on le créé
        else{
            init();
        }

    }

    /**
     * Recherche dans le cache le hash à jour d'un fichier
     * @param file fichier à rechercher
     * @return Hash MD5 du fichier, null si le fichier n'est pas trouvé dans le cache ou qu'il a été modifié depuis la derniere mise en cache
     */
    protected String getHash(File file){
        Node node;
        try {
            node = getFileNode(file);

            if(node == null)
                return null;

            NamedNodeMap attributes = node.getAttributes();
            Date lastModificationInCache = simpleDateFormat.parse(attributes.getNamedItem("lastModification").getTextContent());
            Date lastModificationFile = new Date(file.lastModified());
            if(!lastModificationInCache.equals(lastModificationFile))
                return null;
            return node.getAttributes().getNamedItem("hash").getTextContent();
        }
        catch (ParseException e){
            return null;
        }
    }

    /**
     * Ajoute ou met à jour le hash d'un fichier dans le cache
     * @param file fichier à insérer ou mettre à jour dans le cache
     * @param hash hash MD5 du fichier
     */
    protected void setHash(File file, String hash){
        Node node = getFileNode(file);

        //Si le noeud n'existe pas, on le créé
        if(node == null){
            Element newFileNode = xDocument.createElement("fichier");
            newFileNode.setAttribute("absolutePath", file.getAbsolutePath());
            newFileNode.setAttribute("hash", hash);
            newFileNode.setAttribute("lastModification", getLastModifiedFormat(file));

            xDocument.getDocumentElement().appendChild(newFileNode);
        }

        //Sinon on l'update
        else{
            //Node hashAttribute = node.getAttributes().getNamedItem("hash");
            //Node lastModificationAttribute = node.getAttributes().getNamedItem("lastModification");
            node.getAttributes().getNamedItem("hash").setTextContent(hash);
            node.getAttributes().getNamedItem("lastModification").setTextContent(getLastModifiedFormat(file));
        }
    }

    /**
     * Créer et initialise un fichier de cache
     */
    private void init() throws ParserConfigurationException, TransformerException{
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        this.xDocument = docBuilder.newDocument();
        Element rootElement = this.xDocument.createElement("fichiers");
        this.xDocument.appendChild(rootElement);
    }

    /**
     * Renvoi le noeud du cache corespondant au fichier, null s'il n'est pas présent dans le cache
     * @param file
     * @return le Noeud XML du fichier
     */
    private Node getFileNode(File file){
        Element root = this.xDocument.getDocumentElement();
        XPathFactory xpf = XPathFactory.newInstance();
        XPath path = xpf.newXPath();
        String fileAbsolutePath = file.getAbsolutePath();
        String expression = "//fichiers/fichier[@absolutePath=\"" + fileAbsolutePath + "\"]";
        Node node;
        try {
            node = (Node)path.evaluate(expression, root, XPathConstants.NODE);
            return node;
        }
        catch (XPathExpressionException e){
            e.printStackTrace(System.out);
            return null;
        }


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
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(xDocument);

            StreamResult result = new StreamResult(this.cacheFile);
            transformer.transform(source, result);
        }
        catch (TransformerConfigurationException e) {
            e.printStackTrace(System.out);
        } catch (TransformerException e) {
            e.printStackTrace(System.out);
        }
    }
}
