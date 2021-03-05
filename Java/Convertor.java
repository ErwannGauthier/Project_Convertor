import java.io.*;
import java.lang.*;
import java.math.*;
import java.util.*;


// -------------------------------------- SUPER-CLASS NUMBER --------------------------------------

class Number
{
    public int entier;

    public boolean saisiBin = false;
    public ArrayList<Integer> t_bin = new ArrayList<Integer>(20);

    public boolean saisiHexa = false;
    public ArrayList<Character> t_hexa = new ArrayList<Character>(23) {{
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
    public ArrayList<Integer> t_hexa_calc = new ArrayList<Integer>(5);


    // ---------- METHODES CALCULS ----------

    public void entToBin()
    {
        int dividende, diviseur, quotient, reste;

        dividende = entier;
        diviseur = 2;
        reste = dividende % diviseur;
        quotient = (dividende - reste) / diviseur;

        t_bin.add(reste);

        while(quotient != 0)
        {
            dividende = quotient;
            reste = dividende % diviseur;
            quotient = (dividende - reste) / diviseur;
            t_bin.add(reste);
        }
    }

    public void entToHexa()
    {
        int dividende, diviseur, quotient, reste;

        dividende = entier;
        diviseur = 16;
        reste = dividende % diviseur;
        quotient = (dividende - reste) / diviseur;

        t_hexa_calc.add(reste);

        while(quotient != 0)
        {
            dividende = quotient;
            reste = dividende % diviseur;
            quotient = (dividende - reste) / diviseur;
            t_hexa_calc.add(reste);
        }
    }

    // ---------- METHODES ARRAYLIST EN STRING ----------

    public String chaineBin()
    {
        String s = new String();
        char c;
        int i;

        if(saisiBin == true)
        {
            for(i = 0; i < t_bin.size(); i++)
            {
                c = (char)(t_bin.get(i).intValue() + '0');
                s = s + c;
            }

            saisiBin = false;
        }
        else
        {
            for(i = (t_bin.size() - 1); i >= 0; i--)
            {
                c = (char)(t_bin.get(i).intValue() + '0');
                s = s + c;
            }
        }

        return s;
    }

    public String chaineHexa()
    {
        String s = new String();
        char c;
        int i;

        if(saisiHexa == true)
        {
            for(i = 0; i < t_hexa_calc.size(); i++)
            {
                c = (char)t_hexa.get(t_hexa_calc.get(i).intValue());
                s = s + c;
            }

            saisiHexa = false;
        }
        else
        {
            for(i = (t_hexa_calc.size() - 1); i >= 0; i--)
            {
                c = (char)t_hexa.get(t_hexa_calc.get(i).intValue());
                s = s + c;
            }
        }

        return s;
    }

    // ---------- METHODE AFFICHAGE ----------

    public void affichRes()
    {
        System.out.println("Entier: " + entier);
        System.out.println("Binaire: " + chaineBin());
        System.out.println("Hexadecimal: " + chaineHexa());
    }
}


// -------------------------------------- CLASS-ENFANT ENTIER --------------------------------------

class Entier extends Number
{
    public Entier() throws IOException
    {
        Scanner scan = new Scanner(System.in);
        String videBuffer = new String();

        System.out.println("Entrer un entier: ");
        entier = scan.nextInt();
        videBuffer = scan.nextLine();

        while(entier < 0)
        {
            System.out.println("Erreur: l'entier saisi doit être positif.");
            System.out.println("Entrer un entier: ");
            entier = scan.nextInt();
            videBuffer = scan.nextLine();
        }

        if (entier > 1048575)
        {
            entier = 1048575;
        }
    }
}


// -------------------------------------- CLASS-ENFANT BINAIRE --------------------------------------

class Binaire extends Number
{
    public Binaire() throws IOException
    {
        saisiBin = true;

        // ----- PARTIE SAISI -----
        String saisi = new String();
        Scanner scan = new Scanner(System.in);

        System.out.println("Entrez un binaire: ");
        saisi = scan.nextLine();

        while((saisi.charAt(0) != '0') && (saisi.charAt(0) != '1'))
        {
            System.out.println("Erreur: un nombre binaire est constitué exclusivement de 0 et de 1.");
            System.out.println("Entrez un binaire: ");
            saisi = scan.nextLine();
        }

        // permet d'éviter l'erreur du retour charriot
        saisi = saisi + '.';

        // ----- PARTIE CONVERTION -----
        int i = 0;

        while((t_bin.size() < 20 ) && ((saisi.charAt(i) == '0') || (saisi.charAt(i) == '1')))
        {
            t_bin.add(saisi.charAt(i) + '0' - 96);
            i++;
        }
    }

    // ---------- METHODE CALCUL ----------

    public void binToEnt()
    {
        int i, j;
        j = 0;

        for(i = (t_bin.size() - 1); i >= 0; i--)
        {
            entier = entier + (t_bin.get(i) * (int)Math.pow(2, j));
            j++;
        }
    }
}


// -------------------------------------- CLASS-ENFANT HEXADECIMAL --------------------------------------

class Hexadecimal extends Number
{
    public Hexadecimal() throws IOException
    {
        saisiHexa = true;

        // ----- PARTIE SAISI -----
        String saisi = new String();
        Scanner scan = new Scanner(System.in);

        System.out.println("Entrez un nombre hexadecimal: ");
        saisi = scan.nextLine();

        while(t_hexa.contains(saisi.charAt(0)) == false)
        {
            System.out.println("Erreur: un nombre hexadecimal est constitué de chiffre et/ou des lettres A à F.");
            System.out.println("Entrez un nombre hexadeciaml: ");
            saisi = scan.nextLine();
        }

        // permet d'éviter l'erreur du retour charriot
        saisi = saisi + '.';


        // ----- PARTIE CONVERTION -----
        int i = 0;

        while((t_hexa_calc.size() < 5 ) && (t_hexa.contains(saisi.charAt(i)) == true))
        {
            if ((saisi.charAt(i)=='A') || (saisi.charAt(i)=='a'))
            {
                t_hexa_calc.add(10);
            }
            else if ((saisi.charAt(i)=='B') || (saisi.charAt(i)=='b'))
            {
                t_hexa_calc.add(11);
            }
            else if ((saisi.charAt(i)=='C') || (saisi.charAt(i)=='c'))
            {
                t_hexa_calc.add(12);
            }
            else if ((saisi.charAt(i)=='D') || (saisi.charAt(i)=='d'))
            {
                t_hexa_calc.add(13);
            }
            else if ((saisi.charAt(i)=='E') || (saisi.charAt(i)=='e'))
            {
                t_hexa_calc.add(14);
            }
            else if ((saisi.charAt(i)=='F') || (saisi.charAt(i)=='f'))
            {
                t_hexa_calc.add(15);
            }
            else
            {
                t_hexa_calc.add(saisi.charAt(i) + '0' - 96);
            }
            i++;
        }
    }

    // ---------- METHODE CALCUL ----------

    public void hexaToEnt()
    {
        int i, j;
        j = 0;

        for(i = (t_hexa_calc.size() - 1); i >= 0; i--)
        {
            entier = entier + (t_hexa_calc.get(i) * (int)Math.pow(16, j));
            j++;
        }
    }
}


// -------------------------------------- CLASS-ENFANT HEXADECIMAL --------------------------------------

class Convertor
{
    public static void main(String[] args) throws IOException
    {
        String tete = new String("+-----------------------------------------------+");
        String espace = new String("|\t\t\t\t\t\t|");

        int resSwitch;
        String videBuffer = new String();
        boolean continuer = true;
        Scanner scan = new Scanner(System.in);

        while (continuer != false)
        {
            System.out.println(tete);
            System.out.println(espace);
            System.out.println("| De quel type est votre chiffre ?\t\t|");
            System.out.println("|\t 1 : Entier\t\t\t\t|");
            System.out.println("|\t 2 : Binaire\t\t\t\t|");
            System.out.println("|\t 3 : Hexadecimal\t\t\t|");
            System.out.println("|\t 4 : Quitter\t\t\t\t|");
            System.out.println(espace);
            System.out.println(tete);

            resSwitch = scan.nextInt();
            videBuffer = scan.nextLine();

            switch (resSwitch)
            {
                case 1:
                    Entier ent = new Entier();
                    ent.entToBin();
                    ent.entToHexa();
                    ent.affichRes();
                    break;

                case 2:
                    Binaire bin = new Binaire();
                    bin.binToEnt();
                    bin.entToHexa();
                    bin.affichRes();
                    break;

                case 3:
                    Hexadecimal hexa = new Hexadecimal();
                    hexa.hexaToEnt();
                    hexa.entToBin();
                    hexa.affichRes();
                    break;

                case 4:
                    System.out.println("Aurevoir.");
                    continuer = false;
                    break;

                default:
                    System.out.println("Erreur: Veuillez rentrer 1, 2, 3 ou 4.");
            }
        }
    }
}