package org.insalyon.pc.vienne;

public class Main {

    public static void main(String[] args) {
        displayPoly(new double[]{4,5,0,0,1});
        System.out.println(valPoly(new double[]{1,3,5},2));
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
        System.out.println();
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
}
