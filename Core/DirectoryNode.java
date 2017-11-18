package Core;

import java.io.File;
import java.util.ArrayList;

class DirectoryNode implements INode {
    private File file;
    private DirectoryNode parent;
    private ArrayList<INode> childs;

    public DirectoryNode(File file){
        this.file = file;
        childs = new ArrayList<>();
    }

    @Override
    public File getFile(){
        return this.file;
    }

    @Override
    public ArrayList<INode> getChilds() {
        return this.childs;
    }

    public void setParent(DirectoryNode parentNode){
        this.parent = parentNode;
    }

    public void addChild(INode node){
        node.setParent(this);
        this.childs.add(node);
    }






}
