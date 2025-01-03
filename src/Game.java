import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {
    private String tituloPelicula;        // Título original de la película
    private String tituloOculto;         // Título oculto con asteriscos
    private int intentos;                // Intentos restantes
    private int puntuacion;              // Puntuación acumulada

    // Constructor
    public Game() {
        String moviesfile="movies.txt";
        ArrayList<String> peliculas=cargarPeliculas(moviesfile);

        if (peliculas.isEmpty()){
            System.out.println("No se encontraron películas en el archivo");
            return;
        }

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

    // Metodo para adivinar letras
    public boolean adivinarLetra(char letra) {
        letra = Character.toLowerCase(letra); // Convertir a minúscula
        boolean encontrada = false; // Indica si la letra está en el título

        if (tituloPelicula.toLowerCase().contains(String.valueOf(letra))) {
            // Revelar la letra correcta en el título oculto
            StringBuilder tituloActualizado = new StringBuilder(tituloOculto);
            for (int i = 0; i < tituloPelicula.length(); i++) {
                if (Character.toLowerCase(tituloPelicula.charAt(i)) == letra) {
                    tituloActualizado.setCharAt(i, tituloPelicula.charAt(i));
                    encontrada = true;
                }
            }
            tituloOculto = tituloActualizado.toString(); // Actualizamos el título oculto
            puntuacion += 10; // Sumar puntos por adivinar la letra
        } else {
            intentos--; // Reducir intentos si la letra no está
            puntuacion -= 10; // Restar puntos por una letra fallada
        }

        return encontrada;
    }

    // Metodo para obtener el título oculto
    public String getTituloOculto() {
        return tituloOculto;
    }

    // Metodo para obtener el número de intentos restantes
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

