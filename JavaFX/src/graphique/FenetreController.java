package graphique;

import convertion.Binaire;
import convertion.Entier;
import convertion.Hexadecimal;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FenetreController implements Initializable {

    private final ArrayList<Character> t_hexa = new ArrayList<>() {{
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
    private final ArrayList<Character> t_char_problematiques = new ArrayList<>(){{
        add('.');
        add('+');
        add('-');
        add('*');
        add('/');
        add('^');
        add('(');
        add(')');
        add('{');
        add('}');
        add('[');
        add(']');
        add('$');
        add('|');
        add('?');
        add('\\');
    }};

    private boolean peutClear;

    @FXML
    private TextField textField_Entier;

    @FXML
    private TextField textField_Binaire;

    @FXML
    private TextField textField_Hexa;

    @FXML
    private Label labelErreur;

    @FXML
    private Hyperlink lien;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Ecouteurs
        // Si le texteField subit une modification alors on vérifie que cette modification est correcte.
        this.textField_Entier.textProperty().addListener((observable, oldValue, newValue) -> entier());
        this.textField_Binaire.textProperty().addListener((observable, oldValue, newValue) -> binaire());
        this.textField_Hexa.textProperty().addListener((observable, oldValue, newValue) -> hexa());
    }


    @FXML
    // Fonction pour bouton Réinitialiser
    private void resetTextField(){
        this.textField_Entier.setText("");
        this.textField_Binaire.setText("");
        this.textField_Hexa.setText("");
        this.labelErreur.setText("");
    }

    // Retourne vrai si la fonction modifie le TextField
    private boolean verifPasZeroPremier(TextField textField){
        boolean resultat = false;

        // Tant que qu'il y a plus d'un caractère et que le premier caractère est un zero alors on le supprime
        while(textField.getText().length() > 1 && textField.getText().charAt(0) == '0') {
            textField.setText(new StringBuilder(textField.getText()).deleteCharAt(0).toString());
            resultat = true;
        }

        return resultat;
    }

    // Traite la saisie pour Entier -- Taille max : 2,147,483,647
    private void entier(){

        // Réinitialise le message d'erreur seulement si on peut convertir
        if(this.peutClear) {
            this.labelErreur.setText("");
        }

        /*
        Vérifie que le textField n'est pas vide
        Puis empêche l'utilisateur de saisir un 0 en premier caractère
        */
        if(!this.textField_Entier.getText().equals("") && !verifPasZeroPremier(this.textField_Entier)){

            // Si on saisi un mauvais caractère alors on le supprime
            if(!entier_verifSaisi()){
                this.peutClear = false;
                this.labelErreur.setText("Veuiller saisir uniquement des chiffres.");
                entier_supprimeCaractere();
            }

            // Si on dépasse la limite alors on remplace la valeur saisie par la limite
            entier_maxAtteint();

            // Convertie l'entier
            if(!this.textField_Entier.getText().equals("")){
                this.peutClear = true;
                Entier ent = new Entier(Integer.parseInt(this.textField_Entier.getText()));
                ent.entToBin();
                ent.entToHexa();
                this.textField_Binaire.setText(ent.chaineBin());
                this.textField_Hexa.setText(ent.chaineHexa());
            }
        }
    }

    // Vérifie si le max n'est pas atteint, remplace par max sinon
    private void entier_maxAtteint(){
        String max = "2147483647";
        /* Empêche l'utilisateur de saisir un entier plus grand que max
                --> Converti les String en Long afin de les comparer
                        car la valeur max des int est atteinte */
        try{
            if(this.textField_Entier.getText().length() > max.length() ||
                    Long.parseLong(this.textField_Entier.getText()) > Long.parseLong(max)){
                this.textField_Entier.setText(max);
                this.labelErreur.setText("Vous ne pouvez pas saisir un entier plus grand que :\n 2 147 483 647");
            }
        }
        catch(NumberFormatException ignored){}


    }

    // Retourne faux si un des caractères saisi n'est pas un chiffre
    private boolean entier_verifSaisi(){

        boolean resultat = true;
        try{
            // Retourne faux si le nombre est négatif ou si ce n'est pas un nombre
            if(Integer.parseInt(this.textField_Entier.getText()) < 0){
                resultat = false;
            }
        }
        catch(NumberFormatException ex){
            resultat = false;
        }

        return resultat;
    }

    // Supprime le(s) caractère(s) problèmatique(s)
    private void entier_supprimeCaractere(){
        int i = 0;
        char caractereCourant = ' ';

        while(i < this.textField_Entier.getText().length()){
            try{
                // Prend le caractère en i-ème position dans le TextField
                caractereCourant = this.textField_Entier.getText().charAt(i);

                // Si le caractère est défini en tant que caractere problematique alors on le supprime
                if(this.t_char_problematiques.contains(caractereCourant)){
                    // Supprime le caractère
                    this.textField_Entier.setText(String.valueOf(new StringBuilder(this.textField_Entier.getText()).deleteCharAt(i)));
                    i--;
                }
                else {
                    // Le passe en entier, si ce n'est pas un entier --> Voir catch
                    Integer.parseInt(String.valueOf(caractereCourant));
                }
            }
            catch(NumberFormatException e){
                // Supprime le caractère si ce n'est pas un entier
                this.textField_Entier.setText(this.textField_Entier.getText().replaceAll(String.valueOf(caractereCourant), ""));

                // Enlève 1 à i afin de ne pas sauter le caractère suivant
                i--;
            }

            i++;
        }
    }

    // Traite la saisi pour binaire -- Taille max : 0111 1111 1111 1111 1111 1111 1111 1111
    private void binaire(){

        // Réinitialise le message d'erreur seulement si on peut convertir
        if(this.peutClear) {
            this.labelErreur.setText("");
        }

        /*
        Vérifie que le textField n'est pas vide
        Puis empêche l'utilisateur de saisir un 0 en premier caractère
        */
        if(!this.textField_Binaire.getText().equals("") && !verifPasZeroPremier(this.textField_Binaire)){

            // Si on atteint pas la limite max et qu'on saisi un mauvais caractère alors
            if(!bin_maxAtteint() && !binaire_verifSaisi()){
                this.peutClear = false;
                this.labelErreur.setText("Veuiller saisir uniquement des 0 et des 1.");
                binaire_supprimeCaractere();
            }

            if(!this.textField_Binaire.getText().equals("")){
                this.peutClear = true;
                Binaire bin = new Binaire(this.textField_Binaire.getText());
                bin.binToEnt();
                bin.entToHexa();
                this.textField_Entier.setText(String.valueOf(bin.getEntier()));
                this.textField_Hexa.setText(bin.chaineHexa());
            }
        }
    }

    /* Retourne faux si le max n'est pas atteint, vrai sinon
            Max = 0111 1111 1111 1111 1111 1111 1111 1111
     */
    private boolean bin_maxAtteint(){
        boolean resultat = false;

        // Empêche l'utilisateur de saisir un entier plus grand que max
        if(this.textField_Binaire.getText().length() > 31){
            this.textField_Binaire.setText("1111111111111111111111111111111");
            this.labelErreur.setText("Vous ne pouvez pas saisir un entier plus grand que :\n0111 1111 1111 1111 1111 1111 1111 1111");
            resultat = true;
        }

        return resultat;
    }

    // Retourne faux si un des caractères saisi est différent de 0 ou 1
    private boolean binaire_verifSaisi(){

        // Vérifie que les chiffres sont égaux à 0 ou 1
        // Sinon retourne faux
        int i = 0;
        boolean reponse = true;
        while(i < this.textField_Binaire.getText().length() && reponse){

            char caractereCourant = this.textField_Binaire.getText().charAt(i);
            if( caractereCourant != '0' && caractereCourant != '1'){
                reponse = false;
            }
            i++;
        }

        return reponse;
    }

    // Supprime le(s) caractère(s) problèmatique(s)
    private void binaire_supprimeCaractere(){

        int i = 0;
        char caractereCourant;

        while(i < this.textField_Binaire.getText().length()){

            caractereCourant = this.textField_Binaire.getText().charAt(i);

            // Si le caractère est défini en tant que caractere problematique alors on le supprime
            if(this.t_char_problematiques.contains(caractereCourant)){
                // Supprime le caractère
                this.textField_Binaire.setText(String.valueOf(new StringBuilder(this.textField_Binaire.getText()).deleteCharAt(i)));
                i--;
            }
            else if( caractereCourant != '0' && caractereCourant != '1'){
                // Supprime le caractère si ce n'est pas un 0 ou 1
                this.textField_Binaire.setText(this.textField_Binaire.getText().replaceAll(String.valueOf(caractereCourant), ""));

                // Enlève 1 à i afin de ne pas sauter le charactère suivant
                i--;
            }

            i++;
        }
    }

    // Traite la saisi pour Hexa -- Taille max : 7fffffff
    private void hexa(){

        // Réinitialise le message d'erreur seulement si on peut convertir
        if(this.peutClear) {
            this.labelErreur.setText("");
        }

        /*
        Vérifie que le textField n'est pas vide
        Puis empêche l'utilisateur de saisir un 0 en premier caractère
        */
        if(!this.textField_Hexa.getText().equals("") && !verifPasZeroPremier(this.textField_Hexa)){

            // Si on atteint pas la limite max et qu'on saisi un mauvais caractère alors
            if(!hexa_maxAtteint() && !hexa_verifSaisi()){
                this.peutClear = false;
                this.labelErreur.setText("Veuiller saisir uniquement des chiffres et les lettres A, B, C, D, E, F.");
                hexa_supprimeCaractere();
            }

            if(!this.textField_Hexa.getText().equals("")){
                this.peutClear = true;
                Hexadecimal hexa = new Hexadecimal(this.textField_Hexa.getText());
                hexa.hexaToEnt();
                hexa.entToBin();
                this.textField_Entier.setText(String.valueOf(hexa.getEntier()));
                this.textField_Binaire.setText(hexa.chaineBin());
            }
        }
    }

    /* Retourne faux si le max n'est pas atteint, vrai sinon
            Max = 7F FF FF FF
     */
    private boolean hexa_maxAtteint(){
        boolean resultat = false;
        String max = "7FFFFFFF";

        // Empêche l'utilisateur de saisir un entier plus grand que max
        if(this.textField_Hexa.getText().length() > max.length()){
            this.textField_Hexa.setText(max);
            resultat = true;
        }
        // Sinon si la taille est égale à celle de max, on vérifie que l'utilisateur ne saisi pas qqch de plus grand que max
        // De plus si l'utilisateur saisi qqch du type "7FFFFFF?" avec ? = une lettre > F, alors on remplace ? par F
        else if(this.textField_Hexa.getText().length() == max.length() && this.textField_Hexa.getText().toUpperCase().compareTo(max) > 0){
            this.textField_Hexa.setText(max);
            resultat = true;
        }
        
        if(resultat){
            this.labelErreur.setText("Vous ne pouvez pas saisir un entier plus grand que :\n7F FF FF FF");
        }

        return resultat;
    }

    private boolean hexa_verifSaisi(){

        int i = 0;
        boolean reponse = true;
        char caractereCourant;

        while(i < this.textField_Hexa.getText().length() && reponse){

            caractereCourant = this.textField_Hexa.getText().charAt(i);

            // Si le caractereCourant n'est pas dans le t_hexa alors retourne faux
            if(!this.t_hexa.contains(caractereCourant)){
                reponse = false;
            }

            i++;
        }

        return reponse;
    }

    // Supprime le(s) caractère(s) problèmatique(s)
    private void hexa_supprimeCaractere(){

        int i = 0;
        char caractereCourant;

        while(i < this.textField_Hexa.getText().length()){

            caractereCourant = this.textField_Hexa.getText().charAt(i);

            // Si le caractère est défini en tant que caractere problematique alors on le supprime
            if(this.t_char_problematiques.contains(caractereCourant)){
                // Supprime le caractère
                this.textField_Hexa.setText(String.valueOf(new StringBuilder(this.textField_Hexa.getText()).deleteCharAt(i)));
                i--;
            }
            // Si le caractereCourant n'est pas dans le t_hexa alors on le supprime
            else if(!this.t_hexa.contains(caractereCourant)){
                this.textField_Hexa.setText(this.textField_Hexa.getText().replaceAll(String.valueOf(caractereCourant), ""));

                // Enlève 1 à i afin de ne pas sauter le charactère suivant
                i--;
            }

            i++;
        }
    }

    // Fonction pour l'hyperlien @ErwannGauthier
    @FXML
    private void lienGitHub(){
        try {
            // Ouvre le lien dans le navigateur par defaut
            Desktop.getDesktop().browse(new URI("https://github.com/ErwannGauthier"));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

        // Remet l'hyperlien en non visité
        this.lien.setVisited(false);
    }
}
