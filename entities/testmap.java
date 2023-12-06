package entities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class testmap extends JPanel {
    private int squareSize = 50;
    private int numRows = 7;
    private int numCols = 36;
    private int offsetX, offsetY;
    private boolean displayPosition = false;
    private int lastClickedRow = -1;
    private int lastClickedCol = -1;
    private ImageIcon spriteIcon;
    private ImageIcon outerSpriteIcon;
    private ImageIcon thirdSpriteIcon; 
    private ImageIcon fourthSpriteIcon;
    private ImageIcon fifthSpriteIcon;
    private Timer timer;
    private int spriteX = 0;
    private int spriteY = 0;
    private List<AEnemy> _wave = new ArrayList<>();
    //Game jeux=new Game();




    public testmap(List<AEnemy> _wave) {
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();

                int col = (x - offsetX) / squareSize;
                int row = (y - offsetY) / squareSize;

                if (row >= 0 && row < numRows && col >= 0 && col < numCols) {
                    displayPosition = true;
                    lastClickedRow = row;
                    lastClickedCol = col;
                    repaint();
                }
            }
        });

        try {
            outerSpriteIcon = new ImageIcon(getClass().getResource("/resources/tree.png"));
            Image image = outerSpriteIcon.getImage();
            Image newimg = image.getScaledInstance(55, 55, java.awt.Image.SCALE_SMOOTH);
            outerSpriteIcon = new ImageIcon(newimg);

            spriteIcon = new ImageIcon(getClass().getResource("/resources/grass.png"));
            Image image2 = spriteIcon.getImage();
            Image newimg2 = image2.getScaledInstance(55, 55, java.awt.Image.SCALE_SMOOTH);
            spriteIcon = new ImageIcon(newimg2);

            fourthSpriteIcon = new ImageIcon(getClass().getResource("/resources/rock_path.png"));
            Image image4 = fourthSpriteIcon.getImage();
            Image newimg4 = image4.getScaledInstance(55, 55, java.awt.Image.SCALE_SMOOTH);
            fourthSpriteIcon = new ImageIcon(newimg4);

            thirdSpriteIcon = new ImageIcon(getClass().getResource("/resources/sand.png"));
            Image image3 = thirdSpriteIcon.getImage();
            Image newimg3 = image3.getScaledInstance(55, 55, java.awt.Image.SCALE_SMOOTH);
            thirdSpriteIcon = new ImageIcon(newimg3);

            fifthSpriteIcon = new ImageIcon(getClass().getResource("/resources/tower_sheep.jpg"));
            Image image5 = fifthSpriteIcon.getImage();
            Image newimg5 = image5.getScaledInstance(55, 55, java.awt.Image.SCALE_SMOOTH);
            fifthSpriteIcon = new ImageIcon(newimg5);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur lors du chargement du sprite : " + e.getMessage());
        }

        timer = new Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                moveSprite();
                repaint();
            }
        });

    }

    private void moveSprite() {
        spriteX += 5;
        spriteY += 5;

        if (spriteX > getWidth() - spriteIcon.getIconWidth()) {
            spriteX = 0;
        }
        if (spriteY > getHeight() - spriteIcon.getIconHeight()) {
            spriteY = 0;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int panelWidth = getWidth();
        int panelHeight = getHeight();

        int totalWidth = numCols * squareSize;
        int totalHeight = numRows * squareSize;

        offsetX = (panelWidth - totalWidth) / 2;
        offsetY = (panelHeight - totalHeight) / 2;

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                int x = col * squareSize + offsetX;
                int y = row * squareSize + offsetY;



                // Dessinez le troisième sprite aux positions spécifiées
                if ((row == 2) && col != 0 && col != numCols - 1 || (row == 4) && col != 0 && col != numCols - 1) {
                    thirdSpriteIcon.paintIcon(this, g, x, y);
                } 
                
                else if ((row == 3) && (col != 0 && col != numCols - 1)) {
                    //System.out.println("yes");
                    fourthSpriteIcon.paintIcon(this, g, x, y);
                }

                else if ((row == 3) && (col == 35)){
                    //System.out.println("yes");
                    fifthSpriteIcon.paintIcon(this, g, x, y);
                }
                
                else {
                    // Dessinez les autres sprites au même emplacement
                    boolean isOuterSquare = row == 0 || row == numRows - 1 || col == 0 || col == numCols - 1;
                    if (isOuterSquare) {
                        spriteIcon.paintIcon(this, g, x, y);
                        outerSpriteIcon.paintIcon(this, g, x, y);
                    } else {
                        spriteIcon.paintIcon(this, g, x, y);
                    }
                }


                    

                if (displayPosition && row == lastClickedRow && col == lastClickedCol) {
                    
                    //menuTower(g);

                }




            }
        }

    }

    public void menuTower(Graphics g){

        // JButton btn = new JButton("button1"); 
        // JLabel titre = new JLabel("Menu Tower");
        // JPanel menu_tower = new JPanel();
        // menu_tower.add(titre);
        // menu_tower.setBounds(750,750,300,200);
        // menu_tower.setBackground(Color.lightGray);
        // this.add(menu_tower);

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        int initialWidth = 1920;
        int initialHeight = 1080;
        frame.setSize(initialWidth, initialHeight);
        frame.setTitle("Map");
        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                int newWidth = frame.getWidth();
                int newHeight = frame.getHeight();
                frame.setSize(newWidth, newHeight);
            }
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //testmap map=new testmap();
        //map.menuTower();
        //frame.add(map);
        frame.setVisible(true);
    }
}
