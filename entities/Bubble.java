package entities;

public class Bubble extends ATower{

    public Bubble(int x, int y) {
        super(25, 10, 2, 1, "resources/bubblegun.png",x , y);
        
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
