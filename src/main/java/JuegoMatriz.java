import java.util.Random;
import java.util.Scanner;

class JuegoMatriz {

    public static void main(String[] args) {
        inicializarJuego();
    }

    public static String[][] crearMapa() { // Se crea el método para crear el mapita
        String[][] mapa = new String[10][10];
        return mapa;
    }

    public static String[][] delimitarMapa(String[][] territorio) { // Método para marcar los límites del mapa
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
        int cantidadObstaculos = 10; // Una cantidad máxima de obstáculos predefinida
        for (int obstaculo = 0; obstaculo < cantidadObstaculos; obstaculo++) {
            int fila = azar.nextInt(8) + 1; // Esto evita que se escojan las filas y columnas donde se define la frontera
            int columna = azar.nextInt(8) + 1;
            if (territorio[fila][columna] == null) {
                territorio[fila][columna] = "# ";
            }
        }
    }

    public static void spawnearMalo(String[][] territorio) { // Método que coloca enemigos dentro del mapa
        Random azar = new Random();
        int enemigos = 5;
        for (int malo = 0; malo < enemigos; malo++) {
            int fila = azar.nextInt(8) + 1; // Esto evita que se escojan las filas y columnas donde se define la frontera
            int columna = azar.nextInt(8) + 1;
            if (territorio[fila][columna] == null) { // Para asegurarse de que no se escojan lugares ya ocupados
                territorio[fila][columna] = "E ";
            }
        }
    }

    public static void ponerCofre(String[][] territorio) {
        Random azar = new Random();
        int cofres = 3;
        for (int cofre = 0; cofre < cofres; cofre++) {
            int fila = azar.nextInt(8) + 1; // Esto evita que se escojan las filas y columnas donde se define la frontera
            int columna = azar.nextInt(8) + 1;
            if (territorio[fila][columna] == null) { // Para asegurarse de que no se escojan lugares ya ocupados
                territorio[fila][columna] = "C ";
            }
        }
    } // Se puede retornar la posición del cofre

    public static void despejarVia(String[][] territorio) {
        if (territorio[1][2] == "# ") {
            territorio[1][2] = ". ";
        }
        if (territorio[2][1] == "# ") {
            territorio[2][1] = ". ";
        }
    }

    public static void spawnearPersonaje(String[][] territorio) {
        territorio[1][1] = "P ";
        despejarVia(territorio);
    }

    public static int[] informacionPersonaje() {
        int[] personaje = new int[4];
        personaje[0] = 0; // Almacena la coordenada en X
        personaje[1] = 1; // ídem para la coordenada Y
        personaje[2] = 100; // La vida inicial del mono
        personaje[3] = 15; // Y el ataque con el que comienza
        return personaje;
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

        boolean esValido = validarDecision(movimiento, originalX, originalY);
        if (!esValido) {
            return; // Si el movimiento es inválido, no hace nada
        }

        boolean movimientoValido = comprobarMovimiento(territorio, nuevaX, nuevaY);
        if (!movimientoValido) {
            return; // Si hay bordes u obstáculos, no hace nada
        }
        cambiarDePosicionAlPersonaje(territorio, personaje, nuevaX, nuevaY);

        mostrarTerritorio(territorio);
    }


    public static int[] movimientoDelPersonaje(String movimiento, int originalX, int originalY) {
        int nuevaX = originalX;
        int nuevaY = originalY;
        switch (movimiento) {
            case "w":
                nuevaY--;
                break;
            case "s":
                nuevaY++;
                break;
            case "a":
                nuevaX--;
                break;
            case "d":
                nuevaX++;
                break;
        }

        return new int[]{nuevaX, nuevaY};
    }

    public static boolean validarDecision(String movimiento, int originalX, int originalY) {
        int nuevaX = originalX;
        int nuevaY = originalY;
        switch (movimiento) {
            case "w":
                if (originalY > 0) nuevaY--;
                else {
                    System.out.println("Movimiento inválido. Estás chocando con el borde del mapa.");
                    return false;
                }
                break;
            case "s":
                if (originalY < 9) nuevaY++;
                else {
                    System.out.println("Movimiento inválido. Estás chocando con el borde del mapa.");
                    return false;
                }
                break;
            case "a":
                if (originalX > 0) nuevaX--;
                else {
                    System.out.println("Movimiento inválido. Estás chocando con el borde del mapa.");
                    return false;
                }
                break;
            case "d":
                if (originalX < 9) nuevaX++;
                else {
                    System.out.println("Movimiento inválido. Estás chocando con el borde del mapa.");
                    return false;
                }
                break;
            default:
                System.out.println("Movimiento no válido. Usa w, a, s, d.");
                return false;
        }
        System.out.println("Moviendo a: (" + nuevaX + ", " + nuevaY + ")");
        return true;
    }

    public static boolean comprobarMovimiento(String[][] territorio, int nuevaX, int nuevaY) {
        if (territorio[nuevaX][nuevaY] != null && !territorio[nuevaX][nuevaY].equals("# ")) {
            System.out.println("Movimiento inválido. Hay un obstáculo en la nueva posición.");
            return false;
        }
        return true;
    }

    public static String cambiarDePosicionAlPersonaje(String[][] territorio, int[] personaje, int nuevaX, int nuevaY) {
        territorio[personaje[0]][personaje[1]] = null;

        personaje[0] = nuevaX;
        personaje[1] = nuevaY;

        territorio[personaje[0]][personaje[1]] = "P ";

        return "Moviendo al personaje a la posición: (" + personaje[0] + ", " + personaje[1] + ")";
    }



    public static void colocarMeta(String[][] territorio) {
        Random azar = new Random();
        int fila = azar.nextInt(8) + 1; // Esto evita que se escojan las filas y columnas donde se define la frontera
        int columna = azar.nextInt(8) + 1;
        if (territorio[fila][columna] == null) { // Para asegurarse de que no se escojan lugares ya ocupados
            territorio[fila][columna] = "X ";
        }
    }

    public static void mostrarTerritorio(String[][] territorio) { // Método para que el mapa se muestre
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

    public static void inicializarMapa() {
        String[][] mapa = crearMapa();
        delimitarMapa(mapa);
        colocarObstaculo(mapa);
        spawnearMalo(mapa);
        ponerCofre(mapa);
        spawnearPersonaje(mapa);
        colocarMeta(mapa);
        mostrarTerritorio(mapa);
    }

    public static void inicializarJuego() {
        inicializarMapa();
    }
}