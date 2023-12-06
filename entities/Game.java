package entities;
import screen.*;
import javax.swing.*;

import entities.AEnemy;
import entities.ATower;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Game implements Runnable {

    private Thread gameThread;
    private volatile boolean running = false;
    private List<AEnemy> _wave = new ArrayList<>();
    private List<ATower> _list_tower = new ArrayList<>();
    private int _gold = 30;
    private Map gameMap;
    private Sheep _sheep = new Sheep();

    public Game(){
        System.out.println("Game is starting...");
        populateWave();
        gameMap = new Map(this);
        
    }

    public void displayMapInWindow() {
        SwingUtilities.invokeLater(() -> {
                    gameMap.setVisible(true);
                });
    }

    public void startGame() {
        if (running) return;
        running = true;
        this.displayMapInWindow();
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void stopGame() {
        if (!running) return;
        running = false;
        try {
            gameThread.join();
            System.out.println("Game is stop...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (running) {
            updateGame();
            try {
                Thread.sleep(1000); // Mise à jour toutes les 1 secondes
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateGame() {
        // Mettre à jour la position des ennemis
        // ... Le reste de votre logique de mise à jour ...
    }

    // ... Le reste de vos méthodes ...

    private void populateWave() {
        
        _wave.add(new SmallAlien(3, 1));
        _wave.add(new NormalAlien(3, 1));
        _wave.add(new BigAlien(3, 1));

    }

    public int getGold(){

        return this._gold;

    }

    public void gainGold(int value){

        this._gold+=value;

    }

    public void loseGold(int value){

        if (this._gold-value<0){

            this._gold=0;

        }
        else {

            this._gold-=value;

        }


    }

    public Sheep getSheep(){

        return this._sheep;

    }

    public Map getGameMap(){

        return this.gameMap;
    }

    public List<AEnemy> getWave(){

        return this._wave;

    }

    public List<ATower> getTowers(){

        return this._list_tower;
    }

    public void display_towers(){

        for (int i =0; i<_list_tower.size();i++){

            System.out.println(_list_tower.get(i).getClass().getSimpleName());

        }

    }

    public void display_wave(){

        for (int i =0; i<_wave.size();i++){

            System.out.println(_wave.get(i).getClass().getSimpleName());

        }

    }

    public boolean addTower(ATower tower) {
        try {

            if (this.getGold()>=tower.getValue()){
                this._gold-=tower.getValue();
                _list_tower.add(tower);
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

    public void removeTower(ATower tower) {
        this._gold+=tower.getValue()/2;
        _list_tower.remove(tower);
    }

    public void removeEnemy(AEnemy enemy) {
        System.out.println(this.getGold());
        this._gold+=enemy.getValue();
        System.out.println(this.getGold());
        _wave.remove(enemy);
    }

    public boolean nearSheep(AEnemy enemy){

        if (enemy.getY()+enemy.getSpeed()>=this._sheep.getY()){

            enemy.hit(this._sheep);
            return true;

        }
        else {

            return false;

        }

    }

    public void towerAttack(AEnemy enemy){

        for (ATower tower : this._list_tower){

            tower.hit(enemy);

        }

    }

}
