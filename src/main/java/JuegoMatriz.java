import java.util.Random;
import java.util.Scanner;

class JuegoMatriz {

    public static void main(String[] args) {
        String[][] mapa = crearMapa();

        obtenerCoordenadasMeta(mapa);
        obtenerCoordenadasMalos(mapa);

        inicializarMapa(mapa);
        mostrarTerritorio(mapa);
        jugarJuego(mapa);
    }

    public static String[][] crearMapa() {
        return new String[10][10];
    }

    public static String[][] delimitarMapa(String[][] territorio) {
        for (int fila = 0; fila < territorio.length; fila++) {
            for (int columna = 0; columna < territorio.length; columna++) {
                territorio[0][columna] = "# ";
                territorio[fila][0] = "# ";
                territorio[9][columna] = "# ";
                territorio[fila][9] = "# ";
            }
        }
        return territorio;
    }

    public static void colocarObstaculo(String[][] territorio) {
        Random azar = new Random();
        int cantidadObstaculos = 10;
        for (int obstaculo = 0; obstaculo < cantidadObstaculos; obstaculo++) {
            int fila = azar.nextInt(8) + 1;
            int columna = azar.nextInt(8) + 1;
            if (territorio[fila][columna] == null) {
                territorio[fila][columna] = "# ";
            }
        }
    }

    public static void spawnearMalo(String[][] territorio) {
        Random azar = new Random();
        int enemigos = 5;
        for (int malo = 0; malo < enemigos; malo++) {
            int fila = azar.nextInt(8) + 1;
            int columna = azar.nextInt(8) + 1;
            if (territorio[fila][columna] == null) {
                territorio[fila][columna] = "E ";
            }
        }
    }

    public static void ponerCofre(String[][] territorio) {
        Random azar = new Random();
        int cofres = 3;
        for (int cofre = 0; cofre < cofres; cofre++) {
            int fila = azar.nextInt(8) + 1;
            int columna = azar.nextInt(8) + 1;
            if (territorio[fila][columna] == null) {
                territorio[fila][columna] = "C ";
            }
        }
    }

    public static void spawnearPersonaje(String[][] territorio) {
        territorio[1][1] = "P ";
    }

    public static int[] informacionPersonaje() {
        return new int[]{1, 1, 100, 15}; // La información necesaria del personaje: coordenadas, vida y ataque
    }

    public static void moverPersonaje(String[][] territorio, int[] personaje) {
        Scanner mover = new Scanner(System.in);
        System.out.println("Ingrese su movimiento (w/a/s/d): ");
        String movimiento = mover.nextLine().toLowerCase();

        int originalX = personaje[0];
        int originalY = personaje[1];

        int[] nuevasCoordenadas = movimientoDelPersonaje(movimiento, originalX, originalY);
        int nuevaX = nuevasCoordenadas[0];
        int nuevaY = nuevasCoordenadas[1];

        System.out.println("Moviendo a: (" + nuevaX + ", " + nuevaY + ")");

        if (!validarDecision(nuevaX, nuevaY)) {
            System.out.println("Movimiento inválido. Estás chocando con el borde del mapa.");
            return; // Se choca con los bordes
        }

        if (!comprobarMovimiento(territorio, nuevaX, nuevaY)) {
            System.out.println("Movimiento inválido. Hay un obstáculo en la nueva posición.");
            return; // Movimiento bloqueado por un obstáculo
        }

        if ("E ".equals(territorio[nuevaX][nuevaY])) {
            System.out.println("Enemigo detectado en la posición: (" + nuevaX + ", " + nuevaY + ")");
            menuDeBatalla(territorio, personaje);
            return;
        }

        if ("C ".equals(territorio[nuevaX][nuevaY])) {
            recogerCofre(territorio, personaje);
        }

        cambiarDePosicionAlPersonaje(territorio, personaje, nuevaX, nuevaY);

        int[][] coordenadasMalos = obtenerCoordenadasMalos(territorio);
        for (int[] coordenadasMalo : coordenadasMalos) {
            if (verificarMalo(new int[]{nuevaX, nuevaY}, coordenadasMalo)) {
                System.out.println("Enemigo detectado en la posición: (" + nuevaX + ", " + nuevaY + ")");
                menuDeBatalla(territorio, personaje);
                return;
            }
        }
    }

    public static int[] movimientoDelPersonaje(String movimiento, int originalX, int originalY) {
        int nuevaX = originalX;
        int nuevaY = originalY;
        switch (movimiento) {
            case "a":
                nuevaY--;
                break;
            case "d":
                nuevaY++;
                break;
            case "w":
                nuevaX--;
                break;
            case "s":
                nuevaX++;
                break;
        }
        return new int[]{nuevaX, nuevaY};
    }

    public static boolean validarDecision(int nuevaX, int nuevaY) {
        if (nuevaX < 1 || nuevaX > 8 || nuevaY < 1 || nuevaY > 8) {
            System.out.println("Movimiento inválido. Estás chocando con el borde del mapa.");
            return false;
        }
        return true;
    }

    public static boolean comprobarMovimiento(String[][] territorio, int nuevaX, int nuevaY) {
        if (territorio[nuevaX][nuevaY] != null && territorio[nuevaX][nuevaY].equals("# ")) {
            System.out.println("Movimiento inválido. Hay un obstáculo en la nueva posición.");
            return false;
        }
        return true;
    }

    public static void cambiarDePosicionAlPersonaje(String[][] territorio, int[] personaje, int nuevaX, int nuevaY) {
        territorio[personaje[0]][personaje[1]] = null;
        personaje[0] = nuevaX;
        personaje[1] = nuevaY;
        territorio[personaje[0]][personaje[1]] = "P ";
    }

    public static void colocarMeta(String[][] territorio) {
        Random azar = new Random();
        int fila = azar.nextInt(8) + 1;
        int columna = azar.nextInt(8) + 1;
        if (territorio[fila][columna] == null) {
            territorio[fila][columna] = "X ";
        }
    }

    public static int[] obtenerCoordenadasMeta(String[][] territorio) {
        for (int fila = 0; fila < territorio.length; fila++) {
            for (int columna = 0; columna < territorio[fila].length; columna++) {
                if ("X ".equals(territorio[fila][columna])) {
                    return new int[]{fila, columna};
                }
            }
        }
        return null;
    }

    public static void mostrarTerritorio(String[][] territorio) {
        for (int fila = 0; fila < territorio.length; fila++) {
            for (int columna = 0; columna < territorio.length; columna++) {
                if (territorio[fila][columna] == null) {
                    System.out.print(" . ");
                } else {
                    System.out.print(territorio[fila][columna] + " ");
                }
            }
            System.out.println();
        }
    }

    public static int contarEnemigos(String[][] territorio) {
        int contador = 0;
        for (int fila = 0; fila < territorio.length; fila++) {
            for (int columna = 0; columna < territorio[fila].length; columna++) {
                if ("E ".equals(territorio[fila][columna])) {
                    contador++;
                }
            }
        }
        return contador;
    }

    public static void llenarCoordenadasMalos(String[][] territorio, int[][] coordenadasMalos) {
        int line = 0;
        for (int fila = 0; fila < territorio.length; fila++) {
            for (int columna = 0; columna < territorio[fila].length; columna++) {
                if ("E ".equals(territorio[fila][columna])) {
                    coordenadasMalos[line][0] = fila;
                    coordenadasMalos[line][1] = columna;
                    line++;
                }
            }
        }
    }

    public static int[][] obtenerCoordenadasMalos(String[][] territorio) {
        int cantidadDeMalos = contarEnemigos(territorio);
        int[][] coordenadasMalos = new int[cantidadDeMalos][2];

        llenarCoordenadasMalos(territorio, coordenadasMalos);

        return coordenadasMalos;
    }

    public static void menuDeBatalla(String[][] territorio, int[] personaje) {
        System.out.println("¡¡ADVERTENCIA!! Enemigo en la posición: (" + personaje[0] + ", " + personaje[1] + ")");
        int eleccion = decisionBatalla();
        if (eleccion == 1) {
            System.out.println("¡Has decidido pelear!");
            batallaEpicca(personaje, territorio);
        } else if (eleccion == 2) {
            System.out.println("¡Has decidido huir!");
        } else {
            System.out.println("Opción no válida.");
        }
    }

    public static int decisionBatalla() {
        Scanner decision = new Scanner(System.in);
        System.out.println("¿Qué deseas hacer?");
        System.out.println("1. Pelear y seguir avanzando");
        System.out.println("2. Huir por tu vida");
        System.out.println("Ahhhh.... elijo ");
        int eleccion = decision.nextInt();
        return eleccion;
    }

    public static void batallaEpicca(int[] personaje, String[][] territorio) {
        Random azar = new Random();
        int vidaEnemigo = 50; // Vida del enemigo
        int ataqueEnemigoMaximo = 45; // Para que no esté tan roto

        System.out.println("¡La batalla ha comenzado!");

        while (personaje[2] > 0 && vidaEnemigo > 0) {
            int ataquePersonaje = personaje[3];
            vidaEnemigo -= ataquePersonaje;
            System.out.println("Has atacado al enemigo.");
            System.out.println("LP enemigo: " + vidaEnemigo);

            if (vidaEnemigo <= 0) {
                System.out.println("¡Has derrotado al enemigo!");
                territorio[personaje[0]][personaje[1]] = null;
                return;
            }

            int ataqueEnemigo = azar.nextInt(ataqueEnemigoMaximo) + 1;
            personaje[2] -= ataqueEnemigo;
            System.out.println("El enemigo te ha atacado. Tu vida restante: " + personaje[2]);

            if (personaje[2] <= 0) {
                System.out.println("¡Has sido derrotado por el enemigo!");
                System.exit(0);
            }

            try {
                Thread.sleep(1000); // 1 segundo de pausa
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static String[][] inicializarMapa(String[][] mapa) {
        delimitarMapa(mapa);
        colocarObstaculo(mapa);
        spawnearMalo(mapa);
        ponerCofre(mapa);
        spawnearPersonaje(mapa);
        colocarMeta(mapa);
        return mapa;
    }

    public static boolean verificarMeta(int[] personaje, int[] coordenadasMeta) {
        return personaje[0] == coordenadasMeta[0] && personaje[1] == coordenadasMeta[1];
    }

    public static boolean verificarMalo(int[] personaje, int[] coordenadasMalo) {
        return personaje[0] == coordenadasMalo[0] && personaje[1] == coordenadasMalo[1];
    }

    public static void recogerCofre(String[][] territorio, int[] personaje) {
        if ("C ".equals(territorio[personaje[0]][personaje[1]])) {
            System.out.println("¡Has recogido un cofre!");
            Random premio = new Random();
            territorio[personaje[0]][personaje[1]] = null;
            int fortuna = premio.nextInt(3);
            recompensasCofre(fortuna, personaje);
        }
    }

    public static void recompensasCofre(int azar, int[] personaje) {
        if (azar == 1) {
            System.out.println("Bendición Místico del Rocío Universal");
            System.out.println("Ganas 70 de vida");
            personaje[2] += 70;
        } else if (azar == 2) {
            System.out.println("Zarpazo de las Grietas del Abismo");
            System.out.println("Pierdes 45 de vida");
            personaje[2] -= 45;
        } else if (azar == 3) {
            System.out.println("Canallada del Bromista Juguetón");
            System.out.println("No pasa nada");
        }
    }

    public static void jugarJuego(String[][] mapa) {
        int[] personaje = informacionPersonaje();
        boolean juegoActivo = true;
        Scanner juego = new Scanner(System.in);

        int[] coordenadasMeta = obtenerCoordenadasMeta(mapa);
        if (coordenadasMeta != null) {
            System.out.println("La meta está en: (" + coordenadasMeta[0] + ", " + coordenadasMeta[1] + ")");
        } else {
            System.out.println("No se encontró la meta en el mapa.");
        }

        while (juegoActivo) {
            moverPersonaje(mapa, personaje);
            mostrarTerritorio(mapa);
            juegoActivo = finDelJuego(mapa, personaje, coordenadasMeta);
            if (!juegoActivo) {
                break;
            }
        }
        juego.close();
    }

    public static boolean finDelJuego(String[][] mapa, int[] personaje, int[] coordenadasMeta) {
        if (verificarMeta(personaje, coordenadasMeta)) {
            System.out.println("¡Has alcanzado la meta! ¡Juego terminado!");
            return false; // Termina el juego
        } else if (personaje[2] == 0) {
            System.out.println("Fin del juego :(");
        }
        return true;
    }
}