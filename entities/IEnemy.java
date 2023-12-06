package entities;

import java.awt.Image;

import javax.swing.ImageIcon;

public interface IEnemy {
    
    //boolean hit(Sheep sheep);
    int getDmg();
    int getValue();
    int getHp();
    int getSpeed();
    String getImageName();
    void setterHp(int new_value);
    abstract void move();
    int getX();
    public int getY();

}
