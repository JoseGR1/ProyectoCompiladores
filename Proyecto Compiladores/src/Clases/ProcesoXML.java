package Clases;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;

public class ProcesoXML {

    public  String generarCodigoFrontend(String filePath) throws Exception {
        
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
                if ("ciclo".equals(tagName)) {
                    procesarCiclo(elemento, codigoHTML);
                } else if ("operacion".equals(tagName)) {
                    procesarOperacion(elemento, codigoHTML);
                }
            }
        }

        codigoHTML.append("</script>\n</body>\n</html>");
        return codigoHTML.toString();
    }

    private static void procesarCiclo(Element ciclo, StringBuilder codigoHTML) {
        String indice = ciclo.getElementsByTagName("indice").item(0).getTextContent();
        String iterador = ciclo.getElementsByTagName("iterador").item(0).getTextContent();
        String mensaje = ciclo.getElementsByTagName("mensaje").item(0).getTextContent();

        codigoHTML.append("for(var ").append(indice).append(" = 0; ").append(indice).append(" < ").append(iterador).append("; ").append(indice).append("++) {\n");
        codigoHTML.append("    mensajeGenerado.innerHTML += '").append(mensaje).append("<br />';\n");
        codigoHTML.append("}\n");
    }

    private static void procesarOperacion(Element operacion, StringBuilder codigoHTML) {
        String titulo = operacion.getElementsByTagName("titulo").item(0).getTextContent();
        String signo = operacion.getElementsByTagName("signo").item(0).getTextContent();
        String var1 = operacion.getElementsByTagName("var1").item(0).getTextContent();
        String var2 = operacion.getElementsByTagName("var2").item(0).getTextContent();
        String resultado = realizarOperacion(signo, Integer.parseInt(var1), Integer.parseInt(var2));

        codigoHTML.append("mensajeGenerado.innerHTML += '<br /><strong>").append(titulo).append("</strong><br />';\n");
        codigoHTML.append("mensajeGenerado.innerHTML += '").append(var1).append(" ").append(signo).append(" ").append(var2).append(" = ").append(resultado).append("<br />';\n");
    }

    private static String realizarOperacion(String signo, int var1, int var2) {
        if ("+".equals(signo)) {
            return Integer.toString(var1 + var2);
        } else if ("-".equals(signo)) {
            return Integer.toString(var1 - var2);
        } else if ("*".equals(signo)){
            return Integer.toString(var1 * var2);
        } else if ("/".equals(signo)){
            return Integer.toString(var1 / var2);
        }
        return "";
    }
    
    public  void guardarCodigoHTML(String codigoHTML) {
        try {
            FileWriter writer = new FileWriter("navegador.html");
            writer.write(codigoHTML);
            writer.close();
            System.out.println("Archivo HTML guardado correctamente.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
