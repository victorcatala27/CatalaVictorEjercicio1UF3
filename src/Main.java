import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String nickname;

        // Solicitar el nickname al inicio del programa
        while (true) {
            System.out.print("Introduce tu nickname (máximo 15 caracteres): ");
            nickname = scanner.nextLine();

            if (nickname.length() > 15) {
                System.out.println("El nickname no puede tener más de 15 caracteres. Inténtalo de nuevo.");
            } else {
                break;
            }
        }

        System.out.println("¡Bienvenido/a " + nickname + " al juego de adivinar películas!");

        Game juego = new Game(); // Crear una instancia del juego

        // Verificar que el juego se haya inicializado correctamente
        if (juego.getTituloPelicula() == null) {
            System.out.println("El juego no pudo iniciarse correctamente. Verifica el archivo movies.txt.");
            return;
        }

        // Bucle principal del juego
        while (juego.getIntentos() > 0) {
            System.out.println("\nTítulo oculto: " + juego.getTituloOculto());
            System.out.println("Intentos restantes: " + juego.getIntentos());
            System.out.println("Puntuación: " + juego.getPuntuacion());
            System.out.println("\nElige una opción:");
            System.out.println("[1] Adivinar una letra");
            System.out.println("[2] Adivinar el título completo");
            System.out.println("[3] Salir");

            String opcionInput = scanner.nextLine(); // Leer la entrada como texto
            int opcion;

            try {
                opcion = Integer.parseInt(opcionInput); // Intentar convertir la entrada a un número
            } catch (Exception e) {
                System.out.println("Entrada no válida. Introduce uno de los números del menú.");
                continue; // Volver al inicio del bucle
            }

            switch (opcion) {
                case 1: // Adivinar una letra
                    System.out.print("Introduce una letra: ");
                    String letraInput = scanner.nextLine().toLowerCase(); // Leer la entrada como texto
                    if (letraInput.length() != 1) {
                        System.out.println("Por favor, introduce solo una letra.");
                        break;
                    }
                    char letra = letraInput.charAt(0);
                    juego.adivinarLetra(letra); // Llamar al método
                    break;

                case 2: // Adivinar el título completo
                    System.out.print("Introduce el título completo: ");
                    String titulo = scanner.nextLine(); // Leer el título completo
                    juego.adivinarTituloCompleto(titulo); // Llamar al método
                    break;

                case 3: // Salir del juego
                    System.out.println("¡Gracias por jugar! Hasta la próxima.");
                    return; // Salir del programa

                default:
                    System.out.println("Opción no válida. Introduce un número entre 1 y 3.");
                    break;
            }
        }

        // Fin del juego
        if (juego.getIntentos() == 0) {
            System.out.println("\n¡Se acabaron los intentos! El título era: " + juego.getTituloPelicula());
        }

        System.out.println("Tu puntuación final es: " + juego.getPuntuacion());
        guardarPuntuacion(nickname, juego.getPuntuacion()); // Guardar la puntuación asociada al nickname
    }

    private static void guardarPuntuacion(String nickname, int puntuacion) {
        // Aquí implementaremos el guardado de la puntuación en el archivo de ranking
        System.out.println("Guardando la puntuación de " + nickname + ": " + puntuacion);
        // Implementación futura...
    }

    private static ArrayList<Player> cargarRanking() {
        ArrayList<Player> ranking = new ArrayList<>(); // Inicializamos una lista vacía
        File archivo = new File("scores.data");

        // Verificar si el archivo existe antes de intentar leerlo
        if (!archivo.exists() || archivo.length() == 0) {
            System.out.println("El archivo de ranking no existe o está vacío. Se creará uno nuevo.");
            return ranking; // Devolvemos el ranking vacío
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            ranking = (ArrayList<Player>) ois.readObject(); // Intentamos cargar el ranking del archivo
        } catch (Exception e) {
            System.out.println("Error al cargar el ranking: " + e.getMessage());
        }

        return ranking; // Devolvemos el ranking cargado o vacío
    }
    private static boolean esPuntuacionAlta(int puntuacion) {
        ArrayList<Player> ranking = cargarRanking(); // Cargamos el ranking

        if (ranking.size() < 5) {
            return true;
        }

        // Revisar si la puntuación es mayor que alguna en el ranking
        for (Player jugador : ranking) {
            if (puntuacion > jugador.getPuntuacion()) {
                return true;
            }
        }

        return false; // Si no es mayor que ninguna, no puede entrar
    }


}
