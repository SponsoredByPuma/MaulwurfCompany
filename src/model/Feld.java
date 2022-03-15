package model;

import java.util.ArrayList;


public class Feld {

    private int breite;
    private int hoehe;
    private ArrayList<ArrayList<Integer>> spielbrett;
    private ArrayList<Loch> löcher;
    
    public Feld(int x, int y) {
        breite = x;
        hoehe = y;
        spielbrett = new ArrayList<>(breite);
        for(int i = 0; i < hoehe; i++) {
            spielbrett.add(i, new ArrayList<>(hoehe));
            for(int j = 0; j < breite; j++) {
                spielbrett.get(i).add(j, 0);
            }
        }
    }

    public boolean placeLoch(Loch l) {

        int tmp[] = l.getPos();
        if (tmp[0] > breite || tmp[1] > hoehe) {
            return false;
        }
        spielbrett.get(tmp[1]).set(tmp[0],2);
        löcher = new ArrayList<>();
        löcher.add(l);
        return true;
    }

    public boolean checkAlleLoecher() {
        for(Loch l : löcher) {
            if(l.belegtStatus() == false) {
                return false;
            }
        }
        return true;
    }

    public int getBreite() {
        return this.breite;
    }

    public int gethoehe() {
        return this.hoehe;
    }

    public ArrayList<ArrayList<Integer>> getSpielBrett() {
        return spielbrett;
    }
    
    public boolean placeMaulwurf(Maulwurf mw) {
        int[] tmp = mw.getPosition();
        if (tmp[0] > breite || tmp[1] > hoehe) {
            return false;
        }
        if (spielbrett.get(tmp[1]).get(tmp[0]) == 1 || spielbrett.get(tmp[1]).get(tmp[0]) == 2 ) { // hier 0, 1 vertauscht
            return false;
        }
        spielbrett.get(tmp[1]).set(tmp[0], 1);
        return true;
    }

    public boolean check(Maulwurf mw, int schritte, String wahl){

        int tmp[] = mw.getPosition();
        switch(wahl) {
            case "up":
                if(tmp[1] - schritte  < 0 || tmp[1] - schritte >= hoehe) {
                    return false;
                } else {
                    for (int i = 1; i <= schritte; i++) {
                        if(i != schritte) {
                            if(spielbrett.get(tmp[1] - i).get(tmp[0]) == 1 || spielbrett.get(tmp[1] - i).get(tmp[0]) == 2){
                                return false;
                            }
                        } else {
                            if(spielbrett.get(tmp[1] - i).get(tmp[0]) == 1 ){
                                return false;
                            }
                        }
                    }
                    int[] neuePosi = {tmp[0],tmp[1] - schritte};
                    for(Loch l : löcher) {
                        if(l.getPos()[0] == neuePosi[0] && l.getPos()[1] == neuePosi[1] && l.belegtStatus() == true) {
                            return false;
                        }
                    }
                    return true;
                }

            case "down":
                if(tmp[1] + schritte  < 0 || tmp[1] + schritte >= hoehe) {
                    return false;
                } else {
                    for (int i = 1; i <= schritte; i++) {
                        if(i != schritte) {
                            if(spielbrett.get(tmp[1] + i).get(tmp[0]) == 1 || spielbrett.get(tmp[1] + i).get(tmp[0]) == 2){
                                return false;
                            }
                        } else {
                            if(spielbrett.get(tmp[1] + i).get(tmp[0]) == 1){
                                return false;
                            }
                        }
                    }
                    int[] neuePosi = {tmp[0],tmp[1] + schritte};
                    for(Loch l : löcher) {
                        if(l.getPos()[0] == neuePosi[0] && l.getPos()[1] == neuePosi[1] && l.belegtStatus() == true) { // bei zwei Löchern muss man hier ggf die Logik abändern, sprich dort schon returnen
                            return false;
                        }
                    }
                    return true;
                }
    
            case "left":
                if(tmp[0] - schritte < 0 || tmp[0] - schritte > breite) {
                    return false;
                } else {
                    for (int i = 1; i <= schritte; i++) {
                        if(i != schritte) {
                            if(spielbrett.get(tmp[1]).get(tmp[0] - i) == 1 || spielbrett.get(tmp[1]).get(tmp[0] - i) == 2){
                                return false;
                            }
                        } else {
                            if(spielbrett.get(tmp[1]).get(tmp[0] - i) == 1 ){
                                return false;
                            }
                        }
                    }
                    int[] neuePosi = {tmp[0] - schritte,tmp[1]};
                    for(Loch l : löcher) {
                        if(l.getPos()[0] == neuePosi[0] && l.getPos()[1] == neuePosi[1] && l.belegtStatus() == true) {
                            return false;
                        }
                    }
                    return true;
                }
            case "right":
                if(tmp[0] + schritte < 0 || tmp[0] + schritte >= breite) {
                    return false;
                } else {
                    for (int i = 1; i <= schritte; i++) {
                        if(i != schritte) {
                            if(spielbrett.get(tmp[1]).get(tmp[0] + i) == 1 || spielbrett.get(tmp[1]).get(tmp[0] + i) == 2){
                                return false;
                            }
                        } else {
                            if(spielbrett.get(tmp[1]).get(tmp[0] + i) == 1 ){
                                return false;
                            }
                        }
                    }
                    int[] neuePosi = {tmp[0] + schritte,tmp[1]};
                    for(Loch l : löcher) {
                        if(l.getPos()[0] == neuePosi[0] && l.getPos()[1] == neuePosi[1] && l.belegtStatus() == true) {
                            return false;
                        }
                    }
                    return true;
                }
            default:
                return false;
        }
    }

    public boolean moveMaulwurf(Maulwurf mw, int schritte, String wahl) {

        if(check(mw,schritte,wahl) == true) {
            int[] tmp = mw.getPosition();
            int[] neuePosi = new int[2];
            switch(wahl){
                case "up":
                    spielbrett.get(tmp[1] - schritte).set(tmp[0], 1);
                    spielbrett.get(tmp[1]).set(tmp[0], 0);
                    neuePosi[0] = tmp[0];
                    neuePosi[1] = tmp[1] - schritte;
                    break;
                case "down":
                    spielbrett.get(tmp[1] + schritte).set(tmp[0], 1);
                    spielbrett.get(tmp[1]).set(tmp[0], 0);
                    neuePosi[0] = tmp[0];
                    neuePosi[1] = tmp[1] + schritte;
                    break;
                case "right":
                    spielbrett.get(tmp[1]).set(tmp[0] + schritte, 1);
                    spielbrett.get(tmp[1]).set(tmp[0], 0);
                    neuePosi[0] = tmp[0] + schritte;
                    neuePosi[1] = tmp[1];
                    break;
                case "left":
                    spielbrett.get(tmp[1]).set(tmp[0] - schritte, 1);
                    spielbrett.get(tmp[1]).set(tmp[0], 0);
                    neuePosi[0] = tmp[0] - schritte;
                    neuePosi[1] = tmp[1];
                    break;
                default:
                    break;
            }
            
            for (Loch l : löcher) {
                if(l.getPos()[0] == neuePosi[0] && l.getPos()[1] == neuePosi[1] && l.belegtStatus() == false) {
                    l.setBelegt(mw);
                    mw.setMovement();
                }
            }
            mw.setPosition(neuePosi);
            return true;
        } else {
            return false;
        }
    }

        /*
        int tmp[] = mw.getPosition();
        switch(wahl) {
            case "up":
                if(tmp[1] - schritte  < 0 || tmp[1] - schritte >= hoehe) {
                    System.out.println();
                    System.out.println("Falsche Höheneingabe !");
                    return false;
                } else {
                    for (int i = 1; i <= schritte; i++) {
                        if(i != schritte) {
                            if(spielbrett.get(tmp[1] - i).get(tmp[0]) == 1 || spielbrett.get(tmp[1] - i).get(tmp[0]) == 2){
                                System.out.println("Loch oder Maulwurf im Weg !");
                                return false;
                            }
                        } else {
                            if(spielbrett.get(tmp[1] - i).get(tmp[0]) == 1 ){
                                System.out.println("Maulwurf im Weg !");
                                return false;
                            }
                        }
                    }
                    spielbrett.get(tmp[1] - schritte).set(tmp[0], 1);
                    spielbrett.get(tmp[1]).set(tmp[0], 0);
                    int[] neuePosi = {tmp[0],tmp[1] - schritte};
                    for(Loch l : löcher) {
                        if(l.getPos()[0] == neuePosi[0] && l.getPos()[1] == neuePosi[1] && l.belegtStatus() == false) {
                            l.setBelegt(mw);
                            mw.setMovement();
                        }
                    }
                    mw.setPosition(neuePosi);
                    return true;
                    }

            case "down":
                if(tmp[1] + schritte  < 0 || tmp[1] + schritte >= hoehe) {
                    System.out.println();
                    System.out.println("Falsche Höheneingabe !");
                    return false;
                } else {
                    for (int i = 1; i <= schritte; i++) {
                        if(i != schritte) {
                            if(spielbrett.get(tmp[1] + i).get(tmp[0]) == 1 || spielbrett.get(tmp[1] + i).get(tmp[0]) == 2){
                                System.out.println("Loch oder Maulwurf im Weg !");
                                return false;
                            }
                        } else {
                            if(spielbrett.get(tmp[1] + i).get(tmp[0]) == 1){
                                System.out.println("Maulwurf im Weg !");
                                return false;
                            }
                        }
                    }
                    spielbrett.get(tmp[1] + schritte).set(tmp[0], 1);
                    spielbrett.get(tmp[1]).set(tmp[0], 0);
                    int[] neuePosi = {tmp[0],tmp[1] + schritte};
                    for(Loch l : löcher) {
                        if(l.getPos()[0] == neuePosi[0] && l.getPos()[1] == neuePosi[1] && l.belegtStatus() == false) { // bei zwei Löchern muss man hier ggf die Logik abändern, sprich dort schon returnen
                            l.setBelegt(mw);
                            mw.setMovement();
                        }
                    }
                    mw.setPosition(neuePosi);
                    return true;
                }
    
            case "left":
                if(tmp[0] - schritte < 0 || tmp[0] - schritte > breite) {
                    System.out.println();
                    System.out.println("Falsche Breiteneingabe !(left)");
                    return false;
                } else {
                    for (int i = 1; i <= schritte; i++) {
                        if(i != schritte) {
                            if(spielbrett.get(tmp[1]).get(tmp[0] - i) == 1 || spielbrett.get(tmp[1]).get(tmp[0] - i) == 2){
                                System.out.println("Loch oder Maulwurf im Weg !");
                                return false;
                            }
                        } else {
                            if(spielbrett.get(tmp[1]).get(tmp[0] - i) == 1 ){
                                System.out.println("Maulwurf im Weg !");
                                return false;
                            }
                        }
                    }
                    spielbrett.get(tmp[1]).set(tmp[0] - schritte, 1);
                    spielbrett.get(tmp[1]).set(tmp[0], 0);
                    int[] neuePosi = {tmp[0] - schritte,tmp[1]};
                    for(Loch l : löcher) {
                        if(l.getPos()[0] == neuePosi[0] && l.getPos()[1] == neuePosi[1] && l.belegtStatus() == false) {
                            l.setBelegt(mw);
                            mw.setMovement();
                        }
                    }
                    mw.setPosition(neuePosi);
                    return true;
                }
            case "right":
                if(tmp[0] + schritte < 0 || tmp[0] + schritte >= breite) {
                    System.out.println();
                    System.out.println("Falsche Breiteneingabe ! (right)");
                    return false;
                } else {
                    for (int i = 1; i <= schritte; i++) {
                        if(i != schritte) {
                            if(spielbrett.get(tmp[1]).get(tmp[0] + i) == 1 || spielbrett.get(tmp[1]).get(tmp[0] + i) == 2){
                                System.out.println("Loch oder Maulwurf im Weg !");
                                return false;
                            }
                        } else {
                            if(spielbrett.get(tmp[1]).get(tmp[0] + i) == 1 ){
                                System.out.println("Maulwurf im Weg !");
                                return false;
                            }
                        }
                    }
                    spielbrett.get(tmp[1]).set(tmp[0] + schritte, 1);
                    spielbrett.get(tmp[1]).set(tmp[0], 0);
                    int[] neuePosi = {tmp[0] + schritte,tmp[1]};
                    for(Loch l : löcher) {
                        if(l.getPos()[0] == neuePosi[0] && l.getPos()[1] == neuePosi[1] && l.belegtStatus() == false) {
                            l.setBelegt(mw);
                            mw.setMovement();
                        }
                    }
                    mw.setPosition(neuePosi);
                    return true;
                }
            default:
                return false;
        }
    }
*/
    public boolean checkEinLoch() {
        for(Loch l : löcher) {
            if(l.belegtStatus() == true) {
                return true;
            }
        }
        return false;
        
    }

    public void printSpielFeld() {
        System.out.println();
        System.out.println();
        for(int i = 0; i < spielbrett.size(); i++){
            for(int j = 0; j < spielbrett.get(i).size(); j++) {
                if(j == 0) {
                    System.out.print(spielbrett.get(i).get(j) + "---");
                }else{
                    if(j == spielbrett.get(i).size() - 1) {
                        System.out.print("---" + spielbrett.get(i).get(j));
                    } else {
                        System.out.print("---" + spielbrett.get(i).get(j) + "---");
                    }
                }
            }
            if(i == spielbrett.size() - 1) {
                continue;
            }
            for(int y = 0; y < 2; y++) {
                System.out.println();
                for(int x = 0; x < spielbrett.get(i).size(); x++) {
                    if(x == 0){
                        System.out.print("|   ");
                    } else {
                        if(x == spielbrett.get(i).size() - 1) {
                            System.out.print("   |");
                        } else {
                            System.out.print("   |   ");
                        }
                    }
                }
                System.out.println();
            }
        }
    }
}
