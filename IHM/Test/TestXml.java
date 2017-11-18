package IHM.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;

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

import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class TestXml {

    public static void main(String[] args) {
        readXml();
        /*
        File file = new File("D:\\cache.xml");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        sdf.format(file.lastModified());
        System.out.println("file.lastModified() = " +);
        */
    }

    private static void readXml(){
        // Nous récupérons une instance de factory qui se chargera de nous fournir
        // un parseur
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            // Création de notre parseur via la factory
            DocumentBuilder builder = factory.newDocumentBuilder();
            String filepath = "D:\\cache.xml";
            File fileXML = new File(filepath);

            // parsing de notre fichier via un objet File et récupération d'un
            // objet Document
            // Ce dernier représente la hiérarchie d'objet créée pendant le parsing
            Document xml = builder.parse(fileXML);

            // Via notre objet Document, nous pouvons récupérer un objet Element
            // Ce dernier représente un élément XML mais, avec la méthode ci-dessous,
            // cet élément sera la racine du document
            Element root = xml.getDocumentElement();
            System.out.println(root.getNodeName());
            NodeList nodes =  root.getChildNodes();


            XPathFactory xpf = XPathFactory.newInstance();
            XPath path = xpf.newXPath();

            String expression = "//fichiers/fichier[@absolutePath='aaa']";
            Node node = (Node)path.evaluate(expression, root, XPathConstants.NODE);

            //Update
            Node nodeAttr = node.getAttributes().getNamedItem("lastModification");
            nodeAttr.setTextContent("corrompu");

            //Ajout
            Element age = xml.createElement("fichier");
            age.setAttribute("test", "testvalue");
            root.appendChild(age);

            System.out.println("str = " + node.getAttributes().getNamedItem("lastModification").getTextContent());

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(xml);

            StreamResult result = new StreamResult(new File(filepath));
            transformer.transform(source, result);

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (XPathExpressionException e){
            e.printStackTrace();
        }
        catch (TransformerConfigurationException e){
            e.printStackTrace();
        }
        catch (TransformerException e){
            e.printStackTrace();
        }
    }
}