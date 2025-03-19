package Main;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

public class Game_Window {
    private JFrame fr;
    public Game_Window(Game_Panel gp){
        fr=new JFrame();

        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fr.add(gp);

        fr.setResizable(false);
        fr.pack();
        fr.setLocationRelativeTo(null);
        fr.setVisible(true);
        fr.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                gp.getGame().windowFocustLost();

            }
        });

    }
}
