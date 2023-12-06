package entities;

public class Laser extends ATower{

    public Laser(int x, int y) {
        super(100, 100,1,1,"resources/laser-gun_sprite.png", x, y);

    }
    
    public boolean hit(AEnemy alien){

        if (isEnemyInRange(alien)){
            if (alien.getHp()<=this.getDmg()){

            alien.setterHp(0);
            return true;
        }

        else {

            alien.setterHp(alien.getHp()-this.getDmg());
            return true;

        }
        }
        else {
            return false;
        }

    }
}
