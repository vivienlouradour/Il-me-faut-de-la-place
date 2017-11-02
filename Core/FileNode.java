package Core;

import java.io.File;
import java.util.ArrayList;

public class FileNode implements INode {
    private File file;
    private DirectoryNode parent;

    public FileNode(File file){
        this.file = file;
    }

    @Override
    public File getFile(){
        return this.file;
    }

    @Override
    public ArrayList<INode> getChilds() {
        return null;
    }

    @Override
    public void setParent(DirectoryNode parentNode){
        this.parent = parentNode;
    }
}
