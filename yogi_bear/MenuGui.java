package yogi_bear;

import java.awt.*;
import javax.swing.*;

public class MenuGui extends JPanel {
    public MenuGui(LayoutManager layout) {super(layout);}
    @Override
    protected void paintComponent(Graphics gr) {
        super.paintComponent(gr);
        ImageIcon menu = new ImageIcon("src/resources/menuImg.jpg");
        Image backgroundImg = menu.getImage();
        gr.drawImage(backgroundImg,0,0,null);
    }
}
