package convertion;

import java.util.ArrayList;

abstract class Nombre {

    private int entier;

    private final ArrayList<Integer> t_bin;

    private final ArrayList<Character> t_hexa = new ArrayList<>(23) {{
        add('0');
        add('1');
        add('2');
        add('3');
        add('4');
        add('5');
        add('6');
        add('7');
        add('8');
        add('9');
        add('A');
        add('B');
        add('C');
        add('D');
        add('E');
        add('F');
        add('a');
        add('b');
        add('c');
        add('d');
        add('e');
        add('f');
    }};
    private final ArrayList<Integer> t_hexa_calc;

    public Nombre() {

        t_bin = new ArrayList<>();

        t_hexa_calc = new ArrayList<>();
    }

    // ---------- METHODES CALCULS ----------

    public void entToBin() {

        int dividende, diviseur, quotient, reste;

        dividende = this.entier;
        diviseur = 2;
        reste = dividende % diviseur;
        quotient = (dividende - reste) / diviseur;

        this.t_bin.add(reste);

        while(quotient != 0) {

            dividende = quotient;
            reste = dividende % diviseur;
            quotient = (dividende - reste) / diviseur;
            this.t_bin.add(reste);
        }
    }

    public void entToHexa() {

        int dividende, diviseur, quotient, reste;

        dividende = this.entier;
        diviseur = 16;
        reste = dividende % diviseur;
        quotient = (dividende - reste) / diviseur;

        this.t_hexa_calc.add(reste);

        while(quotient != 0) {

            dividende = quotient;
            reste = dividende % diviseur;
            quotient = (dividende - reste) / diviseur;
            this.t_hexa_calc.add(reste);
        }
    }

    // ---------- METHODES ARRAYLIST EN STRING ----------

    public String chaineBin() {

        StringBuilder s = new StringBuilder();
        char c;
        int i;

        for(i = (this.t_bin.size() - 1); i >= 0; i--) {
            c = (char)(this.t_bin.get(i) + '0');
            s.append(c);
        }

        return s.toString();
    }

    public String chaineHexa() {

        StringBuilder s = new StringBuilder();
        char c;

        for (int i = (this.t_hexa_calc.size() - 1); i >= 0; i--) {
            c = this.t_hexa.get(this.t_hexa_calc.get(i));
            s.append(c);
        }

        return s.toString();
    }

    // --- GETTER ET SETTER ---


    public int getEntier() {
        return entier;
    }

    public void setEntier(int entier) {
        this.entier = entier;
    }

    public ArrayList<Integer> getT_bin() {
        return t_bin;
    }

    public ArrayList<Character> getT_hexa() {
        return t_hexa;
    }

    public ArrayList<Integer> getT_hexa_calc() {
        return t_hexa_calc;
    }
}
