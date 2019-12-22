
package perangantariksa;

import java.util.logging.Level;
import java.sql.*;

import com.sun.istack.internal.logging.Logger;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;


public class GamePanel extends JPanel {

    // Komponen
    private Timer waktu;
    private KontrolKeyboard kontrol;
    // atur ukuran frame game
    private final int lebar = 800;
    private final int tinggi = 800;
    private final int framesPerSecond = 120;

    // Tambahkan counternya
    Random r = new Random();
    public static int score = 0;
    private int level = 1;
    private int nyawa = 3;
    private int highScore;
    private int markerX, markerY;
    private static int bossHealth = 50;
    File f = new File("Highscore.txt");

    // tambahkan objeknya
    private Pesawat playerShip;
    private Pesawat singleLife;
    private Pesawat bonusEnemy;
    private Musuh enemy;
//    private Pelindung shield;
    private Peluru bullet;
    private PeluruMusuh beam, beam2, beam3;

    // Tambah bolean
    private boolean newBulletCanFire = true;
    private boolean newBeamCanFire = true;
    private boolean newBonusEnemy = true;
    private boolean hitMarker = false;

    //  Array Lists
    private ArrayList<Pesawat> lifeList = new ArrayList();
    private ArrayList<Pesawat> bonusEnemyList = new ArrayList();
    private ArrayList<Musuh> enemyList = new ArrayList();
//    private ArrayList<Pelindung> shieldList = new ArrayList();
    private ArrayList<PeluruMusuh> beamList = new ArrayList();
    private ImageIcon background = new ImageIcon("images/playframe.jpg");
    
   

    // Tambahkan suara
    private File beamSound = new File("sounds/alienBeam.wav");
    private File bulletSound = new File("sounds/Laser-shot.wav");
    private File levelUpSound = new File("sounds/levelUpSound.wav");
    private File deathSound = new File("sounds/gameover.wav");
    private File hitmarkerSound = new File("sounds/blash.wav");
    private File bossSound = new File("sounds/alienlanding.wav");
    private File bonusSound = new File("sounds/bonusSound.wav");
     private File damageSound = new File("sounds/boom.wav");

    private AudioStream beamSoundAudio;
    private InputStream beamSoundInput;
    private AudioStream bulletSoundAudio;
    private InputStream bulletSoundInput;
    private AudioStream levelUpSoundAudio;
    private InputStream levelUpSoundInput;
    private AudioStream deathSoundAudio;
    private InputStream deathSoundInput;
    private AudioStream hitSoundAudio;
    private InputStream hitSoundInput;
    private AudioStream bossSoundAudio;
    private InputStream bossSoundInput;
    private AudioStream bonusSoundAudio;
    private InputStream bonusSoundInput;
    private AudioStream damageSoundAudio;
    private InputStream damageSoundInput;
    

// ambil nilai nyawa boss
    public static int getBossHealth() {
        return bossHealth;
    }

    public final void setupGame() {

        // atur musuh baris dan kolom
        if (level != 3 && level != 6 && level != 9 && level != 12) {
            // 6 bariss
            for (int baris = 0; baris < 7; baris++) {
                // 5 koloms
                for (int kolom = 0; kolom < 5; kolom++) {
                    enemy = new Musuh((20 + (baris * 100)), (20 + (kolom * 60)), level, 0, kolom, null, 60, 60); // kecepatan musuh bertambah
                    enemyList.add(enemy);
                }
            }
        }
        // tampilkan  bos di level kelipatan tiga
        if (level == 3 || level == 6 || level == 9 || level == 12) {
            AudioPlayer.player.start(bossSoundAudio); // suara bos
            enemy = new Musuh(20, 20, 3, 0, 100, null, 150, 150);
            enemyList.add(enemy);
            
            
        }
       
         // untuk mereset semua pergerakan agar pesawat tidak grak sendiri
        kontrol.resetController();
       

        // atur pesawat  
        playerShip = new Pesawat(375, 730, null, kontrol);

        // atur life bar
        for (int kolom = 0; kolom < nyawa; kolom++) {
            singleLife = new Pesawat(48 + (kolom * 20), 10, Color.WHITE, null);
            lifeList.add(singleLife);
        }

        
    }
   

    @Override
    public void paint(Graphics g) {

        // atur backround
        background.paintIcon(null, g, 0, -150);

        // mengeluatkan kalimat boom jika mengenai musuh
        if (bullet != null) {
            if (hitMarker) {
                g.setColor(Color.RED);
                if (level != 3 && level != 6 && level != 9 && level != 12) {
                    g.drawString(" boom", markerX + 20, markerY -= 1);
                } else {
                    g.drawString("zraagh", markerX + 75, markerY += 1);//mengeluarkan zraagh mengenai bos
                }
            }
        }
        // gambarkan pesawat pemain
        playerShip.draw(g);

       

        //gambarkan 3 ufo
        try {
            for (int index = 0; index < enemyList.size(); index++) {
                enemyList.get(index).draw(g);
            }

        } catch (IndexOutOfBoundsException e) {
        }

        // mengeluarkan peluru saat tombol fitekan
        if (kontrol.getKeyStatus(32)) {//32 nilai interger untuk spasi
            if (newBulletCanFire) {
                bullet = new Peluru(playerShip.getXPosition() + 60, playerShip.getYPosition() - 20, 0, Color.RED);//posisi dan warna peluru
                AudioPlayer.player.start(bulletSoundAudio); // suara peluru pemain
                newBulletCanFire = false;//pesawat bisa menembak apabila peluru sudah tidak tampil
            }
        }
        
         if (kontrol.getKeyStatus(38)) {//
            if (newBulletCanFire) {
                bullet = new Peluru(playerShip.getXPosition() + 60, playerShip.getYPosition() - 20, 0, Color.WHITE);//posisi dan warna peluru
                AudioPlayer.player.start(bulletSoundAudio); // suara peluru pemain
                newBulletCanFire = false;//pesawat bisa menembak apabila peluru sudah tidak tampil
            }
        }
         
          if (kontrol.getKeyStatus(65)) {//matikan suara dengan a
            
               Menu.close(); 
            }
          
          if (kontrol.getKeyStatus(83)) {//hidupkan suara dengan s
            Menu.close(); 
               Menu.play(); 
            }
          
          
          if (kontrol.getKeyStatus(78)&& kontrol.getKeyStatus(75) && kontrol.getKeyStatus(77)){//cheat menambah score
            score+=1000;
            }
          
           if (kontrol.getKeyStatus(85)&& kontrol.getKeyStatus(80) && kontrol.getKeyStatus(76)){//cheat menambah level dengan sangat cepat
               level++;
            }
          
          
        
        if (bullet != null) {//tampilkan peluru saat pelurunya nol
            bullet.draw(g);
        }
        
        

        // musuh menembak random
        if (level != 3 && level != 6 && level != 9 && level != 12) {
            if (newBeamCanFire) {
                for (int index = 0; index < enemyList.size(); index++) {
                    if (r.nextInt(30) == index) {
                        beam = new PeluruMusuh(enemyList.get(index).getXPosition(), enemyList.get(index).getYPosition(), 0, Color.YELLOW);
                        beamList.add(beam);
                        AudioPlayer.player.start(beamSoundAudio); // suara peluru musuh
                    }
                    newBeamCanFire = false;
                }
            }
        }
        // bos menembak
        if (level == 3 || level == 6 || level == 9 || level == 12) {
            if (newBeamCanFire) {
                for (int index = 0; index < enemyList.size(); index++) {
                    if (r.nextInt(5) == index) {
                        beam = new PeluruMusuh(enemyList.get(index).getXPosition() + 75, enemyList.get(index).getYPosition() + 140, 0, Color.YELLOW);
                        beam2 = new PeluruMusuh(enemyList.get(index).getXPosition(), enemyList.get(index).getYPosition() + 110, 0, Color.RED);
                        beam3 = new PeluruMusuh(enemyList.get(index).getXPosition() + 150, enemyList.get(index).getYPosition() + 110, 0, Color.BLUE);
                        beamList.add(beam);
                        beamList.add(beam2);
                        beamList.add(beam3);
                        AudioPlayer.player.start(beamSoundAudio); // suara peluru
                    }
                    newBeamCanFire = false;
                }
            }
        }
        // gambarkan peluru
        for (int index = 0; index < beamList.size(); index++) {
            beamList.get(index).draw(g);
        }
        // pesawatbonus ditampilkan secara acak
        if (newBonusEnemy) {
            if (r.nextInt(3000) == 1500) {
                bonusEnemy = new Pesawat(-50, 30, Color.RED, null);
                bonusEnemyList.add(bonusEnemy);
                newBonusEnemy = false;
            }
        }
        // gambarkan pesawat bonus
        for (int index = 0; index < bonusEnemyList.size(); index++) {
            bonusEnemyList.get(index).bonusDraw(g);
        }

        // tampilkan score
        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 260, 20);

        // tampilkan nyawa
        g.setColor(Color.WHITE);
        g.drawString("LIFE : ", 11, 20);
        for (int index = 0; index < lifeList.size(); index++) {
            lifeList.get(index).lifeDraw(g);
        }
        // Tampilkan level
        g.setColor(Color.RED);
        g.drawString("Level " + level, 750, 20);

        // tampilkan high score
        g.setColor(Color.WHITE);
        g.drawString("Highscore: " + highScore, 440, 20);

        // tampilkan nyawa bos
        if (level == 3 || level == 6 || level == 9 || level == 12) {
            g.setColor(Color.WHITE);
            g.drawString("HP BOSS: " + bossHealth, 352, 600);
        }
    }
    

    
    public void updateGameState(int frameNumber) {

        // membiarkan pemain untuk tetep bisa menggerakkan pesawat
        playerShip.move();

        // Updates highscore
        try {
            Scanner fileScan = new Scanner(f);
            while (fileScan.hasNextInt()) {
                String nextLine = fileScan.nextLine();
                Scanner lineScan = new Scanner(nextLine);
                highScore = lineScan.nextInt();
            }
        } catch (FileNotFoundException e) {
        }
        // pause
        if (kontrol.getKeyStatus(20)) {//capslock untuk pause
            int answer = JOptionPane.showConfirmDialog(null, "Pause game, Lanjutkan Permainan??", "Game Pause", 0);
            kontrol.resetController();
            if (answer == 0) {
                try {   
                  PrintWriter pw = new PrintWriter(new FileOutputStream(f, false));
                    pw.close();
                } catch (FileNotFoundException e) {
                }
            }else{ System.exit(0);}
        }
        // uipdate high score bila score lebih tinggi dri highscore
        try {
            if (score > highScore) {
                String scoreString = Integer.toString(score);
                PrintWriter pw = new PrintWriter(new FileOutputStream(f, false));
                pw.write(scoreString);
                pw.close();
            }
        } catch (FileNotFoundException e) {
        }

        // membuat musuh bergera mengubah arah
        if ((enemyList.get(enemyList.size() - 1).getXPosition() + enemyList.get(enemyList.size() - 1).getXVelocity()) > 760 || (enemyList.get(0).getXPosition() + enemyList.get(0).getXVelocity()) < 0) {
            for (int index = 0; index < enemyList.size(); index++) {
                enemyList.get(index).setXVelocity(enemyList.get(index).getXVelocity() * -1);
                enemyList.get(index).setYPosition(enemyList.get(index).getYPosition() + 10);
            }
        } else {
            for (int index = 0; index < enemyList.size(); index++) {
                enemyList.get(index).move();
            }
        }

        // luncurkan peluru
        if (bullet != null) {
            bullet.setYPosition(bullet.getYPosition() - 15);//keceptan peluru
            if (bullet.getYPosition() < 0) {//jangkauan peluru
                newBulletCanFire = true;
            }

            // Memeriksa tabrakan dengan musuh normal
            for (int index = 0; index < enemyList.size(); index++) {
                if (bullet.SalingKena(enemyList.get(index))) {
                    AudioPlayer.player.start(hitSoundAudio); // suara saat mengenai musuh
                    bullet = new Peluru(0, 0, 0, null);
                    newBulletCanFire = true;
                    // Updates score 
                    if (level != 3 && level != 6 && level != 9 && level != 12) {
                        score += 100;
                        hitMarker = true;
                        markerX = enemyList.get(index).getXPosition(); 
                        markerY = enemyList.get(index).getYPosition();
                        enemyList.remove(index);

                    }
                    // Updates score  boss levels
                    if (level == 3 || level == 6 || level == 9 || level == 12) {
                        hitMarker = true;
                        markerX = enemyList.get(index).getXPosition(); 
                        markerY = enemyList.get(index).getYPosition() + 165;
                        bossHealth -= 1;
                        if (bossHealth == 0) {
                            enemyList.remove(index);
                            score += 10000;//bonus score untuk boss
                        }
                    }
                }
            }


        }
        // gerakkan pesawat bonus
        if (!bonusEnemyList.isEmpty()) {
            for (int index = 0; index < bonusEnemyList.size(); index++) {
                bonusEnemyList.get(index).setXPosition(bonusEnemyList.get(index).getXPosition() + (2));
                if (bonusEnemyList.get(index).getXPosition() > 800) {
                    bonusEnemyList.remove(index);
                    newBonusEnemy = true;
                }
            }
            // jika tembakan mengenai pesawat bonus
            for (int index = 0; index < bonusEnemyList.size(); index++) {
                if (bullet != null) {
                    if (bonusEnemyList.get(index).SalingKena(bullet)) {
                        bonusEnemyList.remove(index);
                        bullet = new Peluru(0, 0, 0, null);
                        newBulletCanFire = true;
                        newBonusEnemy = true;
                        AudioPlayer.player.start(bonusSoundAudio); // Plays sound if player hits a bonus enemy
                        score += 1000; // add bonus to score on hit
                        
                    }
                }
            }
        }

        // menggerakkan peluru musuh
        if (level != 3 && level != 6 && level != 9 && level != 12) {
            if (beam != null) {
                for (int index = 0; index < beamList.size(); index++) {
                    beamList.get(index).setYPosition(beamList.get(index).getYPosition() + (4));
                    if (beamList.get(index).getYPosition() > 800) {
                        beamList.remove(index);
                    }
                }
            }
        }
        // menggerkkan peluru boss
        if (level == 3 || level == 6 || level == 9 || level == 12) {
            if (beam != null) {
                for (int index = 0; index < beamList.size(); index++) {
                    beamList.get(index).setYPosition(beamList.get(index).getYPosition() + (2 * level)); //Boss beam speed will increase each level
                    if (beamList.get(index).getYPosition() > 800) {
                        beamList.remove(index);
                    }
                }
            }
        }


        // memeriksa peluru musuh mengenai pesawat pemain
        for (int index = 0; index < beamList.size(); index++) {
            if (beamList.get(index).SalingKena(playerShip)) {
                beamList.remove(index);
                AudioPlayer.player.start(damageSoundAudio); // suara pesawat pemain terkena tembakan musuh
                lifeList.remove(lifeList.size() - 1); // nyawa bar berkurang 1
            }
        }

        // musuh boleh menembak lagi saat pelurunya hilang dri layar
        if (beamList.isEmpty()) {
            newBeamCanFire = true;
        }

    
        for (int input = 0; input < enemyList.size(); input++) {

            // jika musuh nyampai dasar maka mengulang level
            if (enemyList.get(input).getYPosition() + 50 >= 750) {
                enemyList.clear();
                lifeList.clear();
                beamList.clear();
                bossHealth = 30;
                nyawa -= 1;
                AudioPlayer.player.start(deathSoundAudio); //suara saat musuh mencapai dasar
                setupGame();
            }
        }

        //jika terkena serangan nyawa berkurang 1
        if (playerShip.SalingKena) {
            int index = lifeList.size() - 1;
            lifeList.remove(index);
        } 
        // game over jika pemain habis nyawanya
        else if (lifeList.isEmpty()) {
            AudioPlayer.player.start(deathSoundAudio); // sound untuk game over   
            
            String nama = JOptionPane.showInputDialog("Masukkan Nama Anda :");
            
             int answer = JOptionPane.showConfirmDialog(null,nama+ " Scoremu "+ score +" Main Lagi?", "GAME OVER", 0);
                
         // jika memilih play again maka kan mereset semuanya
            if (answer == 0) {
                try {
                    Statement s=koneksi.conn.createStatement();
                    String query="insert into player values('"+nama+"',"+score+")";
                    s.execute(query);
                } catch (SQLException ex) {
                    java.util.logging.Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
                     }
            
        
                
                lifeList.clear();
                enemyList.clear();
                beamList.clear();
                bonusEnemyList.clear();
                score = 0;
                level = 1;
                bossHealth = 30;
                nyawa = 3;
                newBulletCanFire = true;
                newBeamCanFire = true;
                newBonusEnemy = true;
                setupGame();
            }
//            //jika pilih no maka game keluar
            if (answer == 1) {
                
                System.exit(0);
            }
        }

        // naik level, semua direset ke awal
        if (enemyList.isEmpty()) {
            beamList.clear();
            bonusEnemyList.clear();
            lifeList.clear();
            level += 1;
            bossHealth = 30;
            setupGame();
            AudioPlayer.player.start(levelUpSoundAudio); // level up sound
        }
        
        // untuk mengaktifkan suara disetiap game
        try {
            beamSoundInput = new FileInputStream(beamSound);
            beamSoundAudio = new AudioStream(beamSoundInput);
            bulletSoundInput = new FileInputStream(bulletSound);
            bulletSoundAudio = new AudioStream(bulletSoundInput);
            levelUpSoundInput = new FileInputStream(levelUpSound);
            levelUpSoundAudio = new AudioStream(levelUpSoundInput);
            deathSoundInput = new FileInputStream(deathSound);
            deathSoundAudio = new AudioStream(deathSoundInput);
            hitSoundInput = new FileInputStream(hitmarkerSound);
            hitSoundAudio = new AudioStream(hitSoundInput);
            bossSoundInput = new FileInputStream(bossSound);
            bossSoundAudio = new AudioStream(bossSoundInput);
            bonusSoundInput = new FileInputStream(bonusSound);
            bonusSoundAudio = new AudioStream(bonusSoundInput);
            damageSoundInput = new FileInputStream(damageSound);
            damageSoundAudio = new AudioStream(damageSoundInput);
            
        } catch (IOException e) {
        }
    }


    
    public GamePanel() {
        // atur ukuran game
        this.setSize(lebar, tinggi);
        this.setPreferredSize(new Dimension(lebar, tinggi));
        this.setBackground(Color.BLACK);
   

        // Daftarkan Kontrol Keyboard sebagai KeyListener
        kontrol = new KontrolKeyboard();
        this.addKeyListener(kontrol);

        // Panggil setupGame untuk menginisialisasi bidang
        this.setupGame();
        this.setFocusable(true);
        this.requestFocusInWindow();
        
        
//         koneksi db=new koneksi();
//        try {
//            db.konek();
//            System.out.println("Berhasil");
//            
//        } catch (ClassNotFoundException | SQLException ex) {
//            java.util.logging.Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

  //method untuk memulai animasi
    public void start() {
        // Satur waktu (50 FPS)
        waktu = new Timer(1000 / framesPerSecond, new ActionListener() {

            // Melacak jumlah frame yang telah diproduksi.
            
            private int frameNumber = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                // Perbarui status permainan dan gambar ulang layar
                updateGameState(frameNumber++);
                repaint();
            }
        });
        Timer waktuHitMarker = new Timer(1000, new ActionListener() {

           // Melacak jumlah frame yang telah diproduksi.
         
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perbarui status permainan dan gambar ulang layar
                hitMarker = false;
            }
        });

        waktu.setRepeats(true);
        waktu.start();
        waktuHitMarker.setRepeats(true);
        waktuHitMarker.start();
    }

   

}
