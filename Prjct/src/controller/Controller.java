package controller;

import java.util.ArrayList;
import java.util.Random;

import model.Feld;
import model.Loch;
import model.Maulwurf;

public class Controller {
    
    private ArrayList<Maulwurf> teamRot = new ArrayList<>();
    private ArrayList<Maulwurf> teamBlau = new ArrayList<>();

    private ArrayList<Integer> schritteRot = new ArrayList<>();
    private ArrayList<Integer> schritteBlau = new ArrayList<>();

    private Feld f;

    public Controller(int x, int y) {

        f = new Feld(x,y);
    }

    public int startTheGame() {
        Random r = new Random();
        int breite = r.nextInt(f.getBreite());
        int hoehe = r.nextInt(f.gethoehe());
        int tmp[] = {breite,hoehe};
        Loch l = new Loch(tmp);
        if(f.placeLoch(l) == false ) {
            System.out.println("Error: Kein Loch wurde platziert !");
        }
        placeAllMaulwurf();
        fillSchritteRot();
        fillSchritteBlau();
        return getSchrittRot();
    }


    public void fillSchritteRot() {
        schritteRot.add(1);
        schritteRot.add(2);
        schritteRot.add(2);
        schritteRot.add(3);
        schritteRot.add(3);
        schritteRot.add(4);
    }
    public void fillSchritteBlau() {
        schritteBlau.add(1);
        schritteBlau.add(2);
        schritteBlau.add(2);
        schritteBlau.add(3);
        schritteBlau.add(3);
        schritteBlau.add(4);
    }

    public int getSchrittRot(){
        Random r = new Random();
        if(schritteRot.size() == 1) {
            int rtn = schritteRot.get(0);
            schritteRot.remove(0);
            fillSchritteRot();
            return rtn;
        }
        int tmp = r.nextInt(schritteRot.size());
        int rtn = schritteRot.get(tmp);
        schritteRot.remove(tmp);
        return rtn;
    }

    public int getSchritteBlau() {
        Random r = new Random();
        if(schritteBlau.size() == 1) {
            int rtn = schritteBlau.get(0);
            schritteBlau.remove(0);
            fillSchritteBlau();
            return rtn;
        }
        int tmp = r.nextInt(schritteBlau.size() - 1);
        int rtn = schritteBlau.get(tmp);
        schritteBlau.remove(tmp);
        return rtn;
    }

    public void placeAllMaulwurf() {
        for (int i = 0; i < 3; i++) {
            Random r = new Random();
            int breite = r.nextInt(f.getBreite());
            int hoehe = r.nextInt(f.gethoehe());
            while(f.getSpielBrett().get(hoehe).get(breite) == 1 || f.getSpielBrett().get(hoehe).get(breite) == 2) {
                breite = r.nextInt(f.getBreite());
                hoehe = r.nextInt(f.gethoehe());
            }
            int[] test = {breite,hoehe};
            Maulwurf t = new Maulwurf("Rot", i, f, test);
            teamRot.add(t);
        }
        for (int i = 0; i < 3; i++) {
            
            Random r = new Random();
            int breite = r.nextInt(f.getBreite());
            int hoehe = r.nextInt(f.gethoehe());
            while(f.getSpielBrett().get(hoehe).get(breite) == 1 || f.getSpielBrett().get(hoehe).get(breite) == 2) {
                breite = r.nextInt(f.getBreite());
                hoehe = r.nextInt(f.gethoehe());
            }
            int[] test = {breite,hoehe};
            Maulwurf t = new Maulwurf("Blau", i, f, test);
            teamBlau.add(t);
            System.out.println("Wie viele Blaue werden geprintet ? " + i );
        }
    }

    public Feld getSpielBrett() {
        return f;
    }

    public boolean checkforVictory() {
        return f.checkAlleLoecher();
    }

    public boolean einLoch() {
        return f.checkEinLoch();
    }

    public Maulwurf whichMaulwurf(int[] posi) {
        for(Maulwurf mw : teamRot) {
            int[] mwPos = mw.getPosition();
            if(mwPos[0] == posi[0] && mwPos[1] == posi[1]) {
                return mw;
            }
        }
        for(Maulwurf mw : teamBlau) {
            int[] mwPos = mw.getPosition();
            if(mwPos[0] == posi[0] && mwPos[1] == posi[1]) {
                return mw;
            }
        }
        return null;
    }

    public void move(Boolean team, int number, int schritte, String wahl) {
        if(team) {
            Maulwurf tmp = teamRot.get(number);
            if(tmp.getMovement() == true) {
                f.moveMaulwurf(tmp, schritte, wahl);
            }
        } else {
            Maulwurf tmp = teamBlau.get(number);
            if(tmp.getMovement() == true) {
                f.moveMaulwurf(tmp, schritte, wahl);
            }
        }
    }

    public ArrayList<Maulwurf> getTeamRot() {
        ArrayList<Maulwurf> tmp = new ArrayList<>();

        for(Maulwurf mw : teamRot) {
            tmp.add(mw);
        }

        return tmp;
    }

    public ArrayList<Maulwurf> getTeamBlau() {
        ArrayList<Maulwurf> tmp = new ArrayList<>();

        for(Maulwurf mw : teamBlau) {
            tmp.add(mw);
        }

        return tmp;
    }
    
}
