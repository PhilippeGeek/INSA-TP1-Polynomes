package org.insalyon.pc.vienne;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // On demande le degré du polynome à entrer
        int deg=readInt("Quel est le degré de votre polynome ?");

        // On prépare le tableau des coefficients
        double[] poly=new double[deg+1];
        for(int i=0;i<=deg;i++)
            // On demande chaque coefficients à l'utilisateur
            poly[i]=readDouble("Coefficient du degré "+i);
        // On affiche le polynôme saisit
        System.out.println("Le polynome est donc :");
        displayPoly(poly);

        // Recherche de racine
        System.out.println("La racine la plus proche est : " +
                // Dans ce paramètre, on demande à la fois la valeur de début et la précision et on lance le calcul.
            polyNewton(poly,readDouble("On commence à la valeur"),readDouble("Quelle précision ?")));
    }

    /**
     * Affiche un polynôme dans la console.
     * @param coeffs Les coefficiants du polynôme
     *
     */
    public static void displayPoly(double[] coeffs){
        String poly = "";
        for (int i = coeffs.length-1; i >= 0; i--) {
            if(coeffs[i]==0) // Si le coefficient est nul on affiche rien
                continue;
            poly+="("+coeffs[i]+")";
            if(i>1) // Si le coefficient est supérieur à 1 on affiche la puissance
                poly+="x^"+i;
            else if(i>0) // Pour 1, on affiche juste x
                poly+="x";
            if(i!=0&&!isCoefNullBetween(coeffs,0,i)) // On écrit '+' uniquement si on a quelque chose à écrire
                poly+="+";
        }
        System.out.println(poly);
    }

    /**
     * Calcule la valeur d'un polynôme pour un x donnée.
     * @param coeffs Les coefficients du polynôme
     * @param x Le x pour lequel on calcule
     * @return La valeur
     */
    public static double valPoly(double[] coeffs, double x){
        double value=0;
        for (int i = coeffs.length-1; i >= 0; i--) {
            value = value * x + coeffs[i];
        }
        return value;
    }

    /**
     * Dérivé du polynôme.
     * @param coeffs Les coefficients du polynôme
     * @return les coefficients de la dérivé
     */
    public static double[] derivePoly(double[] coeffs){
        double[] derive=new double[coeffs.length-1];
        for(int i=1;i<coeffs.length;i++)
            derive[i-1]=coeffs[i]*i; // On décale les coefficients de 1 en multipliant par la puissance (cf. Maths)
        return derive;
    }

    /**
     * Racines par newton.
     * Recherche la racine la plus proche pour le polynôme par la Méthode de Newton (x_(n+1)=x_n-(P(x_n)/P'(x_n))).
     * @param coeffs Les coefficients du polynôme
     * @param start Point de départ de la suite (x_0)
     * @param precision Précision du P(x)
     * @return La racine la plus proche trouvée.
     */
    public static double polyNewton(double[] coeffs, double start, double precision){
        double[] coeffsDerive = derivePoly(coeffs); // On récupère la dérivée
        double x=start;
        while (Math.abs(valPoly(coeffs,x))>precision){ // Tant qu'on a pas la précision voulut
            x=x-(valPoly(coeffs,x)/valPoly(coeffsDerive,x)); // On calcule le terme suivant
        }
        return x;
    }

    ////////////////////////////////////////////////////////////////////////////////////////
    //////         Some utils stuff
    ////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Détermine si les coefficients entre deux indexs sont nulles.
     * @param coeffs Les coefficients du polynôme
     * @param start Index de début de la vérification
     * @param end Index de fin de la vérification (exclu)
     * @return vrais si les coefficients entre start et end sont nulls.
     * @throws java.lang.IllegalArgumentException Le début doit être inférieur ou égal à la fin.
     */
    private static boolean isCoefNullBetween(double[] coeffs, int start, int end){
        if(start>end)
            throw new IllegalArgumentException("Le début de la vérification ne peut se faire après la fin");
        if(start>=coeffs.length)
            throw new IllegalArgumentException("Le début est après la fin du Tableau");
        boolean areNull=true; // Drapeau qui change si un des coef n'est pas nulle
        for(int i=start;areNull&&i<coeffs.length&&i<end;i++){
            if(coeffs[i]!=0)
                areNull=false;
        }
        return areNull;
    }

    /**
     * Demande à l'utilisateur de saisir un entier
     * @param question La question à poser à l'utilisateur
     * @return L'entier saisit
     */
    private static int readInt(String question){
        Scanner sc=new Scanner(System.in);
        System.out.print(question+" : ");
        try{
            return sc.nextInt();
        }catch(Exception e){
            return readInt(question+" (Again !) ");
        }
    }

    /**
     * Demande à l'utilisateur de saisir un décimal
     * @param question La question à poser à l'utilisateur
     * @return L'entier saisit
     */
    private static double readDouble(String question){
        Scanner sc=new Scanner(System.in);
        System.out.print(question+" : ");
        try{
            return sc.nextDouble();
        }catch(Exception e){
            return readDouble(question + " (Again !) ");
        }
    }
}
