package Test;

import Core.FabriqueNode;
import Core.INode;

public class Test {
    public static void main(String[] args){
        FabriqueNode fabrique = new FabriqueNode();
        INode node = fabrique.tree("C:\\");
        System.out.println( "d");
    }
}
