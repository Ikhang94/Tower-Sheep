package screen;
import javax.swing.*;

import entities.Game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class lose_menu extends JFrame {
    public lose_menu() {
        setTitle("Game Over");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel loseLabel = new JLabel("Game Over", SwingConstants.CENTER);
        loseLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JButton retryButton = new JButton("Retry");
        JButton exitButton = new JButton("Exit");

        retryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                ReturnToMenu();
                           
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        setLayout(new BorderLayout());
        add(loseLabel, BorderLayout.NORTH);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(retryButton);
        buttonPanel.add(exitButton);
        add(buttonPanel, BorderLayout.SOUTH);
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

    // public static void main(String[] args) {
    //     SwingUtilities.invokeLater(new Runnable() {
    //         public void run() {
    //             new lose_menu().setVisible(true);
    //         }
    //     });
    // }
}
