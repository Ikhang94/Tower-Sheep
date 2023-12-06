package entities;

public class Gatling extends ATower{

    public Gatling(int x, int y) {
        super(10, 20,3,1,"resources/gatling-gun_sprite.png" ,x, y );

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
