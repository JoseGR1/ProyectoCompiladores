
package Clases;


import java.util.HashSet;

//Ian Garcia
public class TS {
    
    // Definir la tabla de símbolos para las etiquetas permitidas
    private static final HashSet<String> etiquetasPermitidas = new HashSet<>();
    private static final HashSet<String> operadoresPermitidos = new HashSet<>();

    static {
        // Etiquetas permitidas en la tabla de símbolos
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
        
        operadoresPermitidos.add("+");
        operadoresPermitidos.add("-");
        operadoresPermitidos.add("*");
        operadoresPermitidos.add("/");
        operadoresPermitidos.add("<");
        operadoresPermitidos.add(">");
    }

    // Método para validar etiquetas
    public static boolean validarEtiqueta(String Etiqueta) throws Exception {
       return etiquetasPermitidas.contains(Etiqueta);
    }
    //Método para validar operadores
    public static boolean validarOperador(String operador) {
        return operadoresPermitidos.contains(operador);
    }
}

