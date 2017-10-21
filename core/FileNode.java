package core;

import javax.swing.tree.DefaultTreeModel;
import java.io.File;
import java.io.FileFilter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.ArrayList;

public class FileNode implements Node {
    private final String myHash = "MD5";
    private File file;

    private byte[] hash;

    public FileNode(File file){
        this.file = file;

        //Calcul le hash du fichier
        if(this.file.isFile())
            this.hash = this.hash();
    }

    private byte[] hash(){
        try {
            MessageDigest md = MessageDigest.getInstance(myHash);
            md.update(Files.readAllBytes(Paths.get(this.absolutePath())));
            byte[] digest = md.digest();
            return digest;
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            return null;
        }
    }

    @Override
    public ArrayList<File> doublons() {
        return null;
    }

    @Override
    public DefaultTreeModel modelTree() {

        return null;
    }

    @Override
    public String fileName() {

        return this.file.getName();
    }

    @Override
    public byte[] getHash() {
        return this.hash;
    }

    @Override
    public long length() {
        return this.file.length();
    }

    @Override
    public String absolutePath() {
        return this.file.getAbsolutePath();
    }

    @Override
    public ArrayList<Node> getChilds() {
        return null;
    }

    @Override
    public Node filter(ArrayList<FileFilter> filtres) {
        return null;
    }

    @Override
    public String toString(){
        String name = file.getName();
        if (name.equals("")) {
            return file.getAbsolutePath();
        } else {
            return name;
        }
    }

}
