package convertion;

public class Hexadecimal extends Nombre{

    public Hexadecimal(String s) {
        super();

        // ----- PARTIE CONVERTION -----
        int i = 0;
        int taille = s.length();
        char caractereCourant = s.charAt(i);

        while(i < taille && getT_hexa_calc().size() < taille && getT_hexa().contains(caractereCourant))
        {
            caractereCourant = s.charAt(i);

            if (caractereCourant=='A' || caractereCourant=='a') {
                getT_hexa_calc().add(10);
            } else if (caractereCourant=='B' || caractereCourant=='b') {
                getT_hexa_calc().add(11);
            } else if (caractereCourant=='C' || caractereCourant=='c') {
                getT_hexa_calc().add(12);
            } else if (caractereCourant=='D' || caractereCourant=='d') {
                getT_hexa_calc().add(13);
            } else if (caractereCourant=='E' || caractereCourant=='e') {
                getT_hexa_calc().add(14);
            } else if (caractereCourant=='F' || caractereCourant=='f') {
                getT_hexa_calc().add(15);
            } else {
                getT_hexa_calc().add(Integer.parseInt(String.valueOf(caractereCourant)));
            }
            i++;
        }
    }

    // ---------- METHODE CALCUL ----------

    public void hexaToEnt()
    {
        int i, j;
        j = 0;

        for(i = getT_hexa_calc().size() - 1; i >= 0; i--)
        {
            setEntier(getEntier() + (getT_hexa_calc().get(i) * (int)Math.pow(16, j)));
            j++;
        }
    }
}
