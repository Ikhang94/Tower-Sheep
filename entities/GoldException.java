package entities;

public class GoldException extends Exception{
    
    public GoldException(){

       super(NotEnoughGold());

    }

    public static String NotEnoughGold(){

        return "Not enough gold";

    }

}
