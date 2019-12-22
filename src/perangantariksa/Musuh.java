
package perangantariksa;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import javax.swing.ImageIcon;


public class Musuh extends GerakanObjek {
    
    //tambahkan karakter musuh musuh

    ImageIcon alien1 = new ImageIcon("images/ufo1.png");
    ImageIcon alien2 = new ImageIcon("images/ufo2.png");
    ImageIcon alien3 = new ImageIcon("images/ufo3.png");
    ImageIcon alienBoss = new ImageIcon("images/boss1.png");
    ImageIcon alienBoss2 = new ImageIcon("images/boss2.png");
    ImageIcon alienBoss3 = new ImageIcon("images/boss3.png");
    
    private int enemytype, width, height;

    
    // Constuctor for any enemy
    public Musuh(int xPosition, int yPosition, int xVelocity, int yVelocity, int enemyType, Color color, int width, int height) {
        super(xPosition, yPosition, xVelocity, yVelocity, color);
        this.enemytype = enemyType;
        this.width = width;
        this.height = height;
    }
    
   
    // Dgambarkan musuh
    public void draw(Graphics g) {
        // type1 1
        if (this.enemytype % 3 == 0) {
            alien1.paintIcon(null, g, this.getXPosition(), this.getYPosition());
        // type 2
        } else if (this.enemytype % 3 == 1 && this.enemytype != 100) {
            alien2.paintIcon(null, g, this.getXPosition(), this.getYPosition());
        // type 3
        } else if (this.enemytype % 3 == 2) {
            alien3.paintIcon(null, g, this.getXPosition(), this.getYPosition());
        // Boss alien
        } if (this.enemytype == 100)
        {
            if(GamePanel.getBossHealth()>20){// bos berganti tergantung sisa nyawanya
                alienBoss.paintIcon(null, g, this.getXPosition(), this.getYPosition());
            }
            else if(GamePanel.getBossHealth()>10){
                alienBoss2.paintIcon(null, g, this.getXPosition(), this.getYPosition());
            }
            else if(GamePanel.getBossHealth()>0){
                alienBoss3.paintIcon(null, g, this.getXPosition(), this.getYPosition());
            }
        }
    }

    // gambarkan rectanggle musuh ,supaya musuh bisa terkena serangan
    @Override
    public Rectangle getBounds() {  // supaya musuh bisa terkena serangan
        Rectangle enemyHitBox = new Rectangle(this.getXPosition(), this.getYPosition(), width, height);
        return enemyHitBox;
    }

    
    @Override
    public void move() {//untuk menggerakkn semua musuh
        xPos += xVel;
    }
}