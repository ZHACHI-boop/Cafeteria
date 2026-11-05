package Model;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ArchivoGenerico<T> {
    private String rutaArchivo;

    public ArchivoGenerico(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    public boolean guardarDatos(List<T> lista) {
        try (PrintWriter escritor = new PrintWriter(new FileWriter(rutaArchivo))) {
            for (T elemento : lista) {
                escritor.println(elemento.toString());
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<String> leerLineas() {
        List<String> lineas = new ArrayList<>();
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) {
            return lineas;
        }
        try (Scanner lector = new Scanner(archivo)) {
            while (lector.hasNextLine()) {
                lineas.add(lector.nextLine());
            }
        } catch (Exception e) {
            System.out.println("Error al leer archivo.");
        }
        return lineas;
    }
}