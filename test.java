

import javax.swing.SwingUtilities;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import entities.Game;
import screen.start_menu;

public class test {
    
    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(() -> {  

            GraphicsEnvironment graphics = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice device = graphics.getDefaultScreenDevice();
            start_menu ex = new start_menu();
            ex.pack();
            device.setFullScreenWindow(ex);
            ex.setVisible(true);
                });

        
    }

}
