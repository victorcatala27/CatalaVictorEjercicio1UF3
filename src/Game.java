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
    public Game(String rutaArchivo) {
        ArrayList<String> peliculas = cargarPeliculas(rutaArchivo);
        if (peliculas.isEmpty()) {
            System.out.println("No se encontraron películas en el archivo.");
            return;
        }

        this.tituloPelicula = seleccionarPeliculaAleatoria(peliculas);
        this.tituloOculto = ocultarTitulo(this.tituloPelicula);
        this.intentos = 10; // Número máximo de intentos
        this.puntuacion = 0; // Puntuación inicial
    }

