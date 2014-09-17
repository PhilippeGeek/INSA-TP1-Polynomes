package org.insalyon.pc.vienne;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int deg=readInt("Quel est le degré de votre polynome ?");
        double[] poly=new double[deg+1];
        for(int i=0;i<=deg;i++)
            poly[i]=readDouble("Coefficient du degré "+i);
        System.out.println("Le polynome est donc :");
        displayPoly(poly);
        System.out.println("La racine la plus proche est : " +
            polyNewton(poly,readDouble("On commence à la valeur"),readDouble("Quelle précision ?")));
    }

    /**
     * Affiche un polynôme dans la console.
     * @param coeffs Les coefficiants du polynôme
     *
     */
    public static void displayPoly(double[] coeffs){
        StringBuilder builder = new StringBuilder("");
        for (int i = coeffs.length-1; i >= 0; i--) {
            if(coeffs[i]==0)
                continue;
            builder.append('(').append(coeffs[i]).append(')');
            if(i>1)
                builder.append("x^").append(i);
            else if(i>0)
                builder.append("x");
            if(i!=0&&!isCoefNullBetween(coeffs,0,i))
                builder.append("+");
        }
        System.out.println(builder);
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
            derive[i-1]=coeffs[i]*i;
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
        double[] coeffsDerive = derivePoly(coeffs);
        double x=start;
        while (Math.abs(valPoly(coeffs,x))>precision){
            x=x-(valPoly(coeffs,x)/valPoly(coeffsDerive,x));
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
