import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Game juego = new Game(); // Crear una instancia del juego

        // Verificar que el juego se haya inicializado correctamente
        if (juego.getTituloPelicula() == null) {
            System.out.println("El juego no pudo iniciarse correctamente. Verifica el archivo movies.txt.");
            scanner.close(); // Cerramos el scanner
            return;
        }

        // Bucle principal del juego
        while (true) {
            if (juego.getTituloPelicula() == null) { // Verificar si quedan películas disponibles
                System.out.println("¡No quedan más películas en el juego! Gracias por jugar.");
                break;
            }

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
                    juego.adivinarLetra(letra);
                    break;

                case 2: // Adivinar el título completo
                    System.out.print("Introduce el título completo: ");
                    String titulo = scanner.nextLine(); // Leer el título completo
                    juego.adivinarTituloCompleto(titulo);
                    break;

                case 3: // Salir del juego
                    System.out.println("¡Gracias por jugar! Hasta la próxima.");
                    mostrarRanking();
                    scanner.close();
                    return;

                default:
                    System.out.println("Opción no válida. Introduce un número entre 1 y 3.");
                    break;
            }

            // Fin del juego si los intentos se agotan
            if (juego.getIntentos() <= 0) {
                System.out.println("\n¡Ya no te quedan intentos! El título era: " + juego.getTituloPelicula());
                break;
            }
        }

        System.out.println("Tu puntuación final es: " + juego.getPuntuacion());

        if (esPuntuacionAlta(juego.getPuntuacion())) {
            String nickname;

            while (true) {
                System.out.print("¡Tu puntuación ha entrado en el ranking! Introduce tu nickname (máximo 15 caracteres): ");
                nickname = scanner.nextLine();

                if (nickname.length() > 15) {
                    System.out.println("El nickname no puede tener más de 15 caracteres. Inténtalo de nuevo.");
                } else if (nicknameRepetido(nickname)) {
                    System.out.println("El nickname ya existe en el ranking. Por favor, elige otro.");
                } else {
                    break;
                }
            }

            guardarPuntuacion(nickname, juego.getPuntuacion());
        }

        mostrarRanking(); // Mostrar el ranking al final del juego
        scanner.close();
    }

    private static boolean esPuntuacionAlta(int puntuacion) {
        ArrayList<Player> ranking = cargarRanking(); // Cargar el ranking

        if (ranking.size() < 5) {
            return true;
        }

        for (Player jugador : ranking) {
            if (puntuacion > jugador.getPuntuacion()) {
                return true;
            }
        }

        return false;
    }
    private static boolean nicknameRepetido(String nickname) {
        ArrayList<Player> ranking = cargarRanking();
        for (Player jugador : ranking) {
            if (jugador.getNickname().equalsIgnoreCase(nickname)) {
                return true;
            }
        }
        return false;
    }

    private static void guardarPuntuacion(String nickname, int puntuacion) {
        ArrayList<Player> ranking = cargarRanking();

        ranking.add(new Player(nickname, puntuacion));
        ranking.sort((a, b) -> b.getPuntuacion() - a.getPuntuacion()); // Ordenar por puntuación descendente

        if (ranking.size() > 5) {
            ranking.remove(ranking.size() - 1); // Eliminar el último si hay más de 5
        }

        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream("scores.data"));
            oos.writeObject(ranking);
        } catch (Exception e) {
            System.out.println("Error al guardar el ranking: " + e.getMessage());
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    System.out.println("Error al cerrar el flujo de salida: " + e.getMessage());
                }
            }
        }
    }

    private static ArrayList<Player> cargarRanking() {
        ArrayList<Player> ranking = new ArrayList<>();
        File archivo = new File("scores.data");

        if (!archivo.exists() || archivo.length() == 0) {
            return ranking;
        }

        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(archivo));
            ranking = (ArrayList<Player>) ois.readObject();
        } catch (Exception e) {
            System.out.println("Error al cargar el ranking: " + e.getMessage());
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    System.out.println("Error al cerrar el flujo de entrada: " + e.getMessage());
                }
            }
        }

        return ranking;
    }

    private static void mostrarRanking() {
        ArrayList<Player> ranking = cargarRanking();

        System.out.println("\n--- Ranking ---");
        if (ranking.isEmpty()) {
            System.out.println("El ranking está vacío.");
        } else {
            for (Player jugador : ranking) {
                System.out.println(jugador.getNickname() + " - " + jugador.getPuntuacion() + " puntos");
            }
        }
    }
}
