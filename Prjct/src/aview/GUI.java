package aview;

import javax.swing.*;

import controller.Controller;
import model.Feld;
import model.Maulwurf;

import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUI extends JPanel{

    private JComboBox numberChooser = new JComboBox<>();
    private JLabel schritteAnzeige = new JLabel();
    private JButton b1 = new JButton("Move");
    private JCheckBox booUp = new JCheckBox("Up");
    private JCheckBox boodown = new JCheckBox("Down");
    private JCheckBox booleft = new JCheckBox("Left");
    private JCheckBox booright = new JCheckBox("Right");
    private boolean teams = true;
    private JLabel teamAnzeige= new JLabel("Rot");
    private ButtonGroup group = new ButtonGroup();

    private ArrayList<ArrayList<Integer>> spielbrett;
    private Feld f;

    private Controller controller;

    public GUI(int breite, int hoehe) {
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String wahl = "";
                if(booUp.isSelected()) {
                    wahl = "up";
                }
                if(boodown.isSelected()) {
                    wahl = "down";
                }
                if(booleft.isSelected()) {
                    wahl = "left";
                }
                if(booright.isSelected()) {
                    wahl = "right";
                }
                controller.move(teams, Integer.parseInt(numberChooser.getSelectedItem().toString()), Integer.parseInt(schritteAnzeige.getText()), wahl);
                teams = !teams;
                numberChooser.removeAllItems();
                if(teams) {
                    teamAnzeige.setText("Rot");
                    int tmp2 = controller.getSchrittRot();
                    schritteAnzeige.setText(Integer.toString(tmp2));
                } else {
                    teamAnzeige.setText("Blau");
                    int tmp2 = controller.getSchritteBlau();
                    schritteAnzeige.setText(Integer.toString(tmp2));
                }

                if(teamAnzeige.getText().equals("Rot")) {
                    ArrayList<Maulwurf> tmp = new ArrayList<>();
                    tmp = controller.getTeamRot();
                    for(Maulwurf mw : tmp) {
                        if(mw.getMovement() == true) {
                            numberChooser.addItem(mw.getNumber());
                        }
                    }
                } else {
                    ArrayList<Maulwurf> tmp = new ArrayList<>();
                    tmp = controller.getTeamBlau();
                    for(Maulwurf mw : tmp) {
                        if(mw.getMovement() == true) {
                            numberChooser.addItem(mw.getNumber());
                        }
                    }
                }
                if(controller.checkforVictory() == true) {
                    paintVictory();
                    
                } else {
                    repaint();
                }
            }
        });
        
        b1.setBounds(500, 1000, 50, 50);
        booUp.setBounds(300,1000,50,50);
        teamAnzeige.setBounds(225,1000,50,50);
        numberChooser.setBounds(150,1000,50,50);
        schritteAnzeige.setBounds(700,1000,200,100);
        this.add(numberChooser);
        this.add(schritteAnzeige);
        this.add(teamAnzeige);
        group.add(boodown);
        group.add(booleft);
        group.add(booright);
        group.add(booUp);

        this.add(boodown);
        this.add(booleft);
        this.add(booright);
        this.add(booUp);

        this.add(b1);
        controller = new Controller(breite,hoehe);
        int speicher = controller.startTheGame();
        schritteAnzeige.setText(Integer.toString(speicher));
        f = controller.getSpielBrett();

        if(!controller.einLoch()) {
            ArrayList<Maulwurf> tmp = new ArrayList<>();
            tmp = controller.getTeamRot();
            for(Maulwurf mw : tmp) {
                if(mw.getMovement() == true) {
                    numberChooser.addItem(mw.getNumber());
                }
            }
        }  

        this.setPreferredSize(new Dimension(1000,1000));
        JFrame mainframe = new JFrame();
        mainframe.add(this);
        mainframe.pack();
        mainframe.setTitle("Maulwurf Company");
        mainframe.setDefaultCloseOperation(mainframe.EXIT_ON_CLOSE);
        mainframe.setVisible(true);

    }

    private void drawLines(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        this.setBackground(Color.gray);
        float[] dashingPattern1 = {2f,2f};
        Stroke linien = new BasicStroke(2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f, dashingPattern1, 2.0f);
        spielbrett = f.getSpielBrett();
        for(int i = 0;i < f.gethoehe(); i++) {
            for(int j = 0; j < f.getBreite(); j++) {
                if(j == 0) {
                    g2d.setStroke(linien);
                    g2d.setColor(new Color(150,75,0));
                    g2d.drawLine(50, 50 + (i * 100), 150, 50 + (i * 100)); 
                    if(i != 0 && i != f.gethoehe() - 1) {
                        g2d.drawLine(50 + (j * 100), -25 + (i * 100), 50 + (j * 100), 100 + (i* 100));
                    } else {
                        if(i == 0) {
                            g2d.drawLine(50 + (j * 100), 50 , 50 + (j * 100), 200);
                        } else {
                            g2d.drawLine(50 + (j * 100),-25 +  (i * 100), 50 + (j * 100), 50 + (i* 100));
                        }
                    }
                } else {
                    if (j == f.getBreite() - 1) {
                    g2d.setStroke(linien);
                    g2d.setColor(new Color(150,75,0));
                    if(i != 0 && i != f.gethoehe() - 1) {
                        g2d.drawLine(50 + (j * 100), -25 + (i * 100), 50 + (j * 100), 100 + (i* 100));
                    } else {
                        if (i == 0) {
                            g2d.drawLine(50 + (j * 100), 50 , 50 + (j * 100), 200);
                        } else {
                            g2d.drawLine(50 + (j * 100),-25 +  (i * 100), 50 + (j * 100), 50 + (i* 100));
                        }
                    }
                    
                    } else {
                        g2d.setStroke(linien);
                        g2d.setColor(new Color(150,75,0));
                        g2d.drawLine(50 + (j * 100), 50 + (i * 100), 150 + (j * 100), 50 + (i * 100));
                        if(i != f.gethoehe() - 1) {
                            g2d.drawLine(50 + (j * 100), 50 + (i * 100), 50 + (j * 100), 150 + (i * 100));
                        }
                    }
                }
                if(spielbrett.get(i).get(j) == 0) {
                    g2d.setStroke(new BasicStroke(20f));
                    g2d.setColor(new Color(1,83,32));
                    g2d.drawLine(50 + (j * 100), 50 + (i * 100), 50 + (j * 100), 50 + (i * 100));
                }
                else {
                    if (spielbrett.get(i).get(j) == 2) {
                        g2d.setStroke(new BasicStroke(20f));
                        g2d.setColor(new Color(100,255,100));
                        g2d.drawLine(50 + (j * 100), 50 + (i * 100),50 + (j * 100), 50 + (i * 100));
                    }
                    if(spielbrett.get(i).get(j) == 1) {
                        int[] tmpPos = {j,i}; // j = x; i = y
                        Maulwurf tmp = controller.whichMaulwurf(tmpPos);
                        if(tmp.getTeam().equals("Rot")) {
                            g2d.setColor(new Color(131,42,69));
                            g2d.setFont(new Font("Arial", Font.PLAIN, 30));
                            g2d.drawString(Integer.toString(tmp.getNumber()), 50 + (j * 100), 50 + (i * 100));
                        } else {
                            g2d.setColor(new Color(20,30,146));
                            g2d.setFont(new Font("Arial", Font.PLAIN, 30));
                            g2d.drawString(Integer.toString(tmp.getNumber()), 50 + (j * 100), 50 + (i * 100));
                        }
                    }
                }
            }
        }
    
    }

    public void paint(Graphics g) {
        super.paint(g);
        drawLines(g);
    }

    public void paintVictory() {
        this.setVisible(false);
        int tmp = JOptionPane.showConfirmDialog(null, "Möchtest du nochmal Spielen ?","Play Again", JOptionPane.YES_NO_OPTION);
        if(tmp == 1){
            System.exit(0);
        }else {
            GUI g = new GUI(controller.getSpielBrett().getBreite(),controller.getSpielBrett().gethoehe());
        }  
    }

    
    
}
