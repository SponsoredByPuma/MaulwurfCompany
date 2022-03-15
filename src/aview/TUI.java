package aview;

import java.util.Scanner;

import javax.swing.JOptionPane;

import controller.Controller;

public class TUI {

    public TUI() {
        int breite = Integer.parseInt(JOptionPane.showInputDialog(null, "Wie breit soll das Feld sein ?"));
        int hoehe = Integer.parseInt(JOptionPane.showInputDialog(null, "Wie hoch soll das Feld sein ?"));
        Controller c = new Controller(breite,hoehe);

        Scanner sc = new Scanner(System.in);
        String choice = "";
        Boolean tr = false;

        while(! choice.equals("quit")) {

            System.out.println("Type in Move to move!");
            choice = sc.next();

            switch(choice) {
                case "Move":
                   // c.move(tr, 0, 4, true); // team, number, schritte, hoch oder seitlich
                    choice = sc.next();
                    break;
            }

        }

    }
}
