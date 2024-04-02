/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto.compiladores;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.*;

public class FileDragAndDrop extends JFrame {

    public FileDragAndDrop() {
        super("Arrastrar y Soltar Archivo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crear un área de texto para mostrar la ruta del archivo
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        // Habilitar la funcionalidad de arrastrar y soltar
        DropTarget dropTarget = new DropTarget(textArea, new DropTargetAdapter() {
            @Override
            public void drop(DropTargetDropEvent event) {
                try {
                    Transferable transferable = event.getTransferable();
                    if (transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                        event.acceptDrop(DnDConstants.ACTION_COPY);
                        java.util.List fileList = (java.util.List) transferable.getTransferData(DataFlavor.javaFileListFlavor);
                        String filePath = ((java.io.File) fileList.get(0)).getAbsolutePath();
                        textArea.setText(filePath);
                        event.dropComplete(true);
                    } else {
                        event.rejectDrop();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    event.rejectDrop();
                }
            }
        });

        // Configurar el diseño de la interfaz
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        setSize(400, 300);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FileDragAndDrop frame = new FileDragAndDrop();
            frame.setVisible(true);
        });
    }
}
