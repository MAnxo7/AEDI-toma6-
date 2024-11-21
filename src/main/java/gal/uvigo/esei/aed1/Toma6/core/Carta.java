
/*
 * Representa una carta, formada por un número y la cantidad de bueyes correspondiente
 */
package gal.uvigo.esei.aed1.Toma6.core;

public class Carta {
    private int ncarta;
    private int nbueyes;
    public Carta(int ncarta){
        this.ncarta = ncarta;
        
        if (ncarta == 55){
            this.nbueyes = 7;
        }else if (ncarta%5 == 0 && ncarta%10 != 0){
            this.nbueyes = 2;
        }else if (ncarta%10 == 0){
            this.nbueyes = 3;
        }else if (ncarta%11 == 0){
            this.nbueyes = 5;
        }else{
            this.nbueyes = 1;
        }
    }

    public int getNcarta() {
        return ncarta;
    }

    public int getNbueyes() {
        return nbueyes;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(ncarta).append("|").append("B").append(nbueyes).append("]");
        return sb.toString();
    }

}
