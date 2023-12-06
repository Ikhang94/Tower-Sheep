package entities;

import javax.swing.ImageIcon;

public interface ITower {

    boolean hit(AEnemy alien);
    int getDmg();
    int getValue();
    String getImageName();
    int getX();
    int getY();
    boolean upgradeTower(Game jeux);

}
