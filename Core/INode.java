package Core;

import java.io.File;
import java.util.ArrayList;

interface INode {

    void setParent(DirectoryNode parentNode);
    File getFile();
    ArrayList<INode> getChilds();
}
