package entities;

import java.io.IOException;

public class BigAlien extends AEnemy{

    public BigAlien(int x, int y) {
        super(100, 20, 30,1,"resources/boo_sprite.png", x, y);

    }
    public void move() {
        setY(getY()+getSpeed());
    }    
    
}
