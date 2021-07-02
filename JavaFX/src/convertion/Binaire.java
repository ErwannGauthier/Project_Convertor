package convertion;

public class Binaire extends Nombre{

    public Binaire(String saisi) {
        super();

        // ----- PARTIE CONVERTION -----
        int i = 0;
        int taille = saisi.length();

        while((i < taille) && (getT_bin().size() < 32 ) && ((saisi.charAt(i) == '0') || (saisi.charAt(i) == '1'))) {

            getT_bin().add(saisi.charAt(i) + '0' - 96);
            i++;
        }
    }

    // ---------- METHODE CALCUL ----------

    public void binToEnt()
    {
        int i, j;
        j = 0;

        for(i = (getT_bin().size() - 1); i >= 0; i--)
        {
            setEntier(getEntier() + (getT_bin().get(i) * (int)Math.pow(2, j)));
            j++;
        }
    }
}
