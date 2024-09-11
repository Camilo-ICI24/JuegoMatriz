import java.util.Random;

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
    }

    public static void spawnearPersonaje(String[][] territorio) {
        territorio[1][1] = "P ";
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