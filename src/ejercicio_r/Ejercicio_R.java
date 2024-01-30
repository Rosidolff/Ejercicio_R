/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ejercicio_r;

import java.util.Scanner;

/**
 *
 * @author Rosidolff
 */
public class Ejercicio_R {

    public static void mostrarReversi(char[][] tablero) {
//muestra por consola el tablero con las fichas en el estado actual, tb muestra el numero de fichas rojas y azules
        System.out.print("Tablero Reversi\t");
        for (int i = 0; i < tablero[0].length; i++) {
            System.out.print("(" + (i + 1) + ")\t");
        }
        System.out.println();
        for (int i = 0; i < tablero.length; i++) {
            System.out.print("(" + (i + 1) + ")\t\t");

            for (int j = 0; j < tablero[i].length; j++) {
                System.out.print(tablero[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println("FICHAS ROJAS:" + contador(tablero, 'R') + "\t\tFICHAS AZULES:" + contador(tablero, 'A'));
    }

    public static int contador(char[][] tablero, char jugador) {
        int contador = 0;
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                if (tablero[i][j] == jugador) {
                    contador++;
                }
            }
        }
        return contador;
    }

    public static char[][] introducirTablero() {
        Scanner sc = new Scanner(System.in);
        char tablero[][];
        int filas, columnas;
        System.out.println("Introduzca las dimensiones del tablero min(4) y max(8): ");
        do {
            System.out.print("Filas: ");
            filas = sc.nextInt();
        } while (filas < 4 || filas > 8);
        do {
            System.out.print("Columnas: ");
            columnas = sc.nextInt();
        } while (columnas < 4 || columnas > 8);
        tablero = new char[filas][columnas];
        return tablero;
    }

    public static void inicializarTablero(char[][] tablero) {
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                tablero[i][j] = '▢';
            }
        }
    }

    public static int pedirJugada(char[][] tablero, String jugador) {
        Scanner sc = new Scanner(System.in);
        int jugada;
        do {
            System.out.println("Jugador " + jugador + " seleccione una columna:");

            jugada = sc.nextInt() - 1;
        } while (jugada > (tablero.length - 1) && jugada < 0);
        return jugada;
    }

    public static boolean insertarFichaEnColumna(char[][] tablero, String jugador, int columna) {
        //inserta ficha (color) a partir de una columna devuelve si se puede o no introducir.
        boolean puede = false;
        switch (jugador) {
            case "Rojo" -> {
                for (int i = tablero.length-1; i >= 0 && !puede; i--) {
                    if (tablero[i][columna] == '▢') {
                        puede = true;
                        tablero[i][columna] = 'R';
                        efectoReversi(tablero, i, columna, 'R', 'A');
                    }
                }

            }
            case "Azul" -> {
                for (int i = tablero[0].length - 1; i >= 0 && !puede; i--) {
                    if (tablero[i][columna] == '▢') {
                        puede = true;
                        tablero[i][columna] = 'A';
                        efectoReversi(tablero, i, columna, 'A', 'R');
                    }
                }
            }
        }

        return puede;
    }

    public static void efectoReversi(char[][] tablero, int f, int c, char jugador, char contrincante) {

        if (f - 1 >= 0 && c - 1 >= 0 && tablero[f - 1][c - 1] == contrincante) {
            tablero[f - 1][c - 1] = jugador;
        }
        if (f - 1 >= 0 && tablero[f - 1][c] == contrincante) {
            tablero[f - 1][c] = jugador;
        }
        if (f - 1 >= 0 && c + 1 < tablero[f].length && tablero[f - 1][c + 1] == contrincante) {
            tablero[f - 1][c + 1] = jugador;
        }
        if (c - 1 >= 0 && c - 1 >= 0 && tablero[f][c - 1] == contrincante) {
            tablero[f][c - 1] = jugador;
        }
        if (c + 1 < tablero[f].length && tablero[f][c + 1] == contrincante) {
            tablero[f][c + 1] = jugador;
        }
        if (f + 1 < tablero.length && c - 1 >= 0 && tablero[f + 1][c - 1] == contrincante) {
            tablero[f + 1][c - 1] = jugador;
        }
        if (f + 1 < tablero.length && tablero[f + 1][c] == contrincante) {
            tablero[f + 1][c] = jugador;
        }
        if (f + 1 < tablero.length && c + 1 < tablero[f].length && tablero[f + 1][c + 1] == contrincante) {
            tablero[f + 1][c + 1] = jugador;
        }
    }

    public static String cambioJugador(String jugador) {
        if ("Rojo".equals(jugador)) {
            jugador = "Azul";
        } else {
            jugador = "Rojo";
        }
        return jugador;
    }

    public static boolean finJuego(char[][] tablero) {
        boolean fin = true;
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                if (tablero[i][j] == '▢') {
                    fin = false;
                }
            }
        }
        return fin;
    }

    public static void main(String[] args) {

        boolean fin = false, puede = true;
        char tablero[][];
        int columna, contadorRojo, contadorAzul;
        String jugador = "Rojo";

        //introducir tablero (min 4 max 8)
        tablero = introducirTablero();
        //crear tablero
        inicializarTablero(tablero);

        do {
            //monstrar tablero, (los indices empiezan en 1)
            mostrarReversi(tablero);
            //pedir jugada
            do {
                columna = pedirJugada(tablero, jugador);
                //insercion de ficha (solo columna y al fondo)            
                puede = insertarFichaEnColumna(tablero, jugador, columna);
                if (!puede && !finJuego(tablero)) {
                    System.out.println("\nIntentalo con otra columna, aún hay columnas disponibles para jugar\n");
                }
            } while (!puede && !finJuego(tablero));
            //cambio de jugador
            jugador = cambioJugador(jugador);
            //condicion fin de juego (no se pueden colocar mas fichas)
        } while (!finJuego(tablero));
        contadorRojo = contador(tablero, 'R');
        contadorAzul = contador(tablero, 'A');
        if (contadorRojo > contadorAzul) {
            System.out.println("Enhorabuena Jugador Rojo, has ganado!");
        } else if (contadorRojo < contadorAzul) {
            System.out.println("Enhorabuena Jugador Azul, has ganado!");
        } else {
            System.out.println("Enhorabuena, habeis logrado lo más dificil, empatar!");
        }

        // mensaje fin de juego
    }

}
