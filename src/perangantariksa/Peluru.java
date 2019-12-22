
package perangantariksa;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.ImageIcon;


public class Peluru extends GerakanObjek {

    // peluru pemain
    int diameter;
    int yVelocity;

    // konstarktornya
    public Peluru(int xPosition, int yPosition, int diameter, Color color) {
        super(xPosition, yPosition, 0, 0, color);
        this.diameter = diameter;
    }

    // cari diameter
    public int getDiameter() {
        return diameter;
    }


    public void draw(Graphics g) {//gambarkan peluru
        g.setColor(color);
        g.fillRect(this.getXPosition(), this.getYPosition(), 10, 20);//ukuran peluru

    }

    @Override
    public Rectangle getBounds() {//supaya bisa mengenai musuh
        Rectangle bulletHitbox = new Rectangle(xPos, yPos, 10, 20);// rectanggle peluru
        return bulletHitbox;
    }
}
