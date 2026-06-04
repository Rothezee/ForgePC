package logica.administradores;

import java.io.File;

public final class RutasDatos {

    public static final String CARPETA = "datos";
    public static final String CLIENTES = CARPETA + File.separator + "clientes.txt";
    public static final String EMPLEADOS = CARPETA + File.separator + "empleados.txt";
    public static final String COMPONENTES = CARPETA + File.separator + "componentes.txt";
    public static final String PCS = CARPETA + File.separator + "pcs.txt";

    private RutasDatos() {
    }
}
