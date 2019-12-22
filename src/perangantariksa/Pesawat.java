
package perangantariksa;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Pesawat extends KontrolGameObjek {

    ImageIcon ship = new ImageIcon("images/pesawat.png");//karakter pesawat kita
    ImageIcon bonusEnemy = new ImageIcon("images/pesawatbonus.png");//pesawat bonus
    ImageIcon lifeCounterShip = new ImageIcon("images/nyawa.png");//life bar

    // konstarktor pesawat
    public Pesawat(int xPosition, int yPosition, Color color, KontrolKeyboard control) {
        super(xPosition, yPosition, color, control);
    }

    // tampilkan pesawat bonus
    public void bonusDraw(Graphics g) {

        bonusEnemy.paintIcon(null, g, this.getXPosition(), this.getYPosition());
    }

    // gambarkan  life bar
    public void lifeDraw(Graphics g) {

        lifeCounterShip.paintIcon(null, g, this.getXPosition(), this.getYPosition());
    }

    // gambar pesawat kita
    public void draw(Graphics g) {
        ship.paintIcon(null, g, this.getXPosition(), this.getYPosition());

    }

    
    @Override
    public Rectangle getBounds() {//biar bisa mengenai sasaran
        Rectangle shipHitbox = new Rectangle(this.getXPosition(), this.getYPosition(), 129, 82);//rectangle pesawat sesuai ukuran karakter pesawat
        return shipHitbox;
    }

    // kontrol yg digunakan untuk menggerakan pesaway kita
   
    public void move() {
       
        if (control.getKeyStatus(37)) {//gerak ke kiri
            xPos -= 10;
        }
       
        if (control.getKeyStatus(39)) {//gerak ke kanan
            xPos += 10;
        }
        
        // biar pesawatnya tidak hilang
        if (xPos > 800) {
            xPos = -50;
        }
        if (xPos < -50) {
            xPos = 800;
        }
    }
}
