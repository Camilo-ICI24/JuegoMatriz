import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class JuegoMatrizTest {

    @Test
    public void testCrearMapa() {
        String[][] mapa = JuegoMatriz.crearMapa();
        assertNotNull(mapa);
        assertEquals(10, mapa.length);
        assertEquals(10, mapa[0].length);
    }

    @Test
    public void testDelimitarMapa() {
        String[][] mapa = JuegoMatriz.crearMapa();
        mapa = JuegoMatriz.delimitarMapa(mapa);
        assertEquals("# ", mapa[0][0]);
        assertEquals("# ", mapa[9][9]);
        assertEquals("# ", mapa[0][5]);
        assertEquals("# ", mapa[5][0]);
    }

    @Test
    public void testColocarObstaculo() {
        String[][] mapa = JuegoMatriz.crearMapa();
        JuegoMatriz.colocarObstaculo(mapa);
        int count = 0;
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[i].length; j++) {
                if ("# ".equals(mapa[i][j])) {
                    count++;
                }
            }
        }
        assertTrue(count >= 10);
    }

    @Test
    public void testSpawnearMalo() {
        String[][] mapa = JuegoMatriz.crearMapa();
        JuegoMatriz.spawnearMalo(mapa);
        int count = 0;
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[i].length; j++) {
                if ("E ".equals(mapa[i][j])) {
                    count++;
                }
            }
        }
        assertEquals(5, count);
    }

    @Test
    public void testObtenerCoordenadasMeta() {
        String[][] mapa = JuegoMatriz.crearMapa();
        JuegoMatriz.colocarMeta(mapa);
        int[] coordenadasMeta = JuegoMatriz.obtenerCoordenadasMeta(mapa);
        assertNotNull(coordenadasMeta);
        assertEquals("X ", mapa[coordenadasMeta[0]][coordenadasMeta[1]]);
    }

    @Test
    public void testMovimientoDelPersonaje() {
        int[] coords = JuegoMatriz.movimientoDelPersonaje("w", 1, 1);
        assertEquals(0, coords[0]);
        assertEquals(1, coords[1]);
    }
}

