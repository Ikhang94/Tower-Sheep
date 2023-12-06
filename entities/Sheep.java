package entities;

public class Sheep extends ATower{

    public Sheep() {
        super(999999, 999999, 999999,999999,"resources/tower_sheep_sprite.png",3,34);

    }

    private int _hp=100;

    public int getHp(){

        return this._hp;

    }

    public void setterHp(int new_value){

        this._hp=new_value;

    }

    public boolean hit(AEnemy alien){
        return false;
    }
    
}
