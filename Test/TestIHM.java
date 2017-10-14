package Test;

import Core.Api;

import javax.swing.*;
import java.awt.*;

public class TestIHM extends JFrame {
    public TestIHM(){
        this.setSize(new Dimension(300,300));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Api api = new Api();
        this.getContentPane().add(new JScrollPane(api.getTree("C:\\")));
    }
}
