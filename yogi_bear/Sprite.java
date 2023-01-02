package yogi_bear;

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Sprite{
    protected int x;
    protected int y;
    protected int height;
    protected int width;
    protected boolean visible;
    protected Image img;

    public Sprite(int xCoord, int yCoord, String imgName) {
        this.x = xCoord;
        this.y = yCoord;
        this.visible = true;
        ImageIcon imageIc  = new ImageIcon(imgName);
        this.img = imageIc.getImage();
        this.width = this.img.getWidth(null);
        this.height= this.img.getHeight(null);       
    }

    public void setImage(String imgName) {
       ImageIcon imageIc = new ImageIcon(imgName);
       this.img = imageIc.getImage();
       this.width = this.img.getWidth(null);
       this.height = this.img.getHeight(null);
    }

    // SETTERS
    public void setX(int x) {this.x = x;}
    public void setY(int y) {this.y = y;}
    public void setVisible(Boolean visible) {this.visible = visible;}

    //GETTERS
    public Image getImage() {return this.img;}
    public int getX() {return this.x;}
    public int getY() {return this.y;}
    public boolean isVisible() {return this.visible;}
    public Rectangle getBounds() {
        return new Rectangle(this.x, this.y, this.width, this.height);
    }
}