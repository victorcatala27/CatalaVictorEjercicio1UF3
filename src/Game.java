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
    private HashSet<Character> letrasCorrectas; // Letras acertadas
    private HashSet<Character> letrasIncorrectas; // Letras incorrectas
    private HashSet<String> titulosUsados; // Títulos ya adivinados
    private ArrayList<String> peliculasDisponibles; // Lista de títulos disponibles

    // Constructor
    public Game() {
        this.letrasCorrectas = new HashSet<>();
        this.letrasIncorrectas = new HashSet<>();
        this.titulosUsados = new HashSet<>();
        String moviesFile = "movies.txt";
        this.peliculasDisponibles = cargarPeliculas(moviesFile);

        if (peliculasDisponibles.isEmpty()) {
            System.out.println("No se encontraron películas en el archivo.");
            return;
        }

        // Seleccionar el primer título
        seleccionarNuevoTitulo();
        this.intentos = 10; // Número máximo de intentos
        this.puntuacion = 0; // Puntuación inicial
    }

    // Método para cargar las películas desde el archivo
    private ArrayList<String> cargarPeliculas(String rutaArchivo) {
        ArrayList<String> peliculas = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(rutaArchivo))) {
            while (scanner.hasNextLine()) {
                peliculas.add(scanner.nextLine());
            }
        } catch (Exception e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
        return peliculas;
    }

    // Método para seleccionar un nuevo título aleatorio
    public void seleccionarNuevoTitulo() {
        if (peliculasDisponibles.isEmpty()) {
            System.out.println("¡No quedan más títulos por adivinar!");
            this.tituloPelicula = null;
            return;
        }

        Random random = new Random();
        String nuevoTitulo;

        do {
            nuevoTitulo = peliculasDisponibles.get(random.nextInt(peliculasDisponibles.size()));
        } while (titulosUsados.contains(nuevoTitulo));

        this.titulosUsados.add(nuevoTitulo); // Marcar como usado
        this.peliculasDisponibles.remove(nuevoTitulo); // Eliminar de las disponibles
        this.tituloPelicula = nuevoTitulo;
        this.tituloOculto = ocultarTitulo(nuevoTitulo);
        this.letrasCorrectas.clear();
        this.letrasIncorrectas.clear();
        this.intentos = 10; // Reiniciar intentos para el nuevo título
    }

    // Método para ocultar el título con asteriscos
    private String ocultarTitulo(String titulo) {
        return titulo.replaceAll("[a-zA-Z]", "*"); // Reemplaza letras con '*'
    }

    // Método para adivinar una letra
    public boolean adivinarLetra(char letra) {
        letra = Character.toLowerCase(letra);

        if (letrasCorrectas.contains(letra)) {
            System.out.println("Ya has acertado la letra '" + letra + "'. No se sumarán puntos.");
            return true;
        }
        if (letrasIncorrectas.contains(letra)) {
            System.out.println("Ya has fallado la letra '" + letra + "'. No se restarán puntos.");
            return false;
        }

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

    // Método para adivinar el título completo
    public boolean adivinarTituloCompleto(String titulo) {
        if (titulo.equalsIgnoreCase(this.tituloPelicula)) {
            System.out.println("¡Felicidades! Adivinaste el título correctamente: " + tituloPelicula);
            puntuacion += 20;
            seleccionarNuevoTitulo(); // Seleccionar un nuevo título
            return true;
        } else {
            System.out.println("Lo siento, ese no es el título correcto.");
            intentos--;
            puntuacion -= 20;
            return false;
        }
    }

    // Métodos getter
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
