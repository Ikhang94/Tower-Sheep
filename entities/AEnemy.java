package entities;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public abstract class AEnemy implements IEnemy{


    private int _hp;
    private int _dmg;
    private int _value;
    private int _speed;
    private int x, y;
    //private ImageIcon _img;
    private String _img_name;

    public AEnemy(int hp, int dmg, int value, int speed,String img_name, int x, int y){

        this._hp=hp;
        this._dmg=dmg;
        this._value=value;
        this._speed=speed;
        this.x = x;
        this.y = y;
        // this._img=new ImageIcon (getClass().getResource(img));
        // Image image = this._img.getImage();
        // Image newimg = image.getScaledInstance(55, 55, java.awt.Image.SCALE_SMOOTH);
        // this._img = new ImageIcon (newimg);
        this._img_name=img_name;
    }

    public int getDmg(){

        return this._dmg;

    }
    
    public int getValue(){

        return this._value;

    }

    public int getHp(){

        return this._hp;

    }

    public int getSpeed(){

        return this._speed;

    }

    public String getImageName(){

        return this._img_name;

    }

    public void setterHp(int new_value){

        this._hp=new_value;

    }
    
    public boolean hit(Sheep sheep){

        if (sheep.getHp()<=this._dmg){

            sheep.setterHp(0);
            return true;
        }

        else {

            sheep.setterHp(sheep.getHp()-this._dmg);
            return true;

        }

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x=x;
    }

    public void setY(int y) {
        this.y=y;
    }

    public abstract void move();    
    
}