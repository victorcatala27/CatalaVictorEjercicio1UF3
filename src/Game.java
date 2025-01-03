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

    // Constructor
    public Game() {
        String moviesfile = "movies.txt";
        ArrayList<String> peliculas = cargarPeliculas(moviesfile);

        if (peliculas.isEmpty()) {
            System.out.println("No se encontraron películas en el archivo");
            return;
        }

        this.letrasCorrectas = new HashSet<>();
        this.letrasIncorrectas = new HashSet<>();
        this.tituloPelicula = seleccionarPeliculaAleatoria(peliculas);
        this.tituloOculto = ocultarTitulo(this.tituloPelicula);
        this.intentos = 10; // Número máximo de intentos
        this.puntuacion = 0; // Puntuación inicial
    }

    // Metodo para cargar las películas desde el archivo
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

    // Metodo para seleccionar una película al azar
    private String seleccionarPeliculaAleatoria(ArrayList<String> peliculas) {
        Random random = new Random();
        return peliculas.get(random.nextInt(peliculas.size()));
    }

    // Metodo para ocultar el título con asteriscos
    private String ocultarTitulo(String titulo) {
        return titulo.replaceAll("[a-zA-Z]", "*"); // Reemplaza letras con '*'
    }

    // Metodo para adivinar una letra
    public boolean adivinarLetra(char letra) {
        letra = Character.toLowerCase(letra); // Convertir a minúscula para evitar problemas de mayúsculas/minúsculas

        // Verificar si la letra ya fue introducida
        if (letrasCorrectas.contains(letra)) {
            System.out.println("Ya has acertado la letra '" + letra + "'. No se sumarán puntos.");
            return true; // Se considera un acierto porque ya estaba
        }
        if (letrasIncorrectas.contains(letra)) {
            System.out.println("Ya has fallado la letra '" + letra + "'. No se restarán puntos.");
            return false; // Se considera un fallo porque ya estaba
        }

        // Comprobar si la letra está en el título
        boolean encontrada = false;
        StringBuilder tituloActualizado = new StringBuilder(tituloOculto);

        for (int i = 0; i < tituloPelicula.length(); i++) {
            if (Character.toLowerCase(tituloPelicula.charAt(i)) == letra) {
                tituloActualizado.setCharAt(i, tituloPelicula.charAt(i));
                encontrada = true; // Marcar como encontrada
            }
        }

        if (encontrada) {
            // Actualizar el título oculto, añadir la letra a las correctas y sumar puntos
            tituloOculto = tituloActualizado.toString();
            letrasCorrectas.add(letra);
            puntuacion += 10;
            System.out.println("¡Correcto! La letra '" + letra + "' está en el título.");
            return true;
        } else {
            // Añadir la letra a las incorrectas y restar puntos
            letrasIncorrectas.add(letra);
            intentos--;
            puntuacion -= 10;
            System.out.println("Lo siento, la letra '" + letra + "' no está en el título.");
            return false;
        }
    }

    // Metodo para adivinar el título completo
    public boolean adivinarTituloCompleto(String titulo) {
        if (titulo.equalsIgnoreCase(this.tituloPelicula)) { // Comparar ignorando mayúsculas/minúsculas
            System.out.println("¡Felicidades! Adivinaste el título correctamente: " + tituloPelicula);
            puntuacion += 20; // Bonus de puntos por adivinar el título completo
            reiniciarJuego(); // Reinicia el juego con un nuevo título
            return true; // El jugador gana
        } else {
            System.out.println("Lo siento, ese no es el título correcto.");
            intentos--; // Penalización
            puntuacion -= 20; // Pérdida de puntos
            return false; // El juego continúa
        }
    }


    // Metodo para reiniciar el juego con un nuevo título
    public void reiniciarJuego() {
        String moviesfile = "movies.txt";
        ArrayList<String> peliculas = cargarPeliculas(moviesfile);

        if (peliculas.isEmpty()) {
            System.out.println("No se encontraron más películas en el archivo.");
            return;
        }

        this.tituloPelicula = seleccionarPeliculaAleatoria(peliculas); // Nuevo título
        this.tituloOculto = ocultarTitulo(this.tituloPelicula); // Ocultar el nuevo título
        this.letrasCorrectas.clear(); // Reiniciar letras correctas
        this.letrasIncorrectas.clear(); // Reiniciar letras incorrectas
        System.out.println("¡Nuevo título preparado! Sigue jugando.");
    }

    // Metodo para obtener el título oculto
    public String getTituloOculto() {
        return tituloOculto;
    }

    //Metodo para obtener los intentos restantes
    public int getIntentos() {
        return intentos;
    }

    public String getTituloPelicula() {
        return tituloPelicula;
    }

    //Metodos para obtener la puntuación
    public int getPuntuacion() {
        return puntuacion;
    }
    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public void setIntentos(int intentos) {
        this.intentos = intentos;
    }
}
