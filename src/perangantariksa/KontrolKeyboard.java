
package perangantariksa;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KontrolKeyboard implements KeyListener
{
    private boolean[] keyStatus; 
    
    public KontrolKeyboard()
    {
        keyStatus = new boolean[256]; 
    }
    
    public boolean getKeyStatus(int keyCode)
    {
        if(keyCode < 0 || keyCode > 255)//range integer keyboard
        {
            return false; 
        }
        else
        {
            return keyStatus[keyCode]; 
        }
    }
    
    public void resetController()
    {
        keyStatus = new boolean[256]; 
    }
    
   
    

    @Override
    public void keyPressed(KeyEvent ke) {
        keyStatus[ke.getKeyCode()] = true; //ketika tombol ditekan
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        keyStatus[ke.getKeyCode()] = false; //ketika tombol dilepas
    }

    @Override
    public void keyTyped(KeyEvent e) {
       
    }
    
}
