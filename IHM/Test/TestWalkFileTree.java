package IHM.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.Files.walkFileTree;

public class TestWalkFileTree {
    public static void main(String[] args){
        try {
            walkFileTree(Paths.get("D:\\test"), new PrintFiles());
        }
        catch (Exception e){
            e.printStackTrace(System.err);
        }
    }
}
