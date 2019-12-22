
package perangantariksa;

import java.awt.Color;


public abstract class GerakanObjek extends GameObjek{
    
    int xVel;
    int yVel;
    
    // Konstruktor untuk objek yang tidak  dekendalikan
    public GerakanObjek(int xPosition, int yPosition, int xVelocity, int yVelocity, Color color)
    {
        super(xPosition, yPosition, color);
        this.xVel = xVelocity;
        this.yVel = yVelocity;
    
    }
    
    //Accessor dan mutator untuk setiap bagian dari konstruktor gerakan
    public int getXVelocity()
    {
        return xVel;
    }
    public int getYVelocity()
    {
        return yVel;
    }
    public void setXVelocity(int xVelocity)
    {
        this.xVel = xVelocity;
    }
    public void setYVelocity(int yVelocity)
    {
        this.yVel = yVelocity;
    }
  
    
    // untuk menggerakkan objek yang tidak dikontrol
    public void move()
    {
        this.xPos += xVel;
        this.yPos += yVel;
    }
    
}
