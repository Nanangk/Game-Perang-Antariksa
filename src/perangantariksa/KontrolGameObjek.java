
package perangantariksa;


import java.awt.Color;


public abstract class KontrolGameObjek extends GameObjek{
    
    KontrolKeyboard control;
    
    // Konstraktor untuk setiap objek yang dikendalikan
    public KontrolGameObjek(int xPosition, int yPosition, Color color, KontrolKeyboard control)
    {
        super(xPosition, yPosition, color);
        this.control = control;
    }
}
