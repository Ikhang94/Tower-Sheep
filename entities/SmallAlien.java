package entities;

import java.io.IOException;

public class SmallAlien extends AEnemy{

    public SmallAlien(int x, int y) {
        super(25, 5, 5,3,"resources/normal_enemy_sprite.png" ,x, y);

    }

    public void move() {
        setY(getY()+getSpeed());
    }  
    
}
