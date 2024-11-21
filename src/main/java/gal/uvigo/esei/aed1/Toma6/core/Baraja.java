
/*
* Representa la baraja del juego Toma 6, en total 104 cartas, enumeradas del 1 al 104 con el número de bueyes
* correspondiente en función del valor de la misma (revisar condiciones en el enunciado del juego). 
* Estructura: se utilizará un TAD adecuado
* Funcionalidad: barajar las cartas, devolver la carta situada encima del montón de cartas
 */
package gal.uvigo.esei.aed1.Toma6.core;
import java.util.Stack;

public class Baraja {
    
    public static final int NCARTAS_DEF = 104; //Estandar: 104
    Stack<Carta> baraja;
    
    public Baraja(){
        baraja = new Stack<>();
        for (int i = 1; i <= NCARTAS_DEF; i++) {
            baraja.push(new Carta(i));  
        }
    }
    
    /*Mueve nr(numero aleatorio que cambia NCARTAS_DEF*2 veces) elementos de la baraja principal, 
    * a una secundaria y de la secundaria a la terciaria, al devolver los nr elementos de la terciaria 
    * a la principal estos quedan invertidos y los elementos que no se cogieron quedan igual.
    * Esto se hace NCARTAS_DEF*2 veces (cuanto más alto el valor respecto a NCARTAS_DEF más desordenada queda), 
    * para que la baraja se desordene completamente. */
    public void barajar(){
        Stack<Carta> baraja2 = new Stack<>();
        Stack<Carta> baraja3 = new Stack<>();
        for (int i = 0; i < NCARTAS_DEF*2; i++) {
            var nr = (int) Math.floor(Math.random() * baraja.size() + 1);
            for (int j = 0; j < nr; j++) {
                baraja2.push(baraja.pop());
            }
            while (!baraja2.empty()) {
                baraja3.push(baraja2.pop());
            }
            while (!baraja3.empty()) {
                baraja.push(baraja3.pop());
            }
        }
    }
    //Devuelve la carta de la cima de la baraja
    public Carta cogerCarta(){ 
        return baraja.pop();
    }
    //Pone la carta que recibe como parametro en la cima de la baraja
    public void ponerCarta(Carta c){ 
        baraja.push(c);
    }
}
