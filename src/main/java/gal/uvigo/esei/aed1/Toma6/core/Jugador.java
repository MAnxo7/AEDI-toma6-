/*
 * Representa a un jugador, identificado por el nombre y sus cartas de la mano
 * Estructura mano: se utilizará un TAD adecuado
 * Funcionalidad: Añadir carta a la mano (añadir la carta de foma que queden 
 * ordenadas de menor a mayor por su número), convertir a String el objeto Jugador (toString)
 */

package gal.uvigo.esei.aed1.Toma6.core;
import java.util.List;
import java.util.ArrayList;
import java.util.Stack;


public class Jugador {
    private String nombre;
    private List<Carta> mano;
    private Stack<Carta> monton;
    private int nBueyes;

    public Jugador(String nombre) {
        this.nombre = nombre;
        mano = new ArrayList<>();
        monton = new Stack<>();
        nBueyes = 0;
    }
    
    public String getNombre() {
        return nombre;
    }

    public int getnBueyes() {
        return nBueyes;
    }

    public int tamañoMano() {
        return mano.size();
    }
    
    //Actualiza el contador de bueyes (puntos negativos) de cada jugador y
    //devuelve cada carta del monton a la baraja
    public void actualizarBueyes(Baraja b) {
        while(!monton.isEmpty()){  
            nBueyes += monton.peek().getNbueyes();
            b.ponerCarta(monton.pop());
        }
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < mano.size(); j++) {
            sb.append(mano.get(j)).append(" ");
        }
        return sb.toString();
    }
    
    // ----------- METODOS DE JUEGO -------------
    
    //Añade a la mano ordenadamente la carta como parametro
    public void recogerCarta(Carta c) {
        int i = 0;
        while (i < mano.size() && mano.get(i).getNcarta() < c.getNcarta()) {
            i++;
        }
        mano.add(i, c);
    }
    
    //Devuelve una carta con un indice que se le pasa como parametro, y la elimina
    public Carta cogerCarta(int i) {
        return mano.remove(i);
    }
    
    //Se pone en el monton la fila que se pasa como parametro
    public void amontonarFila(Stack<Carta> fila){
        while(!fila.isEmpty()){
            monton.push(fila.pop());
        }
    }  
}
