package model;

public class Loch {

    private boolean belegt = false;
    private Maulwurf king;
    private int[] pos;

    public Loch(int[] pos) {
        this.pos = pos;
        System.out.println("Erstellt ! X: " + pos[0] + " Y: " + pos[1]);
    }

    public int[] getPos() {
        return this.pos;
    }

    public void setBelegt(Maulwurf mw) {
        belegt = true;
        king = mw;
        System.out.println("Hura ! Der Maulwurf " + mw.getNumber() + " aus dem Team " + mw.getTeam() + " hat das Loch besetzt !");
    }

    public Maulwurf getKing() {
        return this.king;
    }

    public boolean belegtStatus() {
        return this.belegt;
    }


    
}
