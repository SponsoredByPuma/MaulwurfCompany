import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import aview.GUI;

public class Main {

    public static void main(String[] args) {
        int breite = Integer.parseInt(JOptionPane.showInputDialog(null, "Wie breit soll das Spielfeld sein ?"));
        int hoehe = Integer.parseInt(JOptionPane.showInputDialog(null, "Wie hoch soll das Spielfeld sein ?"));
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUI(breite,hoehe).setVisible(true);
            }
            
        });
    }
}
