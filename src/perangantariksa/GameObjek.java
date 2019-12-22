
package perangantariksa;

import java.awt.Color;
import java.awt.Rectangle;


public abstract class GameObjek  {

    int xPos;
    int yPos;
    Color color;
    boolean SalingKena;
    
    public GameObjek(){};
    
    // Konstruktor untuk semua Objek
    public GameObjek(int xPosition, int yPosition, Color color) {
        this.xPos = xPosition;
        this.yPos = yPosition;
        this.color = color;
    }

    public abstract Rectangle getBounds();

    // Ambil posisi X dari setiap objek
    public int getXPosition() {
        return xPos;
    }

    //Ambil posisi Y dari setiap objek
    public int getYPosition() {
        return yPos;
    }

    // Ambil posisi warna dari setiap objek
    public Color getColor() {
        return color;
    }

    // Atur posisi X dari setiap objek
    public void setXPosition(int xPosition) {
        this.xPos = xPosition;
    }

    // Atur posisi Y dari setiap objek
    public void setYPosition(int yPosition) {
        this.yPos = yPosition;
    }

    // Atur posisi warna dari setiap objek
    public void setColor(Color color) {
        this.color = color;
    }

    // Cek apakah hitbox dari dua objek bertemu
    public boolean SalingKena(GameObjek other) {
        SalingKena = other.getBounds().intersects(this.getBounds());
        return SalingKena;
    }
}
