/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gal.uvigo.esei.aed1.Toma6.core;

import java.util.Stack;

/**
 *
 * @author M A
 */
public class Mesa {

    public static final int NFILAS = 4; //Estandar: 4 
    public static final int MAXFILA = 5; //Estandar: 5
    private Stack<Carta>[] filas;

    public Mesa() {
        filas = new Stack[NFILAS];
        for (int j = 0; j < NFILAS; j++) {
            filas[j] = new Stack<>();
        }
    }

    public void iniciarMesa(Baraja b) {
        //Limpia las filas y las devuelve a la baraja
        for (int j = 0; j < NFILAS; j++) {
            while (!filas[j].isEmpty()) {
                b.ponerCarta(filas[j].pop());
            }
        }
        b.barajar(); //Baraja la baraja despues de lo anterior
        //Va repartiendo una carta de la baraja por cada fila
        for (int j = 0; j < NFILAS; j++) {
            filas[j].push(b.cogerCarta());
        }
    }

    /*Devuelve verdadero si la carta se inserto correctamente, 
      devuelve falso en que no (la carta más alta de cada fila es mayor que la que quieres jugar y toca seleccionar fila) */
    public int filaAinsertar(Carta c, Jugador jug) {
        int i = -1;
        int dif = Baraja.NCARTAS_DEF;
        //Busca una fila que tenga tenga una carta menor que la tuya y con la menor diferencia posible
        for (int j = 0; j < NFILAS; j++) {
            if (c.getNcarta() > filas[j].peek().getNcarta() && (c.getNcarta() - filas[j].peek().getNcarta()) < dif) {
                i = j;
                dif = (c.getNcarta() - filas[j].peek().getNcarta());
            }
        }
        return i;
    }
    
    //Pone la fila que se le pasa como parametro en el monton del jugador jug
    //Y se añade a esa fila vacia la carta que se la pasa como parametro
    public void limpiarFila(Carta c, int fila, Jugador jug) {
        jug.amontonarFila(filas[fila]);
        filas[fila].push(c);
    }
    
    public boolean isFilaLlena(int fila){
        return filas[fila].size() == MAXFILA;
    }
    
    public void insertarEnFila(Carta c, int fila){
        filas[fila].push(c);
    }

    @Override
    public String toString() {
        Stack<Carta> aux = new Stack<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < NFILAS; i++) {
            while (!filas[i].empty()) {
                aux.push(filas[i].pop());
            }
            sb.append(i + 1).append(". ");
            while (!aux.empty()) {
                sb.append(aux.peek()).append(" ");
                filas[i].push(aux.pop());
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}
