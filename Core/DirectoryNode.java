package Core;

import java.io.File;
import java.util.ArrayList;

public class DirectoryNode extends Node {
    private ArrayList<Node> childs;

    protected DirectoryNode(File file){
        super(file);
        childs = new ArrayList<>();
    }


    @Override
    protected ArrayList<Node> getChilds() {
        return this.childs;
    }

    protected void addChild(Node node){
        node.setParent(this);
        this.childs.add(node);
    }






}
