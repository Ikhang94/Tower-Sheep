package entities;

import java.io.IOException;

public class NormalAlien extends AEnemy{

    public NormalAlien(int x, int y) {
        super(50, 10, 10,2,"resources/piccolo_sprite.png", x, y);

    }

    public void move() {
        setY(getY()+getSpeed());
    }  
    
}
