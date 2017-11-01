package Core;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import java.security.MessageDigest;
import java.util.ArrayList;

public class FileNode implements Node {
    private final String myHash = "MD5"; //Type de hash utilisé
    private File file;
    private String hash;

    public FileNode(File file){
        this.file = file;
    }

    public File getFile(){
        return this.file;
    }

    public boolean isDirectory(){
        return this.file.isDirectory();
    }


    @Override
    public String fileName() {

        return this.file.getName();
    }

    @Override
    public String getHash() {
        return this.hash;
    }

    /**
     * Retourne la taille du fichier en octets
     * @return
     */
    @Override
    public long length() {

        return this.file.isDirectory() ? 0 : this.file.length();
        //return this.file.length();
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
