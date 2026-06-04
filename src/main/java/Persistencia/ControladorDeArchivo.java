package persistencia;

import excepciones.PersistenciaException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class ControladorDeArchivo {

    public boolean existe(String ruta) {
        return new File(ruta).exists();
    }

    public void crearArchivo(String ruta) throws PersistenciaException {
        File archivo = new File(ruta);
        File padre = archivo.getParentFile();
        if (padre != null && !padre.exists() && !padre.mkdirs()) {
            throw new PersistenciaException("No se pudo crear la carpeta: " + padre.getPath());
        }
        try {
            if (!archivo.exists() && !archivo.createNewFile()) {
                throw new PersistenciaException("No se pudo crear el archivo: " + ruta);
            }
        } catch (IOException e) {
            throw new PersistenciaException("Error al crear archivo: " + ruta, e);
        }
    }

    public List<String> leerLineas(String ruta) throws PersistenciaException {
        File archivo = new File(ruta);
        if (!archivo.exists()) {
            return new ArrayList<>();
        }
        List<String> lineas = new ArrayList<>();
        try (BufferedReader lector = Files.newBufferedReader(archivo.toPath(), StandardCharsets.UTF_8)) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    lineas.add(linea.trim());
                }
            }
        } catch (IOException e) {
            throw new PersistenciaException("Error al leer archivo: " + ruta, e);
        }
        return lineas;
    }

    public void guardarLineas(String ruta, List<String> lineas) throws PersistenciaException {
        File archivo = new File(ruta);
        File padre = archivo.getParentFile();
        if (padre != null && !padre.exists() && !padre.mkdirs()) {
            throw new PersistenciaException("No se pudo crear la carpeta: " + padre.getPath());
        }
        try (BufferedWriter escritor = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(archivo, false), StandardCharsets.UTF_8))) {
            for (String linea : lineas) {
                escritor.write(linea);
                escritor.newLine();
            }
        } catch (IOException e) {
            throw new PersistenciaException("Error al guardar archivo: " + ruta, e);
        }
    }
}
