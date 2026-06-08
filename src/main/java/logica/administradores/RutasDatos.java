package logica.administradores;

import java.io.File;

/** Rutas relativas a los archivos de persistencia del sistema. */
public final class RutasDatos {

    /** Carpeta donde se guardan los .txt de datos. */
    public static final String CARPETA = "datos";
    /** Archivo de clientes. */
    public static final String CLIENTES = CARPETA + File.separator + "clientes.txt";
    /** Archivo de empleados. */
    public static final String EMPLEADOS = CARPETA + File.separator + "empleados.txt";
    /** Archivo de componentes. */
    public static final String COMPONENTES = CARPETA + File.separator + "componentes.txt";
    /** Archivo de PCs armadas. */
    public static final String PCS = CARPETA + File.separator + "pcs.txt";

    private RutasDatos() {
    }
}
