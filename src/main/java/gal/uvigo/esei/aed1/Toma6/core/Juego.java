/**
 * Representa el juego Toma 6, con sus reglas (definidas en el documento Primera entrega).
 * Se recomienda una implementación modular.
 */
package gal.uvigo.esei.aed1.Toma6.core;

import gal.uvigo.esei.aed1.Toma6.iu.IU;
import java.util.Arrays;
import java.util.ArrayList;

public class Juego {

    private final IU iu;
    private Baraja baraja;
    private Jugador jugadores[];
    private Mesa mesa;
    private ArrayList<Jugador> ganadores;
    public static final int CartasPJ = 10; //Estandar: 10

    public Juego(IU iu) {
        this.iu = iu;
        this.baraja = new Baraja();
        crearJugadores();
        this.mesa = new Mesa();
        this.ganadores = new ArrayList<>(jugadores.length);
    }

    public void jugar() {
        boolean fin = false;
        //JUEGO PRINCIPAL POR RONDAS HASTA QUE ALGUIEN TENGA MÁS DE 66 BUEYES
        while (!fin) {
            mesa.iniciarMesa(baraja); //Tambien baraja las cartas
            repartir(baraja);
            iu.mostrarJugadores(Arrays.asList(jugadores));
            iu.mostrarMesa(mesa);
            //Ronda de juego
            for (int i = 0; i < CartasPJ; i++) {
                jugarCartas();
                iu.mostrarMesa(mesa);
            }
            //Actualizacion de nBueyes por jugador y comprobacion de condición de fin
            //FINAL DE RONDA
            for (Jugador jug : jugadores) {
                jug.actualizarBueyes(baraja);
                if (jug.getnBueyes() >= 66) { //Estandar: 66
                    fin = true;
                }
            }
            iu.MostrarBueyes(Arrays.asList(jugadores));
            if (!fin) {
                iu.SigRonda(); //Si el juego no se acabo se muestra el mensaje de nueva ronda
            }
        }
        //FIN DE LA PARTIDA, PREPARACIÓN PARA GANADORES
        int min = 0; //Futuro indice con el jugador con menor numero de bueyes en la partida
        //BUSCA EL INDICE DEL GANADOR CON EL MENOR NUMERO DE BUEYES
        for (int i = 1; i < jugadores.length; i++) {
            if (jugadores[min].getnBueyes() > jugadores[i].getnBueyes()) {
                min = i;
            }
        }
        //AÑADE LOS JUGADORES QUE COINCIDAN EN MENOR NUMERO DE BUEYES
        for (int i = min; i < jugadores.length; i++) {
            if (jugadores[i].getnBueyes() == jugadores[min].getnBueyes()) {
                ganadores.add(jugadores[i]);
            }
        }
        iu.FinDelJuego(ganadores);
    }

    //Crea el array primitivo con los jugadores pidiendo una collection de nombres mediante la iu
    private void crearJugadores() {
        Object[] nombres = iu.pedirNombresJugadores().toArray();
        this.jugadores = new Jugador[nombres.length];
        for (int i = 0; i < nombres.length; i++) {
            this.jugadores[i] = new Jugador(nombres[i].toString());
        }
    }

    //Le da las 10 cartas a cada jugador, dandole 1 a cada jugador hasta que todos tengan 10
    public void repartir(Baraja baraja) {
        for (int i = 0; i < CartasPJ; i++) {
            for (Jugador jugador : jugadores) {
                jugador.recogerCarta(baraja.cogerCarta());
            }
        }
    }

    public void jugarCartas() {
        ArrayList<Carta> seleccion = iu.seleccionarCartas(Arrays.asList(jugadores));
        int min, fila;
        Carta cmin;
        for (int i = 0; i < jugadores.length; i++) {
            min = 0;
            //Seleccion del indice de la carta minima de la selección de cartas
            while (seleccion.get(min) == null) {
                min++;
            }
            for (int j = min; j < seleccion.size(); j++) {
                if (seleccion.get(j) != null && seleccion.get(j).getNcarta() < seleccion.get(min).getNcarta()) {
                    min = j;
                }
            }
            /*Se coge de la seleccion la carta más pequeña despues de descubrir su indice,
              se evalua en que fila se debe insertar esa carta segun el estado actual de la mesa dependiendo
              del caso o de si la fila ya esta llena se hacen operaciones diferentes*/
            cmin = seleccion.get(min);
            fila = mesa.filaAinsertar(cmin, jugadores[min]);
            switch (fila) {
                case (-1) -> {
                    //No se puede insertar la carta en ninguna fila, el jugador tiene que elegir fila
                    int selecfila = iu.seleccionarFila(mesa, jugadores[min]); 
                    mesa.limpiarFila(seleccion.get(min), selecfila, jugadores[min]);
                }
                default -> {
                    if (mesa.isFilaLlena(fila)){
                        mesa.limpiarFila(cmin, fila, jugadores[min]);
                        iu.SeComio5(jugadores[min]); //Mensaje para avisar que el jugador se ha comido una fila
                    }else{    
                        mesa.insertarEnFila(cmin, fila);
                    }
                }
            }   
            seleccion.set(min, null);   //Se pone la carta correspondiente a null
        }
    }
}
