package screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import entities.*;

class TilePanel extends JPanel {
    private Image image;
    private Map map_reference;
    private String ImageId;
    public TilePanel(String imagePath, Map map) {
        this.map_reference = map;
        try {
            this.image = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setPreferredSize(new Dimension(50, 50)); // Taille des tuiles

        String imageId;

        if (imagePath.equals("resources/sand.png")) {
            imageId = "SAND";
        } else if (imagePath.equals("resources/bubblegun.png")) {
            imageId = "BUBBLE_GUN";
        } else if (imagePath.equals("resources/gatling-gun_sprite.png")) {
            imageId = "GATLING_GUN";
        } else if (imagePath.equals("resources/laser-gun_sprite.png")) {
            imageId = "LASER_GUN";
        } else {
            imageId = "OTHER"; // Identifiant pour d'autres types d'images
        }

        ImageId = imageId; // Utilisation de l'identifiant final dans la portée interne

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println(ImageId);
                if (SwingUtilities.isLeftMouseButton(e)) {
                    if (ImageId.equals("SAND")) {
                        displayTowerMenu(e.getX(), e.getY());
                    } else if (ImageId.equals("BUBBLE_GUN") || ImageId.equals("GATLING_GUN") || ImageId.equals("LASER_GUN")) {
                        displayRemoveTowers(e.getX(), e.getY());
                    }
                    
                }
            }
        });
    }
    public String getFinalImageId(){

        return this.ImageId;

    }
        public void setImageId(String idImage){

            this.ImageId=idImage;

    }

    private void displayTowerMenu(int clickX, int clickY) {
        JPopupMenu towerMenu = new JPopupMenu("Choose Tower");
        // Ajoute des options de tour au menu
        JMenuItem towerOption1 = new JMenuItem("Bubble");
        JMenuItem towerOption2 = new JMenuItem("Gatling");
        JMenuItem towerOption3 = new JMenuItem("Laser");
        //JMenuItem towerOption4 = new JMenuItem("Remove");
        // System.out.println(clickX);
        // System.out.println(clickY);
        Point clickPoint = new Point(clickX, clickY);
        SwingUtilities.convertPointToScreen(clickPoint, this);
        int screenClickX = (int) clickPoint.getX();
        int screenClickY = (int) clickPoint.getY();
        // System.out.println(screenClickX);
        // System.out.println(screenClickY);

        towerMenu.add(towerOption1).addActionListener(e ->map_reference.createTower(screenClickX,screenClickY,new Bubble(screenClickX,screenClickY)));
        towerMenu.add(towerOption2).addActionListener(e ->map_reference.createTower(screenClickX,screenClickY,new Gatling(screenClickX,screenClickY)));
        towerMenu.add(towerOption3).addActionListener(e ->map_reference.createTower(screenClickX,screenClickY,new Laser(screenClickX,screenClickY)));
        //towerMenu.add(towerOption4).addActionListener(e -> map_reference.removeTower(screenClickX, screenClickY));
        towerMenu.show(this, 0, 0); // Affiche le menu à la position du clic
    }

    private void displayRemoveTowers(int clickX, int clickY){

        JPopupMenu towerMenu = new JPopupMenu("Remove Tower");
        JMenuItem towerOption1 = new JMenuItem("Remove");
        JMenuItem towerOption2 = new JMenuItem("Upgrade");
        Point clickPoint = new Point(clickX, clickY);
        SwingUtilities.convertPointToScreen(clickPoint, this);
        int screenClickX = (int) clickPoint.getX();
        int screenClickY = (int) clickPoint.getY();
        towerMenu.add(towerOption1).addActionListener(e ->map_reference.removeTower(screenClickX, screenClickY));
        towerMenu.add(towerOption2).addActionListener(e ->map_reference.upgradeTower(screenClickX, screenClickY));
        towerMenu.show(this, 0, 0);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }

    public void setImage(String imageName) {

        try {
            this.image = ImageIO.read(new File(imageName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setPreferredSize(new Dimension(50, 50)); // Taille des tuiles

    }
}

public class Map extends JFrame {
    private final int WINDOW_WIDTH = 1980;
    private final int WINDOW_HEIGHT = 1080;
    private final int MAP_WIDTH = 600; // Nouvelle largeur de la carte
    private final int MAP_HEIGHT = 400; // Nouvelle hauteur de la carte
    private final int TILE_SIZE = 50; // Taille de chaque tuile/case

    private int elapsedTimeInSeconds = 0;

    private int rows, cols; // Nombre de lignes et de colonnes de tuiles
    private JPanel mapPanel; // Panneau pour la carte
    private Game jeux;
    private JLabel labelSheepHP;
    private Timer updateTimer = new Timer(1000, e -> updateTimer());
    private Timer waveTimer = new Timer(1000, e -> updateDisplay());
    private JPanel infoPanel;
    private JLabel GoldLabel;
    private JLabel Timerlabel;

    public Map(Game jeux) {
        setTitle("Tower Defense Map");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        this.jeux=jeux;
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setPreferredSize(new Dimension(MAP_WIDTH, MAP_HEIGHT)); // Ajuste la taille du panneau principal

        // Initialisation de la carte avec des tuiles (cases)
        cols = 35;//MAP_WIDTH / TILE_SIZE;
        rows = 7;//MAP_HEIGHT / TILE_SIZE;


        mapPanel = new JPanel(new GridLayout(rows, cols));
        mapPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        infoPanel = new JPanel(new BorderLayout());
        labelSheepHP = new JLabel("Sheep HP : "+String.valueOf(jeux.getSheep().getHp()), SwingConstants.RIGHT);
        labelSheepHP.setForeground(Color.BLACK);
        labelSheepHP.setFont(new Font("Arial", Font.BOLD, 24));

        GoldLabel = new JLabel("Gold : "+String.valueOf(jeux.getGold()), SwingConstants.CENTER);
        GoldLabel.setForeground(Color.BLACK);
        GoldLabel.setFont(new Font("Arial", Font.BOLD, 24));

        Timerlabel = new JLabel("Time : 00:00", SwingConstants.LEFT);
        Timerlabel.setForeground(Color.BLACK);
        Timerlabel.setFont(new Font("Arial", Font.BOLD, 24));

        infoPanel.add(GoldLabel, BorderLayout.NORTH);
        infoPanel.add(labelSheepHP, BorderLayout.SOUTH);
        infoPanel.add(Timerlabel, BorderLayout.CENTER);
        getContentPane().add(infoPanel, BorderLayout.NORTH);
        //getContentPane().add(labelSheepHP, BorderLayout.SOUTH);

        // Chargement d'une image pour les tuiles (exemple : herbe)
        String grassPath = "resources/grass.png";
        String treePath = "resources/tree_on_grass.png";
        String sandPath = "resources/sand.png";
        String rock_pathPath = "resources/rock_path.png";
        String sheep_path = "resources/tower_sheep_sprite.png";
        String spaceship_path="resources/spaceship_sprite.png";
        // Création et ajout des tuiles à la carte sans bordures
        for (int i = 0; i < rows; i++) {
            for (int j =0;j<cols;j++){

                if (i==3 && j==34){

                    TilePanel tilerock = new TilePanel(sheep_path, this);
                    mapPanel.add(tilerock);

                }

                else if (i==3 && j==0){

                    TilePanel tilespaceship = new TilePanel(spaceship_path, this);
                    mapPanel.add(tilespaceship);

                }

                else if ((i==0 || j==0 ||i==rows-1 ||j==cols-1)&&i!=3){

                    // TilePanel tile = new TilePanel(grassPath);
                    // mapPanel.add(tile);
                    TilePanel tiletree = new TilePanel(treePath, this);
                    mapPanel.add(tiletree);

                }

                else if (i==2 && j!=0 && j!=cols-1 || i==4 && j!=0 && j!=cols-1){

                    TilePanel tilesand = new TilePanel(sandPath, this);
                    mapPanel.add(tilesand);

                }



                else if (i==3 && j!=0 && j!=cols-1){

                    TilePanel tilerock = new TilePanel(rock_pathPath, this);
                    mapPanel.add(tilerock);

                }
                

                
                else{

                    TilePanel tile = new TilePanel(grassPath, this);
                    mapPanel.add(tile);

                }


            }

        }

        Action escapeAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                escapeMenu();
                updateTimer.stop();
                waveTimer.stop();
                
            }
        };
        mainPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "escape");
        mainPanel.getActionMap().put("escape", escapeAction);

        JButton startWaveButton = new JButton("Start Wave");
        startWaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                //createWave(); // Appel de la méthode pour créer une vague d'ennemis
                // startEnemyMovement();
                for (AEnemy enemy : jeux.getWave()) {
                
                    SpawnEnnemy(enemy);
            }
                updateTimer.start();
                waveTimer.start();
                mainPanel.remove(startWaveButton);
                getContentPane().remove(startWaveButton);

                mapPanel.revalidate();
                mapPanel.repaint();
            }
        });
        getContentPane().add(startWaveButton, BorderLayout.SOUTH);
        //mainPanel.add(startWaveButton);
        //mainPanel.add(labelSheepHP);

        mainPanel.add(mapPanel); // Positionne la carte au centre du panneau principal
        add(mainPanel);

    }


public void restartTimers() {
    updateTimer.start();
    waveTimer.start();
}

    private void updateTimer() {
        int minutes = elapsedTimeInSeconds / 60;
        int seconds = elapsedTimeInSeconds % 60;
        String formattedTime = String.format("Time : %02d:%02d", minutes, seconds);
        Timerlabel.setText(formattedTime);
        elapsedTimeInSeconds++;
        jeux.gainGold(5);
        GoldLabel.setText("Gold : "+String.valueOf(jeux.getGold()));
        //updateTimer();
    }

    public void updateDisplay() {
        SwingUtilities.invokeLater(() -> {
            //jeux.display_towers();
            if (jeux.getWave().size()==0){

                wingame();
                updateTimer.stop();
                waveTimer.stop();
                jeux.stopGame();
                this.dispose();

            }
            for (AEnemy enemy : jeux.getWave()) {
                if (jeux.nearSheep(enemy)){
                    jeux.getWave().remove(enemy);
                    RemoveSprite(enemy.getY());
                    
                    //System.out.println(jeux.getSheep().getHp());
                    this.labelSheepHP.setText("Sheep HP : "+String.valueOf(jeux.getSheep().getHp()));

                }

                else {
                    jeux.towerAttack(enemy);
                    //System.out.println(enemy.getHp());
                    GoldLabel.setText("Gold : "+String.valueOf(jeux.getGold()));
                    if (enemy.getHp()<=0){
                        jeux.removeEnemy(enemy);
                        
                        RemoveSprite(enemy.getY());

                    }
                    else {
                        int pastY=enemy.getY();              
                        enemy.move();
                        MoveSprite(enemy);
                        RemoveSprite(pastY);
                    }

                    
                    //System.out.println(enemy.getY());

                }


            }
            mapPanel.revalidate();
            mapPanel.repaint();

            if (jeux.getSheep().getHp()<=0) {

                losegame();
                jeux.stopGame();
                updateTimer.stop();
                waveTimer.stop();
                //this.dispose();

            }

        });
    }

    public void losegame(){

    SwingUtilities.invokeLater(new Runnable() {
        public void run() {
            new lose_menu().setVisible(true);
        }
    });

}

public void wingame(){

    SwingUtilities.invokeLater(new Runnable() {
        public void run() {
            new win_menu().setVisible(true);
        }
    });
}

public void escapeMenu(){

    Escape_menu escapeMenu = new Escape_menu();
    escapeMenu.setMapReference(this); // Faites passer une référence de la classe Map à votre menu d'échappement
    escapeMenu.setVisible(true);
    updateTimer.stop();
    waveTimer.stop();

    
}

public void createTower(int x, int y, ATower tower) {
    
    Point panelPos = mapPanel.getLocationOnScreen();
    int relativeX = x - (int) panelPos.getX();
    int relativeY = y - (int) panelPos.getY();

    int row = relativeY / 50;
    int col = relativeX / 50;

    int tileIndex = row * 35 + col;
    System.out.println(tileIndex);

    Component[] components = mapPanel.getComponents();

    for (Component component : components) {
        if (component instanceof TilePanel) {
            int componentX = component.getX();
            int componentY = component.getY();
            int componentRow = componentY / 50;
            int componentCol = componentX / 50;

            if (componentRow == row && componentCol == col) {
                TilePanel tilePanel = (TilePanel) component;
                // Mettre à jour la tour existante ici
                if (tower instanceof Bubble) {
                    Bubble bubbleTower = new Bubble(row, col);
                    if (jeux.addTower(bubbleTower)){
                        tilePanel.setImage(bubbleTower.getImageName());
                        tilePanel.setImageId("BUBBLE_GUN");
                    }
                } else if (tower instanceof Gatling) {
                    Gatling gatlingTower = new Gatling(row, col);
                    if (jeux.addTower(gatlingTower)){
                        tilePanel.setImage(gatlingTower.getImageName());
                        tilePanel.setImageId("GATLING_GUN");
                    }

                } else if (tower instanceof Laser) {
                    Laser laserTower = new Laser(row, col);
                    if(jeux.addTower(laserTower)){
                        tilePanel.setImage(laserTower.getImageName());
                        tilePanel.setImageId("LASER_GUN");
                    }
                }
                // System.out.println(row);
                // System.out.println(col);
                GoldLabel.setText("Gold : "+String.valueOf(jeux.getGold()));
                mapPanel.revalidate();
                mapPanel.repaint();
            }
        }
    }

}

public void upgradeTower(int x, int y){

    Point panelPos = mapPanel.getLocationOnScreen();
    int relativeX = x - (int) panelPos.getX();
    int relativeY = y - (int) panelPos.getY();

    int row = relativeY / 50;
    int col = relativeX / 50;
    Component[] components = mapPanel.getComponents();

    for (Component component : components) {
        if (component instanceof TilePanel) {
            int componentX = component.getX();
            int componentY = component.getY();
            int componentRow = componentY / 50;
            int componentCol = componentX / 50;

            if (componentRow == row && componentCol == col) {
                ATower tower=findTower(row, col);
                if (tower.upgradeTower(jeux)){

                    if (tower instanceof Bubble){

                        TilePanel tilePanel = (TilePanel) component;
                        tilePanel.setImage("resources/bubblegunUpgraded_sprite.png");
                        GoldLabel.setText("Gold : "+String.valueOf(jeux.getGold()));
                        mapPanel.revalidate();
                        mapPanel.repaint();
                        break;
                    }

                    if (tower instanceof Gatling){

                        TilePanel tilePanel = (TilePanel) component;
                        tilePanel.setImage("resources/gatlingUpgraded_sprite.png");
                        GoldLabel.setText("Gold : "+String.valueOf(jeux.getGold()));
                        mapPanel.revalidate();
                        mapPanel.repaint();
                        break;
                    }
                }

            }
        }
    }

}

public void removeTower(int x, int y) {
    Point panelPos = mapPanel.getLocationOnScreen();
    int relativeX = x - (int) panelPos.getX();
    int relativeY = y - (int) panelPos.getY();

    int row = relativeY / 50;
    int col = relativeX / 50;

    //int tileIndex = row * 35 + col;

    Component[] components = mapPanel.getComponents();

    for (Component component : components) {
        if (component instanceof TilePanel) {
            int componentX = component.getX();
            int componentY = component.getY();
            int componentRow = componentY / 50;
            int componentCol = componentX / 50;

            if (componentRow == row && componentCol == col) {
                ATower tower=findTower(row, col);
                jeux.removeTower(tower);
                TilePanel tilePanel = (TilePanel) component;
                tilePanel.setImage("resources/sand.png");
                tilePanel.setImageId("SAND");
                GoldLabel.setText("Gold : "+String.valueOf(jeux.getGold()));
                mapPanel.revalidate();
                mapPanel.repaint();
                break;
            }
        }
    }
}

    public ATower findTower(int row, int col){

        for (ATower tower : jeux.getTowers()){

            if (tower.getX()==row && tower.getY()==col){

                return tower;

            } 

        }
        return null;

    }

    public Void SpawnEnnemy(AEnemy ennemy){
        Component[] components = mapPanel.getComponents();
        int row =3, col = 1;
        for (Component component : components) {
        if (component instanceof TilePanel) {
            int componentX = component.getX();
            int componentY = component.getY();
            int componentRow = componentY / 50;
            int componentCol = componentX / 50;

            if (componentRow == row && componentCol == col) {
                TilePanel tilePanel = (TilePanel) component;

                    tilePanel.setImage(ennemy.getImageName());
                    mapPanel.revalidate();
                    mapPanel.repaint();
                    break;
            }
        }
    }        

        return null;
    }

    public Void MoveSprite(AEnemy ennemy){
        Component[] components = mapPanel.getComponents();
        for (Component component : components) {
        if (component instanceof TilePanel) {
            int componentX = component.getX();
            int componentY = component.getY();
            int componentRow = componentY / 50;
            int componentCol = componentX / 50;

            if (componentRow == ennemy.getX() && componentCol == ennemy.getY()) {
                TilePanel tilePanel = (TilePanel) component;

                    tilePanel.setImage(ennemy.getImageName());
                    mapPanel.revalidate();
                    mapPanel.repaint();
                    break;
            }
        }
    }        

        return null;
    }

    public Void RemoveSprite(int pastY){

    Component[] components = mapPanel.getComponents();
        for (Component component : components) {
        if (component instanceof TilePanel) {
            int componentX = component.getX();
            int componentY = component.getY();
            int componentRow = componentY / 50;
            int componentCol = componentX / 50;

            if (componentRow == 3 && componentCol == pastY) {
                    TilePanel tilePanel = (TilePanel) component;
                    tilePanel.setImage("resources/rock_path.png");
                    mapPanel.revalidate();
                    mapPanel.repaint();
                    break;
            }
        }        

    }
    
        return null;
}

}