/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package File;

import Windows.RunnableViewer;
import factory.Collector;
import factory.CollectorGUI;
import factory.OperationViewer;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author andy737-1
 */
public class FindFiles extends FileName {
    
    private List<File> resultados;
    private Collector values;
    private OperationViewer op;
    private CollectorGUI gui;
    private RunnableViewer vw;
    private Thread v;
    private OperationViewer opr;
    
    public FindFiles(CollectorGUI gui) {
        this.gui = gui;
        resultados = null;
        values = null;
        opr = new OperationViewer(gui);
        opr.setVisible(true);
    }
    
    public void setValues(Collector values) {
        this.values = values;
    }
    
    public void setConsole(OperationViewer op) {
        this.op = op;
    }
    
    public void ActionPerformed() {
        
        resultados = new ArrayList<>();
        Find(new File(values.getDirectorioRecoleccion()), values.getTipoArchivo());
        opr.setExit(true);
        
    }
    
    private List<File> getRes() {
        return resultados;
        //for (Iterator<File> it = resultados.iterator(); it.hasNext();) {
        //  File archivo = it.next();
        //System.out.println(archivo.getAbsolutePath());
        //}
    }
    
    private void AddRes(File archivo) {
        resultados.add(archivo);
    }
    
    private void Find(File raiz, ArrayList tipo) {
        String ext;
        String tmp;
        try {
            File[] archivos = raiz.listFiles();
            for (int i = 0; i < archivos.length; i++) {
                System.out.println(i + " " + archivos.length);
                File archivo = archivos[i];
                if (archivo.isDirectory() && values.getRecursivo()) {
                    Find(archivo, tipo);
                } else {
                    ext = extension(archivo);
                    if (tipo.contains(ext)) {
                        AddRes(archivo);
                        opr.setText(archivo.toString() + "\n");
                        opr.Append();
                    }
                }
                
            }
            
        } catch (Exception ex) {
            
        }
    }
}
