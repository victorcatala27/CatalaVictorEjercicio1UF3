import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

public class Game {
    private String tituloPelicula;        // Título original de la película
    private String tituloOculto;         // Título oculto con asteriscos
    private int intentos;                // Intentos restantes
    private int puntuacion;              // Puntuación acumulada
    private HashSet<Character> letrasCorrectas;
    private HashSet<Character> letrasIncorrectas;
    private ArrayList<String> peliculas; // Lista de películas disponibles

    // Constructor
    public Game() {
        String moviesfile = "movies.txt";
        peliculas = cargarPeliculas(moviesfile);

        if (peliculas.isEmpty()) {
            System.out.println("No se encontraron películas en el archivo.");
            return;
        }

        this.letrasCorrectas = new HashSet<>();
        this.letrasIncorrectas = new HashSet<>();
        this.tituloPelicula = seleccionarPeliculaAleatoria();

        if (this.tituloPelicula == null) {
            System.out.println("No hay más películas disponibles. El juego terminará.");
            return;
        }

        this.tituloOculto = ocultarTitulo(this.tituloPelicula);
        this.intentos = 10; // Número máximo de intentos
        this.puntuacion = 0; // Puntuación inicial
    }

    // Metodo para cargar las películas desde el archivo
    private ArrayList<String> cargarPeliculas(String rutaArchivo) {
        ArrayList<String> peliculas = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(rutaArchivo))) {
            while (scanner.hasNextLine()) {
                peliculas.add(scanner.nextLine().trim());
            }
        } catch (Exception e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
        return peliculas;
    }

    // Metodo para seleccionar una película al azar
    private String seleccionarPeliculaAleatoria() {
        if (peliculas.isEmpty()) {
            return null;
        }
        Random random = new Random();
        int index = random.nextInt(peliculas.size());
        return peliculas.remove(index); // Elimina la película seleccionada para evitar que se repita
    }

    // Metodo para ocultar el título con asteriscos
    private String ocultarTitulo(String titulo) {
        return titulo.replaceAll("[a-zA-Z]", "*"); // Reemplaza letras con '*'
    }

    public boolean adivinarLetra(char letra) {
        if (tituloPelicula == null) {
            System.out.println("No hay más títulos disponibles. El juego ha terminado.");
            return false;
        }

        letra = Character.toLowerCase(letra);

        // Verificar si la letra ya fue introducida
        if (letrasCorrectas.contains(letra)) {
            System.out.println("Ya has acertado la letra '" + letra + "'. No se sumarán puntos.");
            return true;
        }
        if (letrasIncorrectas.contains(letra)) {
            System.out.println("Ya has fallado la letra '" + letra + "'. No se restarán puntos.");
            return false;
        }

        // Comprobar si la letra está en el título
        boolean encontrada = false;
        StringBuilder tituloActualizado = new StringBuilder(tituloOculto);

        for (int i = 0; i < tituloPelicula.length(); i++) {
            if (Character.toLowerCase(tituloPelicula.charAt(i)) == letra) {
                tituloActualizado.setCharAt(i, tituloPelicula.charAt(i));
                encontrada = true;
            }
        }

        if (encontrada) {
            tituloOculto = tituloActualizado.toString();
            letrasCorrectas.add(letra);
            puntuacion += 10;
            System.out.println("¡Correcto! La letra '" + letra + "' está en el título.");
            return true;
        } else {
            letrasIncorrectas.add(letra);
            intentos--;
            puntuacion -= 10;
            System.out.println("Lo siento, la letra '" + letra + "' no está en el título.");
            return false;
        }
    }

    public boolean adivinarTituloCompleto(String titulo) {
        if (tituloPelicula == null) {
            System.out.println("No hay más títulos disponibles. El juego ha terminado.");
            return false;
        }

        if (titulo.equalsIgnoreCase(this.tituloPelicula)) { // Comparar ignorando mayúsculas/minúsculas
            System.out.println("¡Felicidades! Adivinaste el título correctamente: " + tituloPelicula);
            puntuacion += 20; // Bonus de puntos por adivinar el título completo
            seleccionarNuevoTitulo();
            return true; // El jugador gana
        } else {
            System.out.println("Vaya, el título que has introducido no es correcto. Prueba otra vez");
            intentos--; // Penalización
            puntuacion -= 20; // Pérdida de puntos
            return false; // El juego continúa
        }
    }

    private void seleccionarNuevoTitulo() {
        this.letrasCorrectas.clear();
        this.letrasIncorrectas.clear();
        this.tituloPelicula = seleccionarPeliculaAleatoria();

        if (this.tituloPelicula != null) {
            this.tituloOculto = ocultarTitulo(this.tituloPelicula);
        }
    }

    // Métodos para obtener datos
    public String getTituloOculto() {
        return tituloOculto;
    }

    public int getIntentos() {
        return intentos;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public String getTituloPelicula() {
        return tituloPelicula;
    }
}
