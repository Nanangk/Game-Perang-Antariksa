
package perangantariksa;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import javafx.scene.shape.Ellipse;
import javax.swing.ImageIcon;


public class PeluruMusuh extends GerakanObjek {

    //konstraktor peluru musuh
    public PeluruMusuh(int xPosition, int yPosition, int diameter, Color color) {
        super(xPosition, yPosition, 0, 0, color);
    }
    


    public void draw(Graphics g) {//tampilkan peluru musuh
        g.setColor(color);
        g.fillRect(this.getXPosition(), this.getYPosition(), 10, 20);//ukuran peluru mush
    }
    
    // Used to get the hit box of a beam
    @Override
    public Rectangle getBounds() {//supaya bisa mengenai target
        Rectangle beamHitbox = new Rectangle(xPos, yPos, 10, 20);//ukuran rectangle peluru musuh
        return beamHitbox;
    }
}
