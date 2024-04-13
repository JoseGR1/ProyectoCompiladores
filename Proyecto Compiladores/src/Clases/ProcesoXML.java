package Clases;

import java.awt.Desktop;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProcesoXML {
    
    private static int contador = 0;
    
//Jose Gonzalez
    public  String generarCodigoFrontend(String filePath) throws Exception {
        TS ts = new TS();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(filePath));

        Element encabezado = (Element) document.getElementsByTagName("encabezado").item(0);
        String titulo = encabezado.getElementsByTagName("titulo").item(0).getTextContent();

        StringBuilder codigoHTML = new StringBuilder();
        codigoHTML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        codigoHTML.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n");
        codigoHTML.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">\n<head>\n<title>").append(titulo).append("</title>\n<link rel=\"stylesheet\" href=\"styles.css\" type=\"text/css\" />\n</head>\n<body>\n");
        codigoHTML.append("<h1>").append(titulo).append("</h1>\n");
        codigoHTML.append("<div id=\"mensaje-generado\"></div>\n");
        codigoHTML.append("<script type=\"text/javascript\">\n");
        codigoHTML.append("var mensajeGenerado = document.getElementById('mensaje-generado');\n");

        NodeList elementos = encabezado.getChildNodes();
        for (int i = 0; i < elementos.getLength(); i++) {
            Node nodo = elementos.item(i);
            if (nodo instanceof Element) {
                Element elemento = (Element) nodo;
                String tagName = elemento.getTagName();
                if(TS.validarEtiqueta(tagName)){
                    if ("ciclo".equals(tagName) ) {
                        procesarCiclo(elemento, codigoHTML);
                    } else if ("operacion".equals(tagName)) {
                        procesarOperacion(elemento, codigoHTML);
                    } 
                }else {
                    System.out.println("Etiqueta no permitida");
                }
            }
        }

        codigoHTML.append("</script>\n</body>\n</html>");
        return codigoHTML.toString();
    }

//Juan Gonzalez    
    private static void procesarCiclo(Element ciclo, StringBuilder codigoHTML) {
        String indice  = ciclo.getElementsByTagName("indice").item(0).getTextContent();
        String iterador = ciclo.getElementsByTagName("iterador").item(0).getTextContent();
        String mensaje = ciclo.getElementsByTagName("mensaje").item(0).getTextContent();

        try {
            int iteradorInt = Integer.parseInt(iterador);

            // Verificar si el índice y el iterador son válidos
            if (iteradorInt >= 0) {
                codigoHTML.append("for(var ").append(indice).append(" = 0; ").append(indice).append(" < ").append(iterador).append("; ").append(indice).append("++) {\n");
                codigoHTML.append("    mensajeGenerado.innerHTML += '").append(mensaje).append("<br />';\n");
                codigoHTML.append("}\n");
            } else {
                codigoHTML.append("mensajeGenerado.innerHTML += '<br />Iterador no válido: ").append(indice).append(", ").append(iterador).append("<br />';\n");
            }
        } catch (NumberFormatException e) {
            // Manejar el caso de datos no válidos
            codigoHTML.append("mensajeGenerado.innerHTML += '<br />Iterador no son números válidos.<br />';\n");
        }
    }
//Jose Gonzalez
    private static void procesarOperacion(Element operacion, StringBuilder codigoHTML) {
        String titulo = operacion.getElementsByTagName("titulo").item(0).getTextContent();
        String signo = operacion.getElementsByTagName("signo").item(0).getTextContent();
        String var1 = operacion.getElementsByTagName("var1").item(0).getTextContent();
        String var2 = operacion.getElementsByTagName("var2").item(0).getTextContent();

        try {
            int var1Int = Integer.parseInt(var1);
            int var2Int = Integer.parseInt(var2);

            // Verificar si var2 es válido para evitar la división por cero
            if (!signo.equals("/") || var2Int != 0) {
                String resultado = realizarOperacion(signo, var1Int, var2Int);

                codigoHTML.append("mensajeGenerado.innerHTML += '<br /><strong>").append(titulo).append("</strong><br />';\n");
                codigoHTML.append("mensajeGenerado.innerHTML += '").append(var1).append(" ").append(signo).append(" ").append(var2).append(" = ").append(resultado).append("<br />';\n");
            } else {
                codigoHTML.append("mensajeGenerado.innerHTML += '<br />División por cero no permitida.<br />';\n");
            }
        } catch (NumberFormatException e) {
            // Manejar el caso de datos no válidos
            codigoHTML.append("mensajeGenerado.innerHTML += '<br />Uno o ambos operandos no son números válidos.<br />';\n");
        }
    }

//Ian Garcia
    private static String realizarOperacion(String signo, int var1, int var2) {
        TS ts = new TS();
        if (ts.validarOperador(signo)) {
            if ("+".equals(signo)) {
                return Integer.toString(var1 + var2);
            } else if ("-".equals(signo)) {
                return Integer.toString(var1 - var2);
            } else if ("*".equals(signo)){
                return Integer.toString(var1 * var2);
            } else if ("/".equals(signo)){
                if (var2 != 0) {
                    return Integer.toString(var1 / var2);
                } else {
                    return "División por cero no permitida";
                }
            }
        }
        return "Operador no válido";
    }

//Juan Gonzalez    
    public  void guardarCodigoHTML(String codigoHTML) {
        try {          
            if(contador == 1){
                //Actualizar el html
                FileWriter writer = new FileWriter("C:/apache-tomcat-9.0.87/webapps/Proyecto-Compiladores/index.html");
                writer.write(codigoHTML);
                writer.close();
                System.out.println("HTML Actualizado");
            }else{
                FileWriter writer = new FileWriter("C:/apache-tomcat-9.0.87/webapps/Proyecto-Compiladores/index.html");
                writer.write(codigoHTML);
                writer.close();
                System.out.println("HTML Guardado");
                // Abrir una nueva pestaña
                String url = "http://localhost:8080/Proyecto-Compiladores/";
                URI uri = new URI(url);
                Desktop desktop = Desktop.getDesktop();         // Obtenemos el objeto Desktop
                desktop.browse(uri);                // Abrela página web en el navegador predeterminado 
                contador = 1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException ex) {
            Logger.getLogger(ProcesoXML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
}
