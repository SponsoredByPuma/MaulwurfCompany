package model;

public class Maulwurf {

    private String team;
    private int number;
    private int[] position;
    private boolean movement = true;
    private boolean istAusgewählt = false;

    public Maulwurf(String team, int number, int[] pos) {
        this.team = team;
        this.number = number;
        this.position = pos;
    }

    public void setPosition(int[] neuePos) {
        this.position = neuePos;
    }

    public int[] getPosition() {
        return position;
    }

    public String getTeam() {
        return this.team;
    }

    public void setAuswahl() {
        istAusgewählt = true;
    }

    public void setAuswahlfalse() {
        istAusgewählt = false;
    }

    public boolean getAuswahl() {
        return this.istAusgewählt;
    }

    public int getNumber() {
        return this.number;
    }

    public boolean getMovement() {
        return this.movement;
    }

    public void setMovement() {
        this.movement = false;
    }
    
}