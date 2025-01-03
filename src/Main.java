import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Game juego = new Game(); // Crear una instancia del juego
        Scanner scanner = new Scanner(System.in);

        // Verificar que el juego se haya inicializado correctamente
        if (juego.getTituloPelicula() == null) {
            System.out.println("El juego no pudo iniciarse correctamente. Verifica el archivo movies.txt.");
            return;
        }

        System.out.println("¡Bienvenido al juego de adivinar películas!");

        while (true) { // Bucle principal del juego
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
                        juego.adivinarLetra(letra); // Llamar al metodo

                        // Verificar si se ha adivinado completamente el título
                        if (juego.getTituloOculto().equalsIgnoreCase(juego.getTituloPelicula())) {
                            System.out.println("¡Felicidades! Adivinaste el título completo: " + juego.getTituloPelicula());
                            System.out.println("Tu puntuación actual es: " + juego.getPuntuacion());
                            break;
                        }
                        break;

                    case 2: // Adivinar el título completo
                        System.out.print("Introduce el título completo: ");
                        String titulo = scanner.nextLine(); // Leer el título completo
                        boolean resultado = juego.adivinarTituloCompleto(titulo); // Llamada al metodo
                        if (resultado) {
                            System.out.println("¡El título se ha adivinado correctamente! Ahora se seleccionará un nuevo título.");
                        }
                        break;


                    case 3: // Salir del juego
                        System.out.println("¡Gracias por jugar! Hasta la próxima.");
                        return; // Salir del programa

                    default:
                        System.out.println("Opción no válida. Introduce un número entre 1 y 3.");
                        break;
                }

                // Verificar si el título ha sido adivinado por completo
                if (juego.getTituloOculto().equalsIgnoreCase(juego.getTituloPelicula())) {
                    System.out.println("\n¡Título completado! Se seleccionará un nuevo título...");
                    juego.reiniciarJuego(); // Reiniciar el juego con un nuevo título
                    break; // Salir del bucle interno para iniciar un nuevo título
                }
            }

            // Fin del juego por intentos agotados
            if (juego.getIntentos() == 0) {
                System.out.println("\n¡Se acabaron los intentos! El título era: " + juego.getTituloPelicula());
                System.out.println("Tu puntuación final es: " + juego.getPuntuacion());
            }

            // Preguntar si el jugador quiere seguir jugando
            System.out.print("¿Quieres jugar con un nuevo título? (s/n): ");
            String respuesta = scanner.nextLine().toLowerCase();
            if (!respuesta.equals("s")) {
                System.out.println("¡Gracias por jugar! Tu puntuación final fue: " + juego.getPuntuacion());
                break; // Salir del juego por completo
            } else {
                juego.reiniciarJuego(); // Reiniciar el juego con un nuevo título
            }
        }
    }


}
