package Core;

import java.io.File;

public abstract class Node implements INode{
    private File file;

    //Services
    @Override
    public String fileName() {
        return this.file.getName();
    }

    @Override
    public long size() {
        return this.file.length();
    }

    @Override
    public String absolutePath() {
        return this.file.getAbsolutePath();
    }
}
