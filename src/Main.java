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
                    break;

                case 2: // Adivinar el título completo
                    System.out.print("Introduce el título completo: ");
                    String titulo = scanner.nextLine(); // Leer el título completo
                    juego.adivinarTituloCompleto(titulo); // Llamar al metodo
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
            System.out.println("\n¡Game over. Te has quedado sin intentos! El título es: " + juego.getTituloPelicula());
        }

        System.out.println("Tu puntuación final es: " + juego.getPuntuacion());
    }





}

