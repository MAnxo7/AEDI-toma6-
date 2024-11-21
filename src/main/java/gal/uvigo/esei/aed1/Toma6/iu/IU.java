/**
 * Representa la interfaz del juego Toma 6, en este proyecto va a ser una entrada/salida en modo texto 
 * Se recomienda una implementación modular.
 */

package gal.uvigo.esei.aed1.Toma6.iu;

import gal.uvigo.esei.aed1.Toma6.core.Jugador;
import gal.uvigo.esei.aed1.Toma6.core.Mesa;
import gal.uvigo.esei.aed1.Toma6.core.Carta;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class IU {

    private final Scanner teclado;

    public IU() {
        teclado = new Scanner(System.in);
    }

    /**
     * Lee un número de teclado
     *
     * @param msg El mensaje a visualizar.
     * @return El numero como entero
     */
    public int leeNum(String msg) {
        boolean repite;
        int toret = 0;

        do {
            repite = false;
            System.out.print(msg);
            try {
                toret = Integer.parseInt(teclado.nextLine());
            } catch (NumberFormatException exc) {
                repite = true;
            }
        } while (repite);

        return toret;
    }

    /**
     * Lee un texto de teclado
     *
     * @param msg El mensaje a utilizar
     * @return El texto como String
     */
    public String leeString(String msg) {
        String toret;
        System.out.print(msg);
        toret = teclado.nextLine();
        return toret;
    }

    /**
     * Muestra un mensaje por pantalla
     *
     * @param msg El mensaje a mostrar
     */
    public void mostrarMensaje(String msg) {
        System.out.println(msg);
    }

    /**
     * Solicita los nombres de los jugadores por teclado y los almacena en una
     * estructura de datos
     *
     * @return Los datos de los jugadores almacenados en la estructura de datos
     * correspondiente
     */
    public Collection<String> pedirNombresJugadores() {
        
        int njug = 0;
        do {
            njug = leeNum("Inserte el numero de jugadores a jugar (2-10): ");
            if (njug > 10 || njug < 2) {
                System.out.println("Numero de jugadores fuera del rango");
            }
        } while (njug > 10 || njug < 2);
        
        Collection<String> jugadores = new ArrayList<>();
        for (int i = 0; i < njug; i++) {
            jugadores.add(leeString("Inserte el nombre del jugador numero " + (i + 1) + ": "));
        }
        return jugadores;
    }

    /**
     * Muestra por pantalla los datos de un jugador
     *
     * @param jugador Jugador para el cual se mostrarán los datos por pantalla
     */
    private void mostrarJugador(Jugador jugador) {
        System.out.println("Nombre del jugador: " + jugador.getNombre() + "\n" + jugador);
    }

    /**
     * Muestra por pantalla los datos de una coleccion de jugadores
     *
     * @param jugadores Jugadores cuyos datos se mostrarán por pantalla
     */
    public void mostrarJugadores(Collection<Jugador> jugadores) {
        System.out.print("\n");
        for (Jugador jug:jugadores){
            mostrarJugador(jug);         
        }
    }
    
    public void mostrarMesa(Mesa mesa) {
        System.out.println("\nMesa:\n" + mesa);
    }
   
    public int seleccionarFila(Mesa mesa, Jugador jug) {
        int toRet = -1;
        System.out.println("Jugador #" + jug.getNombre() + "# inserte la fila que quieres poner en tu monton:");
        System.out.println(mesa);
        do {
                toRet = leeNum("Fila a seleccionar: ");
        } while (toRet < 1 || toRet > Mesa.NFILAS);
        return toRet-1;
    }
    
    //Le pregunta por teclado a cada jugador que carta quiere jugar, devuelve un arraylist con las cartas
    //de cada jugador respectivamente.
    public ArrayList<Carta> seleccionarCartas(Collection<Jugador> jugadores) {
        ArrayList<Carta> seleccion = new ArrayList<>(jugadores.size());
        int jugada;
        for (Jugador jug : jugadores) {
            System.out.println("Jugador #" + jug.getNombre() + "# eliga una carta de su mano:");
            System.out.println(jug);
            for (int i = 0; i < jug.tamañoMano(); i++) {
                System.out.print("  (" + (i + 1) + ")   ");
            }
            System.out.print("\nCarta a seleccionar: ");
            do {
                jugada = leeNum("");
            } while (jugada < 1 || jugada > jug.tamañoMano());
            System.out.println("");
            seleccion.add(jug.cogerCarta(jugada - 1));
        }
        System.out.println("Cartas seleccionadas:");
        for(Carta c : seleccion){ 
            System.out.print(c + " ");
        }
        System.out.println("\n");
        return seleccion;
        
    }
    //Crea una lista con los jugadores ordenados por bueyes para mostrarlos
    public void MostrarBueyes(Collection<Jugador> jugadores){
        ArrayList<Jugador> jugadoresord = new ArrayList<>(jugadores);
        jugadoresord.sort((Jugador jug1, Jugador jug2) -> jug1.getnBueyes() - jug2.getnBueyes() ); 
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        for (Jugador jugador : jugadoresord) {
            sb.append("Jugador #").append(jugador.getNombre()).append("# tiene: " ).append(jugador.getnBueyes()).append( " bueyes\n");
        }
        System.out.println(sb.toString());
    }
    
    public void FinDelJuego(ArrayList<Jugador> ganadores){
        System.out.println("#FIN DE LA PARTIDA#");
        System.out.println("Los ganador/es son: ");
        for(Jugador ganador: ganadores){
            System.out.print(ganador.getNombre() + " ");
        }
        System.out.println("con " + ganadores.get(0).getnBueyes() + " bueyes en su monton.");
    }
    
    public void SeComio5(Jugador jug){
        System.out.println("El jugador "+jug.getNombre()+" se ha comido una fila de la mesa.");
    }
    
    //Envia a la terminal un mensaje que pone ¡SIGUIENTE RONDA!, esto ocurre cuando se acaba una ronda y fin = false
    public void SigRonda(){
        System.out.println("¡SIGUIENTE RONDA! ");
    }
}