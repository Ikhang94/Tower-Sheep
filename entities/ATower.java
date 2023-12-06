package entities;

import java.awt.Image;

import javax.swing.ImageIcon;

public abstract class ATower implements ITower{

    private int _dmg;
    private int _value;
    private int _range;
    private String _img_name;
    private int _targetNumber;
    private int _x,_y;

    public ATower(int dmg, int value, int range, int targetNumber,String name ,int x, int y){

        this._dmg=dmg;
        this._value=value;
        this._range=range;
        this._targetNumber=targetNumber;
        this._x=x;
        this._y=y;
        this._img_name=name;

    }

    public int getDmg(){

        return this._dmg;

    }

    public int getRange(){

        return this._range;

    }
    
    public int getValue(){

        return this._value;

    }

    public String getImageName(){

        return this._img_name;

    }

    public int getX() {
        return this._x;
    }

    public int getY() {
        return this._y;
    }

    public abstract boolean hit(AEnemy alien);

    protected boolean isEnemyInRange(AEnemy enemy){

        int distance=calculateDistance(enemy.getY(), this.getY());

        if (distance<=this._range && distance>= -this._range){

            return true;
        }
        else {

            return false;
        }

    }

    private int calculateDistance(int y_enemy, int y_tower){

        if  (y_enemy<y_tower){
            return y_tower-y_enemy;
        }
        else {
            return y_enemy-y_tower;
        }

    }

    public boolean upgradeTower(Game jeux){
        try {
            this._value+=20;
            if (jeux.getGold()>=this._value){
                jeux.gainGold(-_value);
                this._dmg+=20;
                this._value+=20;

                return true;

            }
            else {

                throw new GoldException();

            }
        } catch (GoldException e) {
            System.out.println(e.NotEnoughGold());
            return false;
        }

    }

}
