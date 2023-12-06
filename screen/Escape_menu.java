package screen;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Escape_menu extends JFrame {
    private Map mapReference;
    public Escape_menu() {
        setTitle("Menu");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel winLabel = new JLabel("Menu", SwingConstants.CENTER);
        winLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JButton continueButton = new JButton("Continue");
        JButton retryButton = new JButton("New Game");
        JButton exitButton = new JButton("Exit");

        continueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (mapReference != null) {
                    mapReference.restartTimers(); // Redémarrez les timers dans la classe Map
                }
            
                continueGame();
            }
        });

        retryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (mapReference != null) {
                    mapReference.dispose(); // Redémarrez les timers dans la classe Map
                }
                ReturnToMenu();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        setLayout(new BorderLayout());
        add(winLabel, BorderLayout.NORTH);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(continueButton);
        buttonPanel.add(retryButton);
        buttonPanel.add(exitButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void continueGame() {
        SwingUtilities.invokeLater(() -> {
            this.dispose();
        });
        
    }

        private void ReturnToMenu() {
        SwingUtilities.invokeLater(() -> {
            GraphicsEnvironment graphics = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice device = graphics.getDefaultScreenDevice();
            start_menu ex = new start_menu();
            ex.pack();
            device.setFullScreenWindow(ex);
            ex.setVisible(true);
            this.dispose();
        });
        
    }

    public void setMapReference(Map map) {
        this.mapReference = map;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Escape_menu().setVisible(true);
            }
        });
    }
}

