
package perangantariksa;

import java.awt.Color;
import javax.swing.JFrame;

public class PlayFrame extends JFrame
{
    private GamePanel game;
    
    public PlayFrame()
            
    {
        super("Perang antariksa");//nama game
      
      
        
        
        // memastiak program tertututp sempurna
      this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        
       
        game = new GamePanel();
        game.setDoubleBuffered(true);//untuk animasi lancar
        
        //  untuk menampilkannya
        this.getContentPane().add(game); //menambahkan panel(game panel) ke  frame (gameframe)
        this.pack();//buat ngepasin
        this.setResizable(false);//tdk bisa diperbesarkecil
        this.setLocationRelativeTo(null);//posisi ditengah
        
        // mulai
        game.start();  
       
    }
    
    public static void main(String[] args) 
    {
         java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PlayFrame().setVisible(true);
            }
        });
        
    }

   
}
