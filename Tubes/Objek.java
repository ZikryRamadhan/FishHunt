/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tubes;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import javax.imageio.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author user
 */
public abstract class Objek {
    protected int width, height;
    int x, y, level;
    boolean kiri;
    public Image img;
    public BufferedImage img2;
    
    void loadImage(){};
    void getImage(){};
    void getBounds(){};
    
    void scaleIkan(int width, int height){
        img = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        img2 = toBufferedImage(img);
    }

    public static BufferedImage toBufferedImage(Image img){
    if (img instanceof BufferedImage){
        return (BufferedImage) img;
    }

    // Create a buffered image with transparency
    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

    // Draw the image on to the buffered image
    Graphics2D bGr = bimage.createGraphics();
    bGr.drawImage(img, 0, 0, null);
    bGr.dispose();

    // Return the buffered image
    return bimage;
    }    
    
    public void flipHorizontally(){
        if(img2==null){System.out.println("null image");}
        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-img2.getWidth(null), 0);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        
        img2 = op.filter(img2, null);    
    }
    
}

interface MgL{
    abstract void move();
    abstract int getLevel();
}

class Ikan extends Objek implements MgL{
    private int score;
    boolean chain;
    int elevel;
    
    
    Ikan(){
        level = 1;
        score = elevel = 0;
        height = width = x = y = 50;
//        kiri = true;
        chain = false;
        loadImage();
//        scaleIkan(width, height);
    }
    

    
    @Override
    public void move(){}
    
    @Override
    public int getLevel(){
        return level;
    }
    
    public int getlevel(int a){
        return a+level;
    }    
    
    public void setPosition(Point point){
        if(this.x < point.x && kiri){
            flipHorizontally();
            kiri = false;
        }
        else if(this.x > point.x && !kiri){
            flipHorizontally();
            kiri = true;        
        }
        this.x = point.x;
        this.y = point.y;   
        
    }

    public int getScore(){return score;}

    void makan(int levela){
        if(elevel==0){elevel = levela;}
        else if(elevel == levela){chain = true;}
        else {elevel = levela; chain = false;}
        switch(levela){
            case 0: score += 25; break;
            case 1: score += 45; break;
            case 2: score += 65; break;
        }
        if(chain){score += elevel*2;}
        if(score>=70 && level == 1){lvlUp();}
        else if(score>=160 && level == 2){lvlUp();}
        else if(score>=300 && level == 3){lvlUp();}
    }
    
    void resetScore(){score = 0;}
    
    @Override
    void loadImage(){
        kiri = true;
        try {
            img = ImageIO.read(getClass().getResource("fish.png"));
            scaleIkan(width, height);
        } catch (IOException ex) {
            Logger.getLogger(Ikan.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
    
    void lvlUp(){
        level++;     
//        switch(level){
//            case 2: width = height = 65; loadImage(); break;
//            case 3: width = height = 85; loadImage(); break;
//        }
    }
}
    
class IkanLiar extends Objek implements MgL{
    private int initial_x;
    static Random acak = new Random();
    IkanLiar(){
        level = acak.nextInt(3); //angka 0-2
        kiri = true;//hadap kiri
        y = level*acak.nextInt(5)*75 + acak.nextInt(150);
        switch(level){
            case 0: width = 50; height = 14; break;
            case 1: width = 100; height = 28; break;
            case 2: width = 150; height = 42; break;
        }    
        try {
            img = ImageIO.read(getClass().getResource("enemy_fish_"+level+".png"));
            //img = ImageIO.read(getClass().getResource("fish.png"));
            scaleIkan(width, height);            
        } catch (IOException ex) {
            Logger.getLogger(Ikan.class.getName()).log(Level.SEVERE, null, ex);
        }        
        
    }
    
    void setKiri(){
        //System.out.println("x: "+x);
        x = 0;
        //System.out.println("x: "+x);
        flipHorizontally();//hadap kanan
        kiri = false;
    }
    
    void setKanan(){
        x = 800;
        //System.out.println("x: "+x);
    }
    
    @Override
    public void move(){
        if(kiri){
            //x--;
            x -= acak.nextInt(5);
        }
        else {
            //x++;
            x += acak.nextInt(5);
        }
    }
    
    @Override
    public int getLevel(){
        return level;
    }
}
