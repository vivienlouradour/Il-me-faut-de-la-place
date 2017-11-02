package Core;

import java.io.File;
import java.util.ArrayList;

public interface INode {

    public void setParent(DirectoryNode parentNode);
    public File getFile();
    public ArrayList<INode> getChilds();

}
