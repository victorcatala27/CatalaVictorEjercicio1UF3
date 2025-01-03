import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        String filePath="movies.txt";
        ArrayList<String> movies=new ArrayList<>();

        File file =new File ("movies.txt"); // definimos el tipo file y creamos un objeto File
        //ruta relativa hacia el fichero
        try (Scanner scanner= new Scanner(new File (filePath))) {
            while (scanner.hasNextLine()) {

                movies.add(scanner.nextLine());
                String titulo= scanner.nextLine();
                System.out.println("Valor:" + movies.getLast());
            }
            System.out.println("Fin lectura de datos");



        } catch (Exception e) {
            System.out.println("El fichero no existe");
        }
    }
}