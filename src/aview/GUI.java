package aview;

import javax.sql.rowset.spi.SyncResolver;
import javax.swing.*;

import controller.*;
import model.Feld;
import model.Maulwurf;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GUI extends JPanel implements MouseListener{

    private JLabel schritteAnzeige = new JLabel();
    private JButton b1 = new JButton("Move");
    private JCheckBox booUp = new JCheckBox("Up");
    private JCheckBox boodown = new JCheckBox("Down");
    private JCheckBox booleft = new JCheckBox("Left");
    private JCheckBox booright = new JCheckBox("Right");
    private boolean teams = true;
    private JLabel teamAnzeige= new JLabel("Rot");
    private ButtonGroup group = new ButtonGroup();

    private boolean selected = false;
    private String wahl = "";

    private ArrayList<ArrayList<Integer>> spielbrett;
    private Feld f;

    private Controller controller;

    public GUI(int breite, int hoehe) {
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(selected) {
                    
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
                    

                    if(teams == true) {
                        for(Maulwurf mw : controller.getTeamRot()) {
                            if(mw.getAuswahl() == true) {
                                controller.move(teams, mw.getNumber(), Integer.parseInt(schritteAnzeige.getText()), wahl);
                                mw.setAuswahlfalse();
                                selected = false;
                            }
                        }
                    } else {
                        for (Maulwurf mw : controller.getTeamBlau()) {
                            if(mw.getAuswahl() == true) {
                                controller.move(teams, mw.getNumber(), Integer.parseInt(schritteAnzeige.getText()), wahl);
                                mw.setAuswahlfalse();
                                selected = false;
                            }
                        }
                    }
                    
                    teams = !teams;
                    if(teams) {
                        teamAnzeige.setText("Rot");
                        int tmp2 = controller.getSchrittRot();
                        schritteAnzeige.setText(Integer.toString(tmp2));
                    } else {
                        teamAnzeige.setText("Blau");
                        int tmp2 = controller.getSchritteBlau();
                        schritteAnzeige.setText(Integer.toString(tmp2));
                    }
                    System.out.println(controller.checkforVictory());
                    if(controller.checkforVictory() == true) {
                        paintVictory();
                        
                    } else {
                        repaint();
                    }
                }
            }
                
        });
        
        b1.setBounds(500, 1000, 50, 50);
        booUp.setBounds(300,1000,50,50);
        teamAnzeige.setBounds(225,1000,50,50);
        schritteAnzeige.setBounds(700,1000,200,100);
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
        this.addMouseListener(this);

        this.add(b1);
        controller = new Controller(breite,hoehe);
        int speicher = controller.startTheGame();
        schritteAnzeige.setText(Integer.toString(speicher));
        f = controller.getSpielBrett();

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
                            if(!tmp.getAuswahl()) {
                                g2d.setColor(new Color(131,42,69));
                                g2d.setFont(new Font("Arial", Font.PLAIN, 30));
                                g2d.drawString(Integer.toString(tmp.getNumber()), 45 + (j * 100), 60 + (i * 100));
                            } else {
                                g2d.setColor(new Color(200,0,0));
                                g2d.setStroke(new BasicStroke(8f));
                                // Kästchen Markierung
                                g2d.drawLine(50 + (tmp.getPosition()[0] * 100) - 15, 50 + (tmp.getPosition()[1] * 100) - 15, 50 + (tmp.getPosition()[0] * 100) + 15, 50 + (tmp.getPosition()[1] * 100) - 15); // unterste Linie
                                g2d.drawLine(50 + (tmp.getPosition()[0] * 100) - 15, 50 + (tmp.getPosition()[1] * 100) - 15, 50 + (tmp.getPosition()[0] * 100) - 15, 50 + (tmp.getPosition()[1] * 100) + 15); // Links
                                g2d.drawLine(50 + (tmp.getPosition()[0] * 100) - 15, 50 + (tmp.getPosition()[1] * 100) + 15, 50 + (tmp.getPosition()[0] * 100) + 15, 50 + (tmp.getPosition()[1] * 100) + 15); // oben
                                g2d.drawLine(50 + (tmp.getPosition()[0] * 100) + 15, 50 + (tmp.getPosition()[1] * 100) - 15, 50 + (tmp.getPosition()[0] * 100) + 15, 50 + (tmp.getPosition()[1] * 100) + 15); // rechts
                                //--------------------------
                                if (controller.checkifPossible(false, tmp.getNumber(), Integer.parseInt(schritteAnzeige.getText()), "up")) {
                                    g2d.drawLine(50 + (tmp.getPosition()[0] * 100) - 15, 50 + ((tmp.getPosition()[1] - Integer.parseInt(schritteAnzeige.getText()))* 100) - 25, 50 + (tmp.getPosition()[0] * 100) - 15, 50 + ((tmp.getPosition()[1] * 100) - 15));
                                    g2d.drawLine(50 + (tmp.getPosition()[0] * 100) + 15, 50 + ((tmp.getPosition()[1] - Integer.parseInt(schritteAnzeige.getText()))* 100) - 25, 50 + (tmp.getPosition()[0] * 100) + 15, 50 + ((tmp.getPosition()[1] * 100) - 15));
                                    g2d.drawLine(50 + (tmp.getPosition()[0] * 100) + 15, 50 + ((tmp.getPosition()[1] - Integer.parseInt(schritteAnzeige.getText()))* 100) - 25, 50 + (tmp.getPosition()[0] * 100) - 15, 50 + ((tmp.getPosition()[1] - Integer.parseInt(schritteAnzeige.getText()))* 100) - 25);
                                }
                                if (controller.checkifPossible(false, tmp.getNumber(), Integer.parseInt(schritteAnzeige.getText()), "down")) {
                                    g2d.drawLine(50 + (tmp.getPosition()[0] * 100) - 15, 50 + ((tmp.getPosition()[1] + Integer.parseInt(schritteAnzeige.getText()))* 100) + 25, 50 + (tmp.getPosition()[0] * 100) - 15, 50 + ((tmp.getPosition()[1] * 100) + 15));
                                    g2d.drawLine(50 + (tmp.getPosition()[0] * 100) + 15, 50 + ((tmp.getPosition()[1] + Integer.parseInt(schritteAnzeige.getText()))* 100) + 25, 50 + (tmp.getPosition()[0] * 100) + 15, 50 + ((tmp.getPosition()[1] * 100) + 15));
                                    g2d.drawLine(50 + (tmp.getPosition()[0] * 100) + 15, 50 + ((tmp.getPosition()[1] + Integer.parseInt(schritteAnzeige.getText()))* 100) + 25, 50 + (tmp.getPosition()[0] * 100) - 15, 50 + ((tmp.getPosition()[1] + Integer.parseInt(schritteAnzeige.getText()))* 100) +25);
                                }
        
                                if (controller.checkifPossible(false, tmp.getNumber(), Integer.parseInt(schritteAnzeige.getText()), "left")) {
                                    g2d.drawLine(50 + (tmp.getPosition()[0] * 100) - 15, 50 + (tmp.getPosition()[1] * 100) + 15, 50 + ((tmp.getPosition()[0] - Integer.parseInt(schritteAnzeige.getText()) )* 100)- 15, 50 + ((tmp.getPosition()[1] * 100) + 15));
                                    g2d.drawLine(50 + (tmp.getPosition()[0] * 100) - 15, 50 + (tmp.getPosition()[1] * 100) - 15, 50 + ((tmp.getPosition()[0] - Integer.parseInt(schritteAnzeige.getText()) )* 100)- 15, 50 + ((tmp.getPosition()[1] * 100) - 15));
                                    g2d.drawLine(50 + ((tmp.getPosition()[0] - Integer.parseInt(schritteAnzeige.getText()) )* 100)- 15, 50 + (tmp.getPosition()[1] * 100) - 15, 50 + ((tmp.getPosition()[0] - Integer.parseInt(schritteAnzeige.getText()) )* 100) - 15, 50 + ((tmp.getPosition()[1] * 100) + 15));
                                }
        
                                if (controller.checkifPossible(false, tmp.getNumber(), Integer.parseInt(schritteAnzeige.getText()), "right")) {
                                    g2d.drawLine(50 + (tmp.getPosition()[0] * 100) + 15, 50 + (tmp.getPosition()[1] * 100) + 15, 50 + ((tmp.getPosition()[0] + Integer.parseInt(schritteAnzeige.getText()) )* 100) + 15, 50 + ((tmp.getPosition()[1] * 100) + 15));
                                    g2d.drawLine(50 + (tmp.getPosition()[0] * 100) + 15, 50 + (tmp.getPosition()[1] * 100) - 15, 50 + ((tmp.getPosition()[0] + Integer.parseInt(schritteAnzeige.getText()) )* 100) + 15, 50 + ((tmp.getPosition()[1] * 100) - 15));
                                    g2d.drawLine(50 + ((tmp.getPosition()[0] + Integer.parseInt(schritteAnzeige.getText()) ) * 100) + 15, 50 + (tmp.getPosition()[1] * 100) - 15, 50 + ((tmp.getPosition()[0] + Integer.parseInt(schritteAnzeige.getText()) )* 100) + 15, 50 + ((tmp.getPosition()[1] * 100) + 15));
                                }
                                //------------------------
                                g2d.setColor(new Color(131,42,69));
                                g2d.setFont(new Font("Arial", Font.PLAIN, 30));
                                g2d.drawString(Integer.toString(tmp.getNumber()), 45 + (j * 100), 60 + (i * 100));
                            }
                            
                        } else {
                            if(!tmp.getAuswahl()) {
                                g2d.setColor(new Color(20,30,146));
                                g2d.setFont(new Font("Arial", Font.PLAIN, 30));
                                g2d.drawString(Integer.toString(tmp.getNumber()), 45 + (j * 100), 60 + (i * 100));
                            } else {
                                // Kästchen Markierung
                                g2d.setColor(new Color(0,0,200));
                                g2d.setStroke(new BasicStroke(8f));
                                g2d.drawLine(50 + (tmp.getPosition()[0] * 100) - 15, 50 + (tmp.getPosition()[1] * 100) - 15, 50 + (tmp.getPosition()[0] * 100) + 15, 50 + (tmp.getPosition()[1] * 100) - 15); // unterste Linie
                                g2d.drawLine(50 + (tmp.getPosition()[0] * 100) - 15, 50 + (tmp.getPosition()[1] * 100) - 15, 50 + (tmp.getPosition()[0] * 100) - 15, 50 + (tmp.getPosition()[1] * 100) + 15); // Links
                                g2d.drawLine(50 + (tmp.getPosition()[0] * 100) - 15, 50 + (tmp.getPosition()[1] * 100) + 15, 50 + (tmp.getPosition()[0] * 100) + 15, 50 + (tmp.getPosition()[1] * 100) + 15); // oben
                                g2d.drawLine(50 + (tmp.getPosition()[0] * 100) + 15, 50 + (tmp.getPosition()[1] * 100) - 15, 50 + (tmp.getPosition()[0] * 100) + 15, 50 + (tmp.getPosition()[1] * 100) + 15); // rechts
                                //---------------------------------
                                if (controller.checkifPossible(false, tmp.getNumber(), Integer.parseInt(schritteAnzeige.getText()), "up")) {
                                    g2d.drawLine(50 + (tmp.getPosition()[0] * 100) - 15, 50 + ((tmp.getPosition()[1] - Integer.parseInt(schritteAnzeige.getText()))* 100) - 25, 50 + (tmp.getPosition()[0] * 100) - 15, 50 + ((tmp.getPosition()[1] * 100) - 15));
                                    g2d.drawLine(50 + (tmp.getPosition()[0] * 100) + 15, 50 + ((tmp.getPosition()[1] - Integer.parseInt(schritteAnzeige.getText()))* 100) - 25, 50 + (tmp.getPosition()[0] * 100) + 15, 50 + ((tmp.getPosition()[1] * 100) - 15));
                                    g2d.drawLine(50 + (tmp.getPosition()[0] * 100) + 15, 50 + ((tmp.getPosition()[1] - Integer.parseInt(schritteAnzeige.getText()))* 100) - 25, 50 + (tmp.getPosition()[0] * 100) - 15, 50 + ((tmp.getPosition()[1] - Integer.parseInt(schritteAnzeige.getText()))* 100) - 25);
                                }
                                if (controller.checkifPossible(false, tmp.getNumber(), Integer.parseInt(schritteAnzeige.getText()), "down")) {
                                    g2d.drawLine(50 + (tmp.getPosition()[0] * 100) - 15, 50 + ((tmp.getPosition()[1] + Integer.parseInt(schritteAnzeige.getText()))* 100) + 25, 50 + (tmp.getPosition()[0] * 100) - 15, 50 + ((tmp.getPosition()[1] * 100) + 15));
                                    g2d.drawLine(50 + (tmp.getPosition()[0] * 100) + 15, 50 + ((tmp.getPosition()[1] + Integer.parseInt(schritteAnzeige.getText()))* 100) + 25, 50 + (tmp.getPosition()[0] * 100) + 15, 50 + ((tmp.getPosition()[1] * 100) + 15));
                                    g2d.drawLine(50 + (tmp.getPosition()[0] * 100) + 15, 50 + ((tmp.getPosition()[1] + Integer.parseInt(schritteAnzeige.getText()))* 100) + 25, 50 + (tmp.getPosition()[0] * 100) - 15, 50 + ((tmp.getPosition()[1] + Integer.parseInt(schritteAnzeige.getText()))* 100) +25);
                                }
        
                                if (controller.checkifPossible(false, tmp.getNumber(), Integer.parseInt(schritteAnzeige.getText()), "left")) {
                                    g2d.drawLine(50 + (tmp.getPosition()[0] * 100) - 15, 50 + (tmp.getPosition()[1] * 100) + 15, 50 + ((tmp.getPosition()[0] - Integer.parseInt(schritteAnzeige.getText()) )* 100)- 15, 50 + ((tmp.getPosition()[1] * 100) + 15));
                                    g2d.drawLine(50 + (tmp.getPosition()[0] * 100) - 15, 50 + (tmp.getPosition()[1] * 100) - 15, 50 + ((tmp.getPosition()[0] - Integer.parseInt(schritteAnzeige.getText()) )* 100)- 15, 50 + ((tmp.getPosition()[1] * 100) - 15));
                                    g2d.drawLine(50 + ((tmp.getPosition()[0] - Integer.parseInt(schritteAnzeige.getText()) )* 100)- 15, 50 + (tmp.getPosition()[1] * 100) - 15, 50 + ((tmp.getPosition()[0] - Integer.parseInt(schritteAnzeige.getText()) )* 100) - 15, 50 + ((tmp.getPosition()[1] * 100) + 15));
                                }
        
                                if (controller.checkifPossible(false, tmp.getNumber(), Integer.parseInt(schritteAnzeige.getText()), "right")) {
                                    g2d.drawLine(50 + (tmp.getPosition()[0] * 100) + 15, 50 + (tmp.getPosition()[1] * 100) + 15, 50 + ((tmp.getPosition()[0] + Integer.parseInt(schritteAnzeige.getText()) )* 100) + 15, 50 + ((tmp.getPosition()[1] * 100) + 15));
                                    g2d.drawLine(50 + (tmp.getPosition()[0] * 100) + 15, 50 + (tmp.getPosition()[1] * 100) - 15, 50 + ((tmp.getPosition()[0] + Integer.parseInt(schritteAnzeige.getText()) )* 100) + 15, 50 + ((tmp.getPosition()[1] * 100) - 15));
                                    g2d.drawLine(50 + ((tmp.getPosition()[0] + Integer.parseInt(schritteAnzeige.getText()) )* 100) + 15, 50 + (tmp.getPosition()[1] * 100) - 15, 50 + ((tmp.getPosition()[0] + Integer.parseInt(schritteAnzeige.getText()) )* 100) + 15, 50 + ((tmp.getPosition()[1] * 100) + 15));
                                }
                                g2d.setColor(new Color(20,30,146));
                                g2d.setFont(new Font("Arial", Font.PLAIN, 30));
                                g2d.drawString(Integer.toString(tmp.getNumber()), 45 + (j * 100), 60 + (i * 100));
                            }
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

    @Override
    public void mouseClicked(MouseEvent e) {

        ArrayList<Maulwurf> tmpRot = controller.getTeamRot();

        ArrayList<Maulwurf> tmpBlau = controller.getTeamBlau();

        if(teamAnzeige.getText().equals("Rot")) {
            for(Maulwurf mw : tmpRot) {
                if(50 + (mw.getPosition()[0] * 100) >= e.getX() - 25 && 50 + (mw.getPosition()[0] * 100) <= e.getX() + 25) { // schaue ob X-Koordinate passt
                    if(50 + (mw.getPosition()[1] * 100) >= e.getY() - 25 && 50 + (mw.getPosition()[1] * 100) <= e.getY() + 25) {
                        mw.setAuswahl();
                        selected = true;
                        repaint();
                    } else {
                        mw.setAuswahlfalse();
                    }
                } else {
                    mw.setAuswahlfalse();
                }
            }
        } else {
            for(Maulwurf mw : tmpBlau) {
                if(50 + (mw.getPosition()[0] * 100) >= e.getX() - 25 && 50 + (mw.getPosition()[0] * 100) <= e.getX() + 25) { // schaue ob X-Koordinate passt
                    if(50 + (mw.getPosition()[1] * 100) >= e.getY() - 25 && 50 + (mw.getPosition()[1] * 100) <= e.getY() + 25) {
                        mw.setAuswahl();
                        selected = true;
                        repaint();
                    } else {
                        mw.setAuswahlfalse();
                    }
                } else {
                    mw.setAuswahlfalse();
                }
            }
        }
        System.out.println(e.getX() + " " + e.getY());
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

        System.out.println("Entered");
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        System.out.println("Exited");
        
    }
}