package Test;

import Core.FabriqueNode;
import Core.INode;

import javax.swing.*;

public class Test {
    public static void main(String[] args){
 //       FabriqueNode fabrique = new FabriqueNode();
//        Node node = fabrique.tree("C:\\");
        //System.out.println( "d");
        //TestIHM fenetre = new TestIHM();

        SwingUtilities.invokeLater(new FileBrowser());


        //fenetre.setVisible(true);
    }
}
