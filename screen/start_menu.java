package screen;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
import entities.Game;

public class start_menu extends JFrame {

    private String[] weaponsData = {"Bubble Gun", "Gatling Gun", "Laser Gun"};
    private String[] aliensData = {"Normal Alien", "Big Alien", "Little Alien"};
    private Map<String, String> weaponStats;
    private Map<String, String> alienStats;
    private JLabel weaponStatsLabel;
    private JLabel alienStatsLabel;
    private JLabel weaponImageLabel;
    private JLabel alienImageLabel;

    public start_menu() {
        setTitle("Tower Defense Menu");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        weaponImageLabel = new JLabel();
        alienImageLabel = new JLabel();
        initializeStats();
        pack();
        initUI();
    }

    private void initUI() {
        JPanel menuPanel = new JPanel(new BorderLayout());

        weaponStatsLabel = new JLabel("Click on a weapon");
        weaponStatsLabel.setFont(new Font("Arial", Font.BOLD, 24));
        weaponStatsLabel.setForeground(Color.WHITE); // Texte en blanc
        weaponStatsLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        alienStatsLabel = new JLabel("Click on a alien");
        alienStatsLabel.setFont(new Font("Arial", Font.BOLD, 24));
        alienStatsLabel.setForeground(Color.WHITE); // Texte en blanc
        alienStatsLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> startGame());
        startButton.setFont(new Font("Arial", Font.BOLD, 30));
        startButton.setOpaque(true);
        startButton.setBorderPainted(false);
        startButton.setBackground(Color.GREEN);

        JButton quitButton = new JButton("Exit");
        quitButton.addActionListener(e -> leaveGame());
        quitButton.setFont(new Font("Arial", Font.BOLD, 30));
        quitButton.setOpaque(true);
        quitButton.setBorderPainted(false);
        quitButton.setBackground(Color.RED);

        JPanel weaponsInfoPanel = createListPanel("List of Weapons", weaponsData, weaponStats, weaponStatsLabel);
        JPanel enemiesInfoPanel = createListPanel("List of Aliens", aliensData, alienStats, alienStatsLabel);


        JLabel titleLabel = new JLabel();
        ImageIcon originalIcon = new ImageIcon("resources/towerdefense2.jpg");
        Image image = originalIcon.getImage();
        Image newimg = image.getScaledInstance(500, 500,  java.awt.Image.SCALE_SMOOTH);

        Icon titleIcon = new ImageIcon(newimg);
        titleLabel.setIcon(titleIcon);

       // Panneau supérieur pour le titre et les images
       JPanel topPanel = new JPanel(new FlowLayout());
       topPanel.add(enemiesInfoPanel);
       topPanel.add(titleLabel);
       topPanel.add(weaponsInfoPanel);
       menuPanel.add(topPanel, BorderLayout.NORTH);

       // Panneau central pour les listes et statistiques
       JPanel centerPanel = new JPanel(new GridLayout(1, 4, 40, 0));
       centerPanel.add(alienStatsLabel);
       centerPanel.add(alienImageLabel);
       centerPanel.add(weaponImageLabel);
       centerPanel.add(weaponStatsLabel);
       menuPanel.add(centerPanel, BorderLayout.CENTER);

       // Panneau inférieur pour les boutons
       JPanel bottomPanel = new JPanel(new FlowLayout());
       bottomPanel.add(startButton);
       bottomPanel .add(quitButton);
       menuPanel.add(bottomPanel, BorderLayout.SOUTH);

       getContentPane().add(menuPanel);
       centerPanel.setBackground(new Color(4, 25, 72)); // Bleu nuit
        topPanel.setBackground(new Color(4, 25, 72)); // Bleu nuit
        bottomPanel.setBackground(new Color(4, 25, 72)); // Bleu nuit
       pack(); // Ajuste la taille du cadre
    }

    private void initializeStats() {
        weaponStats = new HashMap<>();
        alienStats = new HashMap<>();

        weaponStats.put("Bubble Gun", "Damage: 25, Range: 3, Target: Single, Speed: 1 shoot every turn, Cost: 10G");
        weaponStats.put("Gatling Gun", "Damage: 10, Range: 4, Target: Single, Speed: 2 shoot every turn, Cost: 20G");
        weaponStats.put("Laser Gun", "Damage: 100, Range: 1, Target: Single, Speed: 1 shoot every 3 turn, Cost: 100G");

        alienStats.put("Normal Alien", "Health: 50, Damage: 10, Speed: 1, Reward: 10G");
        alienStats.put("Big Alien", "Health: 100, Damage: 20, Speed: 0,5, Reward: 30G");
        alienStats.put("Little Alien", "Health: 25, Damage: 5, Speed: 2, Reward: 5G");
    }

    private JPanel createListPanel(String title, String[] data, Map<String, String> statsMap, JLabel statsLabel) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(title));

        JList<String> list = new JList<>(data);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setFont(new Font("Arial", Font.BOLD, 24));

        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    String selectedValue = list.getSelectedValue();
                    String stats = statsMap.get(selectedValue);

                    String statsWithLineBreaks = stats.replace(", ", "<br/>");

                    statsLabel.setText("<html>" + selectedValue + " Stats:<br/>" + statsWithLineBreaks + "</html>");
                    statsLabel.setFont(new Font("Arial", Font.BOLD, 24));

                    if (Arrays.asList(weaponsData).contains(selectedValue)) {
                        updateWeaponImage(selectedValue);
                    } else if (Arrays.asList(aliensData).contains(selectedValue)) {
                        updateAlienImage(selectedValue);
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(list);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private ImageIcon resizeImageIcon(String imagePath, int width, int height) {
        ImageIcon imageIcon = new ImageIcon(imagePath);
        Image image = imageIcon.getImage();
        Image resizedImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    private void updateWeaponImage(String weaponName) {
        int imageWidth = 280;
        int imageHeight = 280;

        switch (weaponName) {
            case "Bubble Gun":
                weaponImageLabel.setIcon(resizeImageIcon("resources/bubblegun-removebg.png", imageWidth, imageHeight));
                break;
            case "Gatling Gun":
                weaponImageLabel.setIcon(resizeImageIcon("resources/gatling-gun.png", imageWidth, imageHeight));
                break;
            case "Laser Gun":
                weaponImageLabel.setIcon(resizeImageIcon("resources/laser-gun.png", imageWidth, imageHeight));
                break;
            default:
                weaponImageLabel.setIcon(null);
        }
    }

    private void updateAlienImage(String alienName) {
        int imageWidth = 280;
        int imageHeight = 280;
    
        switch (alienName) {
            case "Normal Alien":
                alienImageLabel.setIcon(resizeImageIcon("resources/Piccolo_DBZ.png", imageWidth, imageHeight));
                break;
            case "Big Alien":
                alienImageLabel.setIcon(resizeImageIcon("resources/boo.png", imageWidth, imageHeight));
                break;
            case "Little Alien":
                alienImageLabel.setIcon(resizeImageIcon("resources/normal_alien.png", imageWidth, imageHeight));
                break;
            default:
                alienImageLabel.setIcon(null);
        }
    }

    private void startGame() {
        Game gameInstance = new Game();
        gameInstance.startGame();
        this.setVisible(false);
        //this.dispose(); // Close window
    }

    public void leaveGame() {
        System.exit(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            start_menu ex = new start_menu();
            ex.pack();
            ex.setVisible(true);
        });
    }
}
