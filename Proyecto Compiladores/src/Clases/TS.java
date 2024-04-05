
package Clases;


import java.util.HashSet;

public class TS {
    
    // Definir la tabla de símbolos para las etiquetas permitidas
    private static final HashSet<String> etiquetasPermitidas = new HashSet<>();

    static {
        // Agregar etiquetas permitidas a la tabla de símbolos
        etiquetasPermitidas.add("encabezado");
        etiquetasPermitidas.add("titulo");
        etiquetasPermitidas.add("ciclo");
        etiquetasPermitidas.add("indice");
        etiquetasPermitidas.add("iterador");
        etiquetasPermitidas.add("mensaje");
        etiquetasPermitidas.add("tipo");
        etiquetasPermitidas.add("operacion");
        etiquetasPermitidas.add("signo");
        etiquetasPermitidas.add("var1");
        etiquetasPermitidas.add("var2");
    }

    // Método para validar etiquetas
    public static void validarEtiqueta(String nombreEtiqueta) throws Exception {
        if (!etiquetasPermitidas.contains(nombreEtiqueta)) {
            throw new Exception("Error: Etiqueta no válida: " + nombreEtiqueta);
        }
        // Puedes agregar más validaciones específicas si es necesario
    }
}

