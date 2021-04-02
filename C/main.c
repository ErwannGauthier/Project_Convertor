#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>
#include <math.h>

// ------------------------------ CONSTANTES ET STRUCTURES ------------------------------

// Affichage

const char TETE[] = "+-----------------------------------------------+";
const char ESPACE[] = "|\t\t\t\t\t\t|";

// Saisi-Calcul
// Chiffre max : 1 048 575, (FFFFF)16, (11111111111111111111)2

const int MAX_ENT = 1048575;

#define MAX_TAB_BIN 20
typedef int tabBin[MAX_TAB_BIN];

#define MAX_TAB_HEXA 5
typedef char tabHexa[MAX_TAB_HEXA];
typedef int tabHexaCalc[MAX_TAB_HEXA];

typedef struct
{
	int resEnt;

	tabBin t_bin;
    int nbBin;
    bool saisiBin;

    tabHexa t_hexa;
    tabHexaCalc t_hexa_calc;
    int nbHexa;
    bool saisiHexa;
} s_tab;

// Ensemble des valeurs hexadécimales possibles
#define MAX_ENSEMBLE_HEXA 22
const char ENSEMBLE_HEXA[MAX_ENSEMBLE_HEXA] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'a', 'b', 'c', 'd', 'e', 'f'};

// ------------------------------ SAISIE ------------------------------

//Convertion char en int.
int charInt(char c)
{
    int charInt;
    charInt = c - '0';

    return charInt;
}

void initTab(s_tab *tab)
{
    (*tab).nbBin = 0;
    (*tab).saisiBin = false;

    (*tab).nbHexa = 0;
    (*tab).saisiHexa = false;
}

void saisiEnt(s_tab *tab)
{
    printf("Entrer le nombre entier : ");
    scanf("%d", &(*tab).resEnt);

    if ((*tab).resEnt < 0)
    {
        printf("Veuillez entrer un entier positif : ");
        saisiEnt(&(*tab));
    }
    else if ((*tab).resEnt > MAX_ENT)
    {
        (*tab).resEnt = MAX_ENT;
    }
}

void saisiBin(s_tab *tab)
{
    // ----- PARTIE SAISI -----
    (*tab).saisiBin = true;
    char saisi[MAX_TAB_BIN];

    printf("Entrer le nombre binaire : ");
    scanf("%s", saisi);

    //Vérifie que la chaine saisie n'est pas trop grande.
    while(strlen(saisi) > MAX_TAB_BIN)
    {
        printf("Erreur, votre nombre binaire ne peut pas dépasser les 20 chiffres. Recommencez :\n");
        scanf("%s", saisi);
    }

    //Vérifie que le premier caractère est égale à '0' ou '1' si non ressaisir.
    while((saisi[0] != '0') && (saisi[0] != '1'))
    {
        printf("Erreur: un nombre binaire est constitué exclusivement de 0 et de 1.");
		printf("Entrer un nombre binaire :");
		scanf("%s", saisi);

		while(strlen(saisi) > MAX_TAB_BIN)
        {
            printf("Erreur, votre nombre binaire ne peut pas dépasser les 20 chiffres. Recommencez :\n");
            scanf("%s", saisi);
        }
    }

    // ----- PARTIE CONVERTION -----
    int i = 0;

    while((i < MAX_TAB_BIN) && ((saisi[i] == '0') || (saisi[i] == '1')))
    {
        (*tab).t_bin[i] = charInt(saisi[i]);
        i++;
    }

    (*tab).nbBin = i;
}

// Retourne true si saisi se trouve dans l'ensemble hexadecimal, false si non
bool estHexa(char saisi)
{
    bool trouve = false;
    int i = 0;

    while((i < MAX_ENSEMBLE_HEXA) && (trouve == false))
    {
        if(saisi - ENSEMBLE_HEXA[i] == 0)
        {
            trouve = true;
        }

        i++;
    }

    return trouve;
}

void saisiHexa(s_tab *tab)
{
    // ----- PARTIE SAISI -----
    (*tab).saisiHexa = true;
    char saisi[MAX_TAB_HEXA];

    printf("Entrer le nombre hexadecimal : ");
    scanf("%s", saisi);

    //Vérifie que la chaine saisie n'est pas trop grande.
    while(strlen(saisi) > MAX_TAB_HEXA)
    {
        printf("Erreur, votre nombre hexadécimal ne peut pas dépasser les 5 chiffres. Recommencez :\n");
        scanf("%s", saisi);
    }

    //Vérifie que le premier caractère est dans l'ensemble hexadécimal si non ressaisir.
    while(estHexa(saisi[0]) == false)
    {
        printf("Erreur: un nombre hexadécimal est constitué de chiffre et/ou des lettres A à F.\n");
		printf("Entrer un nombre hexadécimal : ");
		scanf("%s", saisi);

		//Vérifie que la chaine saisie n'est pas trop grande.
        while(strlen(saisi) > MAX_TAB_HEXA)
        {
            printf("Erreur, votre nombre hexadéciaml ne peut pas dépasser les 5 chiffres. Recommencez :\n");
            scanf("%s", saisi);
        }
    }

    // ----- PARTIE CONVERTION -----
    int i = 0;

    while((i < MAX_TAB_HEXA) && (estHexa(saisi[i]) == true))
    {
        (*tab).t_hexa[i] = saisi[i];
        i++;
    }

    (*tab).nbHexa = i;

}

// ------------------------------ CALCUL ------------------------------

void hexaCalc_To_HexaSaisi(s_tab *tab)
{
    int i;

    for(i=0; i <= (*tab).nbHexa; i++)
    {
        (*tab).t_hexa[i] = ENSEMBLE_HEXA[(*tab).t_hexa_calc[i]];
    }
}

void hexaSaisi_To_HexaCalc(s_tab *tab)
{
    int i;

    for(i=0; i < (*tab).nbHexa; i++)
    {
        if(((*tab).t_hexa[i]=='A') || ((*tab).t_hexa[i]=='a'))
        {
            (*tab).t_hexa_calc[i] = 10;
        }

        else if(((*tab).t_hexa[i]=='B') || ((*tab).t_hexa[i]=='b'))
        {
            (*tab).t_hexa_calc[i] = 11;
        }

        else if(((*tab).t_hexa[i]=='C') || ((*tab).t_hexa[i]=='c'))
        {
            (*tab).t_hexa_calc[i] = 12;
        }

        else if(((*tab).t_hexa[i]=='D') || ((*tab).t_hexa[i]=='d'))
        {
            (*tab).t_hexa_calc[i] = 13;
        }

        else if(((*tab).t_hexa[i]=='E') || ((*tab).t_hexa[i]=='e'))
        {
            (*tab).t_hexa_calc[i] = 14;
        }

        else if(((*tab).t_hexa[i]=='F') || ((*tab).t_hexa[i]=='f'))
        {
            (*tab).t_hexa_calc[i] = 15;
        }

        else{
            (*tab).t_hexa_calc[i] = charInt((*tab).t_hexa[i]);
        }

    }
}

void entToBin(s_tab *tab)
{
    int dividende, diviseur, quotient, reste;
    int i = 0;

    dividende = (*tab).resEnt;
    diviseur = 2;
    reste = dividende % diviseur;
    quotient = (dividende - reste) / diviseur;

    (*tab).t_bin[i] = reste;

    while(quotient > 0)
    {
        i++;
        dividende = quotient;
        reste = dividende % diviseur;
        quotient = (dividende - reste) / diviseur;
        (*tab).t_bin[i] = reste;
    }

    (*tab).nbBin = i;
}

void entToHexa(s_tab *tab)
{
    int dividende, diviseur, quotient, reste;
    int i = 0;

    dividende = (*tab).resEnt;
    diviseur = 16;
    reste = dividende % diviseur;
    quotient = (dividende - reste) / diviseur;
    (*tab).t_hexa_calc[i] = reste;

    while(quotient != 0)
    {
        i++;
        dividende = quotient;
        reste = dividende % diviseur;
        quotient = (dividende - reste) / diviseur;
        (*tab).t_hexa_calc[i] = reste;
    }

    (*tab).nbHexa = i;

    hexaCalc_To_HexaSaisi(&(*tab));
}

void binToEnt(s_tab *tab)
{
    int somme, i, j;
    somme = 0;
    j = 0;

    for(i= ((*tab).nbBin - 1); i >= 0; i--)
    {
        somme = somme + ((*tab).t_bin[i] * pow(2, j));
        j++;
    }

    (*tab).resEnt = somme;
}

void hexaToEnt(s_tab *tab)
{
    hexaSaisi_To_HexaCalc(&(*tab));

    int i, j, somme;
    somme = 0;
    j = 0;

    for(i = ((*tab).nbHexa - 1); i >= 0; i--)
    {
        somme = somme + ((*tab).t_hexa_calc[i] * (int)pow(16, j));
        j++;
    }

    (*tab).resEnt = somme;
}

// ------------------------------ AFFICHAGE ------------------------------

void affichMenu()
{
    printf("%s\n", TETE);
    printf("%s\n", ESPACE);
    printf("| De quel type est votre chiffre ?\t\t|\n");
    printf("|\t 1 : Entier\t\t\t\t|\n");
    printf("|\t 2 : Binaire\t\t\t\t|\n");
    printf("|\t 3 : Hexadecimal\t\t\t|\n");
    printf("|\t 4 : Quitter\t\t\t\t|\n");
    printf("%s\n", ESPACE);
    printf("%s\n\n", TETE);
}

void affichBin(s_tab *tab)
{
    int i;

    if((*tab).saisiBin == true)
    {
        for(i=0; i < (*tab).nbBin; i++)
        {
            printf("%d", (*tab).t_bin[i]);
        }
    }
    else
    {
        for(i= (*tab).nbBin; i >= 0; i--)
        {
            printf("%d", (*tab).t_bin[i]);
        }
    }
}

void affichHexa(s_tab *tab)
{
    int i;

    if((*tab).saisiHexa == true)
    {
        for(i=0; i < (*tab).nbHexa; i++)
        {
            printf("%c", (*tab).t_hexa[i]);
        }
    }
    else
    {
        for(i = (*tab).nbHexa; i >= 0; i--)
        {
            printf("%c", (*tab).t_hexa[i]);
        }
    }
}

void affichRes(s_tab *tab)
{
    printf("\n%s\n", TETE);
    printf("%s\n", ESPACE);
    printf("| Entier : %d\t\t\t\t|\n", (*tab).resEnt);
    printf("| Binaire : ");
    affichBin(tab);
    printf("\t\t|\n");
    printf("| Hexadecimal : ");
    affichHexa(tab);
    printf("\t\t\t\t|\n");
    printf("%s\n", ESPACE);
    printf("%s\n", TETE);
}



int main()
{
    int resSwitch;
    bool continuer;
    s_tab tableaux;
    continuer = true;

    while(continuer != false)
    {
        initTab(&tableaux);

        affichMenu();
        scanf("%d", &resSwitch);
        switch(resSwitch)
        {
            case 1: saisiEnt(&tableaux);
                    entToBin(&tableaux);
                    entToHexa(&tableaux);
                    affichRes(&tableaux);
                    break;

            case 2: saisiBin(&tableaux);
                    binToEnt(&tableaux);
                    entToHexa(&tableaux);
                    affichRes(&tableaux);
                    break;

            case 3: saisiHexa(&tableaux);
                    hexaToEnt(&tableaux);
                    entToBin(&tableaux);
                    affichRes(&tableaux);
                    break;

            case 4: printf("Aurevoir.\n");
                    continuer = false;
                    break;

            default:    printf("Erreur: Veuillez rentrer 1, 2, 3 ou 4.\n");
        }
    }
}

